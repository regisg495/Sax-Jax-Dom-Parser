package com.transporte.carga.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de tipo.
 *
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * <p>
 * <pre>
 * &lt;simpleType name="tipo">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="caixa"/>
 *     &lt;enumeration value="barril"/>
 *     &lt;enumeration value="saco"/>
 *     &lt;enumeration value="caixa refrigerada"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "tipo", namespace = "http://www.w3schools.com")
@XmlEnum
public enum Tipo {

    @XmlEnumValue("caixa")
    CAIXA("caixa"),
    @XmlEnumValue("barril")
    BARRIL("barril"),
    @XmlEnumValue("saco")
    SACO("saco"),
    @XmlEnumValue("caixa refrigerada")
    CAIXA_REFRIGERADA("caixa refrigerada");
    private final String value;

    Tipo(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Tipo fromValue(String v) {
        for (Tipo c : Tipo.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
