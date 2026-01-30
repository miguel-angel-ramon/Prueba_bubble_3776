/**
 * PedidoAdicionalObtenerRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.pedidosPBLWS;

public class PedidoAdicionalObtenerRequest  implements java.io.Serializable {
    private java.math.BigInteger centro;

    private java.math.BigInteger area;

    private java.math.BigInteger seccion;

    private java.math.BigInteger categoria;

    private java.math.BigInteger referencia;

    private java.math.BigInteger clasePedidoAdicional;

    private java.lang.String oferta;

    public PedidoAdicionalObtenerRequest() {
    }

    public PedidoAdicionalObtenerRequest(
           java.math.BigInteger centro,
           java.math.BigInteger area,
           java.math.BigInteger seccion,
           java.math.BigInteger categoria,
           java.math.BigInteger referencia,
           java.math.BigInteger clasePedidoAdicional,
           java.lang.String oferta) {
           this.centro = centro;
           this.area = area;
           this.seccion = seccion;
           this.categoria = categoria;
           this.referencia = referencia;
           this.clasePedidoAdicional = clasePedidoAdicional;
           this.oferta = oferta;
    }


    /**
     * Gets the centro value for this PedidoAdicionalObtenerRequest.
     * 
     * @return centro
     */
    public java.math.BigInteger getCentro() {
        return centro;
    }


    /**
     * Sets the centro value for this PedidoAdicionalObtenerRequest.
     * 
     * @param centro
     */
    public void setCentro(java.math.BigInteger centro) {
        this.centro = centro;
    }


    /**
     * Gets the area value for this PedidoAdicionalObtenerRequest.
     * 
     * @return area
     */
    public java.math.BigInteger getArea() {
        return area;
    }


    /**
     * Sets the area value for this PedidoAdicionalObtenerRequest.
     * 
     * @param area
     */
    public void setArea(java.math.BigInteger area) {
        this.area = area;
    }


    /**
     * Gets the seccion value for this PedidoAdicionalObtenerRequest.
     * 
     * @return seccion
     */
    public java.math.BigInteger getSeccion() {
        return seccion;
    }


    /**
     * Sets the seccion value for this PedidoAdicionalObtenerRequest.
     * 
     * @param seccion
     */
    public void setSeccion(java.math.BigInteger seccion) {
        this.seccion = seccion;
    }


    /**
     * Gets the categoria value for this PedidoAdicionalObtenerRequest.
     * 
     * @return categoria
     */
    public java.math.BigInteger getCategoria() {
        return categoria;
    }


    /**
     * Sets the categoria value for this PedidoAdicionalObtenerRequest.
     * 
     * @param categoria
     */
    public void setCategoria(java.math.BigInteger categoria) {
        this.categoria = categoria;
    }


    /**
     * Gets the referencia value for this PedidoAdicionalObtenerRequest.
     * 
     * @return referencia
     */
    public java.math.BigInteger getReferencia() {
        return referencia;
    }


    /**
     * Sets the referencia value for this PedidoAdicionalObtenerRequest.
     * 
     * @param referencia
     */
    public void setReferencia(java.math.BigInteger referencia) {
        this.referencia = referencia;
    }


    /**
     * Gets the clasePedidoAdicional value for this PedidoAdicionalObtenerRequest.
     * 
     * @return clasePedidoAdicional
     */
    public java.math.BigInteger getClasePedidoAdicional() {
        return clasePedidoAdicional;
    }


    /**
     * Sets the clasePedidoAdicional value for this PedidoAdicionalObtenerRequest.
     * 
     * @param clasePedidoAdicional
     */
    public void setClasePedidoAdicional(java.math.BigInteger clasePedidoAdicional) {
        this.clasePedidoAdicional = clasePedidoAdicional;
    }


    /**
     * Gets the oferta value for this PedidoAdicionalObtenerRequest.
     * 
     * @return oferta
     */
    public java.lang.String getOferta() {
        return oferta;
    }


    /**
     * Sets the oferta value for this PedidoAdicionalObtenerRequest.
     * 
     * @param oferta
     */
    public void setOferta(java.lang.String oferta) {
        this.oferta = oferta;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PedidoAdicionalObtenerRequest)) return false;
        PedidoAdicionalObtenerRequest other = (PedidoAdicionalObtenerRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.centro==null && other.getCentro()==null) || 
             (this.centro!=null &&
              this.centro.equals(other.getCentro()))) &&
            ((this.area==null && other.getArea()==null) || 
             (this.area!=null &&
              this.area.equals(other.getArea()))) &&
            ((this.seccion==null && other.getSeccion()==null) || 
             (this.seccion!=null &&
              this.seccion.equals(other.getSeccion()))) &&
            ((this.categoria==null && other.getCategoria()==null) || 
             (this.categoria!=null &&
              this.categoria.equals(other.getCategoria()))) &&
            ((this.referencia==null && other.getReferencia()==null) || 
             (this.referencia!=null &&
              this.referencia.equals(other.getReferencia()))) &&
            ((this.clasePedidoAdicional==null && other.getClasePedidoAdicional()==null) || 
             (this.clasePedidoAdicional!=null &&
              this.clasePedidoAdicional.equals(other.getClasePedidoAdicional()))) &&
            ((this.oferta==null && other.getOferta()==null) || 
             (this.oferta!=null &&
              this.oferta.equals(other.getOferta())));
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
        if (getCentro() != null) {
            _hashCode += getCentro().hashCode();
        }
        if (getArea() != null) {
            _hashCode += getArea().hashCode();
        }
        if (getSeccion() != null) {
            _hashCode += getSeccion().hashCode();
        }
        if (getCategoria() != null) {
            _hashCode += getCategoria().hashCode();
        }
        if (getReferencia() != null) {
            _hashCode += getReferencia().hashCode();
        }
        if (getClasePedidoAdicional() != null) {
            _hashCode += getClasePedidoAdicional().hashCode();
        }
        if (getOferta() != null) {
            _hashCode += getOferta().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PedidoAdicionalObtenerRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", ">PedidoAdicionalObtenerRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("centro");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "Centro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("area");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "Area"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("seccion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "Seccion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("categoria");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "Categoria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("referencia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "Referencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clasePedidoAdicional");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "ClasePedidoAdicional"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oferta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "Oferta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
