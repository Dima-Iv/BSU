package handler;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class Transform {
    public boolean transformToHtml(StreamSource streamSource, String startFileName, String finishFileName) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        //установка используемого XSL-преобразования
        Transformer transformer = transformerFactory.newTransformer(streamSource);
        //установка исходного XML-документа и конечного XML-файла
        transformer.transform(new StreamSource(startFileName), new StreamResult(finishFileName));
        return true;
    }

    public boolean transformToTxt(StreamSource streamSource,String startFileName,String finishFileName) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer(streamSource);
        transformer.transform(new StreamSource(startFileName), new StreamResult(finishFileName));
        return true;
    }
}
