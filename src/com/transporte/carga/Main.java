package com.transporte.carga;

import com.transporte.carga.model.Carga;
import com.transporte.carga.model.Container;
import com.transporte.carga.model.Subcontainer;
import com.transporte.carga.parser.CargaDomParser;
import com.transporte.carga.parser.CargaJAXBParser;
import com.transporte.carga.parser.CargaSAXParser;
import com.transporte.carga.service.XMLFileService;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws XPathExpressionException {

        Carga carga = null;
        List<String> errors = new LinkedList<>();

        CargaJAXBParser cargaJAXBParser = new CargaJAXBParser();
        try {

            try {

                carga = cargaJAXBParser.unMarshallCarga("carga.xml");
                String company = "Empresa 2 SA";
                System.out.println("JAXB: Valor total sob responsabilidade da empresa "
                        + company + ": " + carga.amountUnderResponsability(company) + System.lineSeparator());

            } catch (JAXBException e) {
                errors.add(e.getLocalizedMessage());
            }

            try {

                Double result = new Double((double) 0);

                CargaSAXParser cargaSAXParser = new CargaSAXParser(new XMLFileService("carga.xml"));
                List<Container> moreExpensiveContainers = cargaSAXParser.getMoreExpensiveContainers(1);


                System.out.println("SAX: Containeres mais caros");

                for (Container container : moreExpensiveContainers) {
                    System.out.println(container.toString());
                    result += container.getValor();
                }

                System.out.println("Total acumulado dos containeres: " + result);

                result = new Double((double) 0);

                System.out.println(System.lineSeparator() + "DOM: Subcontaineres mais caros");

                CargaDomParser cargaDomParser = new CargaDomParser(new XMLFileService("carga.xml"));

                List<Subcontainer> moreExpensiveSubcontainers = cargaDomParser.getMoreExpensiveSubcontainers(1);

                for (Subcontainer subcontainer : moreExpensiveSubcontainers) {
                    System.out.println(subcontainer.toString());
                    result += subcontainer.getValor();
                }

                System.out.println("Total acumulado dos subcontaineres: " + result);

            } catch (ParserConfigurationException | SAXException e) {
                errors.add(e.getLocalizedMessage());
            }

        } catch (URISyntaxException | IOException e) {
            errors.add(e.getLocalizedMessage());
        }

        errors.forEach(s -> {
            System.out.println(s);
        });
    }
}
