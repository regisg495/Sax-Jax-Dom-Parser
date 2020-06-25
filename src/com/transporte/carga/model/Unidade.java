package com.transporte.carga.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de unidade.
 *
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * <p>
 * <pre>
 * &lt;simpleType name="unidade">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="peça"/>
 *     &lt;enumeration value="kilo"/>
 *     &lt;enumeration value="litro"/>
 *     &lt;enumeration value="unidade"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "unidade", namespace = "http://www.w3schools.com")
@XmlEnum
public enum Unidade {

    @XmlEnumValue("pe\u00e7a")
    PEÇA("pe\u00e7a"),
    @XmlEnumValue("kilo")
    KILO("kilo"),
    @XmlEnumValue("litro")
    LITRO("litro"),
    @XmlEnumValue("unidade")
    UNIDADE("unidade");
    private final String value;

    Unidade(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Unidade fromValue(String v) {
        for (Unidade c : Unidade.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
