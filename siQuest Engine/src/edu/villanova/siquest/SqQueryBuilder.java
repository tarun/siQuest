package edu.villanova.siquest;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;


public class SqQueryBuilder
{
	static Logger logger = Logger.getLogger(SqQueryBuilder.class);
	
	/** Default field to extract index from */
	public final static String DEFAULT_FIELD = "contents";
	
	//*********************
	// Attributes
	//
	
	private SqPreferences prefs;
	private SqIndexer indexer;
	private SqInfoExtractor extractor;
	
	/** result query */
	private SqQuery query;
	
	/** directory where index files are being stored */
	private String indexDirectory;
	
	/** analyzer being used for indexing */
	private String analyzer;
	
	/** flag indicating whether or not to overwrite existing index */
	private boolean overwriteIndex = true;
	
	/** flag to indicate indexing has been initialized */
	private boolean indexReady = false;

	/** maximum number of terms (keywords) allowed for the query */
	private int maxTerms;
	
	/** ideal number of terms (keywords) to generate for the query */
	private int numTerms;
	
	/** percentile above which the terms will be cutoff */
	private int percentile;

	
	//*********************
	// public methods
	//
	
	/** Default Constructor */
	public SqQueryBuilder()
	{
		prefs = SqPreferences.getInstance();
		indexDirectory = prefs.getIndexDir();
		analyzer = prefs.getAnalyzerName();
		maxTerms = prefs.getMaxKeywords();
		numTerms = prefs.getNumKeywords();
		percentile = prefs.getPercentile();
		
		indexer = new SqIndexer(indexDirectory, analyzer);
		extractor = new SqInfoExtractor(indexDirectory, analyzer);
		query = null;
	}
	
	public SqQueryBuilder(String analyzer, int maxTerms)
	{
		prefs = SqPreferences.getInstance();
		indexDirectory = prefs.getIndexDir();
		this.analyzer = analyzer;
		this.maxTerms = maxTerms;
		this.numTerms = prefs.getNumKeywords();
		this.percentile = prefs.getPercentile();
		
		indexer = new SqIndexer(indexDirectory, analyzer);
		extractor = new SqInfoExtractor(indexDirectory, analyzer);
		query = null;
	}
	
	
	public String getAnalyzer() {
		return analyzer;
	}


	public void setAnalyzer(String analyzer) {
		this.analyzer = analyzer;
	}


	public SqInfoExtractor getExtractor() {
		return extractor;
	}


	public void setExtractor(SqInfoExtractor extractor) {
		this.extractor = extractor;
	}


	public String getIndexDirectory() {
		return indexDirectory;
	}


	public void setIndexDirectory(String indexDirectory) {
		this.indexDirectory = indexDirectory;
	}


	public SqIndexer getIndexer() {
		return indexer;
	}


	public void setIndexer(SqIndexer indexer) {
		this.indexer = indexer;
	}


	public boolean isOverwriteIndex() {
		return overwriteIndex;
	}

	public void setOverwriteIndex(boolean overwriteIndex) {
		this.overwriteIndex = overwriteIndex;
	}


	public int getNumTerms() {
		return numTerms;
	}

	public void setNumTerms(int numTerms) {
		this.numTerms = numTerms;
	}

	public int getMaxTerms() {
		return maxTerms;
	}

	public void setMaxTerms(int maxTerms) {
		this.maxTerms = maxTerms;
	}
	
	public boolean prepareIndex()
	{
		return prepareIndex(false);
	}
	
	public boolean prepareIndex(boolean force)
	{
		if (!force && indexReady)
			return true;
		
		// Re-create or re-use existing index
		indexer.setIndexDir(indexDirectory);
		try
		{
			if (overwriteIndex)
			{
				indexer.deleteIndex();
				logger.info("Previous index deleted");
				
				indexer.createIndex();
				logger.info("Index created");
			}
			else
			{
				indexer.reuseIndex();
				logger.info("Index is being reused");
			}
		}
		catch (IOException e)
		{
			logger.error("Error while creating index", e);
			return (indexReady = false);
		}
		
		return (indexReady = true);
	}

	
	public void indexSources(Collection<File> sourceFiles)
	{
		prepareIndex();
		
		// Add source files to the index
		logger.info("Start indexing");
		for (File file : sourceFiles)
		{
			try {
				indexer.indexFileOrDirectory(file);
			} catch (IOException e) {
				logger.error("ERROR indexing file: " + e.getMessage());
			}
		}
		logger.info("Finished indexing");
	}
		
	public void optimizeIndex()
	{
		try {
			indexer.optimizeIndex();
		}
		catch (IOException e) {
			logger.error("Error occurred while optimizing index", e);
		}
	}

	public SqQuery getQuery()
	{
		return getQuery(SqSearchAPI.getAPI(prefs.getSearchAPI()));
	}
	
	
	public SqQuery getQuery(SqSearchAPI api)
	{
		// Retrieve useful information from the generated index
		List<SqTerm> termList = null;
		try
		{
			//termList = extractor.getHighFreqTerms(numTerms, DEFAULT_FIELD);
			if (api == SqSearchAPI.MS_LIVE)
			{
				// MS Live does not function if 20 or more terms are given...doh'
				termList = extractor.getTermsAbovePercentile(percentile, numTerms, 19, DEFAULT_FIELD, true);
			}
			else
			{
				termList = extractor.getTermsAbovePercentile(percentile, numTerms, maxTerms, DEFAULT_FIELD, true);
			}
		}
		catch (IOException e)
		{
			logger.error("Error getting query terms", e);
			return null;
		}

		query = api.getQuery(termList);
		logger.info("Generated query: " + query);
		
		return query;
	}

}
