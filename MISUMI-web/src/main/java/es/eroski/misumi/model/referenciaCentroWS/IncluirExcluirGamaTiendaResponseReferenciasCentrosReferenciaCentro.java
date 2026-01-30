/**
 * IncluirExcluirGamaTiendaResponseReferenciasCentrosReferenciaCentro.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.referenciaCentroWS;

public class IncluirExcluirGamaTiendaResponseReferenciasCentrosReferenciaCentro  implements java.io.Serializable {
    private java.lang.String codigoCentro;

    private java.lang.String denominacionCentro;

    private java.math.BigInteger codigoReferencia;

    private java.lang.String descripcionReferencia;

    private java.lang.String accionRealizada;

    private java.math.BigInteger codigoError;

    private java.lang.String descripcionError;

    public IncluirExcluirGamaTiendaResponseReferenciasCentrosReferenciaCentro() {
    }

    public IncluirExcluirGamaTiendaResponseReferenciasCentrosReferenciaCentro(
           java.lang.String codigoCentro,
           java.lang.String denominacionCentro,
           java.math.BigInteger codigoReferencia,
           java.lang.String descripcionReferencia,
           java.lang.String accionRealizada,
           java.math.BigInteger codigoError,
           java.lang.String descripcionError) {
           this.codigoCentro = codigoCentro;
           this.denominacionCentro = denominacionCentro;
           this.codigoReferencia = codigoReferencia;
           this.descripcionReferencia = descripcionReferencia;
           this.accionRealizada = accionRealizada;
           this.codigoError = codigoError;
           this.descripcionError = descripcionError;
    }


    /**
     * Gets the codigoCentro value for this IncluirExcluirGamaTiendaResponseReferenciasCentrosReferenciaCentro.
     * 
     * @return codigoCentro
     */
    public java.lang.String getCodigoCentro() {
        return codigoCentro;
    }


    /**
     * Sets the codigoCentro value for this IncluirExcluirGamaTiendaResponseReferenciasCentrosReferenciaCentro.
     * 
     * @param codigoCentro
     */
    public void setCodigoCentro(java.lang.String codigoCentro) {
        this.codigoCentro = codigoCentro;
    }


    /**
     * Gets the denominacionCentro value for this IncluirExcluirGamaTiendaResponseReferenciasCentrosReferenciaCentro.
     * 
     * @return denominacionCentro
     */
    public java.lang.String getDenominacionCentro() {
        return denominacionCentro;
    }


    /**
     * Sets the denominacionCentro value for this IncluirExcluirGamaTiendaResponseReferenciasCentrosReferenciaCentro.
     * 
     * @param denominacionCentro
     */
    public void setDenominacionCentro(java.lang.String denominacionCentro) {
        this.denominacionCentro = denominacionCentro;
    }


    /**
     * Gets the codigoReferencia value for this IncluirExcluirGamaTiendaResponseReferenciasCentrosReferenciaCentro.
     * 
     * @return codigoReferencia
     */
    public java.math.BigInteger getCodigoReferencia() {
        return codigoReferencia;
    }


    /**
     * Sets the codigoReferencia value for this IncluirExcluirGamaTiendaResponseReferenciasCentrosReferenciaCentro.
     * 
     * @param codigoReferencia
     */
    public void setCodigoReferencia(java.math.BigInteger codigoReferencia) {
        this.codigoReferencia = codigoReferencia;
    }


    /**
     * Gets the descripcionReferencia value for this IncluirExcluirGamaTiendaResponseReferenciasCentrosReferenciaCentro.
     * 
     * @return descripcionReferencia
     */
    public java.lang.String getDescripcionReferencia() {
        return descripcionReferencia;
    }


    /**
     * Sets the descripcionReferencia value for this IncluirExcluirGamaTiendaResponseReferenciasCentrosReferenciaCentro.
     * 
     * @param descripcionReferencia
     */
    public void setDescripcionReferencia(java.lang.String descripcionReferencia) {
        this.descripcionReferencia = descripcionReferencia;
    }


    /**
     * Gets the accionRealizada value for this IncluirExcluirGamaTiendaResponseReferenciasCentrosReferenciaCentro.
     * 
     * @return accionRealizada
     */
    public java.lang.String getAccionRealizada() {
        return accionRealizada;
    }


    /**
     * Sets the accionRealizada value for this IncluirExcluirGamaTiendaResponseReferenciasCentrosReferenciaCentro.
     * 
     * @param accionRealizada
     */
    public void setAccionRealizada(java.lang.String accionRealizada) {
        this.accionRealizada = accionRealizada;
    }


    /**
     * Gets the codigoError value for this IncluirExcluirGamaTiendaResponseReferenciasCentrosReferenciaCentro.
     * 
     * @return codigoError
     */
    public java.math.BigInteger getCodigoError() {
        return codigoError;
    }


    /**
     * Sets the codigoError value for this IncluirExcluirGamaTiendaResponseReferenciasCentrosReferenciaCentro.
     * 
     * @param codigoError
     */
    public void setCodigoError(java.math.BigInteger codigoError) {
        this.codigoError = codigoError;
    }


    /**
     * Gets the descripcionError value for this IncluirExcluirGamaTiendaResponseReferenciasCentrosReferenciaCentro.
     * 
     * @return descripcionError
     */
    public java.lang.String getDescripcionError() {
        return descripcionError;
    }


    /**
     * Sets the descripcionError value for this IncluirExcluirGamaTiendaResponseReferenciasCentrosReferenciaCentro.
     * 
     * @param descripcionError
     */
    public void setDescripcionError(java.lang.String descripcionError) {
        this.descripcionError = descripcionError;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof IncluirExcluirGamaTiendaResponseReferenciasCentrosReferenciaCentro)) return false;
        IncluirExcluirGamaTiendaResponseReferenciasCentrosReferenciaCentro other = (IncluirExcluirGamaTiendaResponseReferenciasCentrosReferenciaCentro) obj;
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
            ((this.denominacionCentro==null && other.getDenominacionCentro()==null) || 
             (this.denominacionCentro!=null &&
              this.denominacionCentro.equals(other.getDenominacionCentro()))) &&
            ((this.codigoReferencia==null && other.getCodigoReferencia()==null) || 
             (this.codigoReferencia!=null &&
              this.codigoReferencia.equals(other.getCodigoReferencia()))) &&
            ((this.descripcionReferencia==null && other.getDescripcionReferencia()==null) || 
             (this.descripcionReferencia!=null &&
              this.descripcionReferencia.equals(other.getDescripcionReferencia()))) &&
            ((this.accionRealizada==null && other.getAccionRealizada()==null) || 
             (this.accionRealizada!=null &&
              this.accionRealizada.equals(other.getAccionRealizada()))) &&
            ((this.codigoError==null && other.getCodigoError()==null) || 
             (this.codigoError!=null &&
              this.codigoError.equals(other.getCodigoError()))) &&
            ((this.descripcionError==null && other.getDescripcionError()==null) || 
             (this.descripcionError!=null &&
              this.descripcionError.equals(other.getDescripcionError())));
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
        if (getDenominacionCentro() != null) {
            _hashCode += getDenominacionCentro().hashCode();
        }
        if (getCodigoReferencia() != null) {
            _hashCode += getCodigoReferencia().hashCode();
        }
        if (getDescripcionReferencia() != null) {
            _hashCode += getDescripcionReferencia().hashCode();
        }
        if (getAccionRealizada() != null) {
            _hashCode += getAccionRealizada().hashCode();
        }
        if (getCodigoError() != null) {
            _hashCode += getCodigoError().hashCode();
        }
        if (getDescripcionError() != null) {
            _hashCode += getDescripcionError().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(IncluirExcluirGamaTiendaResponseReferenciasCentrosReferenciaCentro.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/KOSMOS/schema/ReferenciaCentroKN", ">>>IncluirExcluirGamaTiendaResponse>ReferenciasCentros>ReferenciaCentro"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoCentro");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/KOSMOS/schema/ReferenciaCentroKN", "CodigoCentro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("denominacionCentro");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/KOSMOS/schema/ReferenciaCentroKN", "DenominacionCentro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoReferencia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/KOSMOS/schema/ReferenciaCentroKN", "CodigoReferencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descripcionReferencia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/KOSMOS/schema/ReferenciaCentroKN", "DescripcionReferencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accionRealizada");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/KOSMOS/schema/ReferenciaCentroKN", "AccionRealizada"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoError");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/KOSMOS/schema/ReferenciaCentroKN", "CodigoError"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descripcionError");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/KOSMOS/schema/ReferenciaCentroKN", "DescripcionError"));
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
