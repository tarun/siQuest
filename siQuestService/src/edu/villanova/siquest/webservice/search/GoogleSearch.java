package edu.villanova.siquest.webservice.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.villanova.siquest.webservice.siQuestSOAP.ResultType;

public class GoogleSearch {
	
	public List<ResultType> doSearch(String query){
		List<ResultType> gResults = new ArrayList<ResultType>();
		
		URL url;
		try {			
		url = new URL("http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q="+java.net.URLEncoder.encode(query));
//			url = new URL("http://ajax.googleapis.com/ajax/services/search/web?v=1.0&ResultSetSize=10&q="+java.net.URLEncoder.encode(query));
		System.out.println("----------"+url);
		String[] service = new String[1];
		service[0] = "GOOGLE";

		URLConnection connection = url.openConnection();
		connection.addRequestProperty("Referer", "http://www.mysite.com/index.html");

		String line;
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		while((line = reader.readLine()) != null) {
		 builder.append(line);
		}
				
//		GsonBuilder gsonBuilder = new GsonBuilder();
//		gsonBuilder.registerTypeAdapter(ArrayList.class, new GoogleResultDeserializer());
//		Gson gson = gsonBuilder.create();		
//		List results = gson.fromJson(builder.toString(), List.class);

		JSONObject json = new JSONObject(builder.toString());
		JSONObject responseData = json.getJSONObject("responseData");
		JSONArray results = responseData.getJSONArray("results");
		for(int i = 0; i < results.length(); i++){
			JSONObject result = results.getJSONObject(i);
			ResultType wsResult = new ResultType();
			wsResult.setTitle(result.getString("title"));
			wsResult.setUrl(result.getString("url"));
			wsResult.setSummary(result.getString("content"));
			wsResult.setSearchService(service);
			gResults.add(wsResult);
		}
		
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return gResults;		
	}

}
