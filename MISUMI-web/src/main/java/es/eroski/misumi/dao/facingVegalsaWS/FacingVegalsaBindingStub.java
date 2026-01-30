/**
 * FacingVegalsaBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.eroski.misumi.dao.facingVegalsaWS;
import es.eroski.misumi.util.WSBManager;

public class FacingVegalsaBindingStub extends org.apache.axis.client.Stub implements es.eroski.misumi.dao.facingVegalsaWS.FacingVegalsaPortType {
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
        oper.setName("ConsultaFacing");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ConsultaFacingVegalsaRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ConsultaFacingVegalsaRequestType"), es.eroski.misumi.model.facingVegalsaWS.ConsultaFacingVegalsaRequestType.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ConsultaFacingVegalsaResponseType"));
        oper.setReturnClass(es.eroski.misumi.model.facingVegalsaWS.ConsultaFacingVegalsaResponseType.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ConsultaFacingVegalsaResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ModificarFacing");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ModificarFacingVegalsaRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ModificarFacingVegalsaRequestType"), es.eroski.misumi.model.facingVegalsaWS.ModificarFacingVegalsaRequestType.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ModificarFacingVegalsaResponseType"));
        oper.setReturnClass(es.eroski.misumi.model.facingVegalsaWS.ModificarFacingVegalsaResponseType.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ModificarFacingVegalsaResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ModificarMedidasFacing");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ModificarMedidasFacingVegalsaRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ModificarMedidasFacingVegalsaRequestType"), es.eroski.misumi.model.facingVegalsaWS.ModificarMedidasFacingVegalsaRequestType.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ModificarMedidasFacingVegalsaResponseType"));
        oper.setReturnClass(es.eroski.misumi.model.facingVegalsaWS.ModificarMedidasFacingVegalsaResponseType.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ModificarMedidasFacingVegalsaResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

    }

    public FacingVegalsaBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public FacingVegalsaBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public FacingVegalsaBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ConsultaFacingVegalsaRequestType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.facingVegalsaWS.ConsultaFacingVegalsaRequestType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ConsultaFacingVegalsaResponseType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.facingVegalsaWS.ConsultaFacingVegalsaResponseType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ListaReferenciasTypeRequest");
            cachedSerQNames.add(qName);
            cls = java.math.BigInteger[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer");
            qName2 = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "CodigoReferencia");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ModificarFacingVegalsaRequestType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.facingVegalsaWS.ModificarFacingVegalsaRequestType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ModificarFacingVegalsaResponseType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.facingVegalsaWS.ModificarFacingVegalsaResponseType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ModificarMedidasFacingVegalsaRequestType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.facingVegalsaWS.ModificarMedidasFacingVegalsaRequestType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ModificarMedidasFacingVegalsaResponseType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.facingVegalsaWS.ModificarMedidasFacingVegalsaResponseType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ReferenciaMedidaType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.facingVegalsaWS.ReferenciaMedidaType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ReferenciaModType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.facingVegalsaWS.ReferenciaModType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ReferenciaResMedidaType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.facingVegalsaWS.ReferenciaResMedidaType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ReferenciaResModType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.facingVegalsaWS.ReferenciaResModType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ReferenciasMedidaType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.facingVegalsaWS.ReferenciaMedidaType[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ReferenciaMedidaType");
            qName2 = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "Referencia");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ReferenciasModType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.facingVegalsaWS.ReferenciaModType[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ReferenciaModType");
            qName2 = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "Referencia");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ReferenciasResMedidaType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.facingVegalsaWS.ReferenciaResMedidaType[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ReferenciaResMedidaType");
            qName2 = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "referencia");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ReferenciasResModType");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.facingVegalsaWS.ReferenciaResModType[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ReferenciaResModType");
            qName2 = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "referencia");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ReferenciasTypeResponse");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.facingVegalsaWS.ReferenciaTypeResponse[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ReferenciaTypeResponse");
            qName2 = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "Referencia");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.eroski.es/GCPV/schema/Facing", "ReferenciaTypeResponse");
            cachedSerQNames.add(qName);
            cls = es.eroski.misumi.model.facingVegalsaWS.ReferenciaTypeResponse.class;
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

    public es.eroski.misumi.model.facingVegalsaWS.ConsultaFacingVegalsaResponseType consultaFacing(es.eroski.misumi.model.facingVegalsaWS.ConsultaFacingVegalsaRequestType part1) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("ConsultaFacing");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "ConsultaFacing"));
		_call.setUsername(WSBManager.getProperty("ws.user2"));
        _call.setPassword(WSBManager.getProperty("ws.pwd3"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {part1});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.eroski.misumi.model.facingVegalsaWS.ConsultaFacingVegalsaResponseType) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.eroski.misumi.model.facingVegalsaWS.ConsultaFacingVegalsaResponseType) org.apache.axis.utils.JavaUtils.convert(_resp, es.eroski.misumi.model.facingVegalsaWS.ConsultaFacingVegalsaResponseType.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.eroski.misumi.model.facingVegalsaWS.ModificarFacingVegalsaResponseType modificarFacing(es.eroski.misumi.model.facingVegalsaWS.ModificarFacingVegalsaRequestType part3) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("ModificarFacing");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "ModificarFacing"));
		_call.setUsername(WSBManager.getProperty("ws.user2"));
        _call.setPassword(WSBManager.getProperty("ws.pwd3"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {part3});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.eroski.misumi.model.facingVegalsaWS.ModificarFacingVegalsaResponseType) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.eroski.misumi.model.facingVegalsaWS.ModificarFacingVegalsaResponseType) org.apache.axis.utils.JavaUtils.convert(_resp, es.eroski.misumi.model.facingVegalsaWS.ModificarFacingVegalsaResponseType.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public es.eroski.misumi.model.facingVegalsaWS.ModificarMedidasFacingVegalsaResponseType modificarMedidasFacing(es.eroski.misumi.model.facingVegalsaWS.ModificarMedidasFacingVegalsaRequestType part5) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("ModificarMedidasFacing");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "ModificarMedidasFacing"));
		_call.setUsername(WSBManager.getProperty("ws.user2"));
        _call.setPassword(WSBManager.getProperty("ws.pwd3"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {part5});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (es.eroski.misumi.model.facingVegalsaWS.ModificarMedidasFacingVegalsaResponseType) _resp;
            } catch (java.lang.Exception _exception) {
                return (es.eroski.misumi.model.facingVegalsaWS.ModificarMedidasFacingVegalsaResponseType) org.apache.axis.utils.JavaUtils.convert(_resp, es.eroski.misumi.model.facingVegalsaWS.ModificarMedidasFacingVegalsaResponseType.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
