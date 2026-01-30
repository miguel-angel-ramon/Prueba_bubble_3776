/**
 * ReferenciaModType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.alarmasPLUSWS;

public class ReferenciaModType  implements java.io.Serializable {
    private java.math.BigInteger codigoReferencia;

    private java.math.BigInteger grupoBalanza;

    private java.math.BigInteger PLU_ANTIGUO;

    private java.math.BigInteger PLU;

    public ReferenciaModType() {
    }

    public ReferenciaModType(
           java.math.BigInteger codigoReferencia,
           java.math.BigInteger grupoBalanza,
           java.math.BigInteger PLU_ANTIGUO,
           java.math.BigInteger PLU) {
           this.codigoReferencia = codigoReferencia;
           this.grupoBalanza = grupoBalanza;
           this.PLU_ANTIGUO = PLU_ANTIGUO;
           this.PLU = PLU;
    }


    /**
     * Gets the codigoReferencia value for this ReferenciaModType.
     * 
     * @return codigoReferencia
     */
    public java.math.BigInteger getCodigoReferencia() {
        return codigoReferencia;
    }


    /**
     * Sets the codigoReferencia value for this ReferenciaModType.
     * 
     * @param codigoReferencia
     */
    public void setCodigoReferencia(java.math.BigInteger codigoReferencia) {
        this.codigoReferencia = codigoReferencia;
    }


    /**
     * Gets the grupoBalanza value for this ReferenciaModType.
     * 
     * @return grupoBalanza
     */
    public java.math.BigInteger getGrupoBalanza() {
        return grupoBalanza;
    }


    /**
     * Sets the grupoBalanza value for this ReferenciaModType.
     * 
     * @param grupoBalanza
     */
    public void setGrupoBalanza(java.math.BigInteger grupoBalanza) {
        this.grupoBalanza = grupoBalanza;
    }


    /**
     * Gets the PLU_ANTIGUO value for this ReferenciaModType.
     * 
     * @return PLU_ANTIGUO
     */
    public java.math.BigInteger getPLU_ANTIGUO() {
        return PLU_ANTIGUO;
    }


    /**
     * Sets the PLU_ANTIGUO value for this ReferenciaModType.
     * 
     * @param PLU_ANTIGUO
     */
    public void setPLU_ANTIGUO(java.math.BigInteger PLU_ANTIGUO) {
        this.PLU_ANTIGUO = PLU_ANTIGUO;
    }


    /**
     * Gets the PLU value for this ReferenciaModType.
     * 
     * @return PLU
     */
    public java.math.BigInteger getPLU() {
        return PLU;
    }


    /**
     * Sets the PLU value for this ReferenciaModType.
     * 
     * @param PLU
     */
    public void setPLU(java.math.BigInteger PLU) {
        this.PLU = PLU;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReferenciaModType)) return false;
        ReferenciaModType other = (ReferenciaModType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codigoReferencia==null && other.getCodigoReferencia()==null) || 
             (this.codigoReferencia!=null &&
              this.codigoReferencia.equals(other.getCodigoReferencia()))) &&
            ((this.grupoBalanza==null && other.getGrupoBalanza()==null) || 
             (this.grupoBalanza!=null &&
              this.grupoBalanza.equals(other.getGrupoBalanza()))) &&
            ((this.PLU_ANTIGUO==null && other.getPLU_ANTIGUO()==null) || 
             (this.PLU_ANTIGUO!=null &&
              this.PLU_ANTIGUO.equals(other.getPLU_ANTIGUO()))) &&
            ((this.PLU==null && other.getPLU()==null) || 
             (this.PLU!=null &&
              this.PLU.equals(other.getPLU())));
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
        if (getCodigoReferencia() != null) {
            _hashCode += getCodigoReferencia().hashCode();
        }
        if (getGrupoBalanza() != null) {
            _hashCode += getGrupoBalanza().hashCode();
        }
        if (getPLU_ANTIGUO() != null) {
            _hashCode += getPLU_ANTIGUO().hashCode();
        }
        if (getPLU() != null) {
            _hashCode += getPLU().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ReferenciaModType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/PLUSTienda", "ReferenciaModType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoReferencia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/PLUSTienda", "CodigoReferencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("grupoBalanza");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/PLUSTienda", "GrupoBalanza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("PLU_ANTIGUO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/PLUSTienda", "PLU_ANTIGUO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("PLU");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/PLUSTienda", "PLU"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
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
