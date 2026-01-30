/**
 * PedidoAdicional.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.pedidosPBLWS;

public class PedidoAdicional  implements java.io.Serializable {
    private java.math.BigInteger centro;

    private java.math.BigInteger referencia;

    private java.math.BigInteger identificador;

    private java.math.BigInteger clasePedidoAdicional;

    private java.lang.String oferta;

    private java.lang.String usuario;

    private java.lang.String descripcion;

    private java.math.BigDecimal unidadesCaja;

    private java.lang.String agrupacion;

    private java.math.BigInteger perfil;

    private java.lang.String tipoAprovisionamiento;

    private es.eroski.misumi.model.pedidosPBLWS.ArrayOfPedidoAdicionalLineaLinea[] lineas;

    public PedidoAdicional() {
    }

    public PedidoAdicional(
           java.math.BigInteger centro,
           java.math.BigInteger referencia,
           java.math.BigInteger identificador,
           java.math.BigInteger clasePedidoAdicional,
           java.lang.String oferta,
           java.lang.String usuario,
           java.lang.String descripcion,
           java.math.BigDecimal unidadesCaja,
           java.lang.String agrupacion,
           java.math.BigInteger perfil,
           java.lang.String tipoAprovisionamiento,
           es.eroski.misumi.model.pedidosPBLWS.ArrayOfPedidoAdicionalLineaLinea[] lineas) {
           this.centro = centro;
           this.referencia = referencia;
           this.identificador = identificador;
           this.clasePedidoAdicional = clasePedidoAdicional;
           this.oferta = oferta;
           this.usuario = usuario;
           this.descripcion = descripcion;
           this.unidadesCaja = unidadesCaja;
           this.agrupacion = agrupacion;
           this.perfil = perfil;
           this.tipoAprovisionamiento = tipoAprovisionamiento;
           this.lineas = lineas;
    }


    /**
     * Gets the centro value for this PedidoAdicional.
     * 
     * @return centro
     */
    public java.math.BigInteger getCentro() {
        return centro;
    }


    /**
     * Sets the centro value for this PedidoAdicional.
     * 
     * @param centro
     */
    public void setCentro(java.math.BigInteger centro) {
        this.centro = centro;
    }


    /**
     * Gets the referencia value for this PedidoAdicional.
     * 
     * @return referencia
     */
    public java.math.BigInteger getReferencia() {
        return referencia;
    }


    /**
     * Sets the referencia value for this PedidoAdicional.
     * 
     * @param referencia
     */
    public void setReferencia(java.math.BigInteger referencia) {
        this.referencia = referencia;
    }


    /**
     * Gets the identificador value for this PedidoAdicional.
     * 
     * @return identificador
     */
    public java.math.BigInteger getIdentificador() {
        return identificador;
    }


    /**
     * Sets the identificador value for this PedidoAdicional.
     * 
     * @param identificador
     */
    public void setIdentificador(java.math.BigInteger identificador) {
        this.identificador = identificador;
    }


    /**
     * Gets the clasePedidoAdicional value for this PedidoAdicional.
     * 
     * @return clasePedidoAdicional
     */
    public java.math.BigInteger getClasePedidoAdicional() {
        return clasePedidoAdicional;
    }


    /**
     * Sets the clasePedidoAdicional value for this PedidoAdicional.
     * 
     * @param clasePedidoAdicional
     */
    public void setClasePedidoAdicional(java.math.BigInteger clasePedidoAdicional) {
        this.clasePedidoAdicional = clasePedidoAdicional;
    }


    /**
     * Gets the oferta value for this PedidoAdicional.
     * 
     * @return oferta
     */
    public java.lang.String getOferta() {
        return oferta;
    }


    /**
     * Sets the oferta value for this PedidoAdicional.
     * 
     * @param oferta
     */
    public void setOferta(java.lang.String oferta) {
        this.oferta = oferta;
    }


    /**
     * Gets the usuario value for this PedidoAdicional.
     * 
     * @return usuario
     */
    public java.lang.String getUsuario() {
        return usuario;
    }


    /**
     * Sets the usuario value for this PedidoAdicional.
     * 
     * @param usuario
     */
    public void setUsuario(java.lang.String usuario) {
        this.usuario = usuario;
    }


    /**
     * Gets the descripcion value for this PedidoAdicional.
     * 
     * @return descripcion
     */
    public java.lang.String getDescripcion() {
        return descripcion;
    }


    /**
     * Sets the descripcion value for this PedidoAdicional.
     * 
     * @param descripcion
     */
    public void setDescripcion(java.lang.String descripcion) {
        this.descripcion = descripcion;
    }


    /**
     * Gets the unidadesCaja value for this PedidoAdicional.
     * 
     * @return unidadesCaja
     */
    public java.math.BigDecimal getUnidadesCaja() {
        return unidadesCaja;
    }


    /**
     * Sets the unidadesCaja value for this PedidoAdicional.
     * 
     * @param unidadesCaja
     */
    public void setUnidadesCaja(java.math.BigDecimal unidadesCaja) {
        this.unidadesCaja = unidadesCaja;
    }


    /**
     * Gets the agrupacion value for this PedidoAdicional.
     * 
     * @return agrupacion
     */
    public java.lang.String getAgrupacion() {
        return agrupacion;
    }


    /**
     * Sets the agrupacion value for this PedidoAdicional.
     * 
     * @param agrupacion
     */
    public void setAgrupacion(java.lang.String agrupacion) {
        this.agrupacion = agrupacion;
    }


    /**
     * Gets the perfil value for this PedidoAdicional.
     * 
     * @return perfil
     */
    public java.math.BigInteger getPerfil() {
        return perfil;
    }


    /**
     * Sets the perfil value for this PedidoAdicional.
     * 
     * @param perfil
     */
    public void setPerfil(java.math.BigInteger perfil) {
        this.perfil = perfil;
    }


    /**
     * Gets the tipoAprovisionamiento value for this PedidoAdicional.
     * 
     * @return tipoAprovisionamiento
     */
    public java.lang.String getTipoAprovisionamiento() {
        return tipoAprovisionamiento;
    }


    /**
     * Sets the tipoAprovisionamiento value for this PedidoAdicional.
     * 
     * @param tipoAprovisionamiento
     */
    public void setTipoAprovisionamiento(java.lang.String tipoAprovisionamiento) {
        this.tipoAprovisionamiento = tipoAprovisionamiento;
    }


    /**
     * Gets the lineas value for this PedidoAdicional.
     * 
     * @return lineas
     */
    public es.eroski.misumi.model.pedidosPBLWS.ArrayOfPedidoAdicionalLineaLinea[] getLineas() {
        return lineas;
    }


    /**
     * Sets the lineas value for this PedidoAdicional.
     * 
     * @param lineas
     */
    public void setLineas(es.eroski.misumi.model.pedidosPBLWS.ArrayOfPedidoAdicionalLineaLinea[] lineas) {
        this.lineas = lineas;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PedidoAdicional)) return false;
        PedidoAdicional other = (PedidoAdicional) obj;
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
            ((this.referencia==null && other.getReferencia()==null) || 
             (this.referencia!=null &&
              this.referencia.equals(other.getReferencia()))) &&
            ((this.identificador==null && other.getIdentificador()==null) || 
             (this.identificador!=null &&
              this.identificador.equals(other.getIdentificador()))) &&
            ((this.clasePedidoAdicional==null && other.getClasePedidoAdicional()==null) || 
             (this.clasePedidoAdicional!=null &&
              this.clasePedidoAdicional.equals(other.getClasePedidoAdicional()))) &&
            ((this.oferta==null && other.getOferta()==null) || 
             (this.oferta!=null &&
              this.oferta.equals(other.getOferta()))) &&
            ((this.usuario==null && other.getUsuario()==null) || 
             (this.usuario!=null &&
              this.usuario.equals(other.getUsuario()))) &&
            ((this.descripcion==null && other.getDescripcion()==null) || 
             (this.descripcion!=null &&
              this.descripcion.equals(other.getDescripcion()))) &&
            ((this.unidadesCaja==null && other.getUnidadesCaja()==null) || 
             (this.unidadesCaja!=null &&
              this.unidadesCaja.equals(other.getUnidadesCaja()))) &&
            ((this.agrupacion==null && other.getAgrupacion()==null) || 
             (this.agrupacion!=null &&
              this.agrupacion.equals(other.getAgrupacion()))) &&
            ((this.perfil==null && other.getPerfil()==null) || 
             (this.perfil!=null &&
              this.perfil.equals(other.getPerfil()))) &&
            ((this.tipoAprovisionamiento==null && other.getTipoAprovisionamiento()==null) || 
             (this.tipoAprovisionamiento!=null &&
              this.tipoAprovisionamiento.equals(other.getTipoAprovisionamiento()))) &&
            ((this.lineas==null && other.getLineas()==null) || 
             (this.lineas!=null &&
              java.util.Arrays.equals(this.lineas, other.getLineas())));
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
        if (getReferencia() != null) {
            _hashCode += getReferencia().hashCode();
        }
        if (getIdentificador() != null) {
            _hashCode += getIdentificador().hashCode();
        }
        if (getClasePedidoAdicional() != null) {
            _hashCode += getClasePedidoAdicional().hashCode();
        }
        if (getOferta() != null) {
            _hashCode += getOferta().hashCode();
        }
        if (getUsuario() != null) {
            _hashCode += getUsuario().hashCode();
        }
        if (getDescripcion() != null) {
            _hashCode += getDescripcion().hashCode();
        }
        if (getUnidadesCaja() != null) {
            _hashCode += getUnidadesCaja().hashCode();
        }
        if (getAgrupacion() != null) {
            _hashCode += getAgrupacion().hashCode();
        }
        if (getPerfil() != null) {
            _hashCode += getPerfil().hashCode();
        }
        if (getTipoAprovisionamiento() != null) {
            _hashCode += getTipoAprovisionamiento().hashCode();
        }
        if (getLineas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getLineas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getLineas(), i);
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
        new org.apache.axis.description.TypeDesc(PedidoAdicional.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", ">PedidoAdicional"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("centro");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", "Centro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("referencia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", "Referencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identificador");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", "Identificador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clasePedidoAdicional");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", "ClasePedidoAdicional"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oferta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", "Oferta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usuario");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", "Usuario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descripcion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", "Descripcion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unidadesCaja");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", "UnidadesCaja"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agrupacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", "Agrupacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("perfil");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", "Perfil"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoAprovisionamiento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", "TipoAprovisionamiento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lineas");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", "Lineas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", ">ArrayOfPedidoAdicionalLinea>Linea"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", "Linea"));
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
