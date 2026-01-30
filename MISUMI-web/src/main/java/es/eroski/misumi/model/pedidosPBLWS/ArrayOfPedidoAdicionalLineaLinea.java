/**
 * ArrayOfPedidoAdicionalLineaLinea.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.model.pedidosPBLWS;

public class ArrayOfPedidoAdicionalLineaLinea  implements Cloneable, java.io.Serializable {
    private java.util.Date fechaInicio;

    private java.util.Date fechaFin;

    private java.math.BigDecimal cantidad;

    private java.math.BigInteger implantacionMinima;

    private java.math.BigInteger capacidad;

    private boolean excluir;

    private java.lang.Boolean cajas;

    private java.lang.String tratamiento;

    private java.lang.String tipoPedidoAdicional;

    private java.lang.String modificable;

    public ArrayOfPedidoAdicionalLineaLinea() {
      	 super();
      }
      
      public  ArrayOfPedidoAdicionalLineaLinea clone() throws CloneNotSupportedException{
   		return (ArrayOfPedidoAdicionalLineaLinea)super.clone();
   	}
    public ArrayOfPedidoAdicionalLineaLinea(
           java.util.Date fechaInicio,
           java.util.Date fechaFin,
           java.math.BigDecimal cantidad,
           java.math.BigInteger implantacionMinima,
           java.math.BigInteger capacidad,
           boolean excluir,
           java.lang.Boolean cajas,
           java.lang.String tratamiento,
           java.lang.String tipoPedidoAdicional,
           java.lang.String modificable) {
           this.fechaInicio = fechaInicio;
           this.fechaFin = fechaFin;
           this.cantidad = cantidad;
           this.implantacionMinima = implantacionMinima;
           this.capacidad = capacidad;
           this.excluir = excluir;
           this.cajas = cajas;
           this.tratamiento = tratamiento;
           this.tipoPedidoAdicional = tipoPedidoAdicional;
           this.modificable = modificable;
    }


    /**
     * Gets the fechaInicio value for this ArrayOfPedidoAdicionalLineaLinea.
     * 
     * @return fechaInicio
     */
    public java.util.Date getFechaInicio() {
        return fechaInicio;
    }


    /**
     * Sets the fechaInicio value for this ArrayOfPedidoAdicionalLineaLinea.
     * 
     * @param fechaInicio
     */
    public void setFechaInicio(java.util.Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }


    /**
     * Gets the fechaFin value for this ArrayOfPedidoAdicionalLineaLinea.
     * 
     * @return fechaFin
     */
    public java.util.Date getFechaFin() {
        return fechaFin;
    }


    /**
     * Sets the fechaFin value for this ArrayOfPedidoAdicionalLineaLinea.
     * 
     * @param fechaFin
     */
    public void setFechaFin(java.util.Date fechaFin) {
        this.fechaFin = fechaFin;
    }


    /**
     * Gets the cantidad value for this ArrayOfPedidoAdicionalLineaLinea.
     * 
     * @return cantidad
     */
    public java.math.BigDecimal getCantidad() {
        return cantidad;
    }


    /**
     * Sets the cantidad value for this ArrayOfPedidoAdicionalLineaLinea.
     * 
     * @param cantidad
     */
    public void setCantidad(java.math.BigDecimal cantidad) {
        this.cantidad = cantidad;
    }


    /**
     * Gets the implantacionMinima value for this ArrayOfPedidoAdicionalLineaLinea.
     * 
     * @return implantacionMinima
     */
    public java.math.BigInteger getImplantacionMinima() {
        return implantacionMinima;
    }


    /**
     * Sets the implantacionMinima value for this ArrayOfPedidoAdicionalLineaLinea.
     * 
     * @param implantacionMinima
     */
    public void setImplantacionMinima(java.math.BigInteger implantacionMinima) {
        this.implantacionMinima = implantacionMinima;
    }


    /**
     * Gets the capacidad value for this ArrayOfPedidoAdicionalLineaLinea.
     * 
     * @return capacidad
     */
    public java.math.BigInteger getCapacidad() {
        return capacidad;
    }


    /**
     * Sets the capacidad value for this ArrayOfPedidoAdicionalLineaLinea.
     * 
     * @param capacidad
     */
    public void setCapacidad(java.math.BigInteger capacidad) {
        this.capacidad = capacidad;
    }


    /**
     * Gets the excluir value for this ArrayOfPedidoAdicionalLineaLinea.
     * 
     * @return excluir
     */
    public boolean isExcluir() {
        return excluir;
    }


    /**
     * Sets the excluir value for this ArrayOfPedidoAdicionalLineaLinea.
     * 
     * @param excluir
     */
    public void setExcluir(boolean excluir) {
        this.excluir = excluir;
    }


    /**
     * Gets the cajas value for this ArrayOfPedidoAdicionalLineaLinea.
     * 
     * @return cajas
     */
    public java.lang.Boolean getCajas() {
        return cajas;
    }


    /**
     * Sets the cajas value for this ArrayOfPedidoAdicionalLineaLinea.
     * 
     * @param cajas
     */
    public void setCajas(java.lang.Boolean cajas) {
        this.cajas = cajas;
    }


    /**
     * Gets the tratamiento value for this ArrayOfPedidoAdicionalLineaLinea.
     * 
     * @return tratamiento
     */
    public java.lang.String getTratamiento() {
        return tratamiento;
    }


    /**
     * Sets the tratamiento value for this ArrayOfPedidoAdicionalLineaLinea.
     * 
     * @param tratamiento
     */
    public void setTratamiento(java.lang.String tratamiento) {
        this.tratamiento = tratamiento;
    }


    /**
     * Gets the tipoPedidoAdicional value for this ArrayOfPedidoAdicionalLineaLinea.
     * 
     * @return tipoPedidoAdicional
     */
    public java.lang.String getTipoPedidoAdicional() {
        return tipoPedidoAdicional;
    }


    /**
     * Sets the tipoPedidoAdicional value for this ArrayOfPedidoAdicionalLineaLinea.
     * 
     * @param tipoPedidoAdicional
     */
    public void setTipoPedidoAdicional(java.lang.String tipoPedidoAdicional) {
        this.tipoPedidoAdicional = tipoPedidoAdicional;
    }


    /**
     * Gets the modificable value for this ArrayOfPedidoAdicionalLineaLinea.
     * 
     * @return modificable
     */
    public java.lang.String getModificable() {
        return modificable;
    }


    /**
     * Sets the modificable value for this ArrayOfPedidoAdicionalLineaLinea.
     * 
     * @param modificable
     */
    public void setModificable(java.lang.String modificable) {
        this.modificable = modificable;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ArrayOfPedidoAdicionalLineaLinea)) return false;
        ArrayOfPedidoAdicionalLineaLinea other = (ArrayOfPedidoAdicionalLineaLinea) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.fechaInicio==null && other.getFechaInicio()==null) || 
             (this.fechaInicio!=null &&
              this.fechaInicio.equals(other.getFechaInicio()))) &&
            ((this.fechaFin==null && other.getFechaFin()==null) || 
             (this.fechaFin!=null &&
              this.fechaFin.equals(other.getFechaFin()))) &&
            ((this.cantidad==null && other.getCantidad()==null) || 
             (this.cantidad!=null &&
              this.cantidad.equals(other.getCantidad()))) &&
            ((this.implantacionMinima==null && other.getImplantacionMinima()==null) || 
             (this.implantacionMinima!=null &&
              this.implantacionMinima.equals(other.getImplantacionMinima()))) &&
            ((this.capacidad==null && other.getCapacidad()==null) || 
             (this.capacidad!=null &&
              this.capacidad.equals(other.getCapacidad()))) &&
            this.excluir == other.isExcluir() &&
            ((this.cajas==null && other.getCajas()==null) || 
             (this.cajas!=null &&
              this.cajas.equals(other.getCajas()))) &&
            ((this.tratamiento==null && other.getTratamiento()==null) || 
             (this.tratamiento!=null &&
              this.tratamiento.equals(other.getTratamiento()))) &&
            ((this.tipoPedidoAdicional==null && other.getTipoPedidoAdicional()==null) || 
             (this.tipoPedidoAdicional!=null &&
              this.tipoPedidoAdicional.equals(other.getTipoPedidoAdicional()))) &&
            ((this.modificable==null && other.getModificable()==null) || 
             (this.modificable!=null &&
              this.modificable.equals(other.getModificable())));
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
        if (getFechaInicio() != null) {
            _hashCode += getFechaInicio().hashCode();
        }
        if (getFechaFin() != null) {
            _hashCode += getFechaFin().hashCode();
        }
        if (getCantidad() != null) {
            _hashCode += getCantidad().hashCode();
        }
        if (getImplantacionMinima() != null) {
            _hashCode += getImplantacionMinima().hashCode();
        }
        if (getCapacidad() != null) {
            _hashCode += getCapacidad().hashCode();
        }
        _hashCode += (isExcluir() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getCajas() != null) {
            _hashCode += getCajas().hashCode();
        }
        if (getTratamiento() != null) {
            _hashCode += getTratamiento().hashCode();
        }
        if (getTipoPedidoAdicional() != null) {
            _hashCode += getTipoPedidoAdicional().hashCode();
        }
        if (getModificable() != null) {
            _hashCode += getModificable().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ArrayOfPedidoAdicionalLineaLinea.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", ">ArrayOfPedidoAdicionalLinea>Linea"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaInicio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", "FechaInicio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaFin");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", "FechaFin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cantidad");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", "Cantidad"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("implantacionMinima");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", "ImplantacionMinima"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("capacidad");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", "Capacidad"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("excluir");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", "Excluir"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cajas");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", "Cajas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tratamiento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", "Tratamiento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoPedidoAdicional");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", "TipoPedidoAdicional"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modificable");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", "Modificable"));
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
