package edu.villanova.siquest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.htmlparser.util.ParserException;
import org.pdfbox.util.PDFTextStripper;
import org.pdfbox.pdmodel.PDDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hslf.extractor.PowerPointExtractor;
import org.htmlparser.parserapplications.StringExtractor;
import org.apache.log4j.Logger;

public class SqTextExtractor 
{
  static Logger logger = Logger.getLogger(SqTextExtractor.class);

  private static boolean INCLUDE_HYPERLINKS = true;

  protected SqTextExtractor()
  {
  }
  
  public static String extractText(File fileToParse) throws IOException
  {
    String contents = null;
       
    if(fileToParse.exists() && fileToParse.canRead())
    {
      if(fileToParse.getName().toLowerCase().endsWith(".txt"))
      {
        contents = new String(parseText(fileToParse));
      }
      else if(fileToParse.getName().toLowerCase().endsWith(".html") || fileToParse.getName().toLowerCase().endsWith(".htm"))
      {
        contents = new String(parseHTML(fileToParse));
      }
      else if(fileToParse.getName().toLowerCase().endsWith(".xml"))
      {
        contents = new String(parseXML(fileToParse));
      }
      else if(fileToParse.getName().toLowerCase().endsWith(".pdf"))
      { 
        contents = new String(parsePDF(fileToParse));
      }
      else if(fileToParse.getName().toLowerCase().endsWith(".doc"))
      {
        WordExtractor docExtractor = new WordExtractor(new FileInputStream(fileToParse));
        contents = new String(docExtractor.getText());
      }
      else if(fileToParse.getName().toLowerCase().endsWith(".docx"))
      {
        contents = new String(parseDocx(fileToParse));
      }
      else if(fileToParse.getName().toLowerCase().endsWith(".ppt"))
      {
        PowerPointExtractor pptExtractor = new PowerPointExtractor(new FileInputStream(fileToParse));
        contents = new String(pptExtractor.getText());
      }      
      else if(fileToParse.getName().toLowerCase().endsWith(".xls"))
      {
        ExcelExtractor xlsExtractor = new ExcelExtractor(new HSSFWorkbook(new FileInputStream(fileToParse)));
        contents = new String(xlsExtractor.getText());
      }
    }
   
    return contents;
  }

  private static String parseText(File fileToParse) throws IOException
  {
    StringBuilder contents = null;
    BufferedReader reader = null;

    try
    {
      contents = new StringBuilder();
      reader = new BufferedReader(new FileReader(fileToParse));

      String line = reader.readLine();

      while(line != null)
      {
        contents.append(line);
        contents.append(" ");

        line = reader.readLine();
      }

      if(contents.length() > 0)
      {
        contents.deleteCharAt(contents.length() - 1);
      }
    }
    catch(IOException e)
    {
      logger.error("Error parsing text.", e) ;
    }
    finally
    {
      reader.close();
    }

    return contents == null ? null :contents.toString();
  }

  private static String parseXML(File fileToParse) throws IOException
  {
    String contents = null;

    try
    {
      contents = parseXMLInputStream(new FileInputStream(fileToParse));
    }
    catch(IOException e)
    {
      logger.error("Error parsing XML.", e) ;
    }

    return contents;
  }

  private static String parseXMLInputStream(InputStream stream)
  {
    StringBuilder contents = null;

    XMLInputFactory factory = XMLInputFactory.newInstance();
    XMLStreamReader xmlParser = null;

    try
    {
      xmlParser = factory.createXMLStreamReader(stream);

      contents = new StringBuilder();
      int event = xmlParser.next();

      while(event != XMLStreamConstants.END_DOCUMENT)
      {
        if(event == XMLStreamConstants.CHARACTERS)
        {
          contents.append(xmlParser.getText());
          contents.append("");
        }

        event = xmlParser.next();
      }

      if(contents.length() > 0)
      {
        contents.deleteCharAt(contents.length() - 1);
      }
    }
    catch(XMLStreamException e)
    {
      logger.error("Error parsing XML.", e) ;
    }
    finally
    {
      try
      {
        if(xmlParser != null)
        {
          xmlParser.close();
        }
      }
      catch(XMLStreamException e)
      {
      }
    }

    return contents == null ? null :contents.toString();
  }

  private static String parsePDF(File fileToParse) throws IOException
  {
    String contents = null;
    PDDocument pdfDocument = null;

    try
    {
      pdfDocument = PDDocument.load(fileToParse);

      PDFTextStripper textStripper = new PDFTextStripper();
      contents = new String(textStripper.getText(pdfDocument));
    }
    catch(IOException e)
    {
      logger.error("Error parsing PDF.", e) ;
    }
    finally
    {
      if(pdfDocument != null)
      {
        pdfDocument.close();
      }
    }

    return contents;
  }

  private static String parseDocx(File fileToParse) throws IOException
  {
    String contents = null;
    ZipFile zip = null;

    try
    {
      zip = new ZipFile(fileToParse);
      Enumeration entries = zip.entries();

      while(entries.hasMoreElements())
      {
        ZipEntry entry = (ZipEntry)entries.nextElement();

        if(entry.getName().toLowerCase().equals("word/document.xml"))
        {
          contents = parseXMLInputStream(zip.getInputStream(entry));
        }
      }

      zip.close();
    }
    catch(IOException e)
    {
      logger.error("Error parsing zip.", e) ;
    }
    finally
    {
      zip.close();
    }

    return contents;
  }

  private static String parseHTML(File fileToParse) throws IOException
  {
    String contents = null;

    try
    {
      StringExtractor extractor = new StringExtractor(fileToParse.getPath());
      contents = extractor.extractStrings(INCLUDE_HYPERLINKS);
    }
    catch(ParserException e)
    {
      logger.error("Error parsing HTML.", e) ;
    }

    return contents;
  }
}