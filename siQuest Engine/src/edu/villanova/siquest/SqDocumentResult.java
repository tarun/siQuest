package edu.villanova.siquest;

public class SqDocumentResult
{
  private SqDocument document;
  private float rank;
  
  public SqDocumentResult()
  {
  }
  
  public SqDocumentResult(SqDocument document, float rank)
  {
    this.document = document;
    this.rank = rank;
  }
  
  public void setDocument(SqDocument document)
  {
    this.document = document;
  }
  
  public SqDocument getDocument()
  {
    return document;
  }
  
  public void setRank(float rank)
  {
    this.rank = rank;
  }
  
  public float getRank()
  {
    return rank;
  }
}