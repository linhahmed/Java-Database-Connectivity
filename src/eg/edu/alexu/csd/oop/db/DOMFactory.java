package eg.edu.alexu.csd.oop.db;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

 class DOMFactory {
     static Document getDomObj(File inputFile)throws SQLException {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            return doc;

        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new SQLException("Error loading file");
        }
    }

    static void writeDOMtoFile(Document doc, File outputFile) throws SQLException {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(outputFile);
            transformer.transform(source, result);
        } catch (TransformerException e) {
            throw new SQLException("Error writing to data file");
        }

    }

    static boolean validateXML(Document doc, File schemaFile) {
        Schema schema = null;
        try {
            String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
            SchemaFactory factory = SchemaFactory.newInstance(language);
            schema = factory.newSchema(schemaFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (schema != null) {
                Validator validator = schema.newValidator();
                validator.validate(new DOMSource(doc));

            }else {
                throw new SQLException("Error loading schema file");
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
