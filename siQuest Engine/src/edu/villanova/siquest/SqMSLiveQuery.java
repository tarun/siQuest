package edu.villanova.siquest;

import java.util.List;


public class SqMSLiveQuery extends SqQuery
{

    public SqMSLiveQuery(SqTerm... terms)
    {
        super(terms);
    }

    public SqMSLiveQuery(List<SqTerm> termList)
    {
        super(termList);
    }

    @Override
    public String getString() {
        return null;
    }

}
