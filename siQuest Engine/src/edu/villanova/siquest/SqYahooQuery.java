package edu.villanova.siquest;

import java.util.List;


public class SqYahooQuery extends SqQuery {

	public SqYahooQuery(SqTerm... terms)
    {
        super(terms);
    }

	public SqYahooQuery(List<SqTerm> termList)
    {
        super(termList);
    }

    @Override
    public String getString() {
        // TODO Auto-generated method stub
        return null;
    }

}
