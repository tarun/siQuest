package edu.villanova.siquest;

public class SqTermOffset 
{
  private int startOffset;
  private int endOffset;
  
  public SqTermOffset(int startOffset, int endOffset)
  {
    this.startOffset = startOffset;
    this.endOffset = endOffset;
  }
  
  public void setStartOffset(int startOffset)
  {
    this.startOffset = startOffset;
  }
  
  public int getStartOffset()
  {
    return startOffset;
  }
  
  public void setEndOffset(int endOffset)
  {
    this.endOffset = endOffset;
  }
  
  public int getEndOffset()
  {
    return endOffset;
  }
}
