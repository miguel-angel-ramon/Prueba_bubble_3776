/**
 * TiendaType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.pedidosPBLWS;

public class TiendaType  implements java.io.Serializable {
    private java.lang.String codigoTienda;

    public TiendaType() {
    }

    public TiendaType(
           java.lang.String codigoTienda) {
           this.codigoTienda = codigoTienda;
    }


    /**
     * Gets the codigoTienda value for this TiendaType.
     * 
     * @return codigoTienda
     */
    public java.lang.String getCodigoTienda() {
        return codigoTienda;
    }


    /**
     * Sets the codigoTienda value for this TiendaType.
     * 
     * @param codigoTienda
     */
    public void setCodigoTienda(java.lang.String codigoTienda) {
        this.codigoTienda = codigoTienda;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TiendaType)) return false;
        TiendaType other = (TiendaType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codigoTienda==null && other.getCodigoTienda()==null) || 
             (this.codigoTienda!=null &&
              this.codigoTienda.equals(other.getCodigoTienda())));
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
        if (getCodigoTienda() != null) {
            _hashCode += getCodigoTienda().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TiendaType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/ExclusionVentasPBL/ExclusionVentas", "TiendaType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoTienda");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/ExclusionVentasPBL/ExclusionVentas", "codigoTienda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
