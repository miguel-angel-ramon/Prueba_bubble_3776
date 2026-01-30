/**
 * ReferenciaModType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.facingVegalsaWS;

public class ReferenciaModType  implements java.io.Serializable {
    private java.math.BigInteger codigoReferencia;

    private java.math.BigInteger facing;

    private java.math.BigInteger capacidad;

    public ReferenciaModType() {
    }

    public ReferenciaModType(
           java.math.BigInteger codigoReferencia,
           java.math.BigInteger facing,
           java.math.BigInteger capacidad) {
           this.codigoReferencia = codigoReferencia;
           this.facing = facing;
           this.capacidad = capacidad;
    }


    /**
     * Gets the codigoReferencia value for this ReferenciaModType.
     * 
     * @return codigoReferencia
     */
    public java.math.BigInteger getCodigoReferencia() {
        return codigoReferencia;
    }


    /**
     * Sets the codigoReferencia value for this ReferenciaModType.
     * 
     * @param codigoReferencia
     */
    public void setCodigoReferencia(java.math.BigInteger codigoReferencia) {
        this.codigoReferencia = codigoReferencia;
    }


    /**
     * Gets the facing value for this ReferenciaModType.
     * 
     * @return facing
     */
    public java.math.BigInteger getFacing() {
        return facing;
    }


    /**
     * Sets the facing value for this ReferenciaModType.
     * 
     * @param facing
     */
    public void setFacing(java.math.BigInteger facing) {
        this.facing = facing;
    }


    /**
     * Gets the capacidad value for this ReferenciaModType.
     * 
     * @return capacidad
     */
    public java.math.BigInteger getCapacidad() {
        return capacidad;
    }


    /**
     * Sets the capacidad value for this ReferenciaModType.
     * 
     * @param capacidad
     */
    public void setCapacidad(java.math.BigInteger capacidad) {
        this.capacidad = capacidad;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReferenciaModType)) return false;
        ReferenciaModType other = (ReferenciaModType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codigoReferencia==null && other.getCodigoReferencia()==null) || 
             (this.codigoReferencia!=null &&
              this.codigoReferencia.equals(other.getCodigoReferencia()))) &&
            ((this.facing==null && other.getFacing()==null) || 
             (this.facing!=null &&
              this.facing.equals(other.getFacing()))) &&
            ((this.capacidad==null && other.getCapacidad()==null) || 
             (this.capacidad!=null &&
              this.capacidad.equals(other.getCapacidad())));
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
        if (getCodigoReferencia() != null) {
            _hashCode += getCodigoReferencia().hashCode();
        }
        if (getFacing() != null) {
            _hashCode += getFacing().hashCode();
        }
        if (getCapacidad() != null) {
            _hashCode += getCapacidad().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ReferenciaModType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ReferenciaModType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoReferencia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "CodigoReferencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("facing");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "Facing"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("capacidad");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "Capacidad"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
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
