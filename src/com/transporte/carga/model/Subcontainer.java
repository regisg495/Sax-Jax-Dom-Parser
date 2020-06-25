package com.transporte.carga.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.LinkedList;
import java.util.List;


/**
 * <p>Classe Java de subcontainer complex type.
 *
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 *
 * <pre>
 * &lt;complexType name="subcontainer">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.w3schools.com}abstractContainer">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;group ref="{http://www.w3schools.com}containerContentGroup"/>
 *           &lt;element name="container" type="{http://www.w3schools.com}subcontainer" maxOccurs="unbounded"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "subcontainer", namespace = "http://www.w3schools.com", propOrder = {
        "produto",
        "quantidade",
        "valor",
        "subcontainer"
})
public class Subcontainer
        extends AbstractContainer {

    @XmlElement(namespace = "http://www.w3schools.com")
    protected String produto;
    @XmlElement(namespace = "http://www.w3schools.com")
    protected Quantidade quantidade;
    @XmlElement(namespace = "http://www.w3schools.com")
    protected Double valor;
    @XmlElement(namespace = "http://www.w3schools.com")
    protected List<Subcontainer> subcontainer;

    /**
     * Obtém o valor da propriedade produto.
     *
     * @return possible object is
     * {@link String }
     */
    public String getProduto() {
        return produto;
    }

    /**
     * Define o valor da propriedade produto.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setProduto(String value) {
        this.produto = value;
    }

    /**
     * Obtém o valor da propriedade quantidade.
     *
     * @return possible object is
     * {@link Quantidade }
     */
    public Quantidade getQuantidade() {
        return quantidade;
    }

    /**
     * Define o valor da propriedade quantidade.
     *
     * @param value allowed object is
     *              {@link Quantidade }
     */
    public void setQuantidade(Quantidade value) {
        this.quantidade = value;
    }

    /**
     * Obtém o valor da propriedade valor.
     *
     * @return possible object is
     * {@link Double }
     */

    public Double getValor() {
        if (valor != null) return valor;
        else valor = getTotal();
        return valor;
    }

    /**
     * Define o valor da propriedade valor.
     *
     * @param value allowed object is
     *              {@link Double }
     */
    public void setValor(Double value) {
        this.valor = value;
    }

    private Double getTotal() {
        if (valor == null) valor = new Double((double) 0);
        for (Subcontainer subcontainer : getSubcontainers()) {
            valor += subcontainer.getTotal();
        }
        return valor;
    }

    public Double amountUnderCompanyResponsability(String company) {
        if (responsavel.equals(company)) return getTotal();
        Subcontainer subcontainer = this;
        return amountUnderCompanyResponsability(new Double((double) 0), subcontainer, company);
    }

    private Double amountUnderCompanyResponsability(Double amount, Subcontainer subcontainer, String company) {
        if (subcontainer == null) return amount;
        for (Subcontainer next : subcontainer.getSubcontainers()) {
            if (next.getResponsavel().equals(company)) return next.getTotal();
            else return amountUnderCompanyResponsability(amount, next, company);
        }
        return amount;
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
     * {@link Subcontainer }
     */
    public List<Subcontainer> getSubcontainers() {
        if (subcontainer == null) {
            subcontainer = new LinkedList<>();
        }
        return this.subcontainer;
    }

    @Override
    public String toString() {
        if (getSubcontainers().isEmpty())
            return "Subcontainer{" +
                    "id='" + id + '\'' +
                    ", tipo='" + tipo + '\'' +
                    ", responsavel='" + responsavel + '\'' +
                    ", produto='" + produto + '\'' +
                    ", quantidade='" + quantidade + '\'' +
                    ", valor='" + valor + "}";
        else return "Subcontainer{" +
                "id='" + id + '\'' +
                ", tipo='" + tipo + '\'' +
                ", responsavel='" + responsavel + '\'' +
                ", subcontaineres='" + subcontainer;

    }
}


