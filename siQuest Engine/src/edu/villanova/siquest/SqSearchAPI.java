package edu.villanova.siquest;

import java.util.List;


/** enum to be used when invoking the API */
public enum SqSearchAPI
{
	GOOGLE("Google"),
	YAHOO("Yahoo"),
	MS_LIVE("MS_Live");
	
	
	private String name;
	
	private SqSearchAPI(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	
	public String toString()
	{
		return getName();
	}
	
	public static SqSearchAPI getAPI(String name)
	{
		if (name == null)
			return null;
		
		if (name.equalsIgnoreCase("google"))
			return GOOGLE;
		if (name.equalsIgnoreCase("yahoo"))
			return YAHOO;
		if (name.toLowerCase().matches("((ms|microsoft)[-_]?)?live"))
			return MS_LIVE;
		
		return null;
	}
	
	
	public SqQuery getQuery(List<SqTerm> termList)
	{
		if (this == GOOGLE)
		{
			return new SqGoogleQuery(termList);
		}
		else if (this == YAHOO)
		{
			return new SqYahooQuery(termList);
		}
		else if (this == MS_LIVE)
		{
			return new SqMSLiveQuery(termList);
		}
		
		return null;
	}
}
