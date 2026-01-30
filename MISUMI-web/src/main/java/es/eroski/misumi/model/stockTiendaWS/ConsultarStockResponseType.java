/**
 * ConsultarStockResponseType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.stockTiendaWS;

public class ConsultarStockResponseType  implements java.io.Serializable {
    private java.lang.String codigoRespuesta;

    private java.lang.String descripcionRespuesta;

    private java.lang.String tipoMensaje;

    private es.eroski.misumi.model.stockTiendaWS.ReferenciaType[] listaReferencias;

    public ConsultarStockResponseType() {
    }

    public ConsultarStockResponseType(
           java.lang.String codigoRespuesta,
           java.lang.String descripcionRespuesta,
           java.lang.String tipoMensaje,
           es.eroski.misumi.model.stockTiendaWS.ReferenciaType[] listaReferencias) {
           this.codigoRespuesta = codigoRespuesta;
           this.descripcionRespuesta = descripcionRespuesta;
           this.tipoMensaje = tipoMensaje;
           this.listaReferencias = listaReferencias;
    }


    /**
     * Gets the codigoRespuesta value for this ConsultarStockResponseType.
     * 
     * @return codigoRespuesta
     */
    public java.lang.String getCodigoRespuesta() {
        return codigoRespuesta;
    }


    /**
     * Sets the codigoRespuesta value for this ConsultarStockResponseType.
     * 
     * @param codigoRespuesta
     */
    public void setCodigoRespuesta(java.lang.String codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }


    /**
     * Gets the descripcionRespuesta value for this ConsultarStockResponseType.
     * 
     * @return descripcionRespuesta
     */
    public java.lang.String getDescripcionRespuesta() {
        return descripcionRespuesta;
    }


    /**
     * Sets the descripcionRespuesta value for this ConsultarStockResponseType.
     * 
     * @param descripcionRespuesta
     */
    public void setDescripcionRespuesta(java.lang.String descripcionRespuesta) {
        this.descripcionRespuesta = descripcionRespuesta;
    }


    /**
     * Gets the tipoMensaje value for this ConsultarStockResponseType.
     * 
     * @return tipoMensaje
     */
    public java.lang.String getTipoMensaje() {
        return tipoMensaje;
    }


    /**
     * Sets the tipoMensaje value for this ConsultarStockResponseType.
     * 
     * @param tipoMensaje
     */
    public void setTipoMensaje(java.lang.String tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }


    /**
     * Gets the listaReferencias value for this ConsultarStockResponseType.
     * 
     * @return listaReferencias
     */
    public es.eroski.misumi.model.stockTiendaWS.ReferenciaType[] getListaReferencias() {
        return listaReferencias;
    }


    /**
     * Sets the listaReferencias value for this ConsultarStockResponseType.
     * 
     * @param listaReferencias
     */
    public void setListaReferencias(es.eroski.misumi.model.stockTiendaWS.ReferenciaType[] listaReferencias) {
        this.listaReferencias = listaReferencias;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ConsultarStockResponseType)) return false;
        ConsultarStockResponseType other = (ConsultarStockResponseType) obj;
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
            ((this.tipoMensaje==null && other.getTipoMensaje()==null) || 
             (this.tipoMensaje!=null &&
              this.tipoMensaje.equals(other.getTipoMensaje()))) &&
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
        if (getCodigoRespuesta() != null) {
            _hashCode += getCodigoRespuesta().hashCode();
        }
        if (getDescripcionRespuesta() != null) {
            _hashCode += getDescripcionRespuesta().hashCode();
        }
        if (getTipoMensaje() != null) {
            _hashCode += getTipoMensaje().hashCode();
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
        new org.apache.axis.description.TypeDesc(ConsultarStockResponseType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ConsultarStockResponseType"));
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
        elemField.setFieldName("tipoMensaje");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "TipoMensaje"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listaReferencias");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ListaReferencias"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ReferenciaType"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "Referencia"));
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
