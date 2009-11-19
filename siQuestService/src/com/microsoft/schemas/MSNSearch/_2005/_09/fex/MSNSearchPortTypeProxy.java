package com.microsoft.schemas.MSNSearch._2005._09.fex;

public class MSNSearchPortTypeProxy implements com.microsoft.schemas.MSNSearch._2005._09.fex.MSNSearchPortType {
  private String _endpoint = null;
  private com.microsoft.schemas.MSNSearch._2005._09.fex.MSNSearchPortType mSNSearchPortType = null;
  
  public MSNSearchPortTypeProxy() {
    _initMSNSearchPortTypeProxy();
  }
  
  public MSNSearchPortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initMSNSearchPortTypeProxy();
  }
  
  private void _initMSNSearchPortTypeProxy() {
    try {
      mSNSearchPortType = (new com.microsoft.schemas.MSNSearch._2005._09.fex.MSNSearchServiceLocator()).getMSNSearchPort();
      if (mSNSearchPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)mSNSearchPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)mSNSearchPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (mSNSearchPortType != null)
      ((javax.xml.rpc.Stub)mSNSearchPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.microsoft.schemas.MSNSearch._2005._09.fex.MSNSearchPortType getMSNSearchPortType() {
    if (mSNSearchPortType == null)
      _initMSNSearchPortTypeProxy();
    return mSNSearchPortType;
  }
  
  public com.microsoft.schemas.MSNSearch._2005._09.fex.SearchResponse search(com.microsoft.schemas.MSNSearch._2005._09.fex.SearchRequest request) throws java.rmi.RemoteException{
    if (mSNSearchPortType == null)
      _initMSNSearchPortTypeProxy();
    return mSNSearchPortType.search(request);
  }
  
  
}