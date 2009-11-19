/**
 * QueryType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package edu.villanova.siquest.webservice.siQuestSOAP;

public class QueryType  implements java.io.Serializable {
    private edu.villanova.siquest.webservice.siQuestSOAP.TermType[] term;

    private java.lang.String[] searchService;

    public QueryType() {
    }

    public QueryType(
           edu.villanova.siquest.webservice.siQuestSOAP.TermType[] term,
           java.lang.String[] searchService) {
           this.term = term;
           this.searchService = searchService;
    }


    /**
     * Gets the term value for this QueryType.
     * 
     * @return term
     */
    public edu.villanova.siquest.webservice.siQuestSOAP.TermType[] getTerm() {
        return term;
    }


    /**
     * Sets the term value for this QueryType.
     * 
     * @param term
     */
    public void setTerm(edu.villanova.siquest.webservice.siQuestSOAP.TermType[] term) {
        this.term = term;
    }

    public edu.villanova.siquest.webservice.siQuestSOAP.TermType getTerm(int i) {
        return this.term[i];
    }

    public void setTerm(int i, edu.villanova.siquest.webservice.siQuestSOAP.TermType _value) {
        this.term[i] = _value;
    }


    /**
     * Gets the searchService value for this QueryType.
     * 
     * @return searchService
     */
    public java.lang.String[] getSearchService() {
        return searchService;
    }


    /**
     * Sets the searchService value for this QueryType.
     * 
     * @param searchService
     */
    public void setSearchService(java.lang.String[] searchService) {
        this.searchService = searchService;
    }

    public java.lang.String getSearchService(int i) {
        return this.searchService[i];
    }

    public void setSearchService(int i, java.lang.String _value) {
        this.searchService[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryType)) return false;
        QueryType other = (QueryType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.term==null && other.getTerm()==null) || 
             (this.term!=null &&
              java.util.Arrays.equals(this.term, other.getTerm()))) &&
            ((this.searchService==null && other.getSearchService()==null) || 
             (this.searchService!=null &&
              java.util.Arrays.equals(this.searchService, other.getSearchService())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getTerm() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTerm());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTerm(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSearchService() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSearchService());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSearchService(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservice.siquest.villanova.edu/siQuestSOAP/", "queryType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("term");
        elemField.setXmlName(new javax.xml.namespace.QName("", "term"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservice.siquest.villanova.edu/siQuestSOAP/", "termType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("searchService");
        elemField.setXmlName(new javax.xml.namespace.QName("", "searchService"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
