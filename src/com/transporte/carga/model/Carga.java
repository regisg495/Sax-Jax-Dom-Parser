package com.transporte.carga.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * <p>Classe Java de anonymous complex type.
 *
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="responsavel" type="{http://www.w3schools.com}capitalizedNonEmptyStringWithNumber"/>
 *         &lt;element name="origem" type="{http://www.w3schools.com}capitalizedNonEmptyString"/>
 *         &lt;element name="container" type="{http://www.w3schools.com}container" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "responsavel",
        "origem",
        "container"
})
@XmlRootElement(name = "carga", namespace = "http://www.w3schools.com")
public class Carga {

    @XmlElement(namespace = "http://www.w3schools.com", required = true)
    protected String responsavel;
    @XmlElement(namespace = "http://www.w3schools.com", required = true)
    protected String origem;
    @XmlElement(namespace = "http://www.w3schools.com", required = true)
    protected List<Container> container;

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
     * Obtém o valor da propriedade origem.
     *
     * @return possible object is
     * {@link String }
     */
    public String getOrigem() {
        return origem;
    }

    /**
     * Define o valor da propriedade origem.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setOrigem(String value) {
        this.origem = value;
    }

    /**
     * Gets the value of the container property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the container property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContainer().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Container }
     */
    public List<Container> getContainer() {
        if (container == null) {
            container = new ArrayList<Container>();
        }
        return this.container;
    }

    /**
     * Obtém o valor total da carga.
     *
     * @return possible object is
     * {@link Double }
     */

    public Double getTotal() {
        return getContainer().stream().mapToDouble(Container::getValor).sum();
    }

    public Double amountUnderResponsability(String company) {
        Double amount = new Double(0);
        for (Container container : getContainer()) {
            amount += container.amountUnderCompanyResponsability(company);
        }
        return amount;
    }

    /**
     * Obtém um container da carga dado pelo.
     *
     * @param id allowed object is
     *           {@link Integer }
     *
     * @return possible object is
     * {@link Container }
     */

    public Container findById(Integer id) {
        return container.stream()
                .filter(container1 -> Objects.equals(container1.getId(), id))
                .findFirst().orElseThrow(IllegalArgumentException::new);
    }

}
