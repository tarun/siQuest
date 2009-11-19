/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.villanova.siquest;

import java.util.Comparator;

/**
 *
 * @author narvaeza
 */
public class SqTermComparator implements Comparator<SqTerm> 
{
  public static final int FIELD = 0;
  public static final int TEXT = 1;
  public static final int FREQ = 2;
  public static final int SCORE = 3;
  
  private int comparisonParam;
  private boolean ascending;

  public SqTermComparator() 
  {
    this.comparisonParam = TEXT;
    this.ascending = true;
  }

  public SqTermComparator(int compareParam, boolean ascending) 
  {
    this.comparisonParam = compareParam;
    this.ascending = ascending;
  }

  public int compare(SqTerm term1, SqTerm term2) 
  {
    int offset = 0;

    switch(comparisonParam) 
    {
      case FIELD:
        offset = ascending ? term1.getField().compareTo(term2.getField()) : term2.getField().compareTo(term1.getField());
        break;
      case TEXT:
        offset = ascending ? term1.compareTo(term2) : term2.compareTo(term1);
        break;
      case FREQ:
        offset = ascending ? term1.getFreq() - term2.getFreq() : term2.getFreq() - term1.getFreq();
        break;
      case SCORE:
          offset = ascending ? term1.getScore() - term2.getScore() : term2.getScore() - term1.getScore();
          break;
      default:
        offset = ascending ? term1.compareTo(term2) : term2.compareTo(term1);
        break;
    }

    return offset;
  }
}
