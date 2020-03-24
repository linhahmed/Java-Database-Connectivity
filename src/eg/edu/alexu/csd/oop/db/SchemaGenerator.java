package eg.edu.alexu.csd.oop.db;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.sql.SQLException;

 class SchemaGenerator {

    private static File schemaFile;
    private static SchemaGenerator instance = new SchemaGenerator();

     static SchemaGenerator getInstance(File schema) {
        schemaFile = schema;
        return instance;
    }

    private SchemaGenerator() {

    }

    File createSchema(DataCarrier carrier) throws SQLException {

        try {
            final String NS_PREFIX = "xs:";
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element schemaRoot = doc.createElementNS(XMLConstants.W3C_XML_SCHEMA_NS_URI, NS_PREFIX + "schema");
            doc.appendChild(schemaRoot);
            NameTypeElementMaker elMaker = new NameTypeElementMaker(NS_PREFIX, doc);
            Element tableElement = elMaker.createElement("element", "table");
            Element tableType = elMaker.createElement("complexType");
            Element tableSequence = elMaker.createElement("sequence");
            Element rowElement = elMaker.createElement("element", "row");
            Element rowType = elMaker.createElement("complexType");
            Element rowSequence = elMaker.createElement("sequence");
            Element tableName = elMaker.createElement("attribute", "name", "string");
            schemaRoot.appendChild(tableElement);
            tableElement.appendChild(tableType);
            tableType.appendChild(tableSequence);
            tableType.appendChild(tableName);
            tableSequence.appendChild(rowElement);
            rowElement.setAttribute("maxOccurs", "unbounded");
            rowElement.setAttribute("minOccurs", "0");
            tableName.setAttribute("use", "required");
            rowElement.appendChild(rowType);
            rowType.appendChild(rowSequence);
            for (int i = 0; i < carrier.columns.length; i++) {
                Element element = elMaker.createElement("attribute", carrier.columns[i], carrier.columnsTypes[i]);
                element.setAttribute("use", "required");
                rowType.appendChild(element);
            }
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource domSource = new DOMSource(doc);
            transformer.transform(domSource, new StreamResult(schemaFile));

        } catch (FactoryConfigurationError | ParserConfigurationException | TransformerException e) {
            throw new SQLException("Error generating schema file");
        }
        return schemaFile;

    }

    private static class NameTypeElementMaker {
        private String nsPrefix;
        private Document doc;

        private NameTypeElementMaker(String nsPrefix, Document doc) {
            this.nsPrefix = nsPrefix;
            this.doc = doc;
        }

        private Element createElement(String elementName, String nameAttrVal, String typeAttrVal) {
            Element element = doc.createElementNS(XMLConstants.W3C_XML_SCHEMA_NS_URI, nsPrefix + elementName);
            if (nameAttrVal != null)
                element.setAttribute("name", nameAttrVal);
            if (typeAttrVal != null)
                element.setAttribute("type", nsPrefix + typeAttrVal);
            return element;
        }

        private Element createElement(String elementName, String nameAttrVal) {
            return createElement(elementName, nameAttrVal, null);
        }

        private Element createElement(String elementName) {
            return createElement(elementName, null, null);
        }


    }
}