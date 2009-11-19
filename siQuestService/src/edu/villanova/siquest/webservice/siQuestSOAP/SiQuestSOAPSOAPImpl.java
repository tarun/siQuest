/**
 * SiQuestSOAPSOAPImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package edu.villanova.siquest.webservice.siQuestSOAP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import edu.villanova.siquest.SqQuery;
import edu.villanova.siquest.SqTerm;
import edu.villanova.siquest.webservice.search.SqSearch;

public class SiQuestSOAPSOAPImpl implements edu.villanova.siquest.webservice.siQuestSOAP.SiQuestSOAP_PortType{
    public java.lang.String checkStatus(java.lang.String token, java.lang.String uploadDir) throws java.rmi.RemoteException {
        return "OK";
    }

    public void getQuery(java.lang.String[] filename, java.lang.String analyzer, java.lang.String[] searchService, javax.xml.rpc.holders.StringHolder status, edu.villanova.siquest.webservice.siQuestSOAP.holders.TermTypeArrayHolder term) throws java.rmi.RemoteException {    	
        status.value = new java.lang.String();
        status.value = "Working  "+filename.length;
        term.value = new edu.villanova.siquest.webservice.siQuestSOAP.TermType[0];    	
    	
    	Set<String> sourceFiles = new HashSet<String>(filename.length);
    	for (int i = 0; i < filename.length; i++) {
			sourceFiles.add(filename[i]);
		}

    	HashMap<String, SqQuery> query = SqSearch.getQuery(sourceFiles, "");    	
    	    	
    	if(query.containsKey("ERROR")){
    		status.value = "Error!";
    	}else{
    		for (int i = 0; i < searchService.length; i++) {
				SqQuery sqQuery = query.get(searchService[i]);
				ArrayList<TermType> terms = new ArrayList<TermType>();
				int x = 0;
				for (Iterator<SqTerm> iterator = sqQuery.getTermList().iterator(); iterator
						.hasNext();) {
					SqTerm curTerm = iterator.next();
					TermType tt = new TermType();
					tt.setWord(x+curTerm.getText());
					tt.setWeight(curTerm.getFreq()+"");
					terms.add(tt);
					x++;
				}
				term.value = terms.toArray(new TermType[0]);					
				}
		}
    	    	

    }

    public void getResults(java.lang.String[] filename, java.lang.String analyzer, java.lang.String[] searchService, javax.xml.rpc.holders.StringHolder status, edu.villanova.siquest.webservice.siQuestSOAP.holders.QueryTypeHolder query, edu.villanova.siquest.webservice.siQuestSOAP.holders.ResultTypeArrayHolder result) throws java.rmi.RemoteException {

    	Set<String> sourceFiles = new HashSet<String>(filename.length);
    	for (int i = 0; i < filename.length; i++) {
			sourceFiles.add(filename[i]);
		}
    	
    	HashMap<String, Object> hm = SqSearch.getResults(sourceFiles, analyzer, "");
    	
    	List<ResultType> rslts = (List<ResultType>) hm.get("Result");
    	ResultType[] rsltArray = rslts.toArray(new ResultType[10]);
    	
    	status.value = (String) hm.get("Status");
        query.value = (QueryType) hm.get("Query");
//        query.value = new edu.villanova.siquest.webservice.siQuestSOAP.QueryType();
        result.value = rsltArray;
    }

}
