/**
 * ReferenciaType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.stockTiendaWS;

public class ReferenciaType  implements java.io.Serializable {
    private java.math.BigInteger codigoReferencia;

    private java.math.BigInteger codigoError;

    private java.lang.String mensajeError;

    private java.lang.String tipoReferencia;

    private java.lang.String descripcion;

    private java.lang.String stockPrincipal;

    private java.math.BigInteger bandejas;

    private java.math.BigDecimal stock;

    private java.math.BigDecimal PVP;

    public ReferenciaType() {
    }

    public ReferenciaType(
           java.math.BigInteger codigoReferencia,
           java.math.BigInteger codigoError,
           java.lang.String mensajeError,
           java.lang.String tipoReferencia,
           java.lang.String descripcion,
           java.lang.String stockPrincipal,
           java.math.BigInteger bandejas,
           java.math.BigDecimal stock,
           java.math.BigDecimal PVP) {
           this.codigoReferencia = codigoReferencia;
           this.codigoError = codigoError;
           this.mensajeError = mensajeError;
           this.tipoReferencia = tipoReferencia;
           this.descripcion = descripcion;
           this.stockPrincipal = stockPrincipal;
           this.bandejas = bandejas;
           this.stock = stock;
           this.PVP = PVP;
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
     * Gets the tipoReferencia value for this ReferenciaType.
     * 
     * @return tipoReferencia
     */
    public java.lang.String getTipoReferencia() {
        return tipoReferencia;
    }


    /**
     * Sets the tipoReferencia value for this ReferenciaType.
     * 
     * @param tipoReferencia
     */
    public void setTipoReferencia(java.lang.String tipoReferencia) {
        this.tipoReferencia = tipoReferencia;
    }


    /**
     * Gets the descripcion value for this ReferenciaType.
     * 
     * @return descripcion
     */
    public java.lang.String getDescripcion() {
        return descripcion;
    }


    /**
     * Sets the descripcion value for this ReferenciaType.
     * 
     * @param descripcion
     */
    public void setDescripcion(java.lang.String descripcion) {
        this.descripcion = descripcion;
    }


    /**
     * Gets the stockPrincipal value for this ReferenciaType.
     * 
     * @return stockPrincipal
     */
    public java.lang.String getStockPrincipal() {
        return stockPrincipal;
    }


    /**
     * Sets the stockPrincipal value for this ReferenciaType.
     * 
     * @param stockPrincipal
     */
    public void setStockPrincipal(java.lang.String stockPrincipal) {
        this.stockPrincipal = stockPrincipal;
    }


    /**
     * Gets the bandejas value for this ReferenciaType.
     * 
     * @return bandejas
     */
    public java.math.BigInteger getBandejas() {
        return bandejas;
    }


    /**
     * Sets the bandejas value for this ReferenciaType.
     * 
     * @param bandejas
     */
    public void setBandejas(java.math.BigInteger bandejas) {
        this.bandejas = bandejas;
    }


    /**
     * Gets the stock value for this ReferenciaType.
     * 
     * @return stock
     */
    public java.math.BigDecimal getStock() {
        return stock;
    }


    /**
     * Sets the stock value for this ReferenciaType.
     * 
     * @param stock
     */
    public void setStock(java.math.BigDecimal stock) {
        this.stock = stock;
    }


    /**
     * Gets the PVP value for this ReferenciaType.
     * 
     * @return PVP
     */
    public java.math.BigDecimal getPVP() {
        return PVP;
    }


    /**
     * Sets the PVP value for this ReferenciaType.
     * 
     * @param PVP
     */
    public void setPVP(java.math.BigDecimal PVP) {
        this.PVP = PVP;
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
            ((this.codigoReferencia==null && other.getCodigoReferencia()==null) || 
             (this.codigoReferencia!=null &&
              this.codigoReferencia.equals(other.getCodigoReferencia()))) &&
            ((this.codigoError==null && other.getCodigoError()==null) || 
             (this.codigoError!=null &&
              this.codigoError.equals(other.getCodigoError()))) &&
            ((this.mensajeError==null && other.getMensajeError()==null) || 
             (this.mensajeError!=null &&
              this.mensajeError.equals(other.getMensajeError()))) &&
            ((this.tipoReferencia==null && other.getTipoReferencia()==null) || 
             (this.tipoReferencia!=null &&
              this.tipoReferencia.equals(other.getTipoReferencia()))) &&
            ((this.descripcion==null && other.getDescripcion()==null) || 
             (this.descripcion!=null &&
              this.descripcion.equals(other.getDescripcion()))) &&
            ((this.stockPrincipal==null && other.getStockPrincipal()==null) || 
             (this.stockPrincipal!=null &&
              this.stockPrincipal.equals(other.getStockPrincipal()))) &&
            ((this.bandejas==null && other.getBandejas()==null) || 
             (this.bandejas!=null &&
              this.bandejas.equals(other.getBandejas()))) &&
            ((this.stock==null && other.getStock()==null) || 
             (this.stock!=null &&
              this.stock.equals(other.getStock()))) &&
            ((this.PVP==null && other.getPVP()==null) || 
             (this.PVP!=null &&
              this.PVP.equals(other.getPVP())));
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
        if (getCodigoError() != null) {
            _hashCode += getCodigoError().hashCode();
        }
        if (getMensajeError() != null) {
            _hashCode += getMensajeError().hashCode();
        }
        if (getTipoReferencia() != null) {
            _hashCode += getTipoReferencia().hashCode();
        }
        if (getDescripcion() != null) {
            _hashCode += getDescripcion().hashCode();
        }
        if (getStockPrincipal() != null) {
            _hashCode += getStockPrincipal().hashCode();
        }
        if (getBandejas() != null) {
            _hashCode += getBandejas().hashCode();
        }
        if (getStock() != null) {
            _hashCode += getStock().hashCode();
        }
        if (getPVP() != null) {
            _hashCode += getPVP().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ReferenciaType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ReferenciaType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoReferencia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "CodigoReferencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoError");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "CodigoError"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mensajeError");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "MensajeError"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoReferencia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "TipoReferencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descripcion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "Descripcion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stockPrincipal");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "StockPrincipal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bandejas");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "Bandejas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stock");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "Stock"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("PVP");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "PVP"));
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
