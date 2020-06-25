package com.transporte.carga.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Classe Java de anonymous complex type.
 *
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>double">
 *       &lt;attribute name="unidade" use="required" type="{http://www.w3schools.com}unidade" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "value"
})
@XmlRootElement(name = "quantidade", namespace = "http://www.w3schools.com")
public class Quantidade {

    @XmlValue
    protected double value;
    @XmlAttribute(name = "unidade", required = true)
    protected Unidade unidade;


    public Quantidade() {

    }

    public Quantidade(double value, Unidade unidade) {
        this.value = value;
        this.unidade = unidade;
    }

    /**
     * Obtém o valor da propriedade value.
     */
    public double getValue() {
        return value;
    }

    /**
     * Define o valor da propriedade value.
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * Obtém o valor da propriedade unidade.
     *
     * @return possible object is
     * {@link Unidade }
     */
    public Unidade getUnidade() {
        return unidade;
    }

    /**
     * Define o valor da propriedade unidade.
     *
     * @param value allowed object is
     *              {@link Unidade }
     */
    public void setUnidade(Unidade value) {
        this.unidade = value;
    }

    @Override
    public String toString() {
        return "{" +
                "total=" + value +
                ", unidade=" + unidade +
                '}';
    }
}
