package edu.villanova.siquest;

public class SqTerm implements Comparable<SqTerm>
{
  private String field;
  private String text;
  private int freq;
  private int score;

  public void TermResult()
  {
    field = null;
    text = null;
    freq = 0;
    score = 0;
  }
  
  public void TermResult(String field, String text, int freq)
  {
    this.field = field;
    this.text = text;
    this.freq = freq;
    this.score = 0;
  }
  
  public void setField(String field)
  {
    this.field = field;
  }
  
  public String getField()
  {
    return field;
  }
  
  public void setText(String text)
  {
    this.text = text;
  }
  
  public String getText()
  {
    return text;
  }

  public void setFreq(int freq)
  {
    this.freq = freq;
  }
  
  public int getFreq()
  {
    return freq;
  }

  public int getScore() {
  	return score;
  }

  public void setScore(int score) {
  	this.score = score;
  }

  public int compareTo(SqTerm term) 
  {
    return text.compareTo(term.text);
  }
  
  @Override
  public String toString()
  {
    String toString = "Field: " + field + "\n" +
                      "Text:  " + text + "\n" +
                      "Freq:  " + freq + "\n" +
                      "Score: " + score + "\n";
    
    return toString;
  }
}
