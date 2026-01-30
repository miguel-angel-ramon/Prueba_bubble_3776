/**
 * PedidoAdicionalObtenerResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.pedidosPBLWS;

public class PedidoAdicionalObtenerResponse  implements java.io.Serializable {
    private java.lang.String codigoRespuesta;

    private java.lang.String descripcionRespuesta;

    private es.eroski.misumi.model.pedidosPBLWS.PedidoAdicional[] pedidoAdicional;

    public PedidoAdicionalObtenerResponse() {
    }

    public PedidoAdicionalObtenerResponse(
           java.lang.String codigoRespuesta,
           java.lang.String descripcionRespuesta,
           es.eroski.misumi.model.pedidosPBLWS.PedidoAdicional[] pedidoAdicional) {
           this.codigoRespuesta = codigoRespuesta;
           this.descripcionRespuesta = descripcionRespuesta;
           this.pedidoAdicional = pedidoAdicional;
    }


    /**
     * Gets the codigoRespuesta value for this PedidoAdicionalObtenerResponse.
     * 
     * @return codigoRespuesta
     */
    public java.lang.String getCodigoRespuesta() {
        return codigoRespuesta;
    }


    /**
     * Sets the codigoRespuesta value for this PedidoAdicionalObtenerResponse.
     * 
     * @param codigoRespuesta
     */
    public void setCodigoRespuesta(java.lang.String codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }


    /**
     * Gets the descripcionRespuesta value for this PedidoAdicionalObtenerResponse.
     * 
     * @return descripcionRespuesta
     */
    public java.lang.String getDescripcionRespuesta() {
        return descripcionRespuesta;
    }


    /**
     * Sets the descripcionRespuesta value for this PedidoAdicionalObtenerResponse.
     * 
     * @param descripcionRespuesta
     */
    public void setDescripcionRespuesta(java.lang.String descripcionRespuesta) {
        this.descripcionRespuesta = descripcionRespuesta;
    }


    /**
     * Gets the pedidoAdicional value for this PedidoAdicionalObtenerResponse.
     * 
     * @return pedidoAdicional
     */
    public es.eroski.misumi.model.pedidosPBLWS.PedidoAdicional[] getPedidoAdicional() {
        return pedidoAdicional;
    }


    /**
     * Sets the pedidoAdicional value for this PedidoAdicionalObtenerResponse.
     * 
     * @param pedidoAdicional
     */
    public void setPedidoAdicional(es.eroski.misumi.model.pedidosPBLWS.PedidoAdicional[] pedidoAdicional) {
        this.pedidoAdicional = pedidoAdicional;
    }

    public es.eroski.misumi.model.pedidosPBLWS.PedidoAdicional getPedidoAdicional(int i) {
        return this.pedidoAdicional[i];
    }

    public void setPedidoAdicional(int i, es.eroski.misumi.model.pedidosPBLWS.PedidoAdicional _value) {
        this.pedidoAdicional[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PedidoAdicionalObtenerResponse)) return false;
        PedidoAdicionalObtenerResponse other = (PedidoAdicionalObtenerResponse) obj;
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
            ((this.pedidoAdicional==null && other.getPedidoAdicional()==null) || 
             (this.pedidoAdicional!=null &&
              java.util.Arrays.equals(this.pedidoAdicional, other.getPedidoAdicional())));
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
        if (getPedidoAdicional() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPedidoAdicional());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPedidoAdicional(), i);
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
        new org.apache.axis.description.TypeDesc(PedidoAdicionalObtenerResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", ">PedidoAdicionalObtenerResponse"));
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
        elemField.setFieldName("pedidoAdicional");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", "PedidoAdicional"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", "PedidoAdicional"));
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
