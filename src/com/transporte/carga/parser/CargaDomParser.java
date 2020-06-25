package com.transporte.carga.parser;

import com.transporte.carga.model.Container;
import com.transporte.carga.model.Quantidade;
import com.transporte.carga.model.Subcontainer;
import com.transporte.carga.model.Tipo;
import com.transporte.carga.model.Unidade;
import com.transporte.carga.service.XMLFileService;
import com.transporte.carga.service.XMLTagUtil;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CargaDomParser {

    private final XMLFileService xmlFileService;

    public CargaDomParser(XMLFileService xmlFileService) {
        this.xmlFileService = xmlFileService;
    }

    public List<Subcontainer> getMoreExpensiveSubcontainers(int numContainers) throws XPathExpressionException,
            ParserConfigurationException, SAXException, URISyntaxException, IOException {

        List<Subcontainer> subcontainers = new LinkedList<>();
        XPath xPath = XPathFactory.newInstance().newXPath();

        NodeList nodeList = (NodeList) xPath.compile("//carga/container/container").evaluate(xmlFileService.getDocument(), XPathConstants.NODESET);

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = (Node) nodeList.item(i);
            if (node instanceof Element) {
                Element element = (Element) node;
                subcontainers.add(getSubContainerFromElement(element));
            }
        }

        subcontainers.sort((o1, o2) -> o2.getValor().compareTo(o1.getValor().doubleValue()));

        return subcontainers.subList(0, numContainers);
    }

    public List<Container> getContainers(String expression) throws ParserConfigurationException, SAXException,
            IOException, URISyntaxException, XPathExpressionException {

        List<Container> containers = new ArrayList<>();
        XPath xPath = XPathFactory.newInstance().newXPath();

        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlFileService.getDocument(), XPathConstants.NODESET);

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = (Node) nodeList.item(i);
            if (node instanceof Element) {
                Element element = (Element) node;
                containers.add(getContainerFromElement(element));
            }
        }

        return containers;
    }

    private Container getContainerFromElement(Element containerElementList) {
        Container container = new Container();
        container.setId(Integer.parseInt(containerElementList.getAttribute(XMLTagUtil.TAG_ID)));
        container.setTipo(Tipo.fromValue(containerElementList.getAttribute(XMLTagUtil.TAG_TIPO)));

        for (Node child = containerElementList.getFirstChild(); child != null; child = child.getNextSibling()) {
            if (child instanceof Element) {
                Element element = (Element) child;

                if (element.getNodeName().equals(XMLTagUtil.TAG_RESPONSAVEL))
                    container.setResponsavel(element.getTextContent());
                else if (element.getNodeName().equals(XMLTagUtil.TAG_DESTINO))
                    container.setDestino(element.getTextContent());
                else if (element.getNodeName().equals(XMLTagUtil.TAG_CONTAINER))
                    container.getProdutoAndQuantidadeAndValor().add(getSubContainerFromElement(element));
                else container.getProdutoAndQuantidadeAndValor().add(getSingleContainerFromElement(element));
            }
        }

        return container;
    }

    private Object getSingleContainerFromElement(Element element) throws NullPointerException {
        if (element.getNodeName().equals(XMLTagUtil.TAG_PRODUTO))
            return element.getTextContent();
        else if (element.getNodeName().equals(XMLTagUtil.TAG_QUANTIDADE))
            return new Quantidade(
                    Double.parseDouble(element.getTextContent()),
                    Unidade.fromValue(element.getAttribute(XMLTagUtil.TAG_UNIDADE))
            );
        else return Double.parseDouble(element.getTextContent());
    }

    public Subcontainer getSubContainerFromElement(Element subContainerElementList) {

        Subcontainer subcontainer = new Subcontainer();
        subcontainer.setId(Integer.parseInt(subContainerElementList.getAttribute(XMLTagUtil.TAG_ID)));
        subcontainer.setTipo(Tipo.fromValue(subContainerElementList.getAttribute(XMLTagUtil.TAG_TIPO)));

        for (Node child = subContainerElementList.getFirstChild(); child != null; child = child.getNextSibling()) {
            if (child instanceof Element) {

                Element element = (Element) child;

                if (element.getNodeName().equals(XMLTagUtil.TAG_RESPONSAVEL))
                    subcontainer.setResponsavel(element.getTextContent());
                else if (element.getNodeName().equals(XMLTagUtil.TAG_CONTAINER))
                    subcontainer.getSubcontainers().add(getSubContainerFromElement(element));
                else if (element.getNodeName().equals(XMLTagUtil.TAG_PRODUTO))
                    subcontainer.setProduto(element.getTextContent());
                else if (element.getNodeName().equals(XMLTagUtil.TAG_QUANTIDADE))
                    subcontainer.setQuantidade(new Quantidade(
                            Double.parseDouble(element.getTextContent()),
                            Unidade.fromValue(element.getAttribute(XMLTagUtil.TAG_UNIDADE))
                    ));
                else subcontainer.setValor(Double.parseDouble(element.getTextContent()));
            }
        }

        return subcontainer;
    }

}
