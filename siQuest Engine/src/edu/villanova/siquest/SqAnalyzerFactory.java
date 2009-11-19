package edu.villanova.siquest;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.snowball.SnowballAnalyzer;

public class SqAnalyzerFactory 
{
  protected SqAnalyzerFactory()
  {
  }
  
  public static Analyzer createAnalyzer(String analyzerName, String stopWords)
  {
    Analyzer analyzer = null;
    
    SqPreferences prefs = SqPreferences.getInstance();
    
    String[] stopWordList = null; 
    
//    if(stopWordList != null)
//    {
      stopWordList = prefs.getStopWords().split(",");
//    }
    
    if(analyzerName.equalsIgnoreCase(SqAnalyzers.SIMPLE_ANALYZER))
    {
      analyzer = new SimpleAnalyzer();
    }
    else if(analyzerName.equalsIgnoreCase(SqAnalyzers.SNOWBALL))
    {
      if(stopWordList == null)
      {
        analyzer = new SnowballAnalyzer(prefs.getStemmingLanguage());
      }
      else
      {
        analyzer = new SnowballAnalyzer(prefs.getStemmingLanguage(), stopWordList);
      }
    }
    else if(analyzerName.equalsIgnoreCase(SqAnalyzers.STOP_ANALYZER))
    {
      if(stopWordList == null)
      {  
        analyzer = new StopAnalyzer();
      }
      else
      {
        analyzer = new StopAnalyzer(stopWordList);
      }
    }
    else if(analyzerName.equalsIgnoreCase(SqAnalyzers.WHITE_SPACE_ANALYZER))
    {
      analyzer = new WhitespaceAnalyzer();
    }
    else
    {
      if(stopWordList == null)
      {  
        analyzer = new StandardAnalyzer();
      }
      else
      {   
        analyzer = new StandardAnalyzer(stopWordList);
      }
    }
    
    return analyzer;
  }
}
