package edu.villanova.siquest.webservice.search;

import java.lang.reflect.Type;


import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.villanova.siquest.webservice.siQuestSOAP.ResultType;

public class GoogleResultDeserializer<ArrayList> implements JsonDeserializer<ArrayList> {

	@Override
	public ArrayList deserialize(JsonElement el, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		
//		ArrayList<String> resultList = new ArrayList<String>();
		
		JsonObject bigObject = el.getAsJsonObject();
		JsonElement responseData = bigObject.get("responseData");
		JsonArray results = responseData.getAsJsonObject().get("results").getAsJsonArray();
		for(Iterator<JsonElement> jelIter = results.iterator(); jelIter.hasNext();){			
			JsonObject result = jelIter.next().getAsJsonObject();
			edu.villanova.siquest.webservice.siQuestSOAP.ResultType wsResult = new edu.villanova.siquest.webservice.siQuestSOAP.ResultType();
			wsResult.setTitle(result.get("title").getAsString());
//			resultList.add(wsResult);
		}
		
		
		if(el.isJsonArray()){
			JsonArray elArray = el.getAsJsonArray();
			for (int j = 0; j < elArray.size(); j++) {
				JsonElement array_element = elArray.get(j);
				if(array_element.isJsonObject()){
					JsonObject obj = array_element.getAsJsonObject();
					obj.get("title");
					String x = "";
					x = "";
				}
			}
			
		}
		
		return null;
	}
	

}
