/**
 * SiQuestSOAP_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package edu.villanova.siquest.webservice.siQuestSOAP;

public interface SiQuestSOAP_PortType extends java.rmi.Remote {
    public java.lang.String checkStatus(java.lang.String token, java.lang.String uploadDir) throws java.rmi.RemoteException;
    public void getQuery(java.lang.String[] filename, java.lang.String analyzer, java.lang.String[] searchService, javax.xml.rpc.holders.StringHolder status, edu.villanova.siquest.webservice.siQuestSOAP.holders.TermTypeArrayHolder term) throws java.rmi.RemoteException;
    public void getResults(java.lang.String[] filename, java.lang.String analyzer, java.lang.String[] searchService, javax.xml.rpc.holders.StringHolder status, edu.villanova.siquest.webservice.siQuestSOAP.holders.QueryTypeHolder query, edu.villanova.siquest.webservice.siQuestSOAP.holders.ResultTypeArrayHolder result) throws java.rmi.RemoteException;
}
