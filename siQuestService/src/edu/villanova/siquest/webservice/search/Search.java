package edu.villanova.siquest.webservice.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.villanova.siquest.SqQuery;
import edu.villanova.siquest.webservice.siQuestSOAP.ResultType;

public class Search {
	String status;
	
	public List<ResultType> combinedResults(HashMap<String, SqQuery> queries){
		List<ResultType> results = new ArrayList<ResultType>();
		status = "";
		List<ResultType> lResults = new LiveSearch().doSearch(queries.get("MS_LIVE").toString());
		List<ResultType> yResults = new YahooSearch().doSearch(queries.get("YAHOO").toString());		
		List<ResultType> gResults= new GoogleSearch().doSearch(queries.get("GOOGLE").toString());

		if(gResults == null){
			status += " Google Failed!";
		}else{
			status += "Google "+gResults.size();
			results.addAll(gResults);
		}		
		if(yResults == null){
			status += " Yahoo Failed!";
		}else{
			status += "Yahoo "+yResults.size();
			results.addAll(yResults);
		}
		if(lResults == null){
			status += " MS_Live Failed!";
		}else{
			status += "Live "+lResults.size();
			results.addAll(lResults);
		}
		
		return results;		
	}

}
