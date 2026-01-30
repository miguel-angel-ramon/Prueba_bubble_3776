/**
 * ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.pedidosPBLWS;

public class ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo  implements java.io.Serializable {
    private java.math.BigInteger centro;

    private java.math.BigInteger referencia;

    private java.lang.String codigoRespuesta;

    private java.lang.String descripcionRespuesta;

    private es.eroski.misumi.model.pedidosPBLWS.ArrayOfValidarArticuloResponseValidarArticuloValidarArticuloDatos datos;

    public ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo() {
    }

    public ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo(
           java.math.BigInteger centro,
           java.math.BigInteger referencia,
           java.lang.String codigoRespuesta,
           java.lang.String descripcionRespuesta,
           es.eroski.misumi.model.pedidosPBLWS.ArrayOfValidarArticuloResponseValidarArticuloValidarArticuloDatos datos) {
           this.centro = centro;
           this.referencia = referencia;
           this.codigoRespuesta = codigoRespuesta;
           this.descripcionRespuesta = descripcionRespuesta;
           this.datos = datos;
    }


    /**
     * Gets the centro value for this ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo.
     * 
     * @return centro
     */
    public java.math.BigInteger getCentro() {
        return centro;
    }


    /**
     * Sets the centro value for this ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo.
     * 
     * @param centro
     */
    public void setCentro(java.math.BigInteger centro) {
        this.centro = centro;
    }


    /**
     * Gets the referencia value for this ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo.
     * 
     * @return referencia
     */
    public java.math.BigInteger getReferencia() {
        return referencia;
    }


    /**
     * Sets the referencia value for this ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo.
     * 
     * @param referencia
     */
    public void setReferencia(java.math.BigInteger referencia) {
        this.referencia = referencia;
    }


    /**
     * Gets the codigoRespuesta value for this ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo.
     * 
     * @return codigoRespuesta
     */
    public java.lang.String getCodigoRespuesta() {
        return codigoRespuesta;
    }


    /**
     * Sets the codigoRespuesta value for this ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo.
     * 
     * @param codigoRespuesta
     */
    public void setCodigoRespuesta(java.lang.String codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }


    /**
     * Gets the descripcionRespuesta value for this ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo.
     * 
     * @return descripcionRespuesta
     */
    public java.lang.String getDescripcionRespuesta() {
        return descripcionRespuesta;
    }


    /**
     * Sets the descripcionRespuesta value for this ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo.
     * 
     * @param descripcionRespuesta
     */
    public void setDescripcionRespuesta(java.lang.String descripcionRespuesta) {
        this.descripcionRespuesta = descripcionRespuesta;
    }


    /**
     * Gets the datos value for this ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo.
     * 
     * @return datos
     */
    public es.eroski.misumi.model.pedidosPBLWS.ArrayOfValidarArticuloResponseValidarArticuloValidarArticuloDatos getDatos() {
        return datos;
    }


    /**
     * Sets the datos value for this ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo.
     * 
     * @param datos
     */
    public void setDatos(es.eroski.misumi.model.pedidosPBLWS.ArrayOfValidarArticuloResponseValidarArticuloValidarArticuloDatos datos) {
        this.datos = datos;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo)) return false;
        ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo other = (ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.centro==null && other.getCentro()==null) || 
             (this.centro!=null &&
              this.centro.equals(other.getCentro()))) &&
            ((this.referencia==null && other.getReferencia()==null) || 
             (this.referencia!=null &&
              this.referencia.equals(other.getReferencia()))) &&
            ((this.codigoRespuesta==null && other.getCodigoRespuesta()==null) || 
             (this.codigoRespuesta!=null &&
              this.codigoRespuesta.equals(other.getCodigoRespuesta()))) &&
            ((this.descripcionRespuesta==null && other.getDescripcionRespuesta()==null) || 
             (this.descripcionRespuesta!=null &&
              this.descripcionRespuesta.equals(other.getDescripcionRespuesta()))) &&
            ((this.datos==null && other.getDatos()==null) || 
             (this.datos!=null &&
              this.datos.equals(other.getDatos())));
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
        if (getCentro() != null) {
            _hashCode += getCentro().hashCode();
        }
        if (getReferencia() != null) {
            _hashCode += getReferencia().hashCode();
        }
        if (getCodigoRespuesta() != null) {
            _hashCode += getCodigoRespuesta().hashCode();
        }
        if (getDescripcionRespuesta() != null) {
            _hashCode += getDescripcionRespuesta().hashCode();
        }
        if (getDatos() != null) {
            _hashCode += getDatos().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", ">ArrayOfValidarArticuloResponseValidarArticulo>ValidarArticulo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("centro");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "Centro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("referencia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "Referencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoRespuesta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "CodigoRespuesta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descripcionRespuesta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "DescripcionRespuesta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "Datos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", ">>ArrayOfValidarArticuloResponseValidarArticulo>ValidarArticulo>Datos"));
        elemField.setMinOccurs(0);
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
