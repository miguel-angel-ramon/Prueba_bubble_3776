/**
 * ExclusionVentaInsertarResponseType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.pedidosPBLWS;

public class ExclusionVentaInsertarResponseType  implements java.io.Serializable {
    private es.eroski.misumi.model.pedidosPBLWS.ExclusionVentasType[] listaExclusionVentas;

    public ExclusionVentaInsertarResponseType() {
    }

    public ExclusionVentaInsertarResponseType(
           es.eroski.misumi.model.pedidosPBLWS.ExclusionVentasType[] listaExclusionVentas) {
           this.listaExclusionVentas = listaExclusionVentas;
    }


    /**
     * Gets the listaExclusionVentas value for this ExclusionVentaInsertarResponseType.
     * 
     * @return listaExclusionVentas
     */
    public es.eroski.misumi.model.pedidosPBLWS.ExclusionVentasType[] getListaExclusionVentas() {
        return listaExclusionVentas;
    }


    /**
     * Sets the listaExclusionVentas value for this ExclusionVentaInsertarResponseType.
     * 
     * @param listaExclusionVentas
     */
    public void setListaExclusionVentas(es.eroski.misumi.model.pedidosPBLWS.ExclusionVentasType[] listaExclusionVentas) {
        this.listaExclusionVentas = listaExclusionVentas;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ExclusionVentaInsertarResponseType)) return false;
        ExclusionVentaInsertarResponseType other = (ExclusionVentaInsertarResponseType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.listaExclusionVentas==null && other.getListaExclusionVentas()==null) || 
             (this.listaExclusionVentas!=null &&
              java.util.Arrays.equals(this.listaExclusionVentas, other.getListaExclusionVentas())));
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
        if (getListaExclusionVentas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getListaExclusionVentas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getListaExclusionVentas(), i);
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
        new org.apache.axis.description.TypeDesc(ExclusionVentaInsertarResponseType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/ExclusionVentasPBL/ExclusionVentas", "ExclusionVentaInsertarResponseType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listaExclusionVentas");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/ExclusionVentasPBL/ExclusionVentas", "listaExclusionVentas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/ExclusionVentasPBL/ExclusionVentas", "ExclusionVentasType"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.eroski.es/ExclusionVentasPBL/ExclusionVentas", "exclusionVentas"));
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
