/**
 * PLUSType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.alarmasPLUSWS;

public class PLUSType  implements java.io.Serializable {
    private java.math.BigInteger grupoBalanza;

    private java.lang.String denominacionBalanza;

    private java.math.BigInteger PLU;

    public PLUSType() {
    }

    public PLUSType(
           java.math.BigInteger grupoBalanza,
           java.lang.String denominacionBalanza,
           java.math.BigInteger PLU) {
           this.grupoBalanza = grupoBalanza;
           this.denominacionBalanza = denominacionBalanza;
           this.PLU = PLU;
    }


    /**
     * Gets the grupoBalanza value for this PLUSType.
     * 
     * @return grupoBalanza
     */
    public java.math.BigInteger getGrupoBalanza() {
        return grupoBalanza;
    }


    /**
     * Sets the grupoBalanza value for this PLUSType.
     * 
     * @param grupoBalanza
     */
    public void setGrupoBalanza(java.math.BigInteger grupoBalanza) {
        this.grupoBalanza = grupoBalanza;
    }


    /**
     * Gets the denominacionBalanza value for this PLUSType.
     * 
     * @return denominacionBalanza
     */
    public java.lang.String getDenominacionBalanza() {
        return denominacionBalanza;
    }


    /**
     * Sets the denominacionBalanza value for this PLUSType.
     * 
     * @param denominacionBalanza
     */
    public void setDenominacionBalanza(java.lang.String denominacionBalanza) {
        this.denominacionBalanza = denominacionBalanza;
    }


    /**
     * Gets the PLU value for this PLUSType.
     * 
     * @return PLU
     */
    public java.math.BigInteger getPLU() {
        return PLU;
    }


    /**
     * Sets the PLU value for this PLUSType.
     * 
     * @param PLU
     */
    public void setPLU(java.math.BigInteger PLU) {
        this.PLU = PLU;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PLUSType)) return false;
        PLUSType other = (PLUSType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.grupoBalanza==null && other.getGrupoBalanza()==null) || 
             (this.grupoBalanza!=null &&
              this.grupoBalanza.equals(other.getGrupoBalanza()))) &&
            ((this.denominacionBalanza==null && other.getDenominacionBalanza()==null) || 
             (this.denominacionBalanza!=null &&
              this.denominacionBalanza.equals(other.getDenominacionBalanza()))) &&
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
        if (getGrupoBalanza() != null) {
            _hashCode += getGrupoBalanza().hashCode();
        }
        if (getDenominacionBalanza() != null) {
            _hashCode += getDenominacionBalanza().hashCode();
        }
        if (getPLU() != null) {
            _hashCode += getPLU().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PLUSType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/PLUSTienda", "PLUSType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("grupoBalanza");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/PLUSTienda", "GrupoBalanza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("denominacionBalanza");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/PLUSTienda", "DenominacionBalanza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
