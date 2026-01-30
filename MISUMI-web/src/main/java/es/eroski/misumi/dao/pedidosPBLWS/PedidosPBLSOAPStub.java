/**
 * PedidosPBLSOAPStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.dao.pedidosPBLWS;

import es.eroski.misumi.model.pedidosPBLWS.ArrayOfPedidoAdicionalResponseLineaLinea;
import es.eroski.misumi.model.pedidosPBLWS.ArrayOfValidarArticuloRequestValidarArticuloValidarArticulo;
import es.eroski.misumi.model.pedidosPBLWS.ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo;
import es.eroski.misumi.model.pedidosPBLWS.ArrayOfValidarArticuloResponseValidarArticuloValidarArticuloDatos;
import es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoModificarReponse;
import es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoObtenerRequest;
import es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoObtenerResponse;
import es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoResponse;
import es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalContarClasesRequest;
import es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalContarClasesResponse;
import es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalContarClasesResponseContadores;
import es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalObtenerRequest;
import es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalObtenerResponse;
import es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalResponse;
import es.eroski.misumi.util.WSBManager;

public class PedidosPBLSOAPStub extends org.apache.axis.client.Stub implements es.eroski.misumi.dao.pedidosPBLWS.PedidosPBLSOAP {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[11];
        _initOperationDesc1();
        _initOperationDesc2();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("DetalladoPedidoModificar");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "DetalladoPedidoModificarRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.eroski.es/DetalladoPedidoPBL/DetalladoPedido", "ArrayOfDetalladoPedido"), es.eroski.misumi.model.pedidosPBLWS.ArrayOfDetalladoPedidoDetalladoPedido[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("http://www.eroski.es/DetalladoPedidoPBL/DetalladoPedido", "DetalladoPedido"));
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", ">DetalladoPedidoModificarReponse"));
        oper.setReturnClass(es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoModificarReponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "DetalladoPedidoModificarReponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("DetalladoPedidoObtener");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "DetalladoPedidoObtenerRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", ">DetalladoPedidoObtenerRequest"), es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoObtenerRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", ">DetalladoPedidoObtenerResponse"));
        oper.setReturnClass(es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoObtenerResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "DetalladoPedidoObtenerResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("PedidoAdicionalContarClases");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "PedidoAdicionalContarClasesRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", ">PedidoAdicionalContarClasesRequest"), es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalContarClasesRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", ">PedidoAdicionalContarClasesResponse"));
        oper.setReturnClass(es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalContarClasesResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "PedidoAdicionalContarClasesResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("PedidoAdicionalEliminar");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "PedidoAdicionalEliminarRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", "ArrayOfPedidoAdicional"), es.eroski.misumi.model.pedidosPBLWS.ArrayOfPedidoAdicionalPedidoAdicional[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", "PedidoAdicional"));
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "ArrayOfPedidoAdicionalResponse"));
        oper.setReturnClass(es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalResponse[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "PedidoAdicionalEliminarResponse"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "PedidoAdicional"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("PedidoAdicionalInsertar");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "PedidoAdicionalInsertarRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", "ArrayOfPedidoAdicional"), es.eroski.misumi.model.pedidosPBLWS.ArrayOfPedidoAdicionalPedidoAdicional[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", "PedidoAdicional"));
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "ArrayOfPedidoAdicionalResponse"));
        oper.setReturnClass(es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalResponse[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "PedidoAdicionalInsertarResponse"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "PedidoAdicional"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("PedidoAdicionalModificar");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "PedidoAdicionalModificarRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", "ArrayOfPedidoAdicional"), es.eroski.misumi.model.pedidosPBLWS.ArrayOfPedidoAdicionalPedidoAdicional[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", "PedidoAdicional"));
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "ArrayOfPedidoAdicionalResponse"));
        oper.setReturnClass(es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalResponse[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "PedidoAdicionalModificarResponse"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "PedidoAdicional"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("PedidoAdicionalObtener");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "PedidoAdicionalObtenerRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", ">PedidoAdicionalObtenerRequest"), es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalObtenerRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", ">PedidoAdicionalObtenerResponse"));
        oper.setReturnClass(es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalObtenerResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "PedidoAdicionalObtenerResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ValidarArticulo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "ValidarArticuloRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "ArrayOfValidarArticuloRequestValidarArticulo"), es.eroski.misumi.model.pedidosPBLWS.ArrayOfValidarArticuloRequestValidarArticuloValidarArticulo[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "ValidarArticulo"));
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "ArrayOfValidarArticuloResponseValidarArticulo"));
        oper.setReturnClass(es.eroski.misumi.model.pedidosPBLWS.ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "ValidarArticuloResponse"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "ValidarArticulo"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ExclusionVentaObtener");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "ExclusionVentaObtenerRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.eroski.es/ExclusionVentasPBL/ExclusionVentas", "ExclusionVentaObtenerRequestType"), es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaObtenerRequestType.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.eroski.es/ExclusionVentasPBL/ExclusionVentas", "ExclusionVentaObtenerResponseType"));
        oper.setReturnClass(es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaObtenerResponseType.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "ExclusionVentaObtenerResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ExclusionVentaInsertar");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "ExclusionVentaInsertarRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.eroski.es/ExclusionVentasPBL/ExclusionVentas", "ExclusionVentaInsertarRequestType"), es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaInsertarRequestType.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.eroski.es/ExclusionVentasPBL/ExclusionVentas", "ExclusionVentaInsertarResponseType"));
        oper.setReturnClass(es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaInsertarResponseType.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "ExclusionVentaInsertarResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ExclusionVentaBorrar");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "ExclusionVentaBorrarRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.eroski.es/ExclusionVentasPBL/ExclusionVentas", "ExclusionVentaBorrarRequestType"), es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaBorrarRequestType.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.eroski.es/ExclusionVentasPBL/ExclusionVentas", "ExclusionVentaBorrarResponseType"));
        oper.setReturnClass(es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaBorrarResponseType.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "ExclusionVentaBorrarResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[10] = oper;

    }

    public PedidosPBLSOAPStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public PedidosPBLSOAPStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public PedidosPBLSOAPStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://www.eroski.es/DetalladoPedidoPBL/DetalladoPedido", ">ArrayOfDetalladoPedido>DetalladoPedido");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.pedidosPBLWS.ArrayOfDetalladoPedidoDetalladoPedido.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.eroski.es/DetalladoPedidoPBL/DetalladoPedido", ">DetalladoPedido");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.pedidosPBLWS.DetalladoPedido.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.eroski.es/DetalladoPedidoPBL/DetalladoPedido", "ArrayOfDetalladoPedido");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.pedidosPBLWS.ArrayOfDetalladoPedidoDetalladoPedido[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.eroski.es/DetalladoPedidoPBL/DetalladoPedido", ">ArrayOfDetalladoPedido>DetalladoPedido");
            qName2 = new javax.xml.namespace.QName("http://www.eroski.es/DetalladoPedidoPBL/DetalladoPedido", "DetalladoPedido");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.eroski.es/ExclusionVentasPBL/ExclusionVentas", "ExclusionVentaBorrarRequestType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaBorrarRequestType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.eroski.es/ExclusionVentasPBL/ExclusionVentas", "ExclusionVentaBorrarResponseType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaBorrarResponseType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.eroski.es/ExclusionVentasPBL/ExclusionVentas", "ExclusionVentaInsertarRequestType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaInsertarRequestType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.eroski.es/ExclusionVentasPBL/ExclusionVentas", "ExclusionVentaInsertarResponseType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaInsertarResponseType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.eroski.es/ExclusionVentasPBL/ExclusionVentas", "ExclusionVentaObtenerRequestType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaObtenerRequestType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.eroski.es/ExclusionVentasPBL/ExclusionVentas", "ExclusionVentaObtenerResponseType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaObtenerResponseType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.eroski.es/ExclusionVentasPBL/ExclusionVentas", "ExclusionVentasBorrarType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.pedidosPBLWS.ExclusionVentasBorrarType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.eroski.es/ExclusionVentasPBL/ExclusionVentas", "ExclusionVentasType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.pedidosPBLWS.ExclusionVentasType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.eroski.es/ExclusionVentasPBL/ExclusionVentas", "ListaExclusionVentasRespuestaType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.pedidosPBLWS.ExclusionVentasBorrarType[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.eroski.es/ExclusionVentasPBL/ExclusionVentas", "ExclusionVentasBorrarType");
            qName2 = new javax.xml.namespace.QName("http://www.eroski.es/ExclusionVentasPBL/ExclusionVentas", "exclusionVentasRespuesta");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.eroski.es/ExclusionVentasPBL/ExclusionVentas", "ListaExclusionVentasType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.pedidosPBLWS.ExclusionVentasType[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.eroski.es/ExclusionVentasPBL/ExclusionVentas", "ExclusionVentasType");
            qName2 = new javax.xml.namespace.QName("http://www.eroski.es/ExclusionVentasPBL/ExclusionVentas", "exclusionVentas");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.eroski.es/ExclusionVentasPBL/ExclusionVentas", "TiendaType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.pedidosPBLWS.TiendaType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", ">ArrayOfPedidoAdicional>PedidoAdicional");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.pedidosPBLWS.ArrayOfPedidoAdicionalPedidoAdicional.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", ">ArrayOfPedidoAdicionalLinea>Linea");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.pedidosPBLWS.ArrayOfPedidoAdicionalLineaLinea.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", ">PedidoAdicional");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.pedidosPBLWS.PedidoAdicional.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", "ArrayOfPedidoAdicional");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.pedidosPBLWS.ArrayOfPedidoAdicionalPedidoAdicional[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", ">ArrayOfPedidoAdicional>PedidoAdicional");
            qName2 = new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", "PedidoAdicional");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", "ArrayOfPedidoAdicionalLinea");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.pedidosPBLWS.ArrayOfPedidoAdicionalLineaLinea[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", ">ArrayOfPedidoAdicionalLinea>Linea");
            qName2 = new javax.xml.namespace.QName("http://www.eroski.es/PedidoAdicionalPBL/PedidoAdicional", "Linea");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", ">>ArrayOfValidarArticuloResponseValidarArticulo>ValidarArticulo>Datos");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.pedidosPBLWS.ArrayOfValidarArticuloResponseValidarArticuloValidarArticuloDatos.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", ">>PedidoAdicionalContarClasesResponse>Contadores");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalContarClasesResponseContadores.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", ">ArrayOfPedidoAdicionalResponseLinea>Linea");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.pedidosPBLWS.ArrayOfPedidoAdicionalResponseLineaLinea.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", ">ArrayOfValidarArticuloRequestValidarArticulo>ValidarArticulo");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.pedidosPBLWS.ArrayOfValidarArticuloRequestValidarArticuloValidarArticulo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", ">ArrayOfValidarArticuloResponseValidarArticulo>ValidarArticulo");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.pedidosPBLWS.ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", ">DetalladoPedidoModificarReponse");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoModificarReponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", ">DetalladoPedidoObtenerRequest");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoObtenerRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", ">DetalladoPedidoObtenerResponse");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoObtenerResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", ">PedidoAdicionalContarClasesRequest");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalContarClasesRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", ">PedidoAdicionalContarClasesResponse");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalContarClasesResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", ">PedidoAdicionalObtenerRequest");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalObtenerRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", ">PedidoAdicionalObtenerResponse");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalObtenerResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "ArrayOfPedidoAdicionalResponse");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalResponse[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "PedidoAdicionalResponse");
            qName2 = new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "PedidoAdicional");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "ArrayOfPedidoAdicionalResponseLinea");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.pedidosPBLWS.ArrayOfPedidoAdicionalResponseLineaLinea[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", ">ArrayOfPedidoAdicionalResponseLinea>Linea");
            qName2 = new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "Linea");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "ArrayOfValidarArticuloRequestValidarArticulo");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.pedidosPBLWS.ArrayOfValidarArticuloRequestValidarArticuloValidarArticulo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", ">ArrayOfValidarArticuloRequestValidarArticulo>ValidarArticulo");
            qName2 = new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "ValidarArticulo");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "ArrayOfValidarArticuloResponseValidarArticulo");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.pedidosPBLWS.ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", ">ArrayOfValidarArticuloResponseValidarArticulo>ValidarArticulo");
            qName2 = new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "ValidarArticulo");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "DetalladoPedidoResponse");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "PedidoAdicionalResponse");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoModificarReponse detalladoPedidoModificar(es.eroski.misumi.model.pedidosPBLWS.ArrayOfDetalladoPedidoDetalladoPedido[] detalladoPedidoModificarRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0/DetalladoPedidoModificar");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "DetalladoPedidoModificar"));
        
        _call.setUsername(WSBManager.getProperty("ws.user"));
        _call.setPassword(WSBManager.getProperty("ws.pwd2"));


        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {detalladoPedidoModificarRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoModificarReponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoModificarReponse) org.apache.axis.utils.JavaUtils.convert(_resp, es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoModificarReponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoObtenerResponse detalladoPedidoObtener(es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoObtenerRequest detalladoPedidoObtenerRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0/DetalladoPedidoObtener");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "DetalladoPedidoObtener"));

        _call.setUsername(WSBManager.getProperty("ws.user"));
        _call.setPassword(WSBManager.getProperty("ws.pwd2"));
        
        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {detalladoPedidoObtenerRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoObtenerResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoObtenerResponse) org.apache.axis.utils.JavaUtils.convert(_resp, es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoObtenerResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalContarClasesResponse pedidoAdicionalContarClases(es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalContarClasesRequest pedidoAdicionalContarClasesRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0/PedidoAdicionalContarClases");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "PedidoAdicionalContarClases"));

        _call.setUsername(WSBManager.getProperty("ws.user"));
        _call.setPassword(WSBManager.getProperty("ws.pwd2"));
        
        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {pedidoAdicionalContarClasesRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalContarClasesResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalContarClasesResponse) org.apache.axis.utils.JavaUtils.convert(_resp, es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalContarClasesResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalResponse[] pedidoAdicionalEliminar(es.eroski.misumi.model.pedidosPBLWS.ArrayOfPedidoAdicionalPedidoAdicional[] pedidoAdicionalEliminarRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0/PedidoAdicionalEliminar");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "PedidoAdicionalEliminar"));

        _call.setUsername(WSBManager.getProperty("ws.user"));
        _call.setPassword(WSBManager.getProperty("ws.pwd2"));
        
        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {pedidoAdicionalEliminarRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalResponse[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalResponse[]) org.apache.axis.utils.JavaUtils.convert(_resp, es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalResponse[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalResponse[] pedidoAdicionalInsertar(es.eroski.misumi.model.pedidosPBLWS.ArrayOfPedidoAdicionalPedidoAdicional[] pedidoAdicionalInsertarRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0/PedidoAdicionalInsertar");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "PedidoAdicionalInsertar"));

        _call.setUsername(WSBManager.getProperty("ws.user"));
        _call.setPassword(WSBManager.getProperty("ws.pwd2"));
        
        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {pedidoAdicionalInsertarRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalResponse[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalResponse[]) org.apache.axis.utils.JavaUtils.convert(_resp, es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalResponse[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalResponse[] pedidoAdicionalModificar(es.eroski.misumi.model.pedidosPBLWS.ArrayOfPedidoAdicionalPedidoAdicional[] pedidoAdicionalModificarRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0/PedidoAdicionalModificar");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "PedidoAdicionalModificar"));

        _call.setUsername(WSBManager.getProperty("ws.user"));
        _call.setPassword(WSBManager.getProperty("ws.pwd2"));
        
        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {pedidoAdicionalModificarRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalResponse[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalResponse[]) org.apache.axis.utils.JavaUtils.convert(_resp, es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalResponse[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalObtenerResponse pedidoAdicionalObtener(es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalObtenerRequest pedidoAdicionalObtenerRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0/PedidoAdicionalObtener");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "PedidoAdicionalObtener"));

        _call.setUsername(WSBManager.getProperty("ws.user"));
        _call.setPassword(WSBManager.getProperty("ws.pwd2"));
        
        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {pedidoAdicionalObtenerRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalObtenerResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalObtenerResponse) org.apache.axis.utils.JavaUtils.convert(_resp, es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalObtenerResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.eroski.misumi.model.pedidosPBLWS.ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo[] validarArticulo(es.eroski.misumi.model.pedidosPBLWS.ArrayOfValidarArticuloRequestValidarArticuloValidarArticulo[] validarArticuloRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0/ValidarArticulo");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "ValidarArticulo"));

        _call.setUsername(WSBManager.getProperty("ws.user"));
        _call.setPassword(WSBManager.getProperty("ws.pwd2"));
        
        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {validarArticuloRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.eroski.misumi.model.pedidosPBLWS.ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.eroski.misumi.model.pedidosPBLWS.ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo[]) org.apache.axis.utils.JavaUtils.convert(_resp, es.eroski.misumi.model.pedidosPBLWS.ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaObtenerResponseType exclusionVentaObtener(es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaObtenerRequestType obtenerExclusion) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0/ExclusionVentaObtener");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "ExclusionVentaObtener"));

        _call.setUsername(WSBManager.getProperty("ws.user"));
        _call.setPassword(WSBManager.getProperty("ws.pwd2"));
        
        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {obtenerExclusion});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaObtenerResponseType) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaObtenerResponseType) org.apache.axis.utils.JavaUtils.convert(_resp, es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaObtenerResponseType.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaInsertarResponseType exclusionVentaInsertar(es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaInsertarRequestType insertarExclusion) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0/ExclusionVentaInsertar");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "ExclusionVentaInsertar"));

        _call.setUsername(WSBManager.getProperty("ws.user"));
        _call.setPassword(WSBManager.getProperty("ws.pwd2"));
        
        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {insertarExclusion});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaInsertarResponseType) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaInsertarResponseType) org.apache.axis.utils.JavaUtils.convert(_resp, es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaInsertarResponseType.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaBorrarResponseType exclusionVentaBorrar(es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaBorrarRequestType borrarExclusion) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0/ExclusionVentaBorrar");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "ExclusionVentaBorrar"));

        _call.setUsername(WSBManager.getProperty("ws.user"));
        _call.setPassword(WSBManager.getProperty("ws.pwd2"));
        
        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {borrarExclusion});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaBorrarResponseType) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaBorrarResponseType) org.apache.axis.utils.JavaUtils.convert(_resp, es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaBorrarResponseType.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
