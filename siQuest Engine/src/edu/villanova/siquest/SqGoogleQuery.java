package edu.villanova.siquest;

import java.util.List;


public class SqGoogleQuery extends SqQuery
{
    
    public SqGoogleQuery(SqTerm... terms)
    {
        super(terms);
    }
    
    public SqGoogleQuery(List<SqTerm> termList)
    {
        super(termList);
    }
    
    @Override
    public String getString() {
        // TODO Auto-generated method stub
        return null;
    }

}
