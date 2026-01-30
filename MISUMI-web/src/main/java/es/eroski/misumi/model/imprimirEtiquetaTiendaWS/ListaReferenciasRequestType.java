
package es.eroski.misumi.model.imprimirEtiquetaTiendaWS;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ListaReferenciasRequestType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ListaReferenciasRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="referencia" type="{http://www.eroski.es/gcpv/schema/imprimirEtiquetaTienda}ReferenciaRequestType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListaReferenciasRequestType", propOrder = {
    "referencia"
})
public class ListaReferenciasRequestType {

    @XmlElement(required = true)
    protected List<ReferenciaRequestType> referencia;

    /**
     * Gets the value of the referencia property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the referencia property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReferencia().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReferenciaRequestType }
     * 
     * 
     */
    public List<ReferenciaRequestType> getReferencia() {
        if (referencia == null) {
            referencia = new ArrayList<ReferenciaRequestType>();
        }
        return this.referencia;
    }

}
