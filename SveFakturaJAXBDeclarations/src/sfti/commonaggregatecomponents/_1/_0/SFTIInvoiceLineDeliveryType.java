//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.04.06 at 06:07:59 em CEST 
//


package sfti.commonaggregatecomponents._1._0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import oasis.names.tc.ubl.commonbasiccomponents._1._0.DeliveryDateTimeType;


/**
 * <p>Java class for SFTIInvoiceLineDeliveryType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SFTIInvoiceLineDeliveryType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{urn:oasis:names:tc:ubl:CommonBasicComponents:1:0}ActualDeliveryDateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SFTIInvoiceLineDeliveryType", propOrder = {
    "actualDeliveryDateTime"
})
public class SFTIInvoiceLineDeliveryType {

    @XmlElement(name = "ActualDeliveryDateTime", namespace = "urn:oasis:names:tc:ubl:CommonBasicComponents:1:0")
    protected DeliveryDateTimeType actualDeliveryDateTime;

    /**
     * Gets the value of the actualDeliveryDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link DeliveryDateTimeType }
     *     
     */
    public DeliveryDateTimeType getActualDeliveryDateTime() {
        return actualDeliveryDateTime;
    }

    /**
     * Sets the value of the actualDeliveryDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeliveryDateTimeType }
     *     
     */
    public void setActualDeliveryDateTime(DeliveryDateTimeType value) {
        this.actualDeliveryDateTime = value;
    }

}
