package edu.villanova.siquest;

import java.util.ArrayList;
import java.util.List;


public abstract class SqQuery
{
	/** all different query types */
	public static enum Types
	{
		EXACT_QUERY,
		AND_QUERY,
		OR_QUERY,
		EXCLUDE_QUERY,
		FILE_TYPE,
		SITE_QUERY,
		NUM_RANGE,
		LINK_QUERY,
		DEFINE_QUERY,
		INTITLE_QUERY,
		INURL_QUERY,
		LOCATION_QUERY,
		RELATED_QUERY,
		STEM_QUERY,
		DOMAIN_LINK,
		KEYWORD_QUERY,
		META_TAGS,
		TABLE_QUERY,
		BOOKS_QUERY,
		INORDER_QUERY,
	}
	
	
	/** composite query string */
	protected SqQueryPhrase compositeQuery;
	
	/** list of all keyword terms */
	protected List<SqTerm> termList;
	
	public SqQuery(SqTerm... terms)
	{
		this.termList = new ArrayList<SqTerm>(terms.length);
		for (SqTerm term : terms)
			this.termList.add(term);
	}
	
	public SqQuery(List<SqTerm> termList)
	{
	    this.termList = termList;
	}

	
	public List<SqTerm> getTermList() {
		return termList;
	}

		
	/** overridden to enforce implementation of unique string representation */
	public String toString()
	{	
		if (compositeQuery == null)
		{
			// TEMP - for debugging
			StringBuilder sb = new StringBuilder();
			for (SqTerm term : termList)
			{
				//sb.append(term.toString()).append("\n");
				sb.append(term.getText()).append(" ");
			}
			return sb.toString();
		}
		
		return getString();
	}
	
	
	/** abstract method to be implemented by subclasses */
	public abstract String getString();
	
}
