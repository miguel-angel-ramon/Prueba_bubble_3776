/**
 * ReferenciaResMedidaType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.facingVegalsaWS;

public class ReferenciaResMedidaType  implements java.io.Serializable {
    private java.math.BigInteger codigoError;

    private java.lang.String mensajeError;

    private java.math.BigInteger codigoReferencia;

    private java.math.BigInteger facingAlto;

    private java.math.BigInteger facingAncho;

    private java.math.BigInteger capacidad;
    
    private java.math.BigInteger tipoEtiqueta;

    public ReferenciaResMedidaType() {
    }

    public ReferenciaResMedidaType(
           java.math.BigInteger codigoError,
           java.lang.String mensajeError,
           java.math.BigInteger codigoReferencia,
           java.math.BigInteger facingAlto,
           java.math.BigInteger facingAncho,
           java.math.BigInteger capacidad,
           java.math.BigInteger tipoEtiqueta) {
           this.codigoError = codigoError;
           this.mensajeError = mensajeError;
           this.codigoReferencia = codigoReferencia;
           this.facingAlto = facingAlto;
           this.facingAncho = facingAncho;
           this.capacidad = capacidad;
           this.tipoEtiqueta = tipoEtiqueta;
    }


    /**
     * Gets the codigoError value for this ReferenciaResMedidaType.
     * 
     * @return codigoError
     */
    public java.math.BigInteger getCodigoError() {
        return codigoError;
    }


    /**
     * Sets the codigoError value for this ReferenciaResMedidaType.
     * 
     * @param codigoError
     */
    public void setCodigoError(java.math.BigInteger codigoError) {
        this.codigoError = codigoError;
    }


    /**
     * Gets the mensajeError value for this ReferenciaResMedidaType.
     * 
     * @return mensajeError
     */
    public java.lang.String getMensajeError() {
        return mensajeError;
    }


    /**
     * Sets the mensajeError value for this ReferenciaResMedidaType.
     * 
     * @param mensajeError
     */
    public void setMensajeError(java.lang.String mensajeError) {
        this.mensajeError = mensajeError;
    }


    /**
     * Gets the codigoReferencia value for this ReferenciaResMedidaType.
     * 
     * @return codigoReferencia
     */
    public java.math.BigInteger getCodigoReferencia() {
        return codigoReferencia;
    }


    /**
     * Sets the codigoReferencia value for this ReferenciaResMedidaType.
     * 
     * @param codigoReferencia
     */
    public void setCodigoReferencia(java.math.BigInteger codigoReferencia) {
        this.codigoReferencia = codigoReferencia;
    }


    /**
     * Gets the facingAlto value for this ReferenciaResMedidaType.
     * 
     * @return facingAlto
     */
    public java.math.BigInteger getFacingAlto() {
        return facingAlto;
    }


    /**
     * Sets the facingAlto value for this ReferenciaResMedidaType.
     * 
     * @param facingAlto
     */
    public void setFacingAlto(java.math.BigInteger facingAlto) {
        this.facingAlto = facingAlto;
    }


    /**
     * Gets the facingAncho value for this ReferenciaResMedidaType.
     * 
     * @return facingAncho
     */
    public java.math.BigInteger getFacingAncho() {
        return facingAncho;
    }


    /**
     * Sets the facingAncho value for this ReferenciaResMedidaType.
     * 
     * @param facingAncho
     */
    public void setFacingAncho(java.math.BigInteger facingAncho) {
        this.facingAncho = facingAncho;
    }


    /**
     * Gets the capacidad value for this ReferenciaResMedidaType.
     * 
     * @return capacidad
     */
    public java.math.BigInteger getCapacidad() {
        return capacidad;
    }


    /**
     * Sets the capacidad value for this ReferenciaResMedidaType.
     * 
     * @param capacidad
     */
    public void setCapacidad(java.math.BigInteger capacidad) {
        this.capacidad = capacidad;
    }

    /**
     * Gets the tipoEtiqueta value for this ReferenciaResMedidaType.
     * 
     * @return tipoEtiqueta
     */
    public java.math.BigInteger getTipoEtiqueta() {
        return tipoEtiqueta;
    }


    /**
     * Sets the tipoEtiqueta value for this ReferenciaResMedidaType.
     * 
     * @param tipoEtiqueta
     */
    public void setTipoEtiqueta(java.math.BigInteger tipoEtiqueta) {
        this.tipoEtiqueta = tipoEtiqueta;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReferenciaResMedidaType)) return false;
        ReferenciaResMedidaType other = (ReferenciaResMedidaType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
                ((this.codigoError==null && other.getCodigoError()==null) || 
                 (this.codigoError!=null &&
                  this.codigoError.equals(other.getCodigoError()))) &&
                ((this.mensajeError==null && other.getMensajeError()==null) || 
                 (this.mensajeError!=null &&
                  this.mensajeError.equals(other.getMensajeError()))) &&
                ((this.codigoReferencia==null && other.getCodigoReferencia()==null) || 
                 (this.codigoReferencia!=null &&
                  this.codigoReferencia.equals(other.getCodigoReferencia()))) &&
                ((this.facingAlto==null && other.getFacingAlto()==null) || 
                 (this.facingAlto!=null &&
                  this.facingAlto.equals(other.getFacingAlto()))) &&
                ((this.facingAncho==null && other.getFacingAncho()==null) || 
                 (this.facingAncho!=null &&
                  this.facingAncho.equals(other.getFacingAncho()))) &&
                ((this.capacidad==null && other.getCapacidad()==null) || 
                 (this.capacidad!=null &&
                  this.capacidad.equals(other.getCapacidad()))) &&
                ((this.tipoEtiqueta==null && other.getTipoEtiqueta()==null) || 
                 (this.tipoEtiqueta!=null &&
                  this.tipoEtiqueta.equals(other.getTipoEtiqueta())));
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
        if (getCodigoError() != null) {
            _hashCode += getCodigoError().hashCode();
        }
        if (getMensajeError() != null) {
            _hashCode += getMensajeError().hashCode();
        }
        if (getCodigoReferencia() != null) {
            _hashCode += getCodigoReferencia().hashCode();
        }
        if (getFacingAlto() != null) {
            _hashCode += getFacingAlto().hashCode();
        }
        if (getFacingAncho() != null) {
            _hashCode += getFacingAncho().hashCode();
        }
        if (getCapacidad() != null) {
            _hashCode += getCapacidad().hashCode();
        }
        if (getTipoEtiqueta() != null) {
            _hashCode += getTipoEtiqueta().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ReferenciaResMedidaType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ReferenciaResMedidaType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoError");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "CodigoError"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mensajeError");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "MensajeError"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoReferencia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "CodigoReferencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("facingAlto");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "FacingAlto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("facingAncho");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "FacingAncho"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("capacidad");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "Capacidad"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoEtiqueta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "TipoEtiqueta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
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
