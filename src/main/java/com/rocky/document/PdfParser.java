package com.rocky.document;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

/**
 * Created by Rakesh on 2/9/19.
 */
public class PdfParser {


  public static void main(String[] args) throws IOException {
    System.out.println("Java Version: "+System.getProperty("java.version"));
      Tika tika = new Tika();
    try {
      File file = new File("/Users/Rakesh/Downloads/Rakesh/23478.pdf");
      String content = new Tika().parseToString(file);
      System.out.println("The Content: " + content);
    } catch (final Exception e) {
      e.printStackTrace();
    }

  }

}
