/**
 * DetalladoPedidoModificarReponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.pedidosPBLWS;

public class DetalladoPedidoModificarReponse  implements java.io.Serializable {
    private java.lang.String codigoRespuesta;

    private java.lang.String descripcionRespuesta;

    private es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoResponse[] detalladosPedido;

    public DetalladoPedidoModificarReponse() {
    }

    public DetalladoPedidoModificarReponse(
           java.lang.String codigoRespuesta,
           java.lang.String descripcionRespuesta,
           es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoResponse[] detalladosPedido) {
           this.codigoRespuesta = codigoRespuesta;
           this.descripcionRespuesta = descripcionRespuesta;
           this.detalladosPedido = detalladosPedido;
    }


    /**
     * Gets the codigoRespuesta value for this DetalladoPedidoModificarReponse.
     * 
     * @return codigoRespuesta
     */
    public java.lang.String getCodigoRespuesta() {
        return codigoRespuesta;
    }


    /**
     * Sets the codigoRespuesta value for this DetalladoPedidoModificarReponse.
     * 
     * @param codigoRespuesta
     */
    public void setCodigoRespuesta(java.lang.String codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }


    /**
     * Gets the descripcionRespuesta value for this DetalladoPedidoModificarReponse.
     * 
     * @return descripcionRespuesta
     */
    public java.lang.String getDescripcionRespuesta() {
        return descripcionRespuesta;
    }


    /**
     * Sets the descripcionRespuesta value for this DetalladoPedidoModificarReponse.
     * 
     * @param descripcionRespuesta
     */
    public void setDescripcionRespuesta(java.lang.String descripcionRespuesta) {
        this.descripcionRespuesta = descripcionRespuesta;
    }


    /**
     * Gets the detalladosPedido value for this DetalladoPedidoModificarReponse.
     * 
     * @return detalladosPedido
     */
    public es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoResponse[] getDetalladosPedido() {
        return detalladosPedido;
    }


    /**
     * Sets the detalladosPedido value for this DetalladoPedidoModificarReponse.
     * 
     * @param detalladosPedido
     */
    public void setDetalladosPedido(es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoResponse[] detalladosPedido) {
        this.detalladosPedido = detalladosPedido;
    }

    public es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoResponse getDetalladosPedido(int i) {
        return this.detalladosPedido[i];
    }

    public void setDetalladosPedido(int i, es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoResponse _value) {
        this.detalladosPedido[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DetalladoPedidoModificarReponse)) return false;
        DetalladoPedidoModificarReponse other = (DetalladoPedidoModificarReponse) obj;
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
            ((this.detalladosPedido==null && other.getDetalladosPedido()==null) || 
             (this.detalladosPedido!=null &&
              java.util.Arrays.equals(this.detalladosPedido, other.getDetalladosPedido())));
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
        if (getDetalladosPedido() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDetalladosPedido());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDetalladosPedido(), i);
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
        new org.apache.axis.description.TypeDesc(DetalladoPedidoModificarReponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", ">DetalladoPedidoModificarReponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setFieldName("detalladosPedido");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "DetalladosPedido"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "DetalladoPedidoResponse"));
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
