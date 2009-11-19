package edu.villanova.siquest;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.analysis.Analyzer;

public class SqIndexer 
{
  static Logger logger = Logger.getLogger(SqIndexer.class);
	
  private static final boolean CREATE_INDEX = true;
  private static final boolean APPEND_TO_INDEX = false;
    
  private String indexDir;
  private Analyzer analyzer;

  public SqIndexer(String indexDir, String analyzerName)
  {       
    this.indexDir = indexDir;
    this.analyzer = SqAnalyzerFactory.createAnalyzer(analyzerName, null);
  }
 
  public SqIndexer(String indexDir, String analyzerName, String stopWords)
  {       
    this.indexDir = indexDir;
    this.analyzer = SqAnalyzerFactory.createAnalyzer(analyzerName, stopWords);
  }
 
  /* Get the index directory */
  public String getIndexDir()
  {
    return indexDir;
  }
  
  public void setIndexDir(String indexDir)
  {
    this.indexDir = indexDir;
  }
  
  public void setAnalzyer(String analyzerName, String stopWords)
  {
    analyzer = SqAnalyzerFactory.createAnalyzer(analyzerName, stopWords);
  }
  
  public String getAnalzyer()
  {
    return analyzer.getClass().getName();
  }
  
  /* Delete the index */
  public boolean deleteIndex()
  {
    boolean deleted = false;
    File dir = new File(indexDir);
   
    if(dir.isDirectory())
    {
      if(indexExist())
      {
        for(File file : dir.listFiles())
        {
          file.delete();
        }
        
        deleted = dir.delete();
      }
    }
    
    return deleted;
  }
  
  /* */
  public boolean indexExist()
  {      
    return IndexReader.indexExists(indexDir);
  }
  
  /* Creates an empty index.  This method should be used before any 
     indexing is done. 
  */
  public void createIndex() throws IOException
  {
    IndexWriter writer = null;
    
    try
    {
      File dir = new File(indexDir);
      
      if(!dir.exists())
      {
        dir.mkdir();
      }
      
      writer = new IndexWriter(dir, analyzer, CREATE_INDEX);
      logger.debug("Index created at " + indexDir + " using analyzer " + analyzer);
    }
    catch(IOException e)
    {
      throw e;
    }
    finally
    {
      if(writer != null)
      {
        writer.close();
      }
    }
  }

  /**
   * Reuses existing index. Creates one if it doesn't exist
   */
  public void reuseIndex() throws IOException
  {
	  if(indexExist())
      {
		  // nothing to do
		  logger.debug("Index is set to being reused");
      }
	  else
	  {
		  logger.debug("Index does not exist. Will create one...");
		  createIndex();
	  }
  }
  
  /** recursively index files or directories */
  public void indexFileOrDirectory(File file) throws IOException
  {
    // do not try to index files that cannot be read
	if (file.canRead())
    {
	  if(file.isDirectory())
      {
	    String[] files = file.list();
	    // an IO error could occur
	    if(files != null)
        {
	      for (int i = 0; i < files.length; i++)
          {
		    indexFileOrDirectory(new File(file, files[i]));
		  }
		}
	  }
      else
      {
	    try
	    {
	      indexDocument(file.getPath());
	    }
	    catch(Exception e)
	    {
	      logger.error(e.getMessage());
	    }
	  }
    }
  }

  /* Recursively indexes a directory */
  public void indexDirectory(String dirToIndex) //throws IOException
  {    
    File directory = new File(dirToIndex);
    File[] directoryListing = directory.listFiles(new SqFileFilter());
    
    for(int i = 0; i < directoryListing.length; i++)
    {
      File file = directoryListing[i];
     
      if(file.isFile())
      {
        try
        {
          indexDocument(file.getPath());
        }
        catch(Exception e)
        {
          logger.error(e.getMessage());
        }
      }
      else
      {
        if(!file.isHidden())
        {
          indexDirectory(file.getPath());
        }    
      }
    }
  }
  
  /* Add a document to the index */
  public void indexDocument(String pathname) throws IOException
  {
    IndexWriter writer = null;
      
    try
    {
      writer = new IndexWriter(indexDir, analyzer, APPEND_TO_INDEX);
        
      File fileToIndex = new File(pathname);
    
      try
      {
        Document luceneDocument = SqDocumentFactory.createDocument(fileToIndex);
            
        if(luceneDocument != null)
        {
          writer.addDocument(luceneDocument);
          logger.debug("Added " + pathname);
        }
      }
      catch(IOException e)
      {
        logger.error("Error indexing " + fileToIndex.getPath() + ": " + e.getMessage());
      }
    }
    catch(IOException e)
    {
      throw e;
    }
    finally
    {
      if(writer != null)
      {
        writer.close();
      }
    }
  }
  
  /* Optimizes the index.  This method should be used after 
     indexing a directory 
   */
  public void optimizeIndex() throws IOException
  {
    IndexWriter writer = null;
      
    try
    {
      if(indexExist())
      {
        writer = new IndexWriter(indexDir, analyzer, APPEND_TO_INDEX);
        writer.optimize();
        logger.debug("Index is optimized");
      }
    }
    catch(IOException e)
    {
      throw e;
    }
    finally
    {
      if(writer != null)
      {
        writer.close();
      }
    }
  }

  public void deleteDocument(int docId) throws IOException
  {
    IndexReader reader = null;

    try
    {
      reader = IndexReader.open(indexDir);
      reader.deleteDocument(docId);
    }
    catch(IOException e)
    {
      throw e;
    }
    finally
    {
      if(reader != null)
      {
        reader.close();
      }
    }
  }
}
