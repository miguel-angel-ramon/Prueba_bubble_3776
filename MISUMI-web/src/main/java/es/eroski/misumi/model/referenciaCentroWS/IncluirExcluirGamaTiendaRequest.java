/**
 * IncluirExcluirGamaTiendaRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.referenciaCentroWS;

public class IncluirExcluirGamaTiendaRequest  implements java.io.Serializable {
    private java.math.BigInteger[] centros;

    private java.math.BigInteger[] referencias;

    private java.lang.String accion;

    private java.lang.String usuario;

    public IncluirExcluirGamaTiendaRequest() {
    }

    public IncluirExcluirGamaTiendaRequest(
           java.math.BigInteger[] centros,
           java.math.BigInteger[] referencias,
           java.lang.String accion,
           java.lang.String usuario) {
           this.centros = centros;
           this.referencias = referencias;
           this.accion = accion;
           this.usuario = usuario;
    }


    /**
     * Gets the centros value for this IncluirExcluirGamaTiendaRequest.
     * 
     * @return centros
     */
    public java.math.BigInteger[] getCentros() {
        return centros;
    }


    /**
     * Sets the centros value for this IncluirExcluirGamaTiendaRequest.
     * 
     * @param centros
     */
    public void setCentros(java.math.BigInteger[] centros) {
        this.centros = centros;
    }


    /**
     * Gets the referencias value for this IncluirExcluirGamaTiendaRequest.
     * 
     * @return referencias
     */
    public java.math.BigInteger[] getReferencias() {
        return referencias;
    }


    /**
     * Sets the referencias value for this IncluirExcluirGamaTiendaRequest.
     * 
     * @param referencias
     */
    public void setReferencias(java.math.BigInteger[] referencias) {
        this.referencias = referencias;
    }


    /**
     * Gets the accion value for this IncluirExcluirGamaTiendaRequest.
     * 
     * @return accion
     */
    public java.lang.String getAccion() {
        return accion;
    }


    /**
     * Sets the accion value for this IncluirExcluirGamaTiendaRequest.
     * 
     * @param accion
     */
    public void setAccion(java.lang.String accion) {
        this.accion = accion;
    }


    /**
     * Gets the usuario value for this IncluirExcluirGamaTiendaRequest.
     * 
     * @return usuario
     */
    public java.lang.String getUsuario() {
        return usuario;
    }


    /**
     * Sets the usuario value for this IncluirExcluirGamaTiendaRequest.
     * 
     * @param usuario
     */
    public void setUsuario(java.lang.String usuario) {
        this.usuario = usuario;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof IncluirExcluirGamaTiendaRequest)) return false;
        IncluirExcluirGamaTiendaRequest other = (IncluirExcluirGamaTiendaRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.centros==null && other.getCentros()==null) || 
             (this.centros!=null &&
              java.util.Arrays.equals(this.centros, other.getCentros()))) &&
            ((this.referencias==null && other.getReferencias()==null) || 
             (this.referencias!=null &&
              java.util.Arrays.equals(this.referencias, other.getReferencias()))) &&
            ((this.accion==null && other.getAccion()==null) || 
             (this.accion!=null &&
              this.accion.equals(other.getAccion()))) &&
            ((this.usuario==null && other.getUsuario()==null) || 
             (this.usuario!=null &&
              this.usuario.equals(other.getUsuario())));
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
        if (getCentros() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCentros());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCentros(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
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
        if (getAccion() != null) {
            _hashCode += getAccion().hashCode();
        }
        if (getUsuario() != null) {
            _hashCode += getUsuario().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(IncluirExcluirGamaTiendaRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/KOSMOS/schema/ReferenciaCentroKN", ">IncluirExcluirGamaTiendaRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("centros");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/KOSMOS/schema/ReferenciaCentroKN", "Centros"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.eroski.es/KOSMOS/schema/ReferenciaCentroKN", "CodigoCentro"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("referencias");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/KOSMOS/schema/ReferenciaCentroKN", "Referencias"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.eroski.es/KOSMOS/schema/ReferenciaCentroKN", "CodigoReferencia"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/KOSMOS/schema/ReferenciaCentroKN", "Accion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usuario");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/KOSMOS/schema/ReferenciaCentroKN", "Usuario"));
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
