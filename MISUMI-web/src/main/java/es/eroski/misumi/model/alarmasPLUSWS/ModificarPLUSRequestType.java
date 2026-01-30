/**
 * ModificarPLUSRequestType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.alarmasPLUSWS;

public class ModificarPLUSRequestType  implements java.io.Serializable {
    private java.math.BigInteger codigoCentro;

    private java.lang.String tipoMantenimiento;

    private es.eroski.misumi.model.alarmasPLUSWS.ReferenciaModType[] referencias;

    public ModificarPLUSRequestType() {
    }

    public ModificarPLUSRequestType(
           java.math.BigInteger codigoCentro,
           java.lang.String tipoMantenimiento,
           es.eroski.misumi.model.alarmasPLUSWS.ReferenciaModType[] referencias) {
           this.codigoCentro = codigoCentro;
           this.tipoMantenimiento = tipoMantenimiento;
           this.referencias = referencias;
    }


    /**
     * Gets the codigoCentro value for this ModificarPLUSRequestType.
     * 
     * @return codigoCentro
     */
    public java.math.BigInteger getCodigoCentro() {
        return codigoCentro;
    }


    /**
     * Sets the codigoCentro value for this ModificarPLUSRequestType.
     * 
     * @param codigoCentro
     */
    public void setCodigoCentro(java.math.BigInteger codigoCentro) {
        this.codigoCentro = codigoCentro;
    }


    /**
     * Gets the tipoMantenimiento value for this ModificarPLUSRequestType.
     * 
     * @return tipoMantenimiento
     */
    public java.lang.String getTipoMantenimiento() {
        return tipoMantenimiento;
    }


    /**
     * Sets the tipoMantenimiento value for this ModificarPLUSRequestType.
     * 
     * @param tipoMantenimiento
     */
    public void setTipoMantenimiento(java.lang.String tipoMantenimiento) {
        this.tipoMantenimiento = tipoMantenimiento;
    }


    /**
     * Gets the referencias value for this ModificarPLUSRequestType.
     * 
     * @return referencias
     */
    public es.eroski.misumi.model.alarmasPLUSWS.ReferenciaModType[] getReferencias() {
        return referencias;
    }


    /**
     * Sets the referencias value for this ModificarPLUSRequestType.
     * 
     * @param referencias
     */
    public void setReferencias(es.eroski.misumi.model.alarmasPLUSWS.ReferenciaModType[] referencias) {
        this.referencias = referencias;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ModificarPLUSRequestType)) return false;
        ModificarPLUSRequestType other = (ModificarPLUSRequestType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codigoCentro==null && other.getCodigoCentro()==null) || 
             (this.codigoCentro!=null &&
              this.codigoCentro.equals(other.getCodigoCentro()))) &&
            ((this.tipoMantenimiento==null && other.getTipoMantenimiento()==null) || 
             (this.tipoMantenimiento!=null &&
              this.tipoMantenimiento.equals(other.getTipoMantenimiento()))) &&
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
        if (getCodigoCentro() != null) {
            _hashCode += getCodigoCentro().hashCode();
        }
        if (getTipoMantenimiento() != null) {
            _hashCode += getTipoMantenimiento().hashCode();
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
        new org.apache.axis.description.TypeDesc(ModificarPLUSRequestType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/PLUSTienda", "ModificarPLUSRequestType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoCentro");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/PLUSTienda", "CodigoCentro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoMantenimiento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/PLUSTienda", "TipoMantenimiento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("referencias");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/PLUSTienda", "Referencias"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/PLUSTienda", "ReferenciaModType"));
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
