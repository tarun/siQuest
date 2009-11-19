package edu.villanova.siquest;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

public class SqDocumentFactory 
{
  public static Document createDocument(File fileToIndex) throws IOException
  {
    Document luceneDoc = null;
    
    String text = SqTextExtractor.extractText(fileToIndex);
    
    if(text != null)
    {
      luceneDoc = new Document();

      luceneDoc.add(new Field(SqFields.PATH_FIELD, fileToIndex.getPath().toLowerCase(), Field.Store.YES, Field.Index.UN_TOKENIZED));
      luceneDoc.add(new Field(SqFields.LAST_MODIFIED, DateTools.timeToString(fileToIndex.lastModified(), DateTools.Resolution.MINUTE), Field.Store.YES, Field.Index.UN_TOKENIZED)); 
      luceneDoc.add(new Field(SqFields.CONTENT_FIELD, text, Field.Store.YES, Field.Index.TOKENIZED, Field.TermVector.WITH_POSITIONS_OFFSETS));
    }
    return luceneDoc;
  }
}
