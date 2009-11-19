package edu.villanova.siquest.webservice.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.microsoft.schemas.MSNSearch._2005._09.fex.MSNSearchPortTypeProxy;
import com.microsoft.schemas.MSNSearch._2005._09.fex.MSNSearchService;
import com.microsoft.schemas.MSNSearch._2005._09.fex.MSNSearchServiceLocator;

import edu.villanova.siquest.webservice.siQuestSOAP.ResultType;

public class LiveSearch {
	
	public List<ResultType> doSearch(String query){
		List<ResultType> lResults = new ArrayList<ResultType>();
		boolean error = false;
		
		try {
			String[] service = new String[1];
			service[0] = "MS_LIVE";
			
		String appId = "663254F86001D08B6AE79F10342B99610F248D16";
		
		if(query.length() > 40){
			query = query.substring(1, 40);	
		}
		
		String requestStr = "http://api.search.live.net/json.aspx?"
	        
            // Common request fields (required)
            + "AppId=" + appId
            + "&Query=" + java.net.URLEncoder.encode(query)            
            + "&Sources=Web"
            
            // Common request fields (optional)
            + "&Version=2.0"
            + "&Market=en-us"
            + "&Adult=Moderate"

            // Web-specific request fields (optional)
            + "&Web.Count=10"
            + "&Web.Offset=0"
            //+ "&Web.FileType=DOC"
            ;						
		
			URL url = new URL(requestStr);
			URLConnection connection = url.openConnection();
			connection.addRequestProperty("Referer", "http://www.villanova.edu");

			String line;
			StringBuilder builder = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while((line = reader.readLine()) != null) {
			 builder.append(line);
			}
			
			JSONObject json = new JSONObject(builder.toString());
			JSONObject searchResponse = json.getJSONObject("SearchResponse");
			JSONArray results = searchResponse.getJSONObject("Web").getJSONArray("Results");
			for(int i = 0; i < results.length(); i++){
				JSONObject result = results.getJSONObject(i);
				ResultType wsResult = new ResultType();
				wsResult.setTitle(result.getString("Title"));
				wsResult.setUrl(result.getString("Url"));
				wsResult.setSummary(result.getString("Description"));
				wsResult.setSearchService(service);
				lResults.add(wsResult);
			}
			
			

		} catch (MalformedURLException e) {
			error = true;
		} catch (IOException e) {
			error = true;
		} catch (JSONException e) {
			error = true;
		}
		if(error){
			return null;
		}
		
		return lResults;
	}

}
