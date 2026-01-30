/**
 * ReferenciasValidadasType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.pedidosCentroWS;

public class ReferenciasValidadasType  implements java.io.Serializable {
    private java.math.BigDecimal referencia;

    private java.lang.String pedible;

    private es.eroski.misumi.model.pedidosCentroWS.MotivosType[] motivos;

    private java.util.Date fechaPrevistaEnGama;

    public ReferenciasValidadasType() {
    }

    public ReferenciasValidadasType(
           java.math.BigDecimal referencia,
           java.lang.String pedible,
           es.eroski.misumi.model.pedidosCentroWS.MotivosType[] motivos,
           java.util.Date fechaPrevistaEnGama) {
           this.referencia = referencia;
           this.pedible = pedible;
           this.motivos = motivos;
           this.fechaPrevistaEnGama = fechaPrevistaEnGama;
    }


    /**
     * Gets the referencia value for this ReferenciasValidadasType.
     * 
     * @return referencia
     */
    public java.math.BigDecimal getReferencia() {
        return referencia;
    }


    /**
     * Sets the referencia value for this ReferenciasValidadasType.
     * 
     * @param referencia
     */
    public void setReferencia(java.math.BigDecimal referencia) {
        this.referencia = referencia;
    }


    /**
     * Gets the pedible value for this ReferenciasValidadasType.
     * 
     * @return pedible
     */
    public java.lang.String getPedible() {
        return pedible;
    }


    /**
     * Sets the pedible value for this ReferenciasValidadasType.
     * 
     * @param pedible
     */
    public void setPedible(java.lang.String pedible) {
        this.pedible = pedible;
    }


    /**
     * Gets the motivos value for this ReferenciasValidadasType.
     * 
     * @return motivos
     */
    public es.eroski.misumi.model.pedidosCentroWS.MotivosType[] getMotivos() {
        return motivos;
    }


    /**
     * Sets the motivos value for this ReferenciasValidadasType.
     * 
     * @param motivos
     */
    public void setMotivos(es.eroski.misumi.model.pedidosCentroWS.MotivosType[] motivos) {
        this.motivos = motivos;
    }

    public es.eroski.misumi.model.pedidosCentroWS.MotivosType getMotivos(int i) {
        return this.motivos[i];
    }

    public void setMotivos(int i, es.eroski.misumi.model.pedidosCentroWS.MotivosType _value) {
        this.motivos[i] = _value;
    }


    /**
     * Gets the fechaPrevistaEnGama value for this ReferenciasValidadasType.
     * 
     * @return fechaPrevistaEnGama
     */
    public java.util.Date getFechaPrevistaEnGama() {
        return fechaPrevistaEnGama;
    }


    /**
     * Sets the fechaPrevistaEnGama value for this ReferenciasValidadasType.
     * 
     * @param fechaPrevistaEnGama
     */
    public void setFechaPrevistaEnGama(java.util.Date fechaPrevistaEnGama) {
        this.fechaPrevistaEnGama = fechaPrevistaEnGama;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReferenciasValidadasType)) return false;
        ReferenciasValidadasType other = (ReferenciasValidadasType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.referencia==null && other.getReferencia()==null) || 
             (this.referencia!=null &&
              this.referencia.equals(other.getReferencia()))) &&
            ((this.pedible==null && other.getPedible()==null) || 
             (this.pedible!=null &&
              this.pedible.equals(other.getPedible()))) &&
            ((this.motivos==null && other.getMotivos()==null) || 
             (this.motivos!=null &&
              java.util.Arrays.equals(this.motivos, other.getMotivos()))) &&
            ((this.fechaPrevistaEnGama==null && other.getFechaPrevistaEnGama()==null) || 
             (this.fechaPrevistaEnGama!=null &&
              this.fechaPrevistaEnGama.equals(other.getFechaPrevistaEnGama())));
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
        if (getReferencia() != null) {
            _hashCode += getReferencia().hashCode();
        }
        if (getPedible() != null) {
            _hashCode += getPedible().hashCode();
        }
        if (getMotivos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMotivos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMotivos(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFechaPrevistaEnGama() != null) {
            _hashCode += getFechaPrevistaEnGama().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ReferenciasValidadasType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/GCNP/schema/PedidosCentro", "ReferenciasValidadasType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("referencia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCNP/schema/PedidosCentro", "Referencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pedible");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCNP/schema/PedidosCentro", "Pedible"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("motivos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCNP/schema/PedidosCentro", "Motivos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/GCNP/schema/PedidosCentro", "MotivosType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaPrevistaEnGama");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCNP/schema/PedidosCentro", "FechaPrevistaEnGama"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
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
