/**
 * StockTiendaBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.dao.stockTiendaWS;

import es.eroski.misumi.util.WSBManager;

public class StockTiendaBindingStub extends org.apache.axis.client.Stub implements es.eroski.misumi.dao.stockTiendaWS.StockTiendaPortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[3];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ConsultarStock");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ConsultarStockRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ConsultarStockRequestType"), es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ConsultarStockResponseType"));
        oper.setReturnClass(es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ConsultarStockResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ModificarStock");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ModificarStockRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ModificarStockRequestType"), es.eroski.misumi.model.stockTiendaWS.ModificarStockRequestType.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ModificarStockResponseType"));
        oper.setReturnClass(es.eroski.misumi.model.stockTiendaWS.ModificarStockResponseType.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ModificarStockResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("Devolucion");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "DevolucionRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "DevolucionRequestType"), es.eroski.misumi.model.stockTiendaWS.DevolucionRequestType.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "DevolucionResponseType"));
        oper.setReturnClass(es.eroski.misumi.model.stockTiendaWS.DevolucionResponseType.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "DevolucionResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

    }

    public StockTiendaBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public StockTiendaBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public StockTiendaBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", ">DevolucionRequestType>Documento");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", ">ReferenciasDevolucionType>Stock");
            cachedSerQNames.add(qName);
            cls = java.math.BigDecimal.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "CodigosReferenciaDevolucionType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.stockTiendaWS.CodigosReferenciaDevolucionType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ConsultarStockRequestType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ConsultarStockResponseType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "DevolucionRequestType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.stockTiendaWS.DevolucionRequestType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "DevolucionResponseType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.stockTiendaWS.DevolucionResponseType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ListaCodigosReferenciasDevolucionType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.stockTiendaWS.CodigosReferenciaDevolucionType[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "CodigosReferenciaDevolucionType");
            qName2 = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ReferenciaRespuesta");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ListaCodigosReferenciaType");
            cachedSerQNames.add(qName);
            cls = java.math.BigInteger[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer");
            qName2 = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "CodigoReferencia");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ListaReferenciasDevolucionType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.stockTiendaWS.ReferenciasDevolucionType[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ReferenciasDevolucionType");
            qName2 = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "Referencia");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ListaReferenciasModType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.stockTiendaWS.ReferenciaModType[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ReferenciaModType");
            qName2 = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "Referencia");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ListaReferenciasResModType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.stockTiendaWS.ReferenciaResModType[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ReferenciaResModType");
            qName2 = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ReferenciaResMod");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ListaReferenciasType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.stockTiendaWS.ReferenciaType[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ReferenciaType");
            qName2 = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "Referencia");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ModificarStockRequestType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.stockTiendaWS.ModificarStockRequestType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ModificarStockResponseType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.stockTiendaWS.ModificarStockResponseType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ReferenciaModType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.stockTiendaWS.ReferenciaModType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ReferenciaResModType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.stockTiendaWS.ReferenciaResModType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ReferenciasDevolucionType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.stockTiendaWS.ReferenciasDevolucionType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/StockTienda201907", "ReferenciaType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.stockTiendaWS.ReferenciaType.class;
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

    public es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType consultarStock(es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType part1) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("ConsultarStock");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "ConsultarStock"));
        _call.setUsername(WSBManager.getProperty("ws.user"));
        _call.setPassword(WSBManager.getProperty("ws.pwd2"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {part1});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType) org.apache.axis.utils.JavaUtils.convert(_resp, es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.eroski.misumi.model.stockTiendaWS.ModificarStockResponseType modificarStock(es.eroski.misumi.model.stockTiendaWS.ModificarStockRequestType part3) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("ModificarStock");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "ModificarStock"));
        _call.setUsername(WSBManager.getProperty("ws.user"));
        _call.setPassword(WSBManager.getProperty("ws.pwd2"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {part3});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.eroski.misumi.model.stockTiendaWS.ModificarStockResponseType) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.eroski.misumi.model.stockTiendaWS.ModificarStockResponseType) org.apache.axis.utils.JavaUtils.convert(_resp, es.eroski.misumi.model.stockTiendaWS.ModificarStockResponseType.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.eroski.misumi.model.stockTiendaWS.DevolucionResponseType devolucion(es.eroski.misumi.model.stockTiendaWS.DevolucionRequestType part5) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("Devolucion");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "Devolucion"));
        _call.setUsername(WSBManager.getProperty("ws.user"));
        _call.setPassword(WSBManager.getProperty("ws.pwd2"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {part5});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.eroski.misumi.model.stockTiendaWS.DevolucionResponseType) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.eroski.misumi.model.stockTiendaWS.DevolucionResponseType) org.apache.axis.utils.JavaUtils.convert(_resp, es.eroski.misumi.model.stockTiendaWS.DevolucionResponseType.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
