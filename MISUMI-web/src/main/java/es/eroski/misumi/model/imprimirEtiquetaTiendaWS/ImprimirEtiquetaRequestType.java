/**
 * ImprimirEtiquetaRequestType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.imprimirEtiquetaTiendaWS;

public class ImprimirEtiquetaRequestType  implements java.io.Serializable {
    private java.math.BigInteger codigoCentro;

    private java.math.BigInteger idPistola;

    private java.lang.String buzon;

    private ReferenciaRequestType[] referencias;

    public ImprimirEtiquetaRequestType() {
    }

    public ImprimirEtiquetaRequestType(
           java.math.BigInteger codigoCentro,
           java.math.BigInteger idPistola,
           java.lang.String buzon,
           ReferenciaRequestType[] referencias) {
           this.codigoCentro = codigoCentro;
           this.idPistola = idPistola;
           this.buzon = buzon;
           this.referencias = referencias;
    }


    /**
     * Gets the codigoCentro value for this ImprimirEtiquetaRequestType.
     * 
     * @return codigoCentro
     */
    public java.math.BigInteger getCodigoCentro() {
        return codigoCentro;
    }


    /**
     * Sets the codigoCentro value for this ImprimirEtiquetaRequestType.
     * 
     * @param codigoCentro
     */
    public void setCodigoCentro(java.math.BigInteger codigoCentro) {
        this.codigoCentro = codigoCentro;
    }


    /**
     * Gets the idPistola value for this ImprimirEtiquetaRequestType.
     * 
     * @return idPistola
     */
    public java.math.BigInteger getIdPistola() {
        return idPistola;
    }


    /**
     * Sets the idPistola value for this ImprimirEtiquetaRequestType.
     * 
     * @param idPistola
     */
    public void setIdPistola(java.math.BigInteger idPistola) {
        this.idPistola = idPistola;
    }


    /**
     * Gets the buzon value for this ImprimirEtiquetaRequestType.
     * 
     * @return buzon
     */
    public java.lang.String getBuzon() {
        return buzon;
    }


    /**
     * Sets the buzon value for this ImprimirEtiquetaRequestType.
     * 
     * @param buzon
     */
    public void setBuzon(java.lang.String buzon) {
        this.buzon = buzon;
    }


    /**
     * Gets the referencias value for this ImprimirEtiquetaRequestType.
     * 
     * @return referencias
     */
    public ReferenciaRequestType[] getReferencias() {
        return referencias;
    }


    /**
     * Sets the referencias value for this ImprimirEtiquetaRequestType.
     * 
     * @param referencias
     */
    public void setReferencias(ReferenciaRequestType[] referencias) {
        this.referencias = referencias;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ImprimirEtiquetaRequestType)) return false;
        ImprimirEtiquetaRequestType other = (ImprimirEtiquetaRequestType) obj;
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
            ((this.idPistola==null && other.getIdPistola()==null) || 
             (this.idPistola!=null &&
              this.idPistola.equals(other.getIdPistola()))) &&
            ((this.buzon==null && other.getBuzon()==null) || 
             (this.buzon!=null &&
              this.buzon.equals(other.getBuzon()))) &&
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
        if (getIdPistola() != null) {
            _hashCode += getIdPistola().hashCode();
        }
        if (getBuzon() != null) {
            _hashCode += getBuzon().hashCode();
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
        new org.apache.axis.description.TypeDesc(ImprimirEtiquetaRequestType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/gcpv/schema/imprimirEtiquetaTienda", "ImprimirEtiquetaRequestType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoCentro");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/gcpv/schema/imprimirEtiquetaTienda", "codigoCentro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idPistola");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/gcpv/schema/imprimirEtiquetaTienda", "idPistola"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("buzon");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/gcpv/schema/imprimirEtiquetaTienda", "buzon"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        //Como el WebService de ImprimirEtiqueta no 
        //permite que el campo buzón sea vacío
        //hacemos que cuando el campo buzón
        //sea null no se incluya en el XML del request.
        //Esto se consigue poniendo la propiedad MinOccurs a 0.
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("referencias");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/gcpv/schema/imprimirEtiquetaTienda", "referencias"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/gcpv/schema/imprimirEtiquetaTienda", "ReferenciaRequestType"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.eroski.es/gcpv/schema/imprimirEtiquetaTienda", "referencia"));
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
