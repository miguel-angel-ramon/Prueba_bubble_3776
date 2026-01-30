/**
 * ConsultaEstadoPedidosRequestType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.pedidosCentroWS;

public class ConsultaEstadoPedidosRequestType  implements java.io.Serializable {
    private java.math.BigDecimal centro;

    private java.math.BigInteger[] codigosPedido;

    public ConsultaEstadoPedidosRequestType() {
    }

    public ConsultaEstadoPedidosRequestType(
           java.math.BigDecimal centro,
           java.math.BigInteger[] codigosPedido) {
           this.centro = centro;
           this.codigosPedido = codigosPedido;
    }


    /**
     * Gets the centro value for this ConsultaEstadoPedidosRequestType.
     * 
     * @return centro
     */
    public java.math.BigDecimal getCentro() {
        return centro;
    }


    /**
     * Sets the centro value for this ConsultaEstadoPedidosRequestType.
     * 
     * @param centro
     */
    public void setCentro(java.math.BigDecimal centro) {
        this.centro = centro;
    }


    /**
     * Gets the codigosPedido value for this ConsultaEstadoPedidosRequestType.
     * 
     * @return codigosPedido
     */
    public java.math.BigInteger[] getCodigosPedido() {
        return codigosPedido;
    }


    /**
     * Sets the codigosPedido value for this ConsultaEstadoPedidosRequestType.
     * 
     * @param codigosPedido
     */
    public void setCodigosPedido(java.math.BigInteger[] codigosPedido) {
        this.codigosPedido = codigosPedido;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ConsultaEstadoPedidosRequestType)) return false;
        ConsultaEstadoPedidosRequestType other = (ConsultaEstadoPedidosRequestType) obj;
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
            ((this.codigosPedido==null && other.getCodigosPedido()==null) || 
             (this.codigosPedido!=null &&
              java.util.Arrays.equals(this.codigosPedido, other.getCodigosPedido())));
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
        if (getCodigosPedido() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCodigosPedido());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCodigosPedido(), i);
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
        new org.apache.axis.description.TypeDesc(ConsultaEstadoPedidosRequestType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/GCNP/schema/PedidosCentro", "ConsultaEstadoPedidosRequestType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("centro");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCNP/schema/PedidosCentro", "Centro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigosPedido");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCNP/schema/PedidosCentro", "CodigosPedido"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.eroski.es/GCNP/schema/PedidosCentro", "CodigoPedido"));
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
