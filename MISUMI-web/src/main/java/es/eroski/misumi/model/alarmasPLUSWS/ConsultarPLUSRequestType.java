/**
 * ConsultarPLUSRequestType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.alarmasPLUSWS;

public class ConsultarPLUSRequestType  implements java.io.Serializable {
    private java.math.BigInteger codigoCentro;

    private java.lang.String tipoMantenimiento;

    private java.math.BigInteger codigoReferencia;

    public ConsultarPLUSRequestType() {
    }

    public ConsultarPLUSRequestType(
           java.math.BigInteger codigoCentro,
           java.lang.String tipoMantenimiento,
           java.math.BigInteger codigoReferencia) {
           this.codigoCentro = codigoCentro;
           this.tipoMantenimiento = tipoMantenimiento;
           this.codigoReferencia = codigoReferencia;
    }


    /**
     * Gets the codigoCentro value for this ConsultarPLUSRequestType.
     * 
     * @return codigoCentro
     */
    public java.math.BigInteger getCodigoCentro() {
        return codigoCentro;
    }


    /**
     * Sets the codigoCentro value for this ConsultarPLUSRequestType.
     * 
     * @param codigoCentro
     */
    public void setCodigoCentro(java.math.BigInteger codigoCentro) {
        this.codigoCentro = codigoCentro;
    }


    /**
     * Gets the tipoMantenimiento value for this ConsultarPLUSRequestType.
     * 
     * @return tipoMantenimiento
     */
    public java.lang.String getTipoMantenimiento() {
        return tipoMantenimiento;
    }


    /**
     * Sets the tipoMantenimiento value for this ConsultarPLUSRequestType.
     * 
     * @param tipoMantenimiento
     */
    public void setTipoMantenimiento(java.lang.String tipoMantenimiento) {
        this.tipoMantenimiento = tipoMantenimiento;
    }


    /**
     * Gets the codigoReferencia value for this ConsultarPLUSRequestType.
     * 
     * @return codigoReferencia
     */
    public java.math.BigInteger getCodigoReferencia() {
        return codigoReferencia;
    }


    /**
     * Sets the codigoReferencia value for this ConsultarPLUSRequestType.
     * 
     * @param codigoReferencia
     */
    public void setCodigoReferencia(java.math.BigInteger codigoReferencia) {
        this.codigoReferencia = codigoReferencia;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ConsultarPLUSRequestType)) return false;
        ConsultarPLUSRequestType other = (ConsultarPLUSRequestType) obj;
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
            ((this.tipoMantenimiento==null && other.getTipoMantenimiento()==null) || 
             (this.tipoMantenimiento!=null &&
              this.tipoMantenimiento.equals(other.getTipoMantenimiento()))) &&
            ((this.codigoReferencia==null && other.getCodigoReferencia()==null) || 
             (this.codigoReferencia!=null &&
              this.codigoReferencia.equals(other.getCodigoReferencia())));
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
        if (getTipoMantenimiento() != null) {
            _hashCode += getTipoMantenimiento().hashCode();
        }
        if (getCodigoReferencia() != null) {
            _hashCode += getCodigoReferencia().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ConsultarPLUSRequestType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/PLUSTienda", "ConsultarPLUSRequestType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoCentro");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/PLUSTienda", "CodigoCentro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoMantenimiento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/PLUSTienda", "TipoMantenimiento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoReferencia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/PLUSTienda", "CodigoReferencia"));
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
