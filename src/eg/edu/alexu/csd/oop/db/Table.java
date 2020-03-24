package eg.edu.alexu.csd.oop.db;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

 class Table {
    private File dataFile;
    private File schemaFile;

    Table(File databasePath, DataCarrier carrier) throws SQLException {
        dataFile = new File(databasePath, carrier.tableName + ".xml");
        schemaFile = new File(databasePath, carrier.tableName + ".xsd");
        try {
            if (dataFile.createNewFile() && schemaFile.createNewFile()) {
                initializeXML(carrier);
                schemaFile = SchemaGenerator.getInstance(schemaFile).createSchema(carrier);
            }

        } catch (IOException e) {
            System.out.println("Error loading old databases.");
        }

    }

    int insertSome(DataCarrier carrier) throws SQLException {
        Document doc = DOMFactory.getDomObj(dataFile);
        if (doc != null) {
            checkDataFile(doc, "Bad data File !");
            if (!isValidColumns(carrier)) {
                throw new SQLException("Bad columns names !");
            }
            Element newRow = doc.createElement("row");
            for (int i = 0; i < carrier.columns.length; i++) {
                newRow.setAttribute(carrier.columns[i], carrier.values[i]);
            }
            doc.getDocumentElement().appendChild(newRow);
            checkDataFile(doc, "Bad data entered !");
            DOMFactory.writeDOMtoFile(doc, dataFile);
            return 1;
        } else {
            throw new SQLException("Error loading data file !");
        }
    }

     int insertAll(DataCarrier carrier) throws SQLException {
        carrier.columns = columnsNames();
        return insertSome(carrier);
    }

    int updateWhere(DataCarrier carrier) throws SQLException {
        int counter = 0;
        Document doc = DOMFactory.getDomObj(dataFile);
        if (doc != null) {
            checkDataFile(doc, "Bad data File !");
            if (!isValidColumns(carrier)) {
                throw new SQLException("Bad columns names !");
            }
            NodeList rows = doc.getElementsByTagName("row");
            for (int i = 0; i < rows.getLength(); i++) {
                if (where(carrier, rows.item(i))) {
                    for (int j = 0; j < carrier.columns.length; j++) {
                        rows.item(i).getAttributes().getNamedItem(carrier.columns[j]).setNodeValue(carrier.values[j]);
                    }
                    counter++;
                }
            }
            checkDataFile(doc, "Bad data entered !");
            DOMFactory.writeDOMtoFile(doc, dataFile);
        } else {
            throw new SQLException("Error loading data file !");
        }
        return counter;
    }

    int update(DataCarrier carrier) throws SQLException {
        int counter = 0;
        Document doc = DOMFactory.getDomObj(dataFile);
        if (doc != null) {
            checkDataFile(doc, "Bad data File !");
            if (!isValidColumns(carrier)) {
                throw new SQLException("Bad columns names !");
            }
            NodeList rows = doc.getElementsByTagName("row");
            for (int i = 0; i < rows.getLength(); i++) {
                for (int j = 0; j < carrier.columns.length; j++) {
                    rows.item(i).getAttributes().getNamedItem(carrier.columns[j]).setNodeValue(carrier.values[j]);
                }
                counter++;
            }
            checkDataFile(doc, "Bad data entered !");
            DOMFactory.writeDOMtoFile(doc, dataFile);
        } else {
            throw new SQLException("Error loading data file !");
        }
        return counter;
    }

    String getName() {
        return dataFile.getName().substring(0, dataFile.getName().length() - 4);
    }

    private void initializeXML(DataCarrier carrier) throws SQLException {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            Element rootElement = doc.createElement("table");
            rootElement.setAttribute("name", carrier.tableName);
            doc.appendChild(rootElement);
            DOMFactory.writeDOMtoFile(doc, dataFile);
        } catch (ParserConfigurationException e) {
            throw new SQLException("Error loading data file !");
        }

    }

    int deleteAll() throws SQLException {
        Document doc = DOMFactory.getDomObj(dataFile);
        if (doc != null) {
            checkDataFile(doc, "Bad data File !");
            NodeList rows = doc.getElementsByTagName("row");
            int size = rows.getLength();
            while (rows.getLength()>0){
                doc.getDocumentElement().removeChild(rows.item(0));
            }
            DOMFactory.writeDOMtoFile(doc, dataFile);
            return size;
        } else {
            throw new SQLException("Error loading data file !");
        }
    }

    int deleteWhere(DataCarrier carrier) throws SQLException {
        int counter = 0;
        Document doc = DOMFactory.getDomObj(dataFile);
        if (doc != null) {
            checkDataFile(doc, "Bad data File !");
            NodeList rows = doc.getElementsByTagName("row");
            for (int i = 0; i < rows.getLength(); i++) {
                if (where(carrier, rows.item(i))) {
                    doc.getDocumentElement().removeChild(rows.item(i));
                    i--;
                    counter++;
                }
            }
            DOMFactory.writeDOMtoFile(doc, dataFile);
            return counter;
        } else {
            throw new SQLException("Error loading data file !");
        }
    }

    Object[][] selectAll() throws SQLException {
        DataCarrier carrier = new DataCarrier();
        carrier.columns = columnsNames();
        return selectSome(carrier);
    }


    Object[][] selectSome(DataCarrier carrier) throws SQLException {

        Document doc = DOMFactory.getDomObj(dataFile);
        if (doc != null) {
            checkDataFile(doc, "Bad data File !");
            if (!isValidColumns(carrier)) {
                throw new SQLException("Bad columns names !");
            }
            NodeList rows = doc.getElementsByTagName("row");
            Object[][] table = new Object[rows.getLength()][carrier.columns.length];
            for (int i = 0; i < carrier.columns.length; i++) {
                for (int j = 0; j < rows.getLength(); j++) {
                    selectRow(carrier, rows, table, i, j);
                }
            }
            return table;
        } else {
            throw new SQLException("Error loading data file !");
        }

    }

    Object[][] selectSomeWhere(DataCarrier carrier) throws SQLException {
        Document doc = DOMFactory.getDomObj(dataFile);
        if (doc != null) {
            checkDataFile(doc, "Bad data File !");
            if (!isValidColumns(carrier)) {
                throw new SQLException("Bad columns names !");
            }
            NodeList rows = doc.getElementsByTagName("row");
            Object[][] table = new Object[rows.getLength()][carrier.columns.length];
            for (int i = 0; i < carrier.columns.length; i++) {
                for (int j = 0; j < rows.getLength(); j++) {
                    if (where(carrier, rows.item(j))) {
                        selectRow(carrier, rows, table, i, j);
                    }
                }
            }
            ArrayList<Object[]> toBeReturn=new ArrayList<>();
            for (Object[] objects : table) {
                boolean flag = false;
                for (Object object : objects) {
                    if (object != null) {
                        flag = true;
                    }
                }
                if (flag) {
                    toBeReturn.add(objects);
                }
            }
            Object[][] filtered = new Object[toBeReturn.size()][carrier.columns.length];
            return toBeReturn.toArray(filtered);
        } else {
            throw new SQLException("Error loading data file !");
        }
    }

    private void selectRow(DataCarrier carrier, NodeList rows, Object[][] table, int i, int j) throws SQLException {
        if (isColumnInteger(carrier.columns[i])) {
            table[j][i] = Integer.parseInt(rows.item(j).getAttributes().getNamedItem(carrier.columns[i]).getNodeValue());
        } else {
            table[j][i] = rows.item(j).getAttributes().getNamedItem(carrier.columns[i]).getNodeValue();
        }
    }


    Object[][] selectAllWhere(DataCarrier carrier) throws SQLException {
        carrier.columns = columnsNames();
        return selectSomeWhere(carrier);
    }

    private boolean where(DataCarrier carrier, Node row) throws SQLException {
        if (!isColumnInteger(carrier.conditionColumn) || !isInt(carrier.conditionValue)) {
            switch (carrier.conditionOperator) {
                case '=':
                    return row.getAttributes().getNamedItem(carrier.conditionColumn).getNodeValue().compareTo(carrier.conditionValue) == 0;
                case '>':
                    return row.getAttributes().getNamedItem(carrier.conditionColumn).getNodeValue().compareTo(carrier.conditionValue) > 0;
                case '<':
                    return row.getAttributes().getNamedItem(carrier.conditionColumn).getNodeValue().compareTo(carrier.conditionValue) < 0;
            }
        } else {
            switch (carrier.conditionOperator) {
                case '=':
                    return Integer.parseInt(row.getAttributes().getNamedItem(carrier.conditionColumn).getNodeValue()) == Integer.parseInt(carrier.conditionValue);
                case '>':
                    return Integer.parseInt(row.getAttributes().getNamedItem(carrier.conditionColumn).getNodeValue()) > Integer.parseInt(carrier.conditionValue);
                case '<':
                    return Integer.parseInt(row.getAttributes().getNamedItem(carrier.conditionColumn).getNodeValue()) < Integer.parseInt(carrier.conditionValue);
            }
        }
        return true;
    }

    private boolean columnExists(String columnName) throws SQLException {
        Document doc = DOMFactory.getDomObj(schemaFile);
        if (doc != null) {
            NodeList columns = doc.getElementsByTagName("xs:attribute");
            for (int i = 0; i < columns.getLength(); i++) {
                if (columns.item(i).getAttributes().getNamedItem("name").getNodeValue().toLowerCase().equals(columnName)) {
                    return true;
                }
            }
            return false;
        } else {
            throw new SQLException("Error loading data file !");
        }

    }

    private boolean isColumnInteger(String attrName) throws SQLException {
        Document doc = DOMFactory.getDomObj(schemaFile);
        if (doc != null) {
            NodeList columns = doc.getElementsByTagName("xs:attribute");
            for (int i = 0; i < columns.getLength(); i++) {
                if (columns.item(i).getAttributes().getNamedItem("name").getNodeValue().equals(attrName)) {
                    return columns.item(i).getAttributes().getNamedItem("type").getNodeValue().equals("xs:int");
                }
            }
            return false;
        } else {
            throw new SQLException("Error loading schema file");
        }

    }

    private boolean isInt(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private void checkDataFile(Document doc, String error) throws SQLException {
        if (!DOMFactory.validateXML(doc, schemaFile)) {
            throw new SQLException(error);
        }
    }

    boolean deleteTable() {
        return dataFile.delete() && schemaFile.delete();
    }

    private boolean isValidColumns(DataCarrier carrier) throws SQLException {
        for (String str : carrier.columns
        ) {
            if (!columnExists(str.toLowerCase())) {
                return false;
            }
        }
        return true;
    }

    private String[] columnsNames() throws SQLException {
        Document doc = DOMFactory.getDomObj(schemaFile);
        if (doc != null) {
            NodeList columns = doc.getElementsByTagName("xs:attribute");
            String[] toBeReturn = new String[columns.getLength() - 1];
            for (int i = 0; i < columns.getLength() - 1; i++) {
                toBeReturn[i] = columns.item(i).getAttributes().getNamedItem("name").getNodeValue().toLowerCase();
            }
            return toBeReturn;

        } else {
            throw new SQLException("Error loading data file !");
        }

    }

}