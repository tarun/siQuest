package edu.villanova.siquest;

import java.util.prefs.BackingStoreException;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

public class SqPreferences 
{
  static Logger logger = Logger.getLogger(SqPreferences.class);

  private static final String USER_HOME = System.getProperty("user.home");
  private static final String SI_QUEST_PROFILE = USER_HOME + File.separator + "SiQuest";
  private static final String USER_PREF_FILE = SI_QUEST_PROFILE + File.separator + "siquest.pref";
  
  private static final String FIRST_RUN_PREF = "FIRST_RUN";
  private static final String INDEX_DIR_PREF = "INDEX_DIR";
  private static final String ANALYZER_NAME_PREF = "ANALYZER_NAME";
  private static final String STEMMING_LANGUAGE_PREF = "STEMMING_LANGUAGE";
  private static final String STOPWORDS_PREF = "STOP_WORDS";
  private static final String USE_CUSTOM_STOP_WORDS_PREF = "USE_CUSTOM_STOP_WORDS";
  private static final String LAST_INDEXED_DIR_PREF = "LAST_INDEXED";
  private static final String NUM_KEYWORDS = "NUM_KEYWORDS";
  private static final String MAX_KEYWORDS = "MAX_KEYWORDS";
  private static final String PERCENTILE = "PERCENTILE";
  private static final String SEARCH_API = "SEARCH_API";
  
  private static final boolean DEFAULT_FIRST_RUN = true;
  private static final String DEFAULT_INDEX_DIR = SI_QUEST_PROFILE + File.separator + "Index";
  private static final String DEFAULT_LAST_INDEXED = USER_HOME;
  private static final String DEFAULT_STOP_WORDS = "a,an,and,are,as,at,be,but,by,for,if,in,into,is,it,no,not,of,on,or,such,that,the,their,then,there,these,they,this,to,was,will,with";
  public static String DEFAULT_STEMMING_LANGUAGE = "English"; 
  public static boolean DEFAULT_USE_CUSTOM_STOP_WORDS = false;
  public static int DEFAULT_NUM_KEYWORDS = 25;
  public static int DEFAULT_MAX_KEYWORDS = 50;
  public static int DEFAULT_PERCENTILE = 98;
  public static String DEFAULT_SEARCH_API = "google";
  
  private Preferences prefs;
  
  private static SqPreferences instance = null;
  
  protected SqPreferences()
  {
    prefs = Preferences.userNodeForPackage(this.getClass());
    
    File profileDir = new File(SI_QUEST_PROFILE);

    if(profileDir.exists())
    {
      File prefsFile = new File(USER_PREF_FILE);

	  if(prefsFile.exists())
      {
        importUserPrefs();
      }
      else
      {
        initializePrefs();
        exportUserPrefs();
      }
    }
    else
    {
      boolean created = profileDir.mkdir();

      if(created)
      {
        initializePrefs();
        exportUserPrefs();
      }
      else
      {
        logger.error("User profile was not created ");
      }
    }
  }
  
  public static SqPreferences getInstance()
  {
    if(instance == null)
    {
      instance = new SqPreferences();
    }
    
    return instance;
  }
 
  
  public void initializePrefs()
  {
	  prefs.putBoolean(FIRST_RUN_PREF, DEFAULT_FIRST_RUN);    
	  prefs.put(INDEX_DIR_PREF, DEFAULT_INDEX_DIR);
	  prefs.put(ANALYZER_NAME_PREF, SqAnalyzers.STANDARD_ANALYZER);
	  prefs.put(STEMMING_LANGUAGE_PREF, DEFAULT_STEMMING_LANGUAGE);
	  prefs.putBoolean(USE_CUSTOM_STOP_WORDS_PREF, DEFAULT_USE_CUSTOM_STOP_WORDS);
	  prefs.put(STOPWORDS_PREF, DEFAULT_STOP_WORDS);
	  prefs.putInt(NUM_KEYWORDS, DEFAULT_NUM_KEYWORDS);
	  prefs.putInt(MAX_KEYWORDS, DEFAULT_MAX_KEYWORDS);
	  prefs.putInt(PERCENTILE, DEFAULT_PERCENTILE);
	  prefs.put(SEARCH_API, DEFAULT_SEARCH_API);
  }
  
  
  /** import user preferences */
  public void importUserPrefs()
  {
    InputStream is = null;
	try
    {
	  is = new BufferedInputStream(new FileInputStream(USER_PREF_FILE));
	  Preferences.importPreferences(is);
	  return;
	} 
    catch (FileNotFoundException e)
    {
	  logger.error("User preference file " + USER_PREF_FILE + " not found.", e);
	} 
    catch (InvalidPreferencesFormatException e)
    {
	  logger.error("User preference file " + USER_PREF_FILE + " is corrupt!", e);
	} 
    catch(IOException e)
    {
	  logger.error("Error reading preference file " + USER_PREF_FILE, e);
	} 
    finally
    {
	  if(is != null)
      {
	    try
        {
	      is.close();
	    }
	    catch(IOException ioe)
        {
        }
	  }
	}	    
  }

  public void exportUserPrefs()
  {
    try
    {
	  prefs.exportNode(new FileOutputStream(USER_PREF_FILE));
	} 
    catch(FileNotFoundException e)
    {
      logger.error("Error while exporting user preferences: ", e);
	} 
    catch(IOException e)
    {
	  logger.error("Error while exporting user preferences: ", e);
	} 
    catch (BackingStoreException e)
    {
	  logger.error("Error while exporting user preferences: ", e);
	}
  }
 
  public void setFirstRun(boolean firstRun)
  {
    prefs.putBoolean(FIRST_RUN_PREF, firstRun);
  }
  
  public boolean getFirstRun()
  {
    return prefs.getBoolean(FIRST_RUN_PREF, DEFAULT_FIRST_RUN);
  }
  
  
  public void setIndexDir(String dir)
  {
    prefs.put(INDEX_DIR_PREF, dir);
  }
  
  public String getIndexDir()
  {
    return prefs.get(INDEX_DIR_PREF, DEFAULT_INDEX_DIR);
  }
  
  public void setAnalyzerName(String analyzerName)
  {
    prefs.put(ANALYZER_NAME_PREF, analyzerName);
  }
  
  public String getAnalyzerName()
  {
    return prefs.get(ANALYZER_NAME_PREF, SqAnalyzers.STANDARD_ANALYZER);
  }
  
  public void setStemmingLanguage(String language)
  {
    prefs.put(STEMMING_LANGUAGE_PREF, language);
  }
  
  public String getStemmingLanguage()
  {
    return prefs.get(STEMMING_LANGUAGE_PREF, DEFAULT_STEMMING_LANGUAGE);
  }
  
  public void setUseCustomStopWords(boolean useStopWords)
  {
    prefs.putBoolean(USE_CUSTOM_STOP_WORDS_PREF, useStopWords);
  }
  
  public boolean getUseCustomStopWords()
  {
    return prefs.getBoolean(USE_CUSTOM_STOP_WORDS_PREF, DEFAULT_USE_CUSTOM_STOP_WORDS);
  }
  
  public void setStopWords(String stopWords)
  {
    prefs.put(STOPWORDS_PREF, stopWords);
  }
  
  public String getStopWords()
  {
    return prefs.get(STOPWORDS_PREF, DEFAULT_STOP_WORDS);
  }
  
  public void setLastIndexedDir(String dir)
  {
    prefs.put(LAST_INDEXED_DIR_PREF, dir);
  }
  
  public String getLastIndexedDir()
  {
    return prefs.get(LAST_INDEXED_DIR_PREF, DEFAULT_LAST_INDEXED);
  }
  
  public void setNumKeywords(int numKeywords)
  {
	prefs.putInt(NUM_KEYWORDS, numKeywords);
  }
  
  public int getNumKeywords()
  {
	return prefs.getInt(NUM_KEYWORDS, DEFAULT_NUM_KEYWORDS);
  }

  public void setMaxKeywords(int maxKeywords)
  {
	prefs.putInt(MAX_KEYWORDS, maxKeywords);
  }
  
  public int getMaxKeywords()
  {
	return prefs.getInt(MAX_KEYWORDS, DEFAULT_MAX_KEYWORDS);
  }
  
  public void setPercentile(int percentile)
  {
	prefs.putInt(PERCENTILE, percentile);
  }
  
  public int getPercentile()
  {
	return prefs.getInt(PERCENTILE, DEFAULT_PERCENTILE);
  }
  
  public void setSearchAPI(String api)
  {
    prefs.put(SEARCH_API, api);
  }
  
  public String getSearchAPI()
  {
    return prefs.get(SEARCH_API, DEFAULT_SEARCH_API);
  }
}
