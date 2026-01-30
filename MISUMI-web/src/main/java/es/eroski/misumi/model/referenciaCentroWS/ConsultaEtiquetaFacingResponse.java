/**
 * ConsultaEtiquetaFacingResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.referenciaCentroWS;

public class ConsultaEtiquetaFacingResponse  implements java.io.Serializable {
    private java.math.BigInteger codigoRespuesta;

    private java.lang.String descripcionRespuesta;

    private java.math.BigInteger tipoEtiqueta;

    public ConsultaEtiquetaFacingResponse() {
    }

    public ConsultaEtiquetaFacingResponse(
           java.math.BigInteger codigoRespuesta,
           java.lang.String descripcionRespuesta,
           java.math.BigInteger tipoEtiqueta) {
           this.codigoRespuesta = codigoRespuesta;
           this.descripcionRespuesta = descripcionRespuesta;
           this.tipoEtiqueta = tipoEtiqueta;
    }


    /**
     * Gets the codigoRespuesta value for this ConsultaEtiquetaFacingResponse.
     * 
     * @return codigoRespuesta
     */
    public java.math.BigInteger getCodigoRespuesta() {
        return codigoRespuesta;
    }


    /**
     * Sets the codigoRespuesta value for this ConsultaEtiquetaFacingResponse.
     * 
     * @param codigoRespuesta
     */
    public void setCodigoRespuesta(java.math.BigInteger codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }


    /**
     * Gets the descripcionRespuesta value for this ConsultaEtiquetaFacingResponse.
     * 
     * @return descripcionRespuesta
     */
    public java.lang.String getDescripcionRespuesta() {
        return descripcionRespuesta;
    }


    /**
     * Sets the descripcionRespuesta value for this ConsultaEtiquetaFacingResponse.
     * 
     * @param descripcionRespuesta
     */
    public void setDescripcionRespuesta(java.lang.String descripcionRespuesta) {
        this.descripcionRespuesta = descripcionRespuesta;
    }


    /**
     * Gets the tipoEtiqueta value for this ConsultaEtiquetaFacingResponse.
     * 
     * @return tipoEtiqueta
     */
    public java.math.BigInteger getTipoEtiqueta() {
        return tipoEtiqueta;
    }


    /**
     * Sets the tipoEtiqueta value for this ConsultaEtiquetaFacingResponse.
     * 
     * @param tipoEtiqueta
     */
    public void setTipoEtiqueta(java.math.BigInteger tipoEtiqueta) {
        this.tipoEtiqueta = tipoEtiqueta;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ConsultaEtiquetaFacingResponse)) return false;
        ConsultaEtiquetaFacingResponse other = (ConsultaEtiquetaFacingResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codigoRespuesta==null && other.getCodigoRespuesta()==null) || 
             (this.codigoRespuesta!=null &&
              this.codigoRespuesta.equals(other.getCodigoRespuesta()))) &&
            ((this.descripcionRespuesta==null && other.getDescripcionRespuesta()==null) || 
             (this.descripcionRespuesta!=null &&
              this.descripcionRespuesta.equals(other.getDescripcionRespuesta()))) &&
            ((this.tipoEtiqueta==null && other.getTipoEtiqueta()==null) || 
             (this.tipoEtiqueta!=null &&
              this.tipoEtiqueta.equals(other.getTipoEtiqueta())));
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
        if (getCodigoRespuesta() != null) {
            _hashCode += getCodigoRespuesta().hashCode();
        }
        if (getDescripcionRespuesta() != null) {
            _hashCode += getDescripcionRespuesta().hashCode();
        }
        if (getTipoEtiqueta() != null) {
            _hashCode += getTipoEtiqueta().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ConsultaEtiquetaFacingResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/KOSMOS/schema/ReferenciaCentroKN", ">ConsultaEtiquetaFacingResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoRespuesta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/KOSMOS/schema/ReferenciaCentroKN", "CodigoRespuesta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descripcionRespuesta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/KOSMOS/schema/ReferenciaCentroKN", "DescripcionRespuesta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoEtiqueta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/KOSMOS/schema/ReferenciaCentroKN", "TipoEtiqueta"));
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
