package edu.villanova.siquest.webservice.search;

import java.util.HashMap;
import java.util.HashSet;

import edu.villanova.siquest.SqQuery;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HashSet<String> sourceFiles = new HashSet<String>();
		sourceFiles.add("C:/Users/Tarun/Documents/CSC9010/AliceInWonderland.txt");
		HashMap<String, SqQuery> query = SqSearch.getQuery(sourceFiles, null);

	}

}
