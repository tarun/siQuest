package edu.villanova.siquest.webservice.search;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import edu.villanova.siquest.SqPreferences;
import edu.villanova.siquest.SqQuery;
import edu.villanova.siquest.SqQueryBuilder;
import edu.villanova.siquest.SqSearchAPI;
import edu.villanova.siquest.SqTerm;
import edu.villanova.siquest.webservice.siQuestSOAP.QueryType;
import edu.villanova.siquest.webservice.siQuestSOAP.ResultType;
import edu.villanova.siquest.webservice.siQuestSOAP.TermType;


public class SqSearch {
	
	public static HashMap<String, Object> getResults(Set<String> sourceFileNames, String analyzer, String query){
		HashMap<String, SqQuery> queries = getQuery(sourceFileNames,  analyzer);
		Search searcher = new Search();
		List<ResultType> results = searcher.combinedResults(queries);
		
		SqQuery gQuery = queries.get("GOOGLE");
		List<TermType> soapTerms = new ArrayList<TermType>();
		QueryType soapQuery = new QueryType();		
		List<SqTerm> termList = gQuery.getTermList();
		int minScore = 0, maxScore = 0;
		for (Iterator iterator = termList.iterator(); iterator.hasNext();) {
			SqTerm sqTerm = (SqTerm) iterator.next();
			int curScore =sqTerm.getScore(); 
			if(curScore > maxScore){
				maxScore = curScore;
			}
			if(curScore < minScore){
				minScore = curScore;
			}
		}
		
		//Calculate scaling factor
		int factor = (maxScore - minScore) / 10;

		for (Iterator iterator = termList.iterator(); iterator.hasNext();) {
			SqTerm sqTerm = (SqTerm) iterator.next();
			TermType tt = new TermType();
			tt.setWord(sqTerm.getText());
			tt.setWeight(((sqTerm.getScore()-minScore)/factor)+"");
			soapTerms.add(tt);
		}
		soapQuery.setTerm(soapTerms.toArray(new TermType[0]));
		
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("Query", soapQuery);
		hm.put("Result", results);
		hm.put("Status", searcher.status);
		return hm;
	}

    public static HashMap<String, SqQuery> getQuery(Set<String> sourceFileNames, String analyzer){
    	
        HashMap<String, SqQuery> queries = new HashMap<String, SqQuery>();
        boolean status = true;
        String errorMsg = "";

        // Create query builder instance
        SqQueryBuilder builder = new SqQueryBuilder();
        SqPreferences prefs = SqPreferences.getInstance();
        
		// Creating and initializing builder
            
			// Set miscellaneous options
            builder.setAnalyzer(prefs.getAnalyzerName());
            builder.setIndexDirectory(prefs.getIndexDir());
            builder.setOverwriteIndex(true);
            
            if(analyzer != null && analyzer.length() > 2){
            	builder.setAnalyzer(analyzer);
            }            
            
            if (!builder.prepareIndex())
            {
                status = false;
                errorMsg += " Error Preparing Index ";
            }

        // add the source files and index them
        Set<File> sourceFiles = new HashSet<File>();
        for(Iterator<String> sourceFileNamesIterator = sourceFileNames.iterator();
        sourceFileNamesIterator.hasNext();){
        	String fileName = sourceFileNamesIterator.next(); 
        	File file = new File(fileName);
//        	System.out.println("Processing "+fileName);
        	if(file.exists()){
//        		System.out.println("Processing ADDED"+fileName);
        		sourceFiles.add(file);
        	}
        }

        builder.indexSources(sourceFiles);
        builder.optimizeIndex();

            queries.put("GOOGLE", builder.getQuery(SqSearchAPI.GOOGLE));
            queries.put("YAHOO", builder.getQuery(SqSearchAPI.YAHOO));
            queries.put("MS_LIVE", builder.getQuery(SqSearchAPI.MS_LIVE));

            if(!status){
                queries.put("ERROR", null);
            }
               
        return queries;
    }
}
