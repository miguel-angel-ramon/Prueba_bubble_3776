/**
 * ModificarPLUSResponseType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.alarmasPLUSWS;

public class ModificarPLUSResponseType  implements java.io.Serializable {
    private java.lang.String codigoRespuesta;

    private java.lang.String descripcionRespuesta;

    private es.eroski.misumi.model.alarmasPLUSWS.ReferenciaResModType[] referencias;

    public ModificarPLUSResponseType() {
    }

    public ModificarPLUSResponseType(
           java.lang.String codigoRespuesta,
           java.lang.String descripcionRespuesta,
           es.eroski.misumi.model.alarmasPLUSWS.ReferenciaResModType[] referencias) {
           this.codigoRespuesta = codigoRespuesta;
           this.descripcionRespuesta = descripcionRespuesta;
           this.referencias = referencias;
    }


    /**
     * Gets the codigoRespuesta value for this ModificarPLUSResponseType.
     * 
     * @return codigoRespuesta
     */
    public java.lang.String getCodigoRespuesta() {
        return codigoRespuesta;
    }


    /**
     * Sets the codigoRespuesta value for this ModificarPLUSResponseType.
     * 
     * @param codigoRespuesta
     */
    public void setCodigoRespuesta(java.lang.String codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }


    /**
     * Gets the descripcionRespuesta value for this ModificarPLUSResponseType.
     * 
     * @return descripcionRespuesta
     */
    public java.lang.String getDescripcionRespuesta() {
        return descripcionRespuesta;
    }


    /**
     * Sets the descripcionRespuesta value for this ModificarPLUSResponseType.
     * 
     * @param descripcionRespuesta
     */
    public void setDescripcionRespuesta(java.lang.String descripcionRespuesta) {
        this.descripcionRespuesta = descripcionRespuesta;
    }


    /**
     * Gets the referencias value for this ModificarPLUSResponseType.
     * 
     * @return referencias
     */
    public es.eroski.misumi.model.alarmasPLUSWS.ReferenciaResModType[] getReferencias() {
        return referencias;
    }


    /**
     * Sets the referencias value for this ModificarPLUSResponseType.
     * 
     * @param referencias
     */
    public void setReferencias(es.eroski.misumi.model.alarmasPLUSWS.ReferenciaResModType[] referencias) {
        this.referencias = referencias;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ModificarPLUSResponseType)) return false;
        ModificarPLUSResponseType other = (ModificarPLUSResponseType) obj;
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
            ((this.referencias==null && other.getReferencias()==null) || 
             (this.referencias!=null &&
              java.util.Arrays.equals(this.referencias, other.getReferencias())));
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
        if (getReferencias() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getReferencias());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getReferencias(), i);
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
        new org.apache.axis.description.TypeDesc(ModificarPLUSResponseType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/PLUSTienda", "ModificarPLUSResponseType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoRespuesta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/PLUSTienda", "CodigoRespuesta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descripcionRespuesta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/PLUSTienda", "DescripcionRespuesta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("referencias");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/PLUSTienda", "Referencias"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/PLUSTienda", "ReferenciaResModType"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/PLUSTienda", "Referencia"));
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
