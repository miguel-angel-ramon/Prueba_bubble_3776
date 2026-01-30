/**
 * DetalladoPedido.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.pedidosPBLWS;

public class DetalladoPedido  implements java.io.Serializable {
    private java.math.BigInteger centro;

    private java.math.BigInteger area;

    private java.math.BigInteger seccion;

    private java.math.BigInteger categoria;

    private java.math.BigInteger subCategoria;

    private java.math.BigInteger segmento;

    private java.math.BigInteger referencia;

    private java.lang.String descripcion;

    private java.math.BigDecimal stock;

    private java.math.BigDecimal enCurso1;

    private java.math.BigDecimal enCurso2;

    private java.math.BigDecimal unidadesCaja;

    private java.math.BigInteger cajasPedidas;

    private java.math.BigInteger propuesta;

    private java.math.BigInteger cantidad;

    private java.lang.String tipoDetallado;

    private java.lang.String horaLimite;

    private java.lang.String estado;

    public DetalladoPedido() {
    }

    public DetalladoPedido(
           java.math.BigInteger centro,
           java.math.BigInteger area,
           java.math.BigInteger seccion,
           java.math.BigInteger categoria,
           java.math.BigInteger subCategoria,
           java.math.BigInteger segmento,
           java.math.BigInteger referencia,
           java.lang.String descripcion,
           java.math.BigDecimal stock,
           java.math.BigDecimal enCurso1,
           java.math.BigDecimal enCurso2,
           java.math.BigDecimal unidadesCaja,
           java.math.BigInteger cajasPedidas,
           java.math.BigInteger propuesta,
           java.math.BigInteger cantidad,
           java.lang.String tipoDetallado,
           java.lang.String horaLimite,
           java.lang.String estado) {
           this.centro = centro;
           this.area = area;
           this.seccion = seccion;
           this.categoria = categoria;
           this.subCategoria = subCategoria;
           this.segmento = segmento;
           this.referencia = referencia;
           this.descripcion = descripcion;
           this.stock = stock;
           this.enCurso1 = enCurso1;
           this.enCurso2 = enCurso2;
           this.unidadesCaja = unidadesCaja;
           this.cajasPedidas = cajasPedidas;
           this.propuesta = propuesta;
           this.cantidad = cantidad;
           this.tipoDetallado = tipoDetallado;
           this.horaLimite = horaLimite;
           this.estado = estado;
    }


    /**
     * Gets the centro value for this DetalladoPedido.
     * 
     * @return centro
     */
    public java.math.BigInteger getCentro() {
        return centro;
    }


    /**
     * Sets the centro value for this DetalladoPedido.
     * 
     * @param centro
     */
    public void setCentro(java.math.BigInteger centro) {
        this.centro = centro;
    }


    /**
     * Gets the area value for this DetalladoPedido.
     * 
     * @return area
     */
    public java.math.BigInteger getArea() {
        return area;
    }


    /**
     * Sets the area value for this DetalladoPedido.
     * 
     * @param area
     */
    public void setArea(java.math.BigInteger area) {
        this.area = area;
    }


    /**
     * Gets the seccion value for this DetalladoPedido.
     * 
     * @return seccion
     */
    public java.math.BigInteger getSeccion() {
        return seccion;
    }


    /**
     * Sets the seccion value for this DetalladoPedido.
     * 
     * @param seccion
     */
    public void setSeccion(java.math.BigInteger seccion) {
        this.seccion = seccion;
    }


    /**
     * Gets the categoria value for this DetalladoPedido.
     * 
     * @return categoria
     */
    public java.math.BigInteger getCategoria() {
        return categoria;
    }


    /**
     * Sets the categoria value for this DetalladoPedido.
     * 
     * @param categoria
     */
    public void setCategoria(java.math.BigInteger categoria) {
        this.categoria = categoria;
    }


    /**
     * Gets the subCategoria value for this DetalladoPedido.
     * 
     * @return subCategoria
     */
    public java.math.BigInteger getSubCategoria() {
        return subCategoria;
    }


    /**
     * Sets the subCategoria value for this DetalladoPedido.
     * 
     * @param subCategoria
     */
    public void setSubCategoria(java.math.BigInteger subCategoria) {
        this.subCategoria = subCategoria;
    }


    /**
     * Gets the segmento value for this DetalladoPedido.
     * 
     * @return segmento
     */
    public java.math.BigInteger getSegmento() {
        return segmento;
    }


    /**
     * Sets the segmento value for this DetalladoPedido.
     * 
     * @param segmento
     */
    public void setSegmento(java.math.BigInteger segmento) {
        this.segmento = segmento;
    }


    /**
     * Gets the referencia value for this DetalladoPedido.
     * 
     * @return referencia
     */
    public java.math.BigInteger getReferencia() {
        return referencia;
    }


    /**
     * Sets the referencia value for this DetalladoPedido.
     * 
     * @param referencia
     */
    public void setReferencia(java.math.BigInteger referencia) {
        this.referencia = referencia;
    }


    /**
     * Gets the descripcion value for this DetalladoPedido.
     * 
     * @return descripcion
     */
    public java.lang.String getDescripcion() {
        return descripcion;
    }


    /**
     * Sets the descripcion value for this DetalladoPedido.
     * 
     * @param descripcion
     */
    public void setDescripcion(java.lang.String descripcion) {
        this.descripcion = descripcion;
    }


    /**
     * Gets the stock value for this DetalladoPedido.
     * 
     * @return stock
     */
    public java.math.BigDecimal getStock() {
        return stock;
    }


    /**
     * Sets the stock value for this DetalladoPedido.
     * 
     * @param stock
     */
    public void setStock(java.math.BigDecimal stock) {
        this.stock = stock;
    }


    /**
     * Gets the enCurso1 value for this DetalladoPedido.
     * 
     * @return enCurso1
     */
    public java.math.BigDecimal getEnCurso1() {
        return enCurso1;
    }


    /**
     * Sets the enCurso1 value for this DetalladoPedido.
     * 
     * @param enCurso1
     */
    public void setEnCurso1(java.math.BigDecimal enCurso1) {
        this.enCurso1 = enCurso1;
    }


    /**
     * Gets the enCurso2 value for this DetalladoPedido.
     * 
     * @return enCurso2
     */
    public java.math.BigDecimal getEnCurso2() {
        return enCurso2;
    }


    /**
     * Sets the enCurso2 value for this DetalladoPedido.
     * 
     * @param enCurso2
     */
    public void setEnCurso2(java.math.BigDecimal enCurso2) {
        this.enCurso2 = enCurso2;
    }


    /**
     * Gets the unidadesCaja value for this DetalladoPedido.
     * 
     * @return unidadesCaja
     */
    public java.math.BigDecimal getUnidadesCaja() {
        return unidadesCaja;
    }


    /**
     * Sets the unidadesCaja value for this DetalladoPedido.
     * 
     * @param unidadesCaja
     */
    public void setUnidadesCaja(java.math.BigDecimal unidadesCaja) {
        this.unidadesCaja = unidadesCaja;
    }


    /**
     * Gets the cajasPedidas value for this DetalladoPedido.
     * 
     * @return cajasPedidas
     */
    public java.math.BigInteger getCajasPedidas() {
        return cajasPedidas;
    }


    /**
     * Sets the cajasPedidas value for this DetalladoPedido.
     * 
     * @param cajasPedidas
     */
    public void setCajasPedidas(java.math.BigInteger cajasPedidas) {
        this.cajasPedidas = cajasPedidas;
    }


    /**
     * Gets the propuesta value for this DetalladoPedido.
     * 
     * @return propuesta
     */
    public java.math.BigInteger getPropuesta() {
        return propuesta;
    }


    /**
     * Sets the propuesta value for this DetalladoPedido.
     * 
     * @param propuesta
     */
    public void setPropuesta(java.math.BigInteger propuesta) {
        this.propuesta = propuesta;
    }


    /**
     * Gets the cantidad value for this DetalladoPedido.
     * 
     * @return cantidad
     */
    public java.math.BigInteger getCantidad() {
        return cantidad;
    }


    /**
     * Sets the cantidad value for this DetalladoPedido.
     * 
     * @param cantidad
     */
    public void setCantidad(java.math.BigInteger cantidad) {
        this.cantidad = cantidad;
    }


    /**
     * Gets the tipoDetallado value for this DetalladoPedido.
     * 
     * @return tipoDetallado
     */
    public java.lang.String getTipoDetallado() {
        return tipoDetallado;
    }


    /**
     * Sets the tipoDetallado value for this DetalladoPedido.
     * 
     * @param tipoDetallado
     */
    public void setTipoDetallado(java.lang.String tipoDetallado) {
        this.tipoDetallado = tipoDetallado;
    }


    /**
     * Gets the horaLimite value for this DetalladoPedido.
     * 
     * @return horaLimite
     */
    public java.lang.String getHoraLimite() {
        return horaLimite;
    }


    /**
     * Sets the horaLimite value for this DetalladoPedido.
     * 
     * @param horaLimite
     */
    public void setHoraLimite(java.lang.String horaLimite) {
        this.horaLimite = horaLimite;
    }


    /**
     * Gets the estado value for this DetalladoPedido.
     * 
     * @return estado
     */
    public java.lang.String getEstado() {
        return estado;
    }


    /**
     * Sets the estado value for this DetalladoPedido.
     * 
     * @param estado
     */
    public void setEstado(java.lang.String estado) {
        this.estado = estado;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DetalladoPedido)) return false;
        DetalladoPedido other = (DetalladoPedido) obj;
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
            ((this.subCategoria==null && other.getSubCategoria()==null) || 
             (this.subCategoria!=null &&
              this.subCategoria.equals(other.getSubCategoria()))) &&
            ((this.segmento==null && other.getSegmento()==null) || 
             (this.segmento!=null &&
              this.segmento.equals(other.getSegmento()))) &&
            ((this.referencia==null && other.getReferencia()==null) || 
             (this.referencia!=null &&
              this.referencia.equals(other.getReferencia()))) &&
            ((this.descripcion==null && other.getDescripcion()==null) || 
             (this.descripcion!=null &&
              this.descripcion.equals(other.getDescripcion()))) &&
            ((this.stock==null && other.getStock()==null) || 
             (this.stock!=null &&
              this.stock.equals(other.getStock()))) &&
            ((this.enCurso1==null && other.getEnCurso1()==null) || 
             (this.enCurso1!=null &&
              this.enCurso1.equals(other.getEnCurso1()))) &&
            ((this.enCurso2==null && other.getEnCurso2()==null) || 
             (this.enCurso2!=null &&
              this.enCurso2.equals(other.getEnCurso2()))) &&
            ((this.unidadesCaja==null && other.getUnidadesCaja()==null) || 
             (this.unidadesCaja!=null &&
              this.unidadesCaja.equals(other.getUnidadesCaja()))) &&
            ((this.cajasPedidas==null && other.getCajasPedidas()==null) || 
             (this.cajasPedidas!=null &&
              this.cajasPedidas.equals(other.getCajasPedidas()))) &&
            ((this.propuesta==null && other.getPropuesta()==null) || 
             (this.propuesta!=null &&
              this.propuesta.equals(other.getPropuesta()))) &&
            ((this.cantidad==null && other.getCantidad()==null) || 
             (this.cantidad!=null &&
              this.cantidad.equals(other.getCantidad()))) &&
            ((this.tipoDetallado==null && other.getTipoDetallado()==null) || 
             (this.tipoDetallado!=null &&
              this.tipoDetallado.equals(other.getTipoDetallado()))) &&
            ((this.horaLimite==null && other.getHoraLimite()==null) || 
             (this.horaLimite!=null &&
              this.horaLimite.equals(other.getHoraLimite()))) &&
            ((this.estado==null && other.getEstado()==null) || 
             (this.estado!=null &&
              this.estado.equals(other.getEstado())));
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
        if (getSubCategoria() != null) {
            _hashCode += getSubCategoria().hashCode();
        }
        if (getSegmento() != null) {
            _hashCode += getSegmento().hashCode();
        }
        if (getReferencia() != null) {
            _hashCode += getReferencia().hashCode();
        }
        if (getDescripcion() != null) {
            _hashCode += getDescripcion().hashCode();
        }
        if (getStock() != null) {
            _hashCode += getStock().hashCode();
        }
        if (getEnCurso1() != null) {
            _hashCode += getEnCurso1().hashCode();
        }
        if (getEnCurso2() != null) {
            _hashCode += getEnCurso2().hashCode();
        }
        if (getUnidadesCaja() != null) {
            _hashCode += getUnidadesCaja().hashCode();
        }
        if (getCajasPedidas() != null) {
            _hashCode += getCajasPedidas().hashCode();
        }
        if (getPropuesta() != null) {
            _hashCode += getPropuesta().hashCode();
        }
        if (getCantidad() != null) {
            _hashCode += getCantidad().hashCode();
        }
        if (getTipoDetallado() != null) {
            _hashCode += getTipoDetallado().hashCode();
        }
        if (getHoraLimite() != null) {
            _hashCode += getHoraLimite().hashCode();
        }
        if (getEstado() != null) {
            _hashCode += getEstado().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DetalladoPedido.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/DetalladoPedidoPBL/DetalladoPedido", ">DetalladoPedido"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("centro");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/DetalladoPedidoPBL/DetalladoPedido", "Centro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("area");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/DetalladoPedidoPBL/DetalladoPedido", "Area"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("seccion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/DetalladoPedidoPBL/DetalladoPedido", "Seccion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("categoria");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/DetalladoPedidoPBL/DetalladoPedido", "Categoria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subCategoria");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/DetalladoPedidoPBL/DetalladoPedido", "SubCategoria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("segmento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/DetalladoPedidoPBL/DetalladoPedido", "Segmento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("referencia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/DetalladoPedidoPBL/DetalladoPedido", "Referencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descripcion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/DetalladoPedidoPBL/DetalladoPedido", "Descripcion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stock");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/DetalladoPedidoPBL/DetalladoPedido", "Stock"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("enCurso1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/DetalladoPedidoPBL/DetalladoPedido", "EnCurso1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("enCurso2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/DetalladoPedidoPBL/DetalladoPedido", "EnCurso2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unidadesCaja");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/DetalladoPedidoPBL/DetalladoPedido", "UnidadesCaja"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cajasPedidas");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/DetalladoPedidoPBL/DetalladoPedido", "CajasPedidas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("propuesta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/DetalladoPedidoPBL/DetalladoPedido", "Propuesta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cantidad");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/DetalladoPedidoPBL/DetalladoPedido", "Cantidad"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoDetallado");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/DetalladoPedidoPBL/DetalladoPedido", "TipoDetallado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("horaLimite");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/DetalladoPedidoPBL/DetalladoPedido", "HoraLimite"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estado");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/DetalladoPedidoPBL/DetalladoPedido", "Estado"));
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
