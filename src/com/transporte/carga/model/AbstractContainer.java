package com.transporte.carga.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import java.math.BigInteger;


/**
 * <p>Classe Java de abstractContainer complex type.
 *
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 *
 * <pre>
 * &lt;complexType name="abstractContainer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="responsavel" type="{http://www.w3schools.com}capitalizedNonEmptyStringWithNumber"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *       &lt;attribute name="tipo" use="required" type="{http://www.w3schools.com}tipo" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "abstractContainer", namespace = "http://www.w3schools.com", propOrder = {
        "responsavel"
})
@XmlSeeAlso({
        Container.class,
        Subcontainer.class
})
public abstract class AbstractContainer {

    @XmlElement(namespace = "http://www.w3schools.com", required = true)
    protected String responsavel;
    @XmlAttribute(name = "id", required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected Integer id;
    @XmlAttribute(name = "tipo", required = true)
    protected Tipo tipo;

    /**
     * Obtém o valor da propriedade responsavel.
     *
     * @return possible object is
     * {@link String }
     */
    public String getResponsavel() {
        return responsavel;
    }

    /**
     * Define o valor da propriedade responsavel.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setResponsavel(String value) {
        this.responsavel = value;
    }

    /**
     * Obtém o valor da propriedade id.
     *
     * @return possible object is
     * {@link BigInteger }
     */
    public Integer getId() {
        return id;
    }

    /**
     * Define o valor da propriedade id.
     *
     * @param value allowed object is
     *              {@link BigInteger }
     */
    public void setId(Integer value) {
        this.id = value;
    }

    /**
     * Obtém o valor da propriedade tipo.
     *
     * @return possible object is
     * {@link Tipo }
     */
    public Tipo getTipo() {
        return tipo;
    }

    /**
     * Define o valor da propriedade tipo.
     *
     * @param value allowed object is
     *              {@link Tipo }
     */
    public void setTipo(Tipo value) {
        this.tipo = value;
    }
}
