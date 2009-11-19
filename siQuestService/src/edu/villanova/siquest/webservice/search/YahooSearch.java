package edu.villanova.siquest.webservice.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.yahoo.search.SearchClient;
import com.yahoo.search.SearchException;
import com.yahoo.search.WebSearchRequest;
import com.yahoo.search.WebSearchResult;
import com.yahoo.search.WebSearchResults;

import edu.villanova.siquest.webservice.siQuestSOAP.ResultType;

public class YahooSearch {
	
	public List<ResultType> doSearch(String query){
		boolean error = false;
		List<ResultType> yResults = new ArrayList<ResultType>();
		try{							
		String[] service = new String[1];
		service[0] = "YAHOO";		
		
        // Create the search client. Pass it your application ID.
        SearchClient client = new SearchClient("ZPJK0MLV34EkLd.BK4590yF4vbcZGcR4mNR3PWBeoNIpRxxStdoGUAK.apoPAfy92wwvoIacK4C7uJBjaFWwDTaJxw--");
        WebSearchRequest request = new WebSearchRequest(query);
        request.setResults(20);
        try {
            // Execute the search.
            WebSearchResults results = client.webSearch(request);
            // Print out how many hits were found.
            System.out.println("Found " + results.getTotalResultsAvailable() + " hits for java! Displaying the first " + results.getTotalResultsReturned() + ".");
            // Iterate over the results.
            for (int i = 0; i < results.listResults().length; i++) {
                WebSearchResult result = results.listResults()[i];
                ResultType wsResult = new ResultType();
                wsResult.setTitle(result.getTitle());
                wsResult.setRank(i+"");
                wsResult.setUrl(result.getUrl());
                wsResult.setClick_url(result.getClickUrl());
                wsResult.setSummary(result.getSummary());
                wsResult.setSearchService(service);
                yResults.add(wsResult);
            }
        } catch (IOException ex) {
        	error = true;
        } catch (SearchException ex) {
        	error = true;
        }
		} catch (Exception e){
			error = true;
		}
		if(error){
			return null;
		}
		
		return yResults;		
	}

}
