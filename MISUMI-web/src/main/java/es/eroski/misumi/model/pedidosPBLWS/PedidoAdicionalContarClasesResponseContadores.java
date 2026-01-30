/**
 * PedidoAdicionalContarClasesResponseContadores.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.pedidosPBLWS;

public class PedidoAdicionalContarClasesResponseContadores  implements java.io.Serializable {
    private java.math.BigInteger clasePedidoAdicional;

    private java.math.BigInteger contador;

    public PedidoAdicionalContarClasesResponseContadores() {
    }

    public PedidoAdicionalContarClasesResponseContadores(
           java.math.BigInteger clasePedidoAdicional,
           java.math.BigInteger contador) {
           this.clasePedidoAdicional = clasePedidoAdicional;
           this.contador = contador;
    }


    /**
     * Gets the clasePedidoAdicional value for this PedidoAdicionalContarClasesResponseContadores.
     * 
     * @return clasePedidoAdicional
     */
    public java.math.BigInteger getClasePedidoAdicional() {
        return clasePedidoAdicional;
    }


    /**
     * Sets the clasePedidoAdicional value for this PedidoAdicionalContarClasesResponseContadores.
     * 
     * @param clasePedidoAdicional
     */
    public void setClasePedidoAdicional(java.math.BigInteger clasePedidoAdicional) {
        this.clasePedidoAdicional = clasePedidoAdicional;
    }


    /**
     * Gets the contador value for this PedidoAdicionalContarClasesResponseContadores.
     * 
     * @return contador
     */
    public java.math.BigInteger getContador() {
        return contador;
    }


    /**
     * Sets the contador value for this PedidoAdicionalContarClasesResponseContadores.
     * 
     * @param contador
     */
    public void setContador(java.math.BigInteger contador) {
        this.contador = contador;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PedidoAdicionalContarClasesResponseContadores)) return false;
        PedidoAdicionalContarClasesResponseContadores other = (PedidoAdicionalContarClasesResponseContadores) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.clasePedidoAdicional==null && other.getClasePedidoAdicional()==null) || 
             (this.clasePedidoAdicional!=null &&
              this.clasePedidoAdicional.equals(other.getClasePedidoAdicional()))) &&
            ((this.contador==null && other.getContador()==null) || 
             (this.contador!=null &&
              this.contador.equals(other.getContador())));
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
        if (getClasePedidoAdicional() != null) {
            _hashCode += getClasePedidoAdicional().hashCode();
        }
        if (getContador() != null) {
            _hashCode += getContador().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PedidoAdicionalContarClasesResponseContadores.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", ">>PedidoAdicionalContarClasesResponse>Contadores"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clasePedidoAdicional");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "ClasePedidoAdicional"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contador");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "Contador"));
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
