package edu.villanova.siquest;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Collections;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.TermDocs;
import org.apache.lucene.index.TermEnum;
import org.apache.lucene.index.TermFreqVector;
import org.apache.lucene.index.TermPositionVector;
import org.apache.lucene.index.TermVectorOffsetInfo;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Similarity;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.TermQuery;

public class SqInfoExtractor 
{
  static Logger logger = Logger.getLogger(SqInfoExtractor.class);
	
  /* Constants */
  private static final int DEFAULT_QUEUE_SIZE = 11;
  
  /* Intance variables */
  private String indexDir;

  public SqInfoExtractor(String pathname, String analyzerName)
  {    
    indexDir = pathname;
  }
  
  public SqInfoExtractor(String pathname, String analyzerName, String stopWords)
  {    
    indexDir = pathname;
  }
  
  /* Return the index directory */
  public String getIndexDir()
  {
    return indexDir;
  }
  
  /* Get the number of documents in the index */
  public int getNumIndexDocs() throws IOException
  {
    int numDocs = 0;
    IndexReader reader = null;

    try 
    {
      reader = IndexReader.open(indexDir);
      numDocs = reader.numDocs();
    } 
    catch(IOException e)
    {
      throw e;
    } 
    finally 
    {
      if (reader != null) 
      {
        reader.close();
      }
    }
    
    return numDocs;
  }
  
  /* Get all the document fields in index */
  public ArrayList<String> getIndexedFields() throws IOException
  {
    ArrayList<String> fields = new ArrayList<String>();
    IndexReader reader = null;

    try 
    {
      reader = IndexReader.open(indexDir);
      fields.addAll(reader.getFieldNames(IndexReader.FieldOption.INDEXED));
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
    
    return fields;    
  }
  
  /* Get all the terms in the index */
  public ArrayList<SqTerm> getIndexedTerms() throws IOException
  {
    return getIndexedTerms(null);
  }
  
  public ArrayList<SqTerm> getIndexedTerms(String field) throws IOException
  {
    ArrayList<SqTerm> terms = new ArrayList<SqTerm>();
    IndexReader reader = null;

    try 
    {
      reader = IndexReader.open(indexDir);

      TermEnum termEnum = reader.terms();

      while(termEnum.next())
      {
        if(field == null || termEnum.term().field().equalsIgnoreCase(field))
        {
          SqTerm term = new SqTerm();

          term.setField(termEnum.term().field());
          term.setText(termEnum.term().text());
          term.setFreq(termEnum.docFreq());
        
          terms.add(term);
        }
      }
    } 
    catch(IOException e)
    {
      throw e;
    } 
    finally 
    {
      if (reader != null) 
      {
        reader.close();
      }
    }
    
    Collections.sort(terms);
    
    return terms;
  }
  
  /* Get the high frequency terms in the index */
  public ArrayList<SqTerm> getHighFreqTerms(int numTerms, String field) throws IOException
  {
    ArrayList<SqTerm> terms = new ArrayList<SqTerm>();
    PriorityQueue<SqTerm> queue = new PriorityQueue<SqTerm>(DEFAULT_QUEUE_SIZE, new SqTermComparator(SqTermComparator.FREQ, true));
    IndexReader reader = null;

    try 
    {
      reader = IndexReader.open(indexDir);

      TermEnum termEnum = reader.terms();

      int minFreq = 0;

      while(termEnum.next()) 
      {
        if(field == null || termEnum.term().field().equalsIgnoreCase(field))
        {
          if(termEnum.docFreq() > minFreq)
          {
            SqTerm term = new SqTerm();

            term.setField(termEnum.term().field());
            term.setText(termEnum.term().text());
            term.setFreq(termEnum.docFreq());

            queue.offer(term);

            if(queue.size() > numTerms)
            {
              queue.poll();
              minFreq = queue.peek().getFreq();
            }
          }
        }
      }

      int size = Math.min(queue.size(), numTerms); 
      for(int i = 0; i < size; i++)
      {
        terms.add(queue.poll());
      }
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

    return terms;
  }

  /** Score all terms and get the terms lying above the percentile among all documents in the index */
  public ArrayList<SqTerm> getTermsAbovePercentile(int percentile, int desiredTerms, int maxTerms, String field, boolean useScore) throws IOException
  {
    ArrayList<SqTerm> terms = new ArrayList<SqTerm>();
    PriorityQueue<SqTerm> queue = new PriorityQueue<SqTerm>(DEFAULT_QUEUE_SIZE, new SqTermComparator(SqTermComparator.SCORE, true));
    IndexReader reader = null;
    Searcher searcher = null;
    Similarity similarity = null;
    int numTerms;

    if (percentile > 100 || percentile < 0)
    {
    	logger.warn("Wrong percentile: " + percentile + ". Returnning null results");
    	return null;
    }

    try
    {
      // TODO: very bad implementation, but no good API to get this number!!!
      int numAllTerms = getIndexedTerms().size();
      logger.debug("Total number of terms: " + numAllTerms);

      reader = IndexReader.open(indexDir);
      searcher = new IndexSearcher(reader);
      similarity = searcher.getSimilarity();

      int cutoffIndex = numAllTerms * percentile / 100;
      logger.debug("Cutoff index for " + percentile + "-th percentile: " + cutoffIndex);

      numTerms = numAllTerms - cutoffIndex;
      logger.debug("Number of terms above cutoff index: " + numTerms);

      if (numTerms > maxTerms)
    	  numTerms = maxTerms;
      else if (desiredTerms < maxTerms && numTerms <= desiredTerms/2 )		// desiredTerms/2 is arbitrary number
    	  numTerms = desiredTerms;
      logger.debug("Adjusted number of terms with desired("+desiredTerms+") and max("+maxTerms+"): " + numTerms);

      int minScore = 0;
      //TermEnum termEnum = reader.terms();
      TermEnum termEnum = reader.terms(new Term(field, ""));
      while(termEnum.next())
      {
        if(field == null || termEnum.term().field().equalsIgnoreCase(field))
        {
          Term term = termEnum.term();
          logger.debug("Term: " + term.text());

          if(term.text().length() < 3)
          {
        	  // TODO: hard-coded hack
        	  // An English word having less than 3 characters are most likely a stop word,
        	  // or does not have a significant meaning --> prun it
        	  logger.debug("Less than 3 characters. Skipping...\n");
        	  continue;
          }

          int score = 0;
          if (useScore)
          {
	          // determine normalized term frequency (tf)
//	          int maxTermFreq = 1;   // to prevent possible divide by zero
	          float sumTermFreq = 0;
	          TermDocs termDocs = reader.termDocs(term);
	          while (termDocs.next())
	          {
	        	  int termFreq = termDocs.freq();
	        	  sumTermFreq += similarity.tf(termFreq);
//	        	  sumTermFreq += termFreq;
//	        	  if (maxTermFreq < termFreq)
//	        		  maxTermFreq = termFreq;
	          }

//	          float tf = ((float) sumTermFreq) / ((float) maxTermFreq);
//	          logger.debug("Maximum term frequency = " + maxTermFreq);
//	          logger.debug("Sum of normalized term frequency = " + tf);
	          logger.debug("Sum of term frequency = " + sumTermFreq);

	          // calculate score
	          //
	          // score = sum-tf * df
	          //
	          // df - document frequency = n / N,
	          // where n is number documents containing term
	          //   and N is the total number of documents in the index
	          //
	          // we are not using idf (Inversed Document Frequency)
	          // because we are not trying to identify terms that distinguishes the document as in searches
	          // but rather we are trying to identify terms that are more common to the documents
	          //
	          // 100 is multiplied so the number does not get too small;

	          float idf = similarity.idf(termEnum.docFreq(), reader.numDocs());
	          score = (int) (sumTermFreq * 100 / idf);

	          logger.debug("Inversed document frequency: " + idf);

//	          float df = ((float) termEnum.docFreq()) / ((float) reader.numDocs());
//	          score = (int) (tf * df * 100);

//	          logger.debug("Term document frequency: " + df);
	          logger.debug("Term score: " + score + "\n");
          }
          else
          {
        	  score = termEnum.docFreq();
          }

          if(score > minScore)
          {
            SqTerm sqterm = new SqTerm();

            sqterm.setField(term.field());
            sqterm.setText(term.text());
            sqterm.setFreq(termEnum.docFreq());
            sqterm.setScore(score);

            queue.offer(sqterm);

            if(queue.size() > numTerms)
            {
              queue.poll();
              minScore = queue.peek().getScore();
            }
          }
        }
      }

      int size = Math.min(queue.size(), numTerms);
      logger.debug("Final term list size: " + size);

      for(int i = 0; i < size; i++)
      {
    	// add high freq terms at beginning for better query result
        terms.add(0, queue.poll());
      }
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

    return terms;
  }

  /* Check a documents existance in the index based on the document's path */
  public boolean docExists(String path)
  {
    boolean exists = false;
    
    try
    {
      if(getDocId(path) > 0)
      {
        exists = true;
      }
    }
    catch(IOException e)
    {
      logger.error("", e);
    }

    return exists;
  }
  
  /*  */
  public boolean docExists(int docId)
  {
    boolean exists = false;
    IndexReader reader = null;
    
    try
    {
      reader = IndexReader.open(indexDir);
      
      if(reader.document(docId) != null)
      {
        exists = true;
      }
    }
    catch(IOException e)
    {
      //Do nothing with exception
    }
    finally
    {
      if(reader != null)
      {
        try
        {
          reader.close();
        }
        catch(IOException e)
        {
          //Do nothing with exception
        }
      }
    }
    
    return exists;
  }
 
  /* Check a documents existance in the index based on the document's id */
  public int getDocId(String path) throws IOException
  {
    int id = -1;
    IndexReader reader = null;
    
    try
    {
      reader = IndexReader.open(indexDir);

      Searcher searcher = new IndexSearcher(reader);
    
      Query query = new TermQuery(new Term(SqFields.PATH_FIELD, path.toLowerCase()));

      Hits hits = searcher.search(query);
     
      if(hits.length() > 0)
      {
        id = hits.id(0);
      }
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
     
    return id;
  }
  
  /* Get a document from the index based on the path */
  public SqDocument getDocument(String path) throws ParseException, IOException
  {
    SqDocument document = null;
    
    int docId = getDocId(path);
    
    if(docId >= 0)
    {
      document = getDocument(docId);
    }
    
    return document;
  }
  
  /* Get a document from the index based on the document's id */
  public SqDocument getDocument(int docId) throws IOException
  {
    SqDocument sqDoc = null;
    IndexReader reader = null;

    try
    {
      reader = IndexReader.open(indexDir);

      if(docExists(docId))
      {
        sqDoc = new SqDocument();
        Document LuceneDoc = reader.document(docId);
        
        sqDoc.setDocId(docId);
        sqDoc.setPath(LuceneDoc.get(SqFields.PATH_FIELD));
        sqDoc.setLastModified(LuceneDoc.getField(SqFields.LAST_MODIFIED).stringValue());

        TermFreqVector termFreqVector = reader.getTermFreqVector(docId, SqFields.CONTENT_FIELD);
        
        if(termFreqVector != null)
        {          
          String[] terms = termFreqVector.getTerms();
          int[] freqs = termFreqVector.getTermFrequencies();
          
          for(int i = 0; i < terms.length; i++)
          {
            SqTerm term = new SqTerm();

            term.setText(terms[i]);
            term.setFreq(freqs[i]);
            term.setField(SqFields.CONTENT_FIELD);
            
            sqDoc.addTerm(term);

            TermPositionVector tpv = (TermPositionVector)termFreqVector;
                    
            int[] positions = tpv.getTermPositions(i);
            
            if(positions != null)
            {
              for(int position : positions)
              {
                sqDoc.addTermPosition(terms[i], position);
              }
            }
            
            TermVectorOffsetInfo[] offset = tpv.getOffsets(i);
            
            if(offset != null)
            {
              for(TermVectorOffsetInfo o : offset)
              {
                sqDoc.addTermOffset(terms[i], new SqTermOffset(o.getStartOffset(), o.getEndOffset()));
              }
            }
          }
        }
      }
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

    return sqDoc;
  }
}
