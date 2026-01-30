/**
 * ConsultaPlanogramaPorReferenciaResponseType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.planogramasCentroWS;

public class ConsultaPlanogramaPorReferenciaResponseType  implements java.io.Serializable {
    private java.lang.String codigoRespuesta;

    private java.lang.String descripcionRespuesta;

    private es.eroski.misumi.model.planogramasCentroWS.PlanogramaReferenciaType[] planogramaReferencia;

    public ConsultaPlanogramaPorReferenciaResponseType() {
    }

    public ConsultaPlanogramaPorReferenciaResponseType(
           java.lang.String codigoRespuesta,
           java.lang.String descripcionRespuesta,
           es.eroski.misumi.model.planogramasCentroWS.PlanogramaReferenciaType[] planogramaReferencia) {
           this.codigoRespuesta = codigoRespuesta;
           this.descripcionRespuesta = descripcionRespuesta;
           this.planogramaReferencia = planogramaReferencia;
    }

    
    /**
     * Gets the codigoRespuesta value for this ConsultaPlanogramaPorReferenciaResponseType.
     * 
     * @return codigoRespuesta
     */
    public java.lang.String getCodigoRespuesta() {
        return codigoRespuesta;
    }

    
    /**
     * Sets the codigoRespuesta value for this ConsultaPlanogramaPorReferenciaResponseType.
     * 
     * @param codigoRespuesta
     */
    public void setCodigoRespuesta(java.lang.String codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    
    /**
     * Gets the descripcionRespuesta value for this ConsultaPlanogramaPorReferenciaResponseType.
     * 
     * @return descripcionRespuesta
     */
    public java.lang.String getDescripcionRespuesta() {
        return descripcionRespuesta;
    }


    /**
     * Sets the descripcionRespuesta value for this ConsultaPlanogramaPorReferenciaResponseType.
     * 
     * @param descripcionRespuesta
     */
    public void setDescripcionRespuesta(java.lang.String descripcionRespuesta) {
        this.descripcionRespuesta = descripcionRespuesta;
    }


    /**
     * Gets the planogramaReferencia value for this ConsultaPlanogramaPorReferenciaResponseType.
     * 
     * @return planogramaReferencia
     */
    public es.eroski.misumi.model.planogramasCentroWS.PlanogramaReferenciaType[] getPlanogramaReferencia() {
        return planogramaReferencia;
    }


    /**
     * Sets the planogramaReferencia value for this ConsultaPlanogramaPorReferenciaResponseType.
     * 
     * @param planogramaReferencia
     */
    public void setPlanogramaReferencia(es.eroski.misumi.model.planogramasCentroWS.PlanogramaReferenciaType[] planogramaReferencia) {
        this.planogramaReferencia = planogramaReferencia;
    }

    public es.eroski.misumi.model.planogramasCentroWS.PlanogramaReferenciaType getPlanogramaReferencia(int i) {
        return this.planogramaReferencia[i];
    }

    public void setPlanogramaReferencia(int i, es.eroski.misumi.model.planogramasCentroWS.PlanogramaReferenciaType _value) {
        this.planogramaReferencia[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ConsultaPlanogramaPorReferenciaResponseType)) return false;
        ConsultaPlanogramaPorReferenciaResponseType other = (ConsultaPlanogramaPorReferenciaResponseType) obj;
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
            ((this.planogramaReferencia==null && other.getPlanogramaReferencia()==null) || 
             (this.planogramaReferencia!=null &&
              java.util.Arrays.equals(this.planogramaReferencia, other.getPlanogramaReferencia())));
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
        if (getPlanogramaReferencia() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPlanogramaReferencia());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPlanogramaReferencia(), i);
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
        new org.apache.axis.description.TypeDesc(ConsultaPlanogramaPorReferenciaResponseType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/GCNP/schema/PlanogramasCentro", "ConsultaPlanogramaPorReferenciaResponseType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoRespuesta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCNP/schema/PlanogramasCentro", "CodigoRespuesta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descripcionRespuesta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCNP/schema/PlanogramasCentro", "DescripcionRespuesta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("planogramaReferencia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCNP/schema/PlanogramasCentro", "PlanogramaReferencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/GCNP/schema/PlanogramasCentro", "PlanogramaReferenciaType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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
