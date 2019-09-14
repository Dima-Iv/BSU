package handler;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import collector.Collector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class Handler {
    public void writeXmlFile(Vector<Collector> vector, File file) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        Element rootElement = document.createElement("collectors");
        //rootElement.setAttribute("xmlns","http://www.example.com/collector");
        document.appendChild(rootElement);

        int id = 0;

        for (Collector collector : vector) {
            Element productElement = document.createElement("collector");
            rootElement.appendChild(productElement);

            productElement.setAttribute("id", Integer.toString(collector.getId()));

            Element brand = document.createElement("brand");
            brand.appendChild(document.createTextNode(collector.getBrand()));
            productElement.appendChild(brand);

            Element country = document.createElement("country");
            country.appendChild(document.createTextNode(collector.getCountry()));
            productElement.appendChild(country);

            Element price = document.createElement("price");
            price.appendChild(document.createTextNode(Integer.toString(collector.getPrice())));
            productElement.appendChild(price);

            id++;
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(file);
        transformer.transform(source, result);
    }

    public Vector<Collector> readXmlFile(String fileName) throws ParserConfigurationException, IOException, SAXException {
        Vector<Collector> vector = new Vector<>();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(fileName);

        document.getDocumentElement().normalize();

        NodeList nList = document.getElementsByTagName("collector");

        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                vector.add(new Collector(Integer.parseInt(eElement.getAttribute("id")), eElement.getElementsByTagName("brand").item(0).getTextContent(), eElement.getElementsByTagName("country").item(0).getTextContent(), Integer.parseInt(eElement.getElementsByTagName("price").item(0).getTextContent())));
            }
        }
        return vector;
    }
}
