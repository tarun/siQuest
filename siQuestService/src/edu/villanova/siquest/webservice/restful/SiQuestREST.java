package edu.villanova.siquest.webservice.restful;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import edu.villanova.siquest.webservice.search.SqSearch;
import edu.villanova.siquest.webservice.siQuestSOAP.QueryType;
import edu.villanova.siquest.webservice.siQuestSOAP.ResultType;

/**
 * Servlet implementation class SiQuestREST
 */
public class SiQuestREST extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SiQuestREST() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean error = false;
		try{
			
		
		String files = request.getParameter("files");		
		if(files == null){files = "";}
//		System.out.println("Files are "+files);
		
		String analyzer = request.getParameter("analyzer");
		if(analyzer == null){analyzer = "";}
		
		String query = request.getParameter("query");
		
		String filename[] = files.split(";");
		Set<String> sourceFiles = new HashSet<String>(filename.length);
    	for (int i = 0; i < filename.length; i++) {
			sourceFiles.add(filename[i]);
//			System.out.println(" >" + i +" - " + filename[i]);
		}
    			
		HashMap<String, Object> hm = SqSearch.getResults(sourceFiles, analyzer, query);
    	List<ResultType> rslts = (List<ResultType>) hm.get("Result");
    	ResultType[] rsltArray = rslts.toArray(new ResultType[0]);
    	
		Gson gson = new Gson();
		JSONObject jo = new JSONObject();
		
		PrintWriter out = response.getWriter();
//		out.write(gson.toJson(rsltArray));
//		out.write(gson.toJson((QueryType)hm.get("Query")));
		try {
			jo.put("query", new JSONObject(gson.toJson((QueryType)hm.get("Query"))));
			jo.put("results", new JSONArray(gson.toJson(rsltArray)));			
			jo.put("status", hm.get("Status"));
			out.write(jo.toString(1));
		} catch (JSONException e) {
			error = true;
		}
		} catch(Exception e){
			error = true;
		}
		
		if(error){
			try {
			JSONObject ejo = new JSONObject();			
			ejo.put("status", "ERROR");
			PrintWriter eout = response.getWriter();
			eout.write(ejo.toString(1));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		 
		System.out.println("\n-------------------------Done");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
