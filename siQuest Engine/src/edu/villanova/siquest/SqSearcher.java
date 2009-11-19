package edu.villanova.siquest;

import java.io.File;
import java.util.ArrayList;
import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.TermQuery;


public class SqSearcher 
{
  private String indexDir;
  private Analyzer analyzer;
  private SqInfoExtractor extractor;
  
  public SqSearcher(String indexDir, String analyzerName)
  {
    this.indexDir = indexDir;
    analyzer = SqAnalyzerFactory.createAnalyzer(analyzerName, null);
    extractor = new SqInfoExtractor(indexDir, analyzerName, null);
  }
  
  public SqSearcher(String indexDir, String analyzerName, String stopWords)
  {
    this.indexDir = indexDir;
    analyzer = SqAnalyzerFactory.createAnalyzer(analyzerName, stopWords);
    extractor = new SqInfoExtractor(indexDir, analyzerName, stopWords);
  }
  
  public ArrayList<SqDocumentResult> search(String query) throws ParseException, IOException
  {
    return search(query, SqFields.CONTENT_FIELD);
  }

  public ArrayList<SqDocumentResult> search(String strquery, String field) throws ParseException, IOException
  {
    ArrayList<SqDocumentResult> results = new ArrayList<SqDocumentResult>();
    IndexReader reader = null;

    try
    {
      reader = IndexReader.open(indexDir);

      Searcher searcher = new IndexSearcher(reader);
    
      QueryParser parser =  null;
      Query query = null;

      if(field.equalsIgnoreCase(SqFields.PATH_FIELD) || field.equalsIgnoreCase(SqFields.LAST_MODIFIED))
      {
        query = new TermQuery(new Term(field, strquery.toLowerCase()));
      }
      else
      {
        parser = new QueryParser(field, analyzer);
        query = parser.parse(strquery);
      }

      Hits hits = searcher.search(query);
     
      for(int i = 0; i < hits.length(); i++)
      {     
        SqDocument document = extractor.getDocument(hits.id(i));
       
        SqDocumentResult result = new SqDocumentResult();
        result.setDocument(document);
        result.setRank(hits.score(i));
        
        results.add(result);
      }
    }
    catch(ParseException e)
    {
      throw e;
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
     
    return results;
  }

  public ArrayList<String> analyzeText(String analyzerName, String stopWords, String text) throws IOException
  {
    ArrayList<String> terms = new ArrayList<String>();
    TokenStream stream = null;
    
    try
    {
        Analyzer queryAnalyzer = SqAnalyzerFactory.createAnalyzer(analyzerName, stopWords);
                
        stream = queryAnalyzer.tokenStream(SqFields.CONTENT_FIELD, new StringReader(text));

        Token token = stream.next();
        
        while(token != null)
        {
          terms.add(new String(token.termBuffer(), 0, token.termLength()));
          token = stream.next();
        }
        
        stream.close();
    }
    catch(IOException e)
    {
      throw e;
    }
    finally
    {
      if(stream != null)
      {
        stream.close();
      }
    }
    
    return terms;
  }
  
  public ArrayList<String> analyzeDocument(String analyzerName, String stopWords, File fileToAnalyze) throws IOException
  {
    ArrayList<String> terms = new ArrayList<String>();
  
    String text = SqTextExtractor.extractText(fileToAnalyze);
    
    if(text != null)
    {
      terms = analyzeText(analyzerName, stopWords, text);
    }
    
    return terms;
  }
}