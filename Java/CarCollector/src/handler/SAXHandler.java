package handler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class SAXHandler extends DefaultHandler {

    boolean priceBool = false;
    int max;
    int total;

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("price")) {
            priceBool = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        if (priceBool) {
            String priceStr = new String(ch, start, length);
            Integer price = Integer.parseInt(priceStr);
            total += price;
            if(max < price) {
                max = price;
            }
            priceBool = false;
        }
    }
}