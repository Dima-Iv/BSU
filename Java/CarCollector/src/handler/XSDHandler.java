package handler;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;

public class XSDHandler {
    public static boolean validateXMLByXSD(File xml, StreamSource xsd) throws SAXException, IOException {
        try {
            SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(xsd).newValidator().validate(new StreamSource(xml));
        } catch (Exception e) {
            throw e;
        }
        return true;
    }
}

