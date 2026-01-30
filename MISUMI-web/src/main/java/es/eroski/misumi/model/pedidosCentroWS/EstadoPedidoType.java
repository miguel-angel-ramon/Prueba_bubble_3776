/**
 * EstadoPedidoType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.pedidosCentroWS;

public class EstadoPedidoType  implements java.io.Serializable {
    private java.math.BigInteger codigoError;

    private java.lang.String mensajeError;

    private java.math.BigInteger codigoPedido;

    private java.lang.String estado;

    private java.util.Calendar fechaEstado;

    public EstadoPedidoType() {
    }

    public EstadoPedidoType(
           java.math.BigInteger codigoError,
           java.lang.String mensajeError,
           java.math.BigInteger codigoPedido,
           java.lang.String estado,
           java.util.Calendar fechaEstado) {
           this.codigoError = codigoError;
           this.mensajeError = mensajeError;
           this.codigoPedido = codigoPedido;
           this.estado = estado;
           this.fechaEstado = fechaEstado;
    }


    /**
     * Gets the codigoError value for this EstadoPedidoType.
     * 
     * @return codigoError
     */
    public java.math.BigInteger getCodigoError() {
        return codigoError;
    }


    /**
     * Sets the codigoError value for this EstadoPedidoType.
     * 
     * @param codigoError
     */
    public void setCodigoError(java.math.BigInteger codigoError) {
        this.codigoError = codigoError;
    }


    /**
     * Gets the mensajeError value for this EstadoPedidoType.
     * 
     * @return mensajeError
     */
    public java.lang.String getMensajeError() {
        return mensajeError;
    }


    /**
     * Sets the mensajeError value for this EstadoPedidoType.
     * 
     * @param mensajeError
     */
    public void setMensajeError(java.lang.String mensajeError) {
        this.mensajeError = mensajeError;
    }


    /**
     * Gets the codigoPedido value for this EstadoPedidoType.
     * 
     * @return codigoPedido
     */
    public java.math.BigInteger getCodigoPedido() {
        return codigoPedido;
    }


    /**
     * Sets the codigoPedido value for this EstadoPedidoType.
     * 
     * @param codigoPedido
     */
    public void setCodigoPedido(java.math.BigInteger codigoPedido) {
        this.codigoPedido = codigoPedido;
    }


    /**
     * Gets the estado value for this EstadoPedidoType.
     * 
     * @return estado
     */
    public java.lang.String getEstado() {
        return estado;
    }


    /**
     * Sets the estado value for this EstadoPedidoType.
     * 
     * @param estado
     */
    public void setEstado(java.lang.String estado) {
        this.estado = estado;
    }


    /**
     * Gets the fechaEstado value for this EstadoPedidoType.
     * 
     * @return fechaEstado
     */
    public java.util.Calendar getFechaEstado() {
        return fechaEstado;
    }


    /**
     * Sets the fechaEstado value for this EstadoPedidoType.
     * 
     * @param fechaEstado
     */
    public void setFechaEstado(java.util.Calendar fechaEstado) {
        this.fechaEstado = fechaEstado;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EstadoPedidoType)) return false;
        EstadoPedidoType other = (EstadoPedidoType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codigoError==null && other.getCodigoError()==null) || 
             (this.codigoError!=null &&
              this.codigoError.equals(other.getCodigoError()))) &&
            ((this.mensajeError==null && other.getMensajeError()==null) || 
             (this.mensajeError!=null &&
              this.mensajeError.equals(other.getMensajeError()))) &&
            ((this.codigoPedido==null && other.getCodigoPedido()==null) || 
             (this.codigoPedido!=null &&
              this.codigoPedido.equals(other.getCodigoPedido()))) &&
            ((this.estado==null && other.getEstado()==null) || 
             (this.estado!=null &&
              this.estado.equals(other.getEstado()))) &&
            ((this.fechaEstado==null && other.getFechaEstado()==null) || 
             (this.fechaEstado!=null &&
              this.fechaEstado.equals(other.getFechaEstado())));
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
        if (getCodigoError() != null) {
            _hashCode += getCodigoError().hashCode();
        }
        if (getMensajeError() != null) {
            _hashCode += getMensajeError().hashCode();
        }
        if (getCodigoPedido() != null) {
            _hashCode += getCodigoPedido().hashCode();
        }
        if (getEstado() != null) {
            _hashCode += getEstado().hashCode();
        }
        if (getFechaEstado() != null) {
            _hashCode += getFechaEstado().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EstadoPedidoType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/GCNP/schema/PedidosCentro", "EstadoPedidoType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoError");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCNP/schema/PedidosCentro", "CodigoError"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mensajeError");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCNP/schema/PedidosCentro", "MensajeError"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoPedido");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCNP/schema/PedidosCentro", "CodigoPedido"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estado");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCNP/schema/PedidosCentro", "Estado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaEstado");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCNP/schema/PedidosCentro", "FechaEstado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
