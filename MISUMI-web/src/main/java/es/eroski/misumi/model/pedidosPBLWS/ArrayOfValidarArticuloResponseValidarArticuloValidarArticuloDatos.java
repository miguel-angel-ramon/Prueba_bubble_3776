/**
 * ArrayOfValidarArticuloResponseValidarArticuloValidarArticuloDatos.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.pedidosPBLWS;

public class ArrayOfValidarArticuloResponseValidarArticuloValidarArticuloDatos  implements java.io.Serializable {
    private java.util.Date primeraFechaServicio;

    private boolean bloqueoEncargo;

    private boolean bloqueoPilada;

    private boolean bloqueoDetallado;

    public ArrayOfValidarArticuloResponseValidarArticuloValidarArticuloDatos() {
    }

    public ArrayOfValidarArticuloResponseValidarArticuloValidarArticuloDatos(
           java.util.Date primeraFechaServicio,
           boolean bloqueoEncargo,
           boolean bloqueoPilada,
           boolean bloqueoDetallado) {
           this.primeraFechaServicio = primeraFechaServicio;
           this.bloqueoEncargo = bloqueoEncargo;
           this.bloqueoPilada = bloqueoPilada;
           this.bloqueoDetallado = bloqueoDetallado;
    }


    /**
     * Gets the primeraFechaServicio value for this ArrayOfValidarArticuloResponseValidarArticuloValidarArticuloDatos.
     * 
     * @return primeraFechaServicio
     */
    public java.util.Date getPrimeraFechaServicio() {
        return primeraFechaServicio;
    }


    /**
     * Sets the primeraFechaServicio value for this ArrayOfValidarArticuloResponseValidarArticuloValidarArticuloDatos.
     * 
     * @param primeraFechaServicio
     */
    public void setPrimeraFechaServicio(java.util.Date primeraFechaServicio) {
        this.primeraFechaServicio = primeraFechaServicio;
    }


    /**
     * Gets the bloqueoEncargo value for this ArrayOfValidarArticuloResponseValidarArticuloValidarArticuloDatos.
     * 
     * @return bloqueoEncargo
     */
    public boolean isBloqueoEncargo() {
        return bloqueoEncargo;
    }


    /**
     * Sets the bloqueoEncargo value for this ArrayOfValidarArticuloResponseValidarArticuloValidarArticuloDatos.
     * 
     * @param bloqueoEncargo
     */
    public void setBloqueoEncargo(boolean bloqueoEncargo) {
        this.bloqueoEncargo = bloqueoEncargo;
    }


    /**
     * Gets the bloqueoPilada value for this ArrayOfValidarArticuloResponseValidarArticuloValidarArticuloDatos.
     * 
     * @return bloqueoPilada
     */
    public boolean isBloqueoPilada() {
        return bloqueoPilada;
    }


    /**
     * Sets the bloqueoPilada value for this ArrayOfValidarArticuloResponseValidarArticuloValidarArticuloDatos.
     * 
     * @param bloqueoPilada
     */
    public void setBloqueoPilada(boolean bloqueoPilada) {
        this.bloqueoPilada = bloqueoPilada;
    }


    /**
     * Gets the bloqueoDetallado value for this ArrayOfValidarArticuloResponseValidarArticuloValidarArticuloDatos.
     * 
     * @return bloqueoDetallado
     */
    public boolean isBloqueoDetallado() {
        return bloqueoDetallado;
    }


    /**
     * Sets the bloqueoDetallado value for this ArrayOfValidarArticuloResponseValidarArticuloValidarArticuloDatos.
     * 
     * @param bloqueoDetallado
     */
    public void setBloqueoDetallado(boolean bloqueoDetallado) {
        this.bloqueoDetallado = bloqueoDetallado;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ArrayOfValidarArticuloResponseValidarArticuloValidarArticuloDatos)) return false;
        ArrayOfValidarArticuloResponseValidarArticuloValidarArticuloDatos other = (ArrayOfValidarArticuloResponseValidarArticuloValidarArticuloDatos) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.primeraFechaServicio==null && other.getPrimeraFechaServicio()==null) || 
             (this.primeraFechaServicio!=null &&
              this.primeraFechaServicio.equals(other.getPrimeraFechaServicio()))) &&
            this.bloqueoEncargo == other.isBloqueoEncargo() &&
            this.bloqueoPilada == other.isBloqueoPilada() &&
            this.bloqueoDetallado == other.isBloqueoDetallado();
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
        if (getPrimeraFechaServicio() != null) {
            _hashCode += getPrimeraFechaServicio().hashCode();
        }
        _hashCode += (isBloqueoEncargo() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isBloqueoPilada() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isBloqueoDetallado() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ArrayOfValidarArticuloResponseValidarArticuloValidarArticuloDatos.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", ">>ArrayOfValidarArticuloResponseValidarArticulo>ValidarArticulo>Datos"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("primeraFechaServicio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "PrimeraFechaServicio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bloqueoEncargo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "BloqueoEncargo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bloqueoPilada");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "BloqueoPilada"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bloqueoDetallado");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "BloqueoDetallado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
