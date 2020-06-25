package com.transporte.carga.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>Classe Java de container complex type.
 *
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 *
 * <pre>
 * &lt;complexType name="container">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.w3schools.com}abstractContainer">
 *       &lt;sequence>
 *         &lt;element name="destino" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;choice maxOccurs="unbounded">
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
@XmlType(name = "container", namespace = "http://www.w3schools.com", propOrder = {
        "destino",
        "produtoAndQuantidadeAndValor"
})
public class Container extends AbstractContainer {

    @XmlElement(namespace = "http://www.w3schools.com", required = true)
    private String destino;

    @XmlElements({
            @XmlElement(name = "produto", namespace = "http://www.w3schools.com", type = String.class),
            @XmlElement(name = "quantidade", namespace = "http://www.w3schools.com", type = Quantidade.class),
            @XmlElement(name = "valor", namespace = "http://www.w3schools.com", type = Double.class),
            @XmlElement(name = "container", namespace = "http://www.w3schools.com", type = Subcontainer.class)
    })
    private List<Object> produtoAndQuantidadeAndValor;


    /**
     * Obtém o valor da propriedade destino.
     *
     * @return possible object is
     * {@link String }
     */
    public String getDestino() {
        return destino;
    }

    /**
     * Define o valor da propriedade destino.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDestino(String value) {
        this.destino = value;
    }

    /**
     * Gets the value of the produtoAndQuantidadeAndValor property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the produtoAndQuantidadeAndValor property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProdutoAndQuantidadeAndValor().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * {@link Quantidade }
     * {@link Double }
     * {@link Subcontainer }
     */
    public List<Object> getProdutoAndQuantidadeAndValor() {
        if (produtoAndQuantidadeAndValor == null) {
            produtoAndQuantidadeAndValor = new ArrayList<Object>();
        }
        return this.produtoAndQuantidadeAndValor;
    }

    public void setProdutoAndQuantidadeAndValor(List<Object> produtoAndQuantidadeAndValor) {
        this.produtoAndQuantidadeAndValor = produtoAndQuantidadeAndValor;
    }

    private boolean isSingleContainer() {
        return !getProdutoAndQuantidadeAndValor().stream().anyMatch(o -> o instanceof Subcontainer);
    }

    @Override
    public String toString() {
        if (!isSingleContainer())
            return "Container{" +
                    "id='" + id + '\'' +
                    ", tipo='" + tipo + '\'' +
                    ", destino='" + destino + '\'' + ", subcontaineres='" + mapSubcontainers().toString() + '\'' + "}";
        else return "Container{" +
                "id='" + id + '\'' +
                ", tipo='" + tipo + '\'' +
                ", destino='" + destino + '\'' + ", produto='" + mapProduto() + '\'' +
                ", quantidade='" + mapQuantidade().toString() + "\'" + ", valor='" + getValor() + "\'" + "}";
    }

    public Double getValor() {
        if (isSingleContainer())
            return getProdutoAndQuantidadeAndValor().stream().filter(o -> o instanceof Double)
                    .map(o -> (Double) o)
                    .mapToDouble(Double::doubleValue).sum();

        else return mapSubcontainers().stream().mapToDouble(Subcontainer::getValor).sum();

    }

    private String mapProduto() {
        return getProdutoAndQuantidadeAndValor().stream()
                .filter(o -> o instanceof String).map(o -> (String) o)
                .map(String.class::cast).collect(Collectors.joining());
    }

    private Quantidade mapQuantidade() throws NullPointerException {
        return getProdutoAndQuantidadeAndValor().stream()
                .filter(o -> o instanceof Quantidade).map(o -> (Quantidade) o)
                .map(Quantidade.class::cast).findFirst().orElseThrow(NullPointerException::new);
    }

    private List<Subcontainer> mapSubcontainers() {
        return getProdutoAndQuantidadeAndValor().stream()
                .filter(objects -> objects instanceof Subcontainer)
                .map(objects -> (Subcontainer) objects)
                .map(Subcontainer.class::cast)
                .collect(Collectors.toList());
    }

    public Double amountUnderCompanyResponsability(String company) {
        return amountUnderCompanyResponsability(new Double((double) 0), company);
    }

    private Double amountUnderCompanyResponsability(Double amount, String company) {
        if (company.equals(responsavel)) return getValor();
        for (Subcontainer subcontainer : mapSubcontainers()) {
            amount += subcontainer.amountUnderCompanyResponsability(company);
        }
        return amount;
    }
}
