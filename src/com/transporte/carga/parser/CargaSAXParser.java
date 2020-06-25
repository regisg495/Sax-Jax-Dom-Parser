package com.transporte.carga.parser;

import com.transporte.carga.model.Container;
import com.transporte.carga.service.XMLFileService;
import com.transporte.carga.service.XMLTagUtil;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class CargaSAXParser extends DefaultHandler {

    private static List<String> expressions;

    private final XMLFileService xmlFileService;

    private String atual;
    private int level;
    private Map<Integer, Double> idValorMap;
    private Integer idAtual;
    private Double valor;

    public CargaSAXParser(XMLFileService xmlFileService) throws SAXException, ParserConfigurationException, IOException, URISyntaxException {
        this.xmlFileService = xmlFileService;
        SAXParserFactory.newInstance().newSAXParser().parse(xmlFileService.toFile(), this);
    }

    @Override
    public void startDocument() throws SAXException {
        idValorMap = new HashMap<>();

    }

    @Override
    public void endDocument() throws SAXException {

        List<Integer> ids = idValorMap.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                .map(Map.Entry::getKey).collect(Collectors.toList());


        if (ids.isEmpty()) return;

        final String commonExpression = "//carga/container";

        expressions = ids.stream()
                .map(integer -> commonExpression + "[@id='" + integer + '\'' + "]")
                .collect(Collectors.toList());

    }

    public List<Container> getMoreExpensiveContainers(int numContainers) throws SAXException, URISyntaxException, ParserConfigurationException,
            IOException, XPathExpressionException {

        List<String> expressionsAnother = expressions.subList(0, numContainers);

        List<Container> containers = new ArrayList<>();
        CargaDomParser cargaDomParser = new CargaDomParser(xmlFileService);

        for (String expression : expressionsAnother) {
            containers.addAll(cargaDomParser.getContainers(expression));
        }

        return containers;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        atual = qName;

        if (qName.equals(XMLTagUtil.TAG_CONTAINER)) {
            level++;
            if (level == 1) {
                idAtual = Integer.parseInt(attributes.getValue(XMLTagUtil.TAG_ID));
                valor = new Double((double) 0);
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals(XMLTagUtil.TAG_CONTAINER)) {
            level--;
            if (level == 0) idValorMap.put(idAtual, valor);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String string = new String(ch, start, length);
        if (string.trim().length() > 0)
            if (atual.equals(XMLTagUtil.TAG_VALOR) && level >= 1) valor += Double.parseDouble(string);
    }

}