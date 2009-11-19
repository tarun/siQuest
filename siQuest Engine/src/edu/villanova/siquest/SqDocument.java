package edu.villanova.siquest;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Collections;

public class SqDocument 
{
  private int docId;
  private String path;
  private String lastModified;
  private Hashtable<String, SqTerm> terms;
  private Hashtable<String, ArrayList<Integer>> termPositions;
  private Hashtable<String, ArrayList<SqTermOffset>> termOffsets;
  
  public SqDocument()
  {
    docId = -1;
    path = null;
    lastModified = null;
    terms = new Hashtable<String, SqTerm>();
    termPositions = new Hashtable<String, ArrayList<Integer>>();
    termOffsets = new Hashtable<String, ArrayList<SqTermOffset>>();
  }
  
  public SqDocument(int docId, String path, String lastModified)
  {
    this.docId = docId;
    this.path = path;
    this.lastModified = lastModified;
    terms = new Hashtable<String, SqTerm>();
    termPositions = new Hashtable<String, ArrayList<Integer>>();
  }
  
  public void setDocId(int docId)
  {
    this.docId = docId;
  }
  
  public int getDocId()
  {
    return docId;
  }
  
  public void setPath(String path)
  {
    this.path = path;
  }
  
  public String getPath()
  {
    return path;
  }
  
  public void setLastModified(String lastModified)
  {
    this.lastModified = lastModified;
  }
  
  public String getLastModified()
  {
    return lastModified;
  }

  public void addTerm(SqTerm term)
  {
    if(term.getText() != null && terms.contains(term.getText()))
    {
      terms.remove(term.getText());
      terms.put(term.getText(), term); 
    }
    else if(term.getText() != null && !terms.contains(term.getText()))
    {
      terms.put(term.getText(), term);
    }
  }
  
  public SqTerm getTerm(String text)
  {
    SqTerm term = terms.get(text);
    
    return term;
  }
  
  /*
   * Return all the terms within the document sorted by frequency
   */
  public ArrayList<SqTerm> getTerms()
  {
    ArrayList<SqTerm> temp = new ArrayList(terms.values());
    Collections.sort(temp, new SqTermComparator(SqTermComparator.FREQ, true));
    
    return temp;
  }
  
  public void addTermPosition(String text, int position)
  {
    if(termPositions.containsKey(text))
    {
      ArrayList<Integer> positions = termPositions.remove(text);
      
      if(positions == null)
      {
        positions = new ArrayList<Integer>();
        positions.add(new Integer(position));
      
        termPositions.put(text, positions);
      }
      else
      {
        positions.add(new Integer(position));
        termPositions.put(text, positions);
      }
    }
    else
    {
      ArrayList<Integer> temp = new ArrayList<Integer>();
      temp.add(new Integer(position));
      
      termPositions.put(text, temp);
    }
  }
  
  public ArrayList<Integer> getTermPositions(String text)
  {
    ArrayList<Integer> positions = termPositions.get(text);
    
    return positions;
  }
  
  public void addTermOffset(String text, SqTermOffset offset)
  {
    if(termPositions.containsKey(text))
    {
      ArrayList<SqTermOffset> offsets = termOffsets.remove(text);
      
      if(offsets == null)
      {
        offsets = new ArrayList<SqTermOffset>();
        offsets.add(offset);
      
        termOffsets.put(text, offsets);
      }
      else
      {
        offsets.add(offset);
        termOffsets.put(text, offsets);
      }
    }
    else
    {
      ArrayList<SqTermOffset> temp = new ArrayList<SqTermOffset>();
      temp.add(offset);
      
      termOffsets.put(text, temp);
    }
  }
  
  public ArrayList<SqTermOffset> getTermOffsets(String text)
  {
    ArrayList<SqTermOffset> offsets = termOffsets.get(text);
    
    return offsets;
  }
  
//  @Override
//  public String toString()
//  {
//    String toString = "Doc Id: " + docId + "\n" +
//                      "Path: " + path + "\n" +
//                      "Terms: \n";
//    
//    Collection<SqTerm> termCollection = terms.values();
//    //Collections.sort(termCollection);
//    
//    for(SqTerm term : termCollection)
//    {
//      toString += "\t" + term.toString();
//      
//      //ArrayList<int> termPositions.get(term.getText());
//    }
//    
//    return toString;
//  }
}
