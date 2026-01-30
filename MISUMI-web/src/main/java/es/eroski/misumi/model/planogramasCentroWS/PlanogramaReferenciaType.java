/**
 * PlanogramaReferenciaType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.planogramasCentroWS;

public class PlanogramaReferenciaType  implements java.io.Serializable {
    private java.math.BigDecimal referencia;

    private java.lang.String planogramada;

    private java.lang.String motivo;

    private java.lang.String nombrePlanograma;

    private java.math.BigInteger sustituidaPor;

    private java.math.BigInteger sustitutaDe;

    private java.lang.String implantancion;

    private java.util.Calendar fechaActivacion;

    public PlanogramaReferenciaType() {
    }

    public PlanogramaReferenciaType(
           java.math.BigDecimal referencia,
           java.lang.String planogramada,
           java.lang.String motivo,
           java.lang.String nombrePlanograma,
           java.math.BigInteger sustituidaPor,
           java.math.BigInteger sustitutaDe,
           java.lang.String implantancion,
           java.util.Calendar fechaActivacion) {
           this.referencia = referencia;
           this.planogramada = planogramada;
           this.motivo = motivo;
           this.nombrePlanograma = nombrePlanograma;
           this.sustituidaPor = sustituidaPor;
           this.sustitutaDe = sustitutaDe;
           this.implantancion = implantancion;
           this.fechaActivacion = fechaActivacion;
    }


    /**
     * Gets the referencia value for this PlanogramaReferenciaType.
     * 
     * @return referencia
     */
    public java.math.BigDecimal getReferencia() {
        return referencia;
    }


    /**
     * Sets the referencia value for this PlanogramaReferenciaType.
     * 
     * @param referencia
     */
    public void setReferencia(java.math.BigDecimal referencia) {
        this.referencia = referencia;
    }


    /**
     * Gets the planogramada value for this PlanogramaReferenciaType.
     * 
     * @return planogramada
     */
    public java.lang.String getPlanogramada() {
        return planogramada;
    }


    /**
     * Sets the planogramada value for this PlanogramaReferenciaType.
     * 
     * @param planogramada
     */
    public void setPlanogramada(java.lang.String planogramada) {
        this.planogramada = planogramada;
    }


    /**
     * Gets the motivo value for this PlanogramaReferenciaType.
     * 
     * @return motivo
     */
    public java.lang.String getMotivo() {
        return motivo;
    }


    /**
     * Sets the motivo value for this PlanogramaReferenciaType.
     * 
     * @param motivo
     */
    public void setMotivo(java.lang.String motivo) {
        this.motivo = motivo;
    }


    /**
     * Gets the nombrePlanograma value for this PlanogramaReferenciaType.
     * 
     * @return nombrePlanograma
     */
    public java.lang.String getNombrePlanograma() {
        return nombrePlanograma;
    }


    /**
     * Sets the nombrePlanograma value for this PlanogramaReferenciaType.
     * 
     * @param nombrePlanograma
     */
    public void setNombrePlanograma(java.lang.String nombrePlanograma) {
        this.nombrePlanograma = nombrePlanograma;
    }


    /**
     * Gets the sustituidaPor value for this PlanogramaReferenciaType.
     * 
     * @return sustituidaPor
     */
    public java.math.BigInteger getSustituidaPor() {
        return sustituidaPor;
    }


    /**
     * Sets the sustituidaPor value for this PlanogramaReferenciaType.
     * 
     * @param sustituidaPor
     */
    public void setSustituidaPor(java.math.BigInteger sustituidaPor) {
        this.sustituidaPor = sustituidaPor;
    }


    /**
     * Gets the sustitutaDe value for this PlanogramaReferenciaType.
     * 
     * @return sustitutaDe
     */
    public java.math.BigInteger getSustitutaDe() {
        return sustitutaDe;
    }


    /**
     * Sets the sustitutaDe value for this PlanogramaReferenciaType.
     * 
     * @param sustitutaDe
     */
    public void setSustitutaDe(java.math.BigInteger sustitutaDe) {
        this.sustitutaDe = sustitutaDe;
    }


    /**
     * Gets the implantancion value for this PlanogramaReferenciaType.
     * 
     * @return implantancion
     */
    public java.lang.String getImplantancion() {
        return implantancion;
    }


    /**
     * Sets the implantancion value for this PlanogramaReferenciaType.
     * 
     * @param implantancion
     */
    public void setImplantancion(java.lang.String implantancion) {
        this.implantancion = implantancion;
    }


    /**
     * Gets the fechaActivacion value for this PlanogramaReferenciaType.
     * 
     * @return fechaActivacion
     */
    public java.util.Calendar getFechaActivacion() {
        return fechaActivacion;
    }


    /**
     * Sets the fechaActivacion value for this PlanogramaReferenciaType.
     * 
     * @param fechaActivacion
     */
    public void setFechaActivacion(java.util.Calendar fechaActivacion) {
        this.fechaActivacion = fechaActivacion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PlanogramaReferenciaType)) return false;
        PlanogramaReferenciaType other = (PlanogramaReferenciaType) obj;
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
            ((this.planogramada==null && other.getPlanogramada()==null) || 
             (this.planogramada!=null &&
              this.planogramada.equals(other.getPlanogramada()))) &&
            ((this.motivo==null && other.getMotivo()==null) || 
             (this.motivo!=null &&
              this.motivo.equals(other.getMotivo()))) &&
            ((this.nombrePlanograma==null && other.getNombrePlanograma()==null) || 
             (this.nombrePlanograma!=null &&
              this.nombrePlanograma.equals(other.getNombrePlanograma()))) &&
            ((this.sustituidaPor==null && other.getSustituidaPor()==null) || 
             (this.sustituidaPor!=null &&
              this.sustituidaPor.equals(other.getSustituidaPor()))) &&
            ((this.sustitutaDe==null && other.getSustitutaDe()==null) || 
             (this.sustitutaDe!=null &&
              this.sustitutaDe.equals(other.getSustitutaDe()))) &&
            ((this.implantancion==null && other.getImplantancion()==null) || 
             (this.implantancion!=null &&
              this.implantancion.equals(other.getImplantancion()))) &&
            ((this.fechaActivacion==null && other.getFechaActivacion()==null) || 
             (this.fechaActivacion!=null &&
              this.fechaActivacion.equals(other.getFechaActivacion())));
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
        if (getPlanogramada() != null) {
            _hashCode += getPlanogramada().hashCode();
        }
        if (getMotivo() != null) {
            _hashCode += getMotivo().hashCode();
        }
        if (getNombrePlanograma() != null) {
            _hashCode += getNombrePlanograma().hashCode();
        }
        if (getSustituidaPor() != null) {
            _hashCode += getSustituidaPor().hashCode();
        }
        if (getSustitutaDe() != null) {
            _hashCode += getSustitutaDe().hashCode();
        }
        if (getImplantancion() != null) {
            _hashCode += getImplantancion().hashCode();
        }
        if (getFechaActivacion() != null) {
            _hashCode += getFechaActivacion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PlanogramaReferenciaType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/GCNP/schema/PlanogramasCentro", "PlanogramaReferenciaType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("referencia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCNP/schema/PlanogramasCentro", "Referencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("planogramada");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCNP/schema/PlanogramasCentro", "Planogramada"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("motivo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCNP/schema/PlanogramasCentro", "Motivo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombrePlanograma");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCNP/schema/PlanogramasCentro", "NombrePlanograma"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sustituidaPor");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCNP/schema/PlanogramasCentro", "SustituidaPor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sustitutaDe");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCNP/schema/PlanogramasCentro", "SustitutaDe"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("implantancion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCNP/schema/PlanogramasCentro", "Implantancion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaActivacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/GCNP/schema/PlanogramasCentro", "FechaActivacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
