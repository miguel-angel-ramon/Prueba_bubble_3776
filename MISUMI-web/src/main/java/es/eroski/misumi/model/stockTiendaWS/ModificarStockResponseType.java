/**
 * ModificarStockResponseType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.stockTiendaWS;

public class ModificarStockResponseType  implements java.io.Serializable {
    private java.lang.String codigoRespuesta;

    private java.lang.String descripcionRespuesta;

    private java.math.BigInteger codigoReferenciaModificada;

    private java.lang.String stockPrincipal;

    private java.math.BigInteger bandejas;

    private java.math.BigDecimal stock;

    private es.eroski.misumi.model.stockTiendaWS.ReferenciaResModType[] listaCodigosReferencia;

    public ModificarStockResponseType() {
    }

    public ModificarStockResponseType(
           java.lang.String codigoRespuesta,
           java.lang.String descripcionRespuesta,
           java.math.BigInteger codigoReferenciaModificada,
           java.lang.String stockPrincipal,
           java.math.BigInteger bandejas,
           java.math.BigDecimal stock,
           es.eroski.misumi.model.stockTiendaWS.ReferenciaResModType[] listaCodigosReferencia) {
           this.codigoRespuesta = codigoRespuesta;
           this.descripcionRespuesta = descripcionRespuesta;
           this.codigoReferenciaModificada = codigoReferenciaModificada;
           this.stockPrincipal = stockPrincipal;
           this.bandejas = bandejas;
           this.stock = stock;
           this.listaCodigosReferencia = listaCodigosReferencia;
    }


    /**
     * Gets the codigoRespuesta value for this ModificarStockResponseType.
     * 
     * @return codigoRespuesta
     */
    public java.lang.String getCodigoRespuesta() {
        return codigoRespuesta;
    }


    /**
     * Sets the codigoRespuesta value for this ModificarStockResponseType.
     * 
     * @param codigoRespuesta
     */
    public void setCodigoRespuesta(java.lang.String codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }


    /**
     * Gets the descripcionRespuesta value for this ModificarStockResponseType.
     * 
     * @return descripcionRespuesta
     */
    public java.lang.String getDescripcionRespuesta() {
        return descripcionRespuesta;
    }


    /**
     * Sets the descripcionRespuesta value for this ModificarStockResponseType.
     * 
     * @param descripcionRespuesta
     */
    public void setDescripcionRespuesta(java.lang.String descripcionRespuesta) {
        this.descripcionRespuesta = descripcionRespuesta;
    }


    /**
     * Gets the codigoReferenciaModificada value for this ModificarStockResponseType.
     * 
     * @return codigoReferenciaModificada
     */
    public java.math.BigInteger getCodigoReferenciaModificada() {
        return codigoReferenciaModificada;
    }


    /**
     * Sets the codigoReferenciaModificada value for this ModificarStockResponseType.
     * 
     * @param codigoReferenciaModificada
     */
    public void setCodigoReferenciaModificada(java.math.BigInteger codigoReferenciaModificada) {
        this.codigoReferenciaModificada = codigoReferenciaModificada;
    }


    /**
     * Gets the stockPrincipal value for this ModificarStockResponseType.
     * 
     * @return stockPrincipal
     */
    public java.lang.String getStockPrincipal() {
        return stockPrincipal;
    }


    /**
     * Sets the stockPrincipal value for this ModificarStockResponseType.
     * 
     * @param stockPrincipal
     */
    public void setStockPrincipal(java.lang.String stockPrincipal) {
        this.stockPrincipal = stockPrincipal;
    }


    /**
     * Gets the bandejas value for this ModificarStockResponseType.
     * 
     * @return bandejas
     */
    public java.math.BigInteger getBandejas() {
        return bandejas;
    }


    /**
     * Sets the bandejas value for this ModificarStockResponseType.
     * 
     * @param bandejas
     */
    public void setBandejas(java.math.BigInteger bandejas) {
        this.bandejas = bandejas;
    }


    /**
     * Gets the stock value for this ModificarStockResponseType.
     * 
     * @return stock
     */
    public java.math.BigDecimal getStock() {
        return stock;
    }


    /**
     * Sets the stock value for this ModificarStockResponseType.
     * 
     * @param stock
     */
    public void setStock(java.math.BigDecimal stock) {
        this.stock = stock;
    }


    /**
     * Gets the listaCodigosReferencia value for this ModificarStockResponseType.
     * 
     * @return listaCodigosReferencia
     */
    public es.eroski.misumi.model.stockTiendaWS.ReferenciaResModType[] getListaCodigosReferencia() {
        return listaCodigosReferencia;
    }


    /**
     * Sets the listaCodigosReferencia value for this ModificarStockResponseType.
     * 
     * @param listaCodigosReferencia
     */
    public void setListaCodigosReferencia(es.eroski.misumi.model.stockTiendaWS.ReferenciaResModType[] listaCodigosReferencia) {
        this.listaCodigosReferencia = listaCodigosReferencia;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ModificarStockResponseType)) return false;
        ModificarStockResponseType other = (ModificarStockResponseType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codigoRespuesta==null && other.getCodigoRespuesta()==null) || 
             (this.codigoRespuesta!=null &&
              this.codigoRespuesta.equals(other.getCodigoRespuesta()))) &&
            ((this.descripcionRespuesta==null && other.getDescripcionRespuesta()==null) || 
             (this.descripcionRespuesta!=null &&
              this.descripcionRespuesta.equals(other.getDescripcionRespuesta()))) &&
            ((this.codigoReferenciaModificada==null && other.getCodigoReferenciaModificada()==null) || 
             (this.codigoReferenciaModificada!=null &&
              this.codigoReferenciaModificada.equals(other.getCodigoReferenciaModificada()))) &&
            ((this.stockPrincipal==null && other.getStockPrincipal()==null) || 
             (this.stockPrincipal!=null &&
              this.stockPrincipal.equals(other.getStockPrincipal()))) &&
            ((this.bandejas==null && other.getBandejas()==null) || 
             (this.bandejas!=null &&
              this.bandejas.equals(other.getBandejas()))) &&
            ((this.stock==null && other.getStock()==null) || 
             (this.stock!=null &&
              this.stock.equals(other.getStock()))) &&
            ((this.listaCodigosReferencia==null && other.getListaCodigosReferencia()==null) || 
             (this.listaCodigosReferencia!=null &&
              java.util.Arrays.equals(this.listaCodigosReferencia, other.getListaCodigosReferencia())));
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
        if (getCodigoRespuesta() != null) {
            _hashCode += getCodigoRespuesta().hashCode();
        }
        if (getDescripcionRespuesta() != null) {
            _hashCode += getDescripcionRespuesta().hashCode();
        }
        if (getCodigoReferenciaModificada() != null) {
            _hashCode += getCodigoReferenciaModificada().hashCode();
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
        if (getListaCodigosReferencia() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getListaCodigosReferencia());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getListaCodigosReferencia(), i);
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
        new org.apache.axis.description.TypeDesc(ModificarStockResponseType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ModificarStockResponseType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoRespuesta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "CodigoRespuesta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descripcionRespuesta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "DescripcionRespuesta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoReferenciaModificada");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "CodigoReferenciaModificada"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
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
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listaCodigosReferencia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ListaCodigosReferencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ReferenciaResModType"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ReferenciaResMod"));
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
