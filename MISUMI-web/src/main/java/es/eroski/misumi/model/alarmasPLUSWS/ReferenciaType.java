/**
 * ReferenciaType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.alarmasPLUSWS;

public class ReferenciaType  implements java.io.Serializable {
    private java.math.BigInteger codigoError;

    private java.lang.String mensajeError;

    private java.math.BigInteger codigoReferencia;

    private java.math.BigInteger codigoBalanza;

    private java.math.BigDecimal stockTeorico;

    private java.util.Date fechaUltimaVenta;

    private java.math.BigInteger diasCaducidad;

    private java.lang.String envasadoTienda;

    private es.eroski.misumi.model.alarmasPLUSWS.PLUSType[] listadoPLUS;

    public ReferenciaType() {
    }

    public ReferenciaType(
           java.math.BigInteger codigoError,
           java.lang.String mensajeError,
           java.math.BigInteger codigoReferencia,
           java.math.BigInteger codigoBalanza,
           java.math.BigDecimal stockTeorico,
           java.util.Date fechaUltimaVenta,
           java.math.BigInteger diasCaducidad,
           java.lang.String envasadoTienda,
           es.eroski.misumi.model.alarmasPLUSWS.PLUSType[] listadoPLUS) {
           this.codigoError = codigoError;
           this.mensajeError = mensajeError;
           this.codigoReferencia = codigoReferencia;
           this.codigoBalanza = codigoBalanza;
           this.stockTeorico = stockTeorico;
           this.fechaUltimaVenta = fechaUltimaVenta;
           this.diasCaducidad = diasCaducidad;
           this.envasadoTienda = envasadoTienda;
           this.listadoPLUS = listadoPLUS;
    }


    /**
     * Gets the codigoError value for this ReferenciaType.
     * 
     * @return codigoError
     */
    public java.math.BigInteger getCodigoError() {
        return codigoError;
    }


    /**
     * Sets the codigoError value for this ReferenciaType.
     * 
     * @param codigoError
     */
    public void setCodigoError(java.math.BigInteger codigoError) {
        this.codigoError = codigoError;
    }


    /**
     * Gets the mensajeError value for this ReferenciaType.
     * 
     * @return mensajeError
     */
    public java.lang.String getMensajeError() {
        return mensajeError;
    }


    /**
     * Sets the mensajeError value for this ReferenciaType.
     * 
     * @param mensajeError
     */
    public void setMensajeError(java.lang.String mensajeError) {
        this.mensajeError = mensajeError;
    }


    /**
     * Gets the codigoReferencia value for this ReferenciaType.
     * 
     * @return codigoReferencia
     */
    public java.math.BigInteger getCodigoReferencia() {
        return codigoReferencia;
    }


    /**
     * Sets the codigoReferencia value for this ReferenciaType.
     * 
     * @param codigoReferencia
     */
    public void setCodigoReferencia(java.math.BigInteger codigoReferencia) {
        this.codigoReferencia = codigoReferencia;
    }


    /**
     * Gets the codigoBalanza value for this ReferenciaType.
     * 
     * @return codigoBalanza
     */
    public java.math.BigInteger getCodigoBalanza() {
        return codigoBalanza;
    }


    /**
     * Sets the codigoBalanza value for this ReferenciaType.
     * 
     * @param codigoBalanza
     */
    public void setCodigoBalanza(java.math.BigInteger codigoBalanza) {
        this.codigoBalanza = codigoBalanza;
    }


    /**
     * Gets the stockTeorico value for this ReferenciaType.
     * 
     * @return stockTeorico
     */
    public java.math.BigDecimal getStockTeorico() {
        return stockTeorico;
    }


    /**
     * Sets the stockTeorico value for this ReferenciaType.
     * 
     * @param stockTeorico
     */
    public void setStockTeorico(java.math.BigDecimal stockTeorico) {
        this.stockTeorico = stockTeorico;
    }


    /**
     * Gets the fechaUltimaVenta value for this ReferenciaType.
     * 
     * @return fechaUltimaVenta
     */
    public java.util.Date getFechaUltimaVenta() {
        return fechaUltimaVenta;
    }


    /**
     * Sets the fechaUltimaVenta value for this ReferenciaType.
     * 
     * @param fechaUltimaVenta
     */
    public void setFechaUltimaVenta(java.util.Date fechaUltimaVenta) {
        this.fechaUltimaVenta = fechaUltimaVenta;
    }


    /**
     * Gets the diasCaducidad value for this ReferenciaType.
     * 
     * @return diasCaducidad
     */
    public java.math.BigInteger getDiasCaducidad() {
        return diasCaducidad;
    }


    /**
     * Sets the diasCaducidad value for this ReferenciaType.
     * 
     * @param diasCaducidad
     */
    public void setDiasCaducidad(java.math.BigInteger diasCaducidad) {
        this.diasCaducidad = diasCaducidad;
    }


    /**
     * Gets the envasadoTienda value for this ReferenciaType.
     * 
     * @return envasadoTienda
     */
    public java.lang.String getEnvasadoTienda() {
        return envasadoTienda;
    }


    /**
     * Sets the envasadoTienda value for this ReferenciaType.
     * 
     * @param envasadoTienda
     */
    public void setEnvasadoTienda(java.lang.String envasadoTienda) {
        this.envasadoTienda = envasadoTienda;
    }


    /**
     * Gets the listadoPLUS value for this ReferenciaType.
     * 
     * @return listadoPLUS
     */
    public es.eroski.misumi.model.alarmasPLUSWS.PLUSType[] getListadoPLUS() {
        return listadoPLUS;
    }


    /**
     * Sets the listadoPLUS value for this ReferenciaType.
     * 
     * @param listadoPLUS
     */
    public void setListadoPLUS(es.eroski.misumi.model.alarmasPLUSWS.PLUSType[] listadoPLUS) {
        this.listadoPLUS = listadoPLUS;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReferenciaType)) return false;
        ReferenciaType other = (ReferenciaType) obj;
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
            ((this.codigoBalanza==null && other.getCodigoBalanza()==null) || 
             (this.codigoBalanza!=null &&
              this.codigoBalanza.equals(other.getCodigoBalanza()))) &&
            ((this.stockTeorico==null && other.getStockTeorico()==null) || 
             (this.stockTeorico!=null &&
              this.stockTeorico.equals(other.getStockTeorico()))) &&
            ((this.fechaUltimaVenta==null && other.getFechaUltimaVenta()==null) || 
             (this.fechaUltimaVenta!=null &&
              this.fechaUltimaVenta.equals(other.getFechaUltimaVenta()))) &&
            ((this.diasCaducidad==null && other.getDiasCaducidad()==null) || 
             (this.diasCaducidad!=null &&
              this.diasCaducidad.equals(other.getDiasCaducidad()))) &&
            ((this.envasadoTienda==null && other.getEnvasadoTienda()==null) || 
             (this.envasadoTienda!=null &&
              this.envasadoTienda.equals(other.getEnvasadoTienda()))) &&
            ((this.listadoPLUS==null && other.getListadoPLUS()==null) || 
             (this.listadoPLUS!=null &&
              java.util.Arrays.equals(this.listadoPLUS, other.getListadoPLUS())));
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
        if (getCodigoBalanza() != null) {
            _hashCode += getCodigoBalanza().hashCode();
        }
        if (getStockTeorico() != null) {
            _hashCode += getStockTeorico().hashCode();
        }
        if (getFechaUltimaVenta() != null) {
            _hashCode += getFechaUltimaVenta().hashCode();
        }
        if (getDiasCaducidad() != null) {
            _hashCode += getDiasCaducidad().hashCode();
        }
        if (getEnvasadoTienda() != null) {
            _hashCode += getEnvasadoTienda().hashCode();
        }
        if (getListadoPLUS() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getListadoPLUS());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getListadoPLUS(), i);
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
        new org.apache.axis.description.TypeDesc(ReferenciaType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/PLUSTienda", "ReferenciaType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoError");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/PLUSTienda", "CodigoError"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mensajeError");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/PLUSTienda", "MensajeError"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoReferencia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/PLUSTienda", "CodigoReferencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoBalanza");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/PLUSTienda", "CodigoBalanza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stockTeorico");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/PLUSTienda", "StockTeorico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaUltimaVenta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/PLUSTienda", "FechaUltimaVenta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("diasCaducidad");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/PLUSTienda", "DiasCaducidad"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("envasadoTienda");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/PLUSTienda", "EnvasadoTienda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listadoPLUS");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/PLUSTienda", "ListadoPLUS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/PLUSTienda", "PLUSType"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/PLUSTienda", "PLUS"));
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
