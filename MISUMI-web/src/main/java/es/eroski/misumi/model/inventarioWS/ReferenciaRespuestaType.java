/**
 * ReferenciaRespuestaType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.inventarioWS;

public class ReferenciaRespuestaType  implements java.io.Serializable {
    private java.math.BigInteger codigoReferencia;

    private java.lang.String nsr;

    private java.math.BigInteger codArea;

    private java.lang.String descArea;

    private java.math.BigInteger codSeccion;

    private java.lang.String descSeccion;

    private java.lang.String tipoListado;

    private java.math.BigInteger codigoError;

    private java.lang.String mensajeError;

    public ReferenciaRespuestaType() {
    }

    public ReferenciaRespuestaType(
           java.math.BigInteger codigoReferencia,
           java.lang.String nsr,
           java.math.BigInteger codArea,
           java.lang.String descArea,
           java.math.BigInteger codSeccion,
           java.lang.String descSeccion,
           java.lang.String tipoListado,
           java.math.BigInteger codigoError,
           java.lang.String mensajeError) {
           this.codigoReferencia = codigoReferencia;
           this.nsr = nsr;
           this.codArea = codArea;
           this.descArea = descArea;
           this.codSeccion = codSeccion;
           this.descSeccion = descSeccion;
           this.tipoListado = tipoListado;
           this.codigoError = codigoError;
           this.mensajeError = mensajeError;
    }


    /**
     * Gets the codigoReferencia value for this ReferenciaRespuestaType.
     * 
     * @return codigoReferencia
     */
    public java.math.BigInteger getCodigoReferencia() {
        return codigoReferencia;
    }


    /**
     * Sets the codigoReferencia value for this ReferenciaRespuestaType.
     * 
     * @param codigoReferencia
     */
    public void setCodigoReferencia(java.math.BigInteger codigoReferencia) {
        this.codigoReferencia = codigoReferencia;
    }


    /**
     * Gets the nsr value for this ReferenciaRespuestaType.
     * 
     * @return nsr
     */
    public java.lang.String getNsr() {
        return nsr;
    }


    /**
     * Sets the nsr value for this ReferenciaRespuestaType.
     * 
     * @param nsr
     */
    public void setNsr(java.lang.String nsr) {
        this.nsr = nsr;
    }


    /**
     * Gets the codArea value for this ReferenciaRespuestaType.
     * 
     * @return codArea
     */
    public java.math.BigInteger getCodArea() {
        return codArea;
    }


    /**
     * Sets the codArea value for this ReferenciaRespuestaType.
     * 
     * @param codArea
     */
    public void setCodArea(java.math.BigInteger codArea) {
        this.codArea = codArea;
    }


    /**
     * Gets the descArea value for this ReferenciaRespuestaType.
     * 
     * @return descArea
     */
    public java.lang.String getDescArea() {
        return descArea;
    }


    /**
     * Sets the descArea value for this ReferenciaRespuestaType.
     * 
     * @param descArea
     */
    public void setDescArea(java.lang.String descArea) {
        this.descArea = descArea;
    }


    /**
     * Gets the codSeccion value for this ReferenciaRespuestaType.
     * 
     * @return codSeccion
     */
    public java.math.BigInteger getCodSeccion() {
        return codSeccion;
    }


    /**
     * Sets the codSeccion value for this ReferenciaRespuestaType.
     * 
     * @param codSeccion
     */
    public void setCodSeccion(java.math.BigInteger codSeccion) {
        this.codSeccion = codSeccion;
    }


    /**
     * Gets the descSeccion value for this ReferenciaRespuestaType.
     * 
     * @return descSeccion
     */
    public java.lang.String getDescSeccion() {
        return descSeccion;
    }


    /**
     * Sets the descSeccion value for this ReferenciaRespuestaType.
     * 
     * @param descSeccion
     */
    public void setDescSeccion(java.lang.String descSeccion) {
        this.descSeccion = descSeccion;
    }


    /**
     * Gets the tipoListado value for this ReferenciaRespuestaType.
     * 
     * @return tipoListado
     */
    public java.lang.String getTipoListado() {
        return tipoListado;
    }


    /**
     * Sets the tipoListado value for this ReferenciaRespuestaType.
     * 
     * @param tipoListado
     */
    public void setTipoListado(java.lang.String tipoListado) {
        this.tipoListado = tipoListado;
    }


    /**
     * Gets the codigoError value for this ReferenciaRespuestaType.
     * 
     * @return codigoError
     */
    public java.math.BigInteger getCodigoError() {
        return codigoError;
    }


    /**
     * Sets the codigoError value for this ReferenciaRespuestaType.
     * 
     * @param codigoError
     */
    public void setCodigoError(java.math.BigInteger codigoError) {
        this.codigoError = codigoError;
    }


    /**
     * Gets the mensajeError value for this ReferenciaRespuestaType.
     * 
     * @return mensajeError
     */
    public java.lang.String getMensajeError() {
        return mensajeError;
    }


    /**
     * Sets the mensajeError value for this ReferenciaRespuestaType.
     * 
     * @param mensajeError
     */
    public void setMensajeError(java.lang.String mensajeError) {
        this.mensajeError = mensajeError;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReferenciaRespuestaType)) return false;
        ReferenciaRespuestaType other = (ReferenciaRespuestaType) obj;
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
            ((this.nsr==null && other.getNsr()==null) || 
             (this.nsr!=null &&
              this.nsr.equals(other.getNsr()))) &&
            ((this.codArea==null && other.getCodArea()==null) || 
             (this.codArea!=null &&
              this.codArea.equals(other.getCodArea()))) &&
            ((this.descArea==null && other.getDescArea()==null) || 
             (this.descArea!=null &&
              this.descArea.equals(other.getDescArea()))) &&
            ((this.codSeccion==null && other.getCodSeccion()==null) || 
             (this.codSeccion!=null &&
              this.codSeccion.equals(other.getCodSeccion()))) &&
            ((this.descSeccion==null && other.getDescSeccion()==null) || 
             (this.descSeccion!=null &&
              this.descSeccion.equals(other.getDescSeccion()))) &&
            ((this.tipoListado==null && other.getTipoListado()==null) || 
             (this.tipoListado!=null &&
              this.tipoListado.equals(other.getTipoListado()))) &&
            ((this.codigoError==null && other.getCodigoError()==null) || 
             (this.codigoError!=null &&
              this.codigoError.equals(other.getCodigoError()))) &&
            ((this.mensajeError==null && other.getMensajeError()==null) || 
             (this.mensajeError!=null &&
              this.mensajeError.equals(other.getMensajeError())));
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
        if (getNsr() != null) {
            _hashCode += getNsr().hashCode();
        }
        if (getCodArea() != null) {
            _hashCode += getCodArea().hashCode();
        }
        if (getDescArea() != null) {
            _hashCode += getDescArea().hashCode();
        }
        if (getCodSeccion() != null) {
            _hashCode += getCodSeccion().hashCode();
        }
        if (getDescSeccion() != null) {
            _hashCode += getDescSeccion().hashCode();
        }
        if (getTipoListado() != null) {
            _hashCode += getTipoListado().hashCode();
        }
        if (getCodigoError() != null) {
            _hashCode += getCodigoError().hashCode();
        }
        if (getMensajeError() != null) {
            _hashCode += getMensajeError().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ReferenciaRespuestaType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/gcpv/schema/inventarioTienda", "ReferenciaRespuestaType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoReferencia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/gcpv/schema/inventarioTienda", "codigoReferencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nsr");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/gcpv/schema/inventarioTienda", "nsr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codArea");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/gcpv/schema/inventarioTienda", "codArea"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descArea");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/gcpv/schema/inventarioTienda", "descArea"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codSeccion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/gcpv/schema/inventarioTienda", "codSeccion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descSeccion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/gcpv/schema/inventarioTienda", "descSeccion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoListado");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/gcpv/schema/inventarioTienda", "tipoListado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoError");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/gcpv/schema/inventarioTienda", "codigoError"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mensajeError");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/gcpv/schema/inventarioTienda", "mensajeError"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
