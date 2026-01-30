/**
 * PedidoAdicionalResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.pedidosPBLWS;

public class PedidoAdicionalResponse  implements java.io.Serializable {
    private java.math.BigInteger centro;

    private java.math.BigInteger referencia;

    private java.math.BigInteger identificador;

    private java.lang.String codigoRespuesta;

    private java.lang.String descripcionRespuesta;

    private es.eroski.misumi.model.pedidosPBLWS.ArrayOfPedidoAdicionalResponseLineaLinea[] lineas;

    public PedidoAdicionalResponse() {
    }

    public PedidoAdicionalResponse(
           java.math.BigInteger centro,
           java.math.BigInteger referencia,
           java.math.BigInteger identificador,
           java.lang.String codigoRespuesta,
           java.lang.String descripcionRespuesta,
           es.eroski.misumi.model.pedidosPBLWS.ArrayOfPedidoAdicionalResponseLineaLinea[] lineas) {
           this.centro = centro;
           this.referencia = referencia;
           this.identificador = identificador;
           this.codigoRespuesta = codigoRespuesta;
           this.descripcionRespuesta = descripcionRespuesta;
           this.lineas = lineas;
    }


    /**
     * Gets the centro value for this PedidoAdicionalResponse.
     * 
     * @return centro
     */
    public java.math.BigInteger getCentro() {
        return centro;
    }


    /**
     * Sets the centro value for this PedidoAdicionalResponse.
     * 
     * @param centro
     */
    public void setCentro(java.math.BigInteger centro) {
        this.centro = centro;
    }


    /**
     * Gets the referencia value for this PedidoAdicionalResponse.
     * 
     * @return referencia
     */
    public java.math.BigInteger getReferencia() {
        return referencia;
    }


    /**
     * Sets the referencia value for this PedidoAdicionalResponse.
     * 
     * @param referencia
     */
    public void setReferencia(java.math.BigInteger referencia) {
        this.referencia = referencia;
    }


    /**
     * Gets the identificador value for this PedidoAdicionalResponse.
     * 
     * @return identificador
     */
    public java.math.BigInteger getIdentificador() {
        return identificador;
    }


    /**
     * Sets the identificador value for this PedidoAdicionalResponse.
     * 
     * @param identificador
     */
    public void setIdentificador(java.math.BigInteger identificador) {
        this.identificador = identificador;
    }


    /**
     * Gets the codigoRespuesta value for this PedidoAdicionalResponse.
     * 
     * @return codigoRespuesta
     */
    public java.lang.String getCodigoRespuesta() {
        return codigoRespuesta;
    }


    /**
     * Sets the codigoRespuesta value for this PedidoAdicionalResponse.
     * 
     * @param codigoRespuesta
     */
    public void setCodigoRespuesta(java.lang.String codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }


    /**
     * Gets the descripcionRespuesta value for this PedidoAdicionalResponse.
     * 
     * @return descripcionRespuesta
     */
    public java.lang.String getDescripcionRespuesta() {
        return descripcionRespuesta;
    }


    /**
     * Sets the descripcionRespuesta value for this PedidoAdicionalResponse.
     * 
     * @param descripcionRespuesta
     */
    public void setDescripcionRespuesta(java.lang.String descripcionRespuesta) {
        this.descripcionRespuesta = descripcionRespuesta;
    }


    /**
     * Gets the lineas value for this PedidoAdicionalResponse.
     * 
     * @return lineas
     */
    public es.eroski.misumi.model.pedidosPBLWS.ArrayOfPedidoAdicionalResponseLineaLinea[] getLineas() {
        return lineas;
    }


    /**
     * Sets the lineas value for this PedidoAdicionalResponse.
     * 
     * @param lineas
     */
    public void setLineas(es.eroski.misumi.model.pedidosPBLWS.ArrayOfPedidoAdicionalResponseLineaLinea[] lineas) {
        this.lineas = lineas;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PedidoAdicionalResponse)) return false;
        PedidoAdicionalResponse other = (PedidoAdicionalResponse) obj;
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
            ((this.identificador==null && other.getIdentificador()==null) || 
             (this.identificador!=null &&
              this.identificador.equals(other.getIdentificador()))) &&
            ((this.codigoRespuesta==null && other.getCodigoRespuesta()==null) || 
             (this.codigoRespuesta!=null &&
              this.codigoRespuesta.equals(other.getCodigoRespuesta()))) &&
            ((this.descripcionRespuesta==null && other.getDescripcionRespuesta()==null) || 
             (this.descripcionRespuesta!=null &&
              this.descripcionRespuesta.equals(other.getDescripcionRespuesta()))) &&
            ((this.lineas==null && other.getLineas()==null) || 
             (this.lineas!=null &&
              java.util.Arrays.equals(this.lineas, other.getLineas())));
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
        if (getIdentificador() != null) {
            _hashCode += getIdentificador().hashCode();
        }
        if (getCodigoRespuesta() != null) {
            _hashCode += getCodigoRespuesta().hashCode();
        }
        if (getDescripcionRespuesta() != null) {
            _hashCode += getDescripcionRespuesta().hashCode();
        }
        if (getLineas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getLineas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getLineas(), i);
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
        new org.apache.axis.description.TypeDesc(PedidoAdicionalResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "PedidoAdicionalResponse"));
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
        elemField.setFieldName("identificador");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "Identificador"));
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
        elemField.setFieldName("lineas");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "Lineas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", ">ArrayOfPedidoAdicionalResponseLinea>Linea"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "Linea"));
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
