/**
 * ConsultaFacingVegalsaRequestType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.facingVegalsaWS;

public class ConsultaFacingVegalsaRequestType  implements java.io.Serializable {
    private java.math.BigInteger codigoCentro;

    private java.lang.String tipo;

    private java.math.BigInteger[] listaReferencias;

    public ConsultaFacingVegalsaRequestType() {
    }

    public ConsultaFacingVegalsaRequestType(
           java.math.BigInteger codigoCentro,
           java.lang.String tipo,
           java.math.BigInteger[] listaReferencias) {
           this.codigoCentro = codigoCentro;
           this.tipo = tipo;
           this.listaReferencias = listaReferencias;
    }


    /**
     * Gets the codigoCentro value for this ConsultaFacingVegalsaRequestType.
     * 
     * @return codigoCentro
     */
    public java.math.BigInteger getCodigoCentro() {
        return codigoCentro;
    }


    /**
     * Sets the codigoCentro value for this ConsultaFacingVegalsaRequestType.
     * 
     * @param codigoCentro
     */
    public void setCodigoCentro(java.math.BigInteger codigoCentro) {
        this.codigoCentro = codigoCentro;
    }


    /**
     * Gets the tipo value for this ConsultaFacingVegalsaRequestType.
     * 
     * @return tipo
     */
    public java.lang.String getTipo() {
        return tipo;
    }


    /**
     * Sets the tipo value for this ConsultaFacingVegalsaRequestType.
     * 
     * @param tipo
     */
    public void setTipo(java.lang.String tipo) {
        this.tipo = tipo;
    }


    /**
     * Gets the listaReferencias value for this ConsultaFacingVegalsaRequestType.
     * 
     * @return listaReferencias
     */
    public java.math.BigInteger[] getListaReferencias() {
        return listaReferencias;
    }


    /**
     * Sets the listaReferencias value for this ConsultaFacingVegalsaRequestType.
     * 
     * @param listaReferencias
     */
    public void setListaReferencias(java.math.BigInteger[] listaReferencias) {
        this.listaReferencias = listaReferencias;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ConsultaFacingVegalsaRequestType)) return false;
        ConsultaFacingVegalsaRequestType other = (ConsultaFacingVegalsaRequestType) obj;
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
            ((this.tipo==null && other.getTipo()==null) || 
             (this.tipo!=null &&
              this.tipo.equals(other.getTipo()))) &&
            ((this.listaReferencias==null && other.getListaReferencias()==null) || 
             (this.listaReferencias!=null &&
              java.util.Arrays.equals(this.listaReferencias, other.getListaReferencias())));
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
        if (getTipo() != null) {
            _hashCode += getTipo().hashCode();
        }
        if (getListaReferencias() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getListaReferencias());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getListaReferencias(), i);
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
        new org.apache.axis.description.TypeDesc(ConsultaFacingVegalsaRequestType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ConsultaFacingVegalsaRequestType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoCentro");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "CodigoCentro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "Tipo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listaReferencias");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ListaReferencias"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "CodigoReferencia"));
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
