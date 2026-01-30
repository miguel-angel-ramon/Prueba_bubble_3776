/**
 * ComunicarNoServidoRequestType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.inventarioWS;

public class ComunicarNoServidoRequestType  implements java.io.Serializable {
    private java.math.BigInteger codigoCentro;

    private es.eroski.misumi.model.inventarioWS.ReferenciaType[] listaReferencias;

    public ComunicarNoServidoRequestType() {
    }

    public ComunicarNoServidoRequestType(
           java.math.BigInteger codigoCentro,
           es.eroski.misumi.model.inventarioWS.ReferenciaType[] listaReferencias) {
           this.codigoCentro = codigoCentro;
           this.listaReferencias = listaReferencias;
    }


    /**
     * Gets the codigoCentro value for this ComunicarNoServidoRequestType.
     * 
     * @return codigoCentro
     */
    public java.math.BigInteger getCodigoCentro() {
        return codigoCentro;
    }


    /**
     * Sets the codigoCentro value for this ComunicarNoServidoRequestType.
     * 
     * @param codigoCentro
     */
    public void setCodigoCentro(java.math.BigInteger codigoCentro) {
        this.codigoCentro = codigoCentro;
    }


    /**
     * Gets the listaReferencias value for this ComunicarNoServidoRequestType.
     * 
     * @return listaReferencias
     */
    public es.eroski.misumi.model.inventarioWS.ReferenciaType[] getListaReferencias() {
        return listaReferencias;
    }


    /**
     * Sets the listaReferencias value for this ComunicarNoServidoRequestType.
     * 
     * @param listaReferencias
     */
    public void setListaReferencias(es.eroski.misumi.model.inventarioWS.ReferenciaType[] listaReferencias) {
        this.listaReferencias = listaReferencias;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ComunicarNoServidoRequestType)) return false;
        ComunicarNoServidoRequestType other = (ComunicarNoServidoRequestType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codigoCentro==null && other.getCodigoCentro()==null) || 
             (this.codigoCentro!=null &&
              this.codigoCentro.equals(other.getCodigoCentro()))) &&
            ((this.listaReferencias==null && other.getListaReferencias()==null) || 
             (this.listaReferencias!=null &&
              java.util.Arrays.equals(this.listaReferencias, other.getListaReferencias())));
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
        if (getCodigoCentro() != null) {
            _hashCode += getCodigoCentro().hashCode();
        }
        if (getListaReferencias() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getListaReferencias());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getListaReferencias(), i);
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
        new org.apache.axis.description.TypeDesc(ComunicarNoServidoRequestType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/gcpv/schema/inventarioTienda", "ComunicarNoServidoRequestType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoCentro");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/gcpv/schema/inventarioTienda", "codigoCentro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listaReferencias");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/gcpv/schema/inventarioTienda", "listaReferencias"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/gcpv/schema/inventarioTienda", "ReferenciaType"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.eroski.es/gcpv/schema/inventarioTienda", "referencia"));
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
