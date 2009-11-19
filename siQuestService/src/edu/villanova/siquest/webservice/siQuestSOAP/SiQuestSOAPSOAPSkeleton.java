/**
 * SiQuestSOAPSOAPSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package edu.villanova.siquest.webservice.siQuestSOAP;

public class SiQuestSOAPSOAPSkeleton implements edu.villanova.siquest.webservice.siQuestSOAP.SiQuestSOAP_PortType, org.apache.axis.wsdl.Skeleton {
    private edu.villanova.siquest.webservice.siQuestSOAP.SiQuestSOAP_PortType impl;
    private static java.util.Map _myOperations = new java.util.Hashtable();
    private static java.util.Collection _myOperationsList = new java.util.ArrayList();

    /**
    * Returns List of OperationDesc objects with this name
    */
    public static java.util.List getOperationDescByName(java.lang.String methodName) {
        return (java.util.List)_myOperations.get(methodName);
    }

    /**
    * Returns Collection of OperationDescs
    */
    public static java.util.Collection getOperationDescs() {
        return _myOperationsList;
    }

    static {
        org.apache.axis.description.OperationDesc _oper;
        org.apache.axis.description.FaultDesc _fault;
        org.apache.axis.description.ParameterDesc [] _params;
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "uploadDir"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("checkStatus", _params, new javax.xml.namespace.QName("", "status"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://webservice.siquest.villanova.edu/siQuestSOAP/", "checkStatus"));
        _oper.setSoapAction("http://webservice.siquest.villanova.edu/siQuestSOAP/checkStatus");
        _myOperationsList.add(_oper);
        if (_myOperations.get("checkStatus") == null) {
            _myOperations.put("checkStatus", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("checkStatus")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "filename"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String[].class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "analyzer"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "searchService"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String[].class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "status"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "term"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://webservice.siquest.villanova.edu/siQuestSOAP/", "termType"), edu.villanova.siquest.webservice.siQuestSOAP.TermType[].class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getQuery", _params, null);
        _oper.setElementQName(new javax.xml.namespace.QName("http://webservice.siquest.villanova.edu/siQuestSOAP/", "getQuery"));
        _oper.setSoapAction("http://webservice.siquest.villanova.edu/siQuestSOAP/getQuery");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getQuery") == null) {
            _myOperations.put("getQuery", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getQuery")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "filename"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String[].class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "analyzer"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "searchService"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String[].class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "status"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "query"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://webservice.siquest.villanova.edu/siQuestSOAP/", "queryType"), edu.villanova.siquest.webservice.siQuestSOAP.QueryType.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "result"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://webservice.siquest.villanova.edu/siQuestSOAP/", "resultType"), edu.villanova.siquest.webservice.siQuestSOAP.ResultType[].class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getResults", _params, null);
        _oper.setElementQName(new javax.xml.namespace.QName("http://webservice.siquest.villanova.edu/siQuestSOAP/", "getResults"));
        _oper.setSoapAction("http://webservice.siquest.villanova.edu/siQuestSOAP/getResults");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getResults") == null) {
            _myOperations.put("getResults", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getResults")).add(_oper);
    }

    public SiQuestSOAPSOAPSkeleton() {
        this.impl = new edu.villanova.siquest.webservice.siQuestSOAP.SiQuestSOAPSOAPImpl();
    }

    public SiQuestSOAPSOAPSkeleton(edu.villanova.siquest.webservice.siQuestSOAP.SiQuestSOAP_PortType impl) {
        this.impl = impl;
    }
    public java.lang.String checkStatus(java.lang.String token, java.lang.String uploadDir) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.checkStatus(token, uploadDir);
        return ret;
    }

    public void getQuery(java.lang.String[] filename, java.lang.String analyzer, java.lang.String[] searchService, javax.xml.rpc.holders.StringHolder status, edu.villanova.siquest.webservice.siQuestSOAP.holders.TermTypeArrayHolder term) throws java.rmi.RemoteException
    {
        impl.getQuery(filename, analyzer, searchService, status, term);
    }

    public void getResults(java.lang.String[] filename, java.lang.String analyzer, java.lang.String[] searchService, javax.xml.rpc.holders.StringHolder status, edu.villanova.siquest.webservice.siQuestSOAP.holders.QueryTypeHolder query, edu.villanova.siquest.webservice.siQuestSOAP.holders.ResultTypeArrayHolder result) throws java.rmi.RemoteException
    {
        impl.getResults(filename, analyzer, searchService, status, query, result);
    }

}
