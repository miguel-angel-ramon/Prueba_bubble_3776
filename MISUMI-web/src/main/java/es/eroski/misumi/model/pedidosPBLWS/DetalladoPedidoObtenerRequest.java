/**
 * DetalladoPedidoObtenerRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.pedidosPBLWS;

public class DetalladoPedidoObtenerRequest  implements java.io.Serializable {
    private java.math.BigInteger centro;

    private java.math.BigInteger area;

    private java.math.BigInteger seccion;

    private java.math.BigInteger categoria;

    public DetalladoPedidoObtenerRequest() {
    }

    public DetalladoPedidoObtenerRequest(
           java.math.BigInteger centro,
           java.math.BigInteger area,
           java.math.BigInteger seccion,
           java.math.BigInteger categoria) {
           this.centro = centro;
           this.area = area;
           this.seccion = seccion;
           this.categoria = categoria;
    }


    /**
     * Gets the centro value for this DetalladoPedidoObtenerRequest.
     * 
     * @return centro
     */
    public java.math.BigInteger getCentro() {
        return centro;
    }


    /**
     * Sets the centro value for this DetalladoPedidoObtenerRequest.
     * 
     * @param centro
     */
    public void setCentro(java.math.BigInteger centro) {
        this.centro = centro;
    }


    /**
     * Gets the area value for this DetalladoPedidoObtenerRequest.
     * 
     * @return area
     */
    public java.math.BigInteger getArea() {
        return area;
    }


    /**
     * Sets the area value for this DetalladoPedidoObtenerRequest.
     * 
     * @param area
     */
    public void setArea(java.math.BigInteger area) {
        this.area = area;
    }


    /**
     * Gets the seccion value for this DetalladoPedidoObtenerRequest.
     * 
     * @return seccion
     */
    public java.math.BigInteger getSeccion() {
        return seccion;
    }


    /**
     * Sets the seccion value for this DetalladoPedidoObtenerRequest.
     * 
     * @param seccion
     */
    public void setSeccion(java.math.BigInteger seccion) {
        this.seccion = seccion;
    }


    /**
     * Gets the categoria value for this DetalladoPedidoObtenerRequest.
     * 
     * @return categoria
     */
    public java.math.BigInteger getCategoria() {
        return categoria;
    }


    /**
     * Sets the categoria value for this DetalladoPedidoObtenerRequest.
     * 
     * @param categoria
     */
    public void setCategoria(java.math.BigInteger categoria) {
        this.categoria = categoria;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DetalladoPedidoObtenerRequest)) return false;
        DetalladoPedidoObtenerRequest other = (DetalladoPedidoObtenerRequest) obj;
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
            ((this.area==null && other.getArea()==null) || 
             (this.area!=null &&
              this.area.equals(other.getArea()))) &&
            ((this.seccion==null && other.getSeccion()==null) || 
             (this.seccion!=null &&
              this.seccion.equals(other.getSeccion()))) &&
            ((this.categoria==null && other.getCategoria()==null) || 
             (this.categoria!=null &&
              this.categoria.equals(other.getCategoria())));
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
        if (getArea() != null) {
            _hashCode += getArea().hashCode();
        }
        if (getSeccion() != null) {
            _hashCode += getSeccion().hashCode();
        }
        if (getCategoria() != null) {
            _hashCode += getCategoria().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DetalladoPedidoObtenerRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", ">DetalladoPedidoObtenerRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("centro");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "Centro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("area");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "Area"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("seccion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "Seccion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("categoria");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "Categoria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
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
