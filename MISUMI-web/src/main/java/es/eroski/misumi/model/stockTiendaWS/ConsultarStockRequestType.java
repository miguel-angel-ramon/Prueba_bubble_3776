/**
 * ConsultarStockRequestType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.stockTiendaWS;

public class ConsultarStockRequestType  implements java.io.Serializable {
    private java.math.BigInteger codigoCentro;

    private java.lang.String tipoMensaje;

    private java.math.BigInteger[] listaCodigosReferencia;

    public ConsultarStockRequestType() {
    }

    public ConsultarStockRequestType(
           java.math.BigInteger codigoCentro,
           java.lang.String tipoMensaje,
           java.math.BigInteger[] listaCodigosReferencia) {
           this.codigoCentro = codigoCentro;
           this.tipoMensaje = tipoMensaje;
           this.listaCodigosReferencia = listaCodigosReferencia;
    }


    /**
     * Gets the codigoCentro value for this ConsultarStockRequestType.
     * 
     * @return codigoCentro
     */
    public java.math.BigInteger getCodigoCentro() {
        return codigoCentro;
    }


    /**
     * Sets the codigoCentro value for this ConsultarStockRequestType.
     * 
     * @param codigoCentro
     */
    public void setCodigoCentro(java.math.BigInteger codigoCentro) {
        this.codigoCentro = codigoCentro;
    }


    /**
     * Gets the tipoMensaje value for this ConsultarStockRequestType.
     * 
     * @return tipoMensaje
     */
    public java.lang.String getTipoMensaje() {
        return tipoMensaje;
    }


    /**
     * Sets the tipoMensaje value for this ConsultarStockRequestType.
     * 
     * @param tipoMensaje
     */
    public void setTipoMensaje(java.lang.String tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }


    /**
     * Gets the listaCodigosReferencia value for this ConsultarStockRequestType.
     * 
     * @return listaCodigosReferencia
     */
    public java.math.BigInteger[] getListaCodigosReferencia() {
        return listaCodigosReferencia;
    }


    /**
     * Sets the listaCodigosReferencia value for this ConsultarStockRequestType.
     * 
     * @param listaCodigosReferencia
     */
    public void setListaCodigosReferencia(java.math.BigInteger[] listaCodigosReferencia) {
        this.listaCodigosReferencia = listaCodigosReferencia;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ConsultarStockRequestType)) return false;
        ConsultarStockRequestType other = (ConsultarStockRequestType) obj;
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
            ((this.tipoMensaje==null && other.getTipoMensaje()==null) || 
             (this.tipoMensaje!=null &&
              this.tipoMensaje.equals(other.getTipoMensaje()))) &&
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
        if (getCodigoCentro() != null) {
            _hashCode += getCodigoCentro().hashCode();
        }
        if (getTipoMensaje() != null) {
            _hashCode += getTipoMensaje().hashCode();
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
        new org.apache.axis.description.TypeDesc(ConsultarStockRequestType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ConsultarStockRequestType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoCentro");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "CodigoCentro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoMensaje");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "TipoMensaje"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listaCodigosReferencia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ListaCodigosReferencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "CodigoReferencia"));
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
