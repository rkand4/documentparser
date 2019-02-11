package com.rocky.document;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.ocr.TesseractOCRConfig;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.parser.pdf.PDFParserConfig;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.ExpandedTitleContentHandler;
import org.xml.sax.SAXException;

/**
 * Created by Rakesh on 2/9/19.
 */
public class PdfParser {


  public static void main(String[] args)
      throws IOException, TikaException, SAXException, TransformerConfigurationException {
    System.out.println("Java Version: "+System.getProperty("java.version"));
      Tika tika = new Tika();
      /* Approach 1
    try {
      File file = new File("/Users/Rakesh/Downloads/Rakesh/23478.pdf");
      String content = new Tika().parseToString(file);
      System.out.println("The Content: " + content);
    } catch (final Exception e) {
      e.printStackTrace();
    }  */


      /** Approach 2
      BodyContentHandler handler = new BodyContentHandler();
    Metadata metadata = new Metadata();
    FileInputStream inputstream = new FileInputStream(new File("/Users/Rakesh/Downloads/Rakesh/23478.pdf"));
    ParseContext pcontext = new ParseContext();

    //parsing the document using PDF parser
    PDFParser pdfparser = new PDFParser();
    pdfparser.parse(inputstream, handler, metadata,pcontext);

    //getting the content of the document
    System.out.println("Contents of the PDF :" + handler.toString());

    //getting metadata of the document
    System.out.println("Metadata of the PDF:");
    String[] metadataNames = metadata.names();

    for(String name : metadataNames) {
      System.out.println(name+ " : " + metadata.get(name));
    }  **/

    File inputFile = new File("/Users/Rakesh/Downloads/Rakesh/23478.pdf");
    AutoDetectParser parser = new AutoDetectParser();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    SAXTransformerFactory factory = (SAXTransformerFactory)
        SAXTransformerFactory.newInstance();
    TransformerHandler handler = factory.newTransformerHandler();
    handler.getTransformer().setOutputProperty(OutputKeys.METHOD, "html");
    handler.getTransformer().setOutputProperty(OutputKeys.INDENT, "yes");
    handler.getTransformer().setOutputProperty(OutputKeys.ENCODING, "UTF-8");
    handler.setResult(new StreamResult(out));
    ExpandedTitleContentHandler handler1 = new ExpandedTitleContentHandler(handler);

    PDFParserConfig pdfConfig = new PDFParserConfig();
    pdfConfig.setExtractInlineImages(true);
    pdfConfig.setExtractUniqueInlineImagesOnly(true);
    pdfConfig.setSortByPosition(true);


    ParseContext parseContext = new ParseContext();
    parseContext.set(PDFParserConfig.class, pdfConfig);
    parseContext.set(Parser.class, parser);

    parser.parse(new ByteArrayInputStream(Files.readAllBytes(Paths.get(inputFile.toURI()))), handler1, new Metadata(),parseContext);
    System.out.println("HTML "+new String(out.toByteArray(), "UTF-8")); ;

  }

}
