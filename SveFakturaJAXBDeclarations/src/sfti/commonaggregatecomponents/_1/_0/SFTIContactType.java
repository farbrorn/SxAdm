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
import oasis.names.tc.ubl.commonbasiccomponents._1._0.MailType;
import oasis.names.tc.ubl.commonbasiccomponents._1._0.NameType;
import oasis.names.tc.ubl.commonbasiccomponents._1._0.TelefaxType;
import oasis.names.tc.ubl.commonbasiccomponents._1._0.TelephoneType;


/**
 * <p>Java class for SFTIContactType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SFTIContactType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{urn:oasis:names:tc:ubl:CommonBasicComponents:1:0}Name" minOccurs="0"/>
 *         &lt;element ref="{urn:oasis:names:tc:ubl:CommonBasicComponents:1:0}Telephone" minOccurs="0"/>
 *         &lt;element ref="{urn:oasis:names:tc:ubl:CommonBasicComponents:1:0}Telefax" minOccurs="0"/>
 *         &lt;element ref="{urn:oasis:names:tc:ubl:CommonBasicComponents:1:0}ElectronicMail" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SFTIContactType", propOrder = {
    "name",
    "telephone",
    "telefax",
    "electronicMail"
})
public class SFTIContactType {

    @XmlElement(name = "Name", namespace = "urn:oasis:names:tc:ubl:CommonBasicComponents:1:0")
    protected NameType name;
    @XmlElement(name = "Telephone", namespace = "urn:oasis:names:tc:ubl:CommonBasicComponents:1:0")
    protected TelephoneType telephone;
    @XmlElement(name = "Telefax", namespace = "urn:oasis:names:tc:ubl:CommonBasicComponents:1:0")
    protected TelefaxType telefax;
    @XmlElement(name = "ElectronicMail", namespace = "urn:oasis:names:tc:ubl:CommonBasicComponents:1:0")
    protected MailType electronicMail;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link NameType }
     *     
     */
    public NameType getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameType }
     *     
     */
    public void setName(NameType value) {
        this.name = value;
    }

    /**
     * Gets the value of the telephone property.
     * 
     * @return
     *     possible object is
     *     {@link TelephoneType }
     *     
     */
    public TelephoneType getTelephone() {
        return telephone;
    }

    /**
     * Sets the value of the telephone property.
     * 
     * @param value
     *     allowed object is
     *     {@link TelephoneType }
     *     
     */
    public void setTelephone(TelephoneType value) {
        this.telephone = value;
    }

    /**
     * Gets the value of the telefax property.
     * 
     * @return
     *     possible object is
     *     {@link TelefaxType }
     *     
     */
    public TelefaxType getTelefax() {
        return telefax;
    }

    /**
     * Sets the value of the telefax property.
     * 
     * @param value
     *     allowed object is
     *     {@link TelefaxType }
     *     
     */
    public void setTelefax(TelefaxType value) {
        this.telefax = value;
    }

    /**
     * Gets the value of the electronicMail property.
     * 
     * @return
     *     possible object is
     *     {@link MailType }
     *     
     */
    public MailType getElectronicMail() {
        return electronicMail;
    }

    /**
     * Sets the value of the electronicMail property.
     * 
     * @param value
     *     allowed object is
     *     {@link MailType }
     *     
     */
    public void setElectronicMail(MailType value) {
        this.electronicMail = value;
    }

}
