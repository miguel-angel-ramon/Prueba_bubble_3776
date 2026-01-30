
package es.eroski.misumi.model.imprimirEtiquetaTiendaWS;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the es.eroski.gcpv.schema.imprimiretiquetatienda package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ImprimirEtiquetaRequest_QNAME = new QName("http://www.eroski.es/gcpv/schema/imprimirEtiquetaTienda", "imprimirEtiquetaRequest");
    private final static QName _ImprimirEtiquetaFault_QNAME = new QName("http://www.eroski.es/gcpv/schema/imprimirEtiquetaTienda", "ImprimirEtiquetaFault");
    private final static QName _ImprimirEtiquetaResponse_QNAME = new QName("http://www.eroski.es/gcpv/schema/imprimirEtiquetaTienda", "imprimirEtiquetaResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: es.eroski.gcpv.schema.imprimiretiquetatienda
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ImprimirEtiquetaResponseType }
     * 
     */
    public ImprimirEtiquetaResponseType createImprimirEtiquetaResponseType() {
        return new ImprimirEtiquetaResponseType();
    }

    /**
     * Create an instance of {@link ImprimirEtiquetaRequestType }
     * 
     */
    public ImprimirEtiquetaRequestType createImprimirEtiquetaRequestType() {
        return new ImprimirEtiquetaRequestType();
    }

    /**
     * Create an instance of {@link ImprimirEtiquetaFaultType }
     * 
     */
    public ImprimirEtiquetaFaultType createImprimirEtiquetaFaultType() {
        return new ImprimirEtiquetaFaultType();
    }

    /**
     * Create an instance of {@link ReferenciaResponseType }
     * 
     */
    public ReferenciaResponseType createReferenciaResponseType() {
        return new ReferenciaResponseType();
    }

    /**
     * Create an instance of {@link ReferenciaRequestType }
     * 
     */
    public ReferenciaRequestType createReferenciaRequestType() {
        return new ReferenciaRequestType();
    }

    /**
     * Create an instance of {@link ListaReferenciasRequestType }
     * 
     */
    public ListaReferenciasRequestType createListaReferenciasRequestType() {
        return new ListaReferenciasRequestType();
    }

    /**
     * Create an instance of {@link ListaReferenciasResponseType }
     * 
     */
    public ListaReferenciasResponseType createListaReferenciasResponseType() {
        return new ListaReferenciasResponseType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ImprimirEtiquetaRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.eroski.es/gcpv/schema/imprimirEtiquetaTienda", name = "imprimirEtiquetaRequest")
    public JAXBElement<ImprimirEtiquetaRequestType> createImprimirEtiquetaRequest(ImprimirEtiquetaRequestType value) {
        return new JAXBElement<ImprimirEtiquetaRequestType>(_ImprimirEtiquetaRequest_QNAME, ImprimirEtiquetaRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ImprimirEtiquetaFaultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.eroski.es/gcpv/schema/imprimirEtiquetaTienda", name = "ImprimirEtiquetaFault")
    public JAXBElement<ImprimirEtiquetaFaultType> createImprimirEtiquetaFault(ImprimirEtiquetaFaultType value) {
        return new JAXBElement<ImprimirEtiquetaFaultType>(_ImprimirEtiquetaFault_QNAME, ImprimirEtiquetaFaultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ImprimirEtiquetaResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.eroski.es/gcpv/schema/imprimirEtiquetaTienda", name = "imprimirEtiquetaResponse")
    public JAXBElement<ImprimirEtiquetaResponseType> createImprimirEtiquetaResponse(ImprimirEtiquetaResponseType value) {
        return new JAXBElement<ImprimirEtiquetaResponseType>(_ImprimirEtiquetaResponse_QNAME, ImprimirEtiquetaResponseType.class, null, value);
    }

}
