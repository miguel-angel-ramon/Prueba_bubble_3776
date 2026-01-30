/**
 * ReferenciaRespuetaType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.ventasTiendaWS;

public class ReferenciaRespuetaType  implements java.io.Serializable {
    private java.math.BigInteger codigoError;

    private java.math.BigInteger codigoReferencia;

    private java.math.BigDecimal totalVentaTarifa;

    private java.math.BigDecimal totalVentaAnticipada;

    private java.math.BigDecimal totalVentaOferta;

    private java.math.BigDecimal totalVentaCompetencia;

    private java.lang.String mensajeError;

    private java.math.BigDecimal totalEntradas;

    private java.math.BigDecimal totalSalidasAjustes;

    private java.math.BigDecimal totalRegulaciones;

    public ReferenciaRespuetaType() {
    }

    public ReferenciaRespuetaType(
           java.math.BigInteger codigoError,
           java.math.BigInteger codigoReferencia,
           java.math.BigDecimal totalVentaTarifa,
           java.math.BigDecimal totalVentaAnticipada,
           java.math.BigDecimal totalVentaOferta,
           java.math.BigDecimal totalVentaCompetencia,
           java.lang.String mensajeError,
           java.math.BigDecimal totalEntradas,
           java.math.BigDecimal totalSalidasAjustes,
           java.math.BigDecimal totalRegulaciones) {
           this.codigoError = codigoError;
           this.codigoReferencia = codigoReferencia;
           this.totalVentaTarifa = totalVentaTarifa;
           this.totalVentaAnticipada = totalVentaAnticipada;
           this.totalVentaOferta = totalVentaOferta;
           this.totalVentaCompetencia = totalVentaCompetencia;
           this.mensajeError = mensajeError;
           this.totalEntradas = totalEntradas;
           this.totalSalidasAjustes = totalSalidasAjustes;
           this.totalRegulaciones = totalRegulaciones;
    }


    /**
     * Gets the codigoError value for this ReferenciaRespuetaType.
     * 
     * @return codigoError
     */
    public java.math.BigInteger getCodigoError() {
        return codigoError;
    }


    /**
     * Sets the codigoError value for this ReferenciaRespuetaType.
     * 
     * @param codigoError
     */
    public void setCodigoError(java.math.BigInteger codigoError) {
        this.codigoError = codigoError;
    }


    /**
     * Gets the codigoReferencia value for this ReferenciaRespuetaType.
     * 
     * @return codigoReferencia
     */
    public java.math.BigInteger getCodigoReferencia() {
        return codigoReferencia;
    }


    /**
     * Sets the codigoReferencia value for this ReferenciaRespuetaType.
     * 
     * @param codigoReferencia
     */
    public void setCodigoReferencia(java.math.BigInteger codigoReferencia) {
        this.codigoReferencia = codigoReferencia;
    }


    /**
     * Gets the totalVentaTarifa value for this ReferenciaRespuetaType.
     * 
     * @return totalVentaTarifa
     */
    public java.math.BigDecimal getTotalVentaTarifa() {
        return totalVentaTarifa;
    }


    /**
     * Sets the totalVentaTarifa value for this ReferenciaRespuetaType.
     * 
     * @param totalVentaTarifa
     */
    public void setTotalVentaTarifa(java.math.BigDecimal totalVentaTarifa) {
        this.totalVentaTarifa = totalVentaTarifa;
    }


    /**
     * Gets the totalVentaAnticipada value for this ReferenciaRespuetaType.
     * 
     * @return totalVentaAnticipada
     */
    public java.math.BigDecimal getTotalVentaAnticipada() {
        return totalVentaAnticipada;
    }


    /**
     * Sets the totalVentaAnticipada value for this ReferenciaRespuetaType.
     * 
     * @param totalVentaAnticipada
     */
    public void setTotalVentaAnticipada(java.math.BigDecimal totalVentaAnticipada) {
        this.totalVentaAnticipada = totalVentaAnticipada;
    }


    /**
     * Gets the totalVentaOferta value for this ReferenciaRespuetaType.
     * 
     * @return totalVentaOferta
     */
    public java.math.BigDecimal getTotalVentaOferta() {
        return totalVentaOferta;
    }


    /**
     * Sets the totalVentaOferta value for this ReferenciaRespuetaType.
     * 
     * @param totalVentaOferta
     */
    public void setTotalVentaOferta(java.math.BigDecimal totalVentaOferta) {
        this.totalVentaOferta = totalVentaOferta;
    }


    /**
     * Gets the totalVentaCompetencia value for this ReferenciaRespuetaType.
     * 
     * @return totalVentaCompetencia
     */
    public java.math.BigDecimal getTotalVentaCompetencia() {
        return totalVentaCompetencia;
    }


    /**
     * Sets the totalVentaCompetencia value for this ReferenciaRespuetaType.
     * 
     * @param totalVentaCompetencia
     */
    public void setTotalVentaCompetencia(java.math.BigDecimal totalVentaCompetencia) {
        this.totalVentaCompetencia = totalVentaCompetencia;
    }


    /**
     * Gets the mensajeError value for this ReferenciaRespuetaType.
     * 
     * @return mensajeError
     */
    public java.lang.String getMensajeError() {
        return mensajeError;
    }


    /**
     * Sets the mensajeError value for this ReferenciaRespuetaType.
     * 
     * @param mensajeError
     */
    public void setMensajeError(java.lang.String mensajeError) {
        this.mensajeError = mensajeError;
    }


    /**
     * Gets the totalEntradas value for this ReferenciaRespuetaType.
     * 
     * @return totalEntradas
     */
    public java.math.BigDecimal getTotalEntradas() {
        return totalEntradas;
    }


    /**
     * Sets the totalEntradas value for this ReferenciaRespuetaType.
     * 
     * @param totalEntradas
     */
    public void setTotalEntradas(java.math.BigDecimal totalEntradas) {
        this.totalEntradas = totalEntradas;
    }


    /**
     * Gets the totalSalidasAjustes value for this ReferenciaRespuetaType.
     * 
     * @return totalSalidasAjustes
     */
    public java.math.BigDecimal getTotalSalidasAjustes() {
        return totalSalidasAjustes;
    }


    /**
     * Sets the totalSalidasAjustes value for this ReferenciaRespuetaType.
     * 
     * @param totalSalidasAjustes
     */
    public void setTotalSalidasAjustes(java.math.BigDecimal totalSalidasAjustes) {
        this.totalSalidasAjustes = totalSalidasAjustes;
    }


    /**
     * Gets the totalRegulaciones value for this ReferenciaRespuetaType.
     * 
     * @return totalRegulaciones
     */
    public java.math.BigDecimal getTotalRegulaciones() {
        return totalRegulaciones;
    }


    /**
     * Sets the totalRegulaciones value for this ReferenciaRespuetaType.
     * 
     * @param totalRegulaciones
     */
    public void setTotalRegulaciones(java.math.BigDecimal totalRegulaciones) {
        this.totalRegulaciones = totalRegulaciones;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReferenciaRespuetaType)) return false;
        ReferenciaRespuetaType other = (ReferenciaRespuetaType) obj;
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
            ((this.codigoReferencia==null && other.getCodigoReferencia()==null) || 
             (this.codigoReferencia!=null &&
              this.codigoReferencia.equals(other.getCodigoReferencia()))) &&
            ((this.totalVentaTarifa==null && other.getTotalVentaTarifa()==null) || 
             (this.totalVentaTarifa!=null &&
              this.totalVentaTarifa.equals(other.getTotalVentaTarifa()))) &&
            ((this.totalVentaAnticipada==null && other.getTotalVentaAnticipada()==null) || 
             (this.totalVentaAnticipada!=null &&
              this.totalVentaAnticipada.equals(other.getTotalVentaAnticipada()))) &&
            ((this.totalVentaOferta==null && other.getTotalVentaOferta()==null) || 
             (this.totalVentaOferta!=null &&
              this.totalVentaOferta.equals(other.getTotalVentaOferta()))) &&
            ((this.totalVentaCompetencia==null && other.getTotalVentaCompetencia()==null) || 
             (this.totalVentaCompetencia!=null &&
              this.totalVentaCompetencia.equals(other.getTotalVentaCompetencia()))) &&
            ((this.mensajeError==null && other.getMensajeError()==null) || 
             (this.mensajeError!=null &&
              this.mensajeError.equals(other.getMensajeError()))) &&
            ((this.totalEntradas==null && other.getTotalEntradas()==null) || 
             (this.totalEntradas!=null &&
              this.totalEntradas.equals(other.getTotalEntradas()))) &&
            ((this.totalSalidasAjustes==null && other.getTotalSalidasAjustes()==null) || 
             (this.totalSalidasAjustes!=null &&
              this.totalSalidasAjustes.equals(other.getTotalSalidasAjustes()))) &&
            ((this.totalRegulaciones==null && other.getTotalRegulaciones()==null) || 
             (this.totalRegulaciones!=null &&
              this.totalRegulaciones.equals(other.getTotalRegulaciones())));
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
        if (getCodigoReferencia() != null) {
            _hashCode += getCodigoReferencia().hashCode();
        }
        if (getTotalVentaTarifa() != null) {
            _hashCode += getTotalVentaTarifa().hashCode();
        }
        if (getTotalVentaAnticipada() != null) {
            _hashCode += getTotalVentaAnticipada().hashCode();
        }
        if (getTotalVentaOferta() != null) {
            _hashCode += getTotalVentaOferta().hashCode();
        }
        if (getTotalVentaCompetencia() != null) {
            _hashCode += getTotalVentaCompetencia().hashCode();
        }
        if (getMensajeError() != null) {
            _hashCode += getMensajeError().hashCode();
        }
        if (getTotalEntradas() != null) {
            _hashCode += getTotalEntradas().hashCode();
        }
        if (getTotalSalidasAjustes() != null) {
            _hashCode += getTotalSalidasAjustes().hashCode();
        }
        if (getTotalRegulaciones() != null) {
            _hashCode += getTotalRegulaciones().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ReferenciaRespuetaType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/gcpv/schema/ventasTienda", "ReferenciaRespuetaType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoError");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/gcpv/schema/ventasTienda", "codigoError"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoReferencia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/gcpv/schema/ventasTienda", "codigoReferencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalVentaTarifa");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/gcpv/schema/ventasTienda", "totalVentaTarifa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalVentaAnticipada");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/gcpv/schema/ventasTienda", "totalVentaAnticipada"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalVentaOferta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/gcpv/schema/ventasTienda", "totalVentaOferta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalVentaCompetencia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/gcpv/schema/ventasTienda", "totalVentaCompetencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mensajeError");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/gcpv/schema/ventasTienda", "mensajeError"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalEntradas");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/gcpv/schema/ventasTienda", "totalEntradas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalSalidasAjustes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/gcpv/schema/ventasTienda", "totalSalidasAjustes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalRegulaciones");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/gcpv/schema/ventasTienda", "totalRegulaciones"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
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
