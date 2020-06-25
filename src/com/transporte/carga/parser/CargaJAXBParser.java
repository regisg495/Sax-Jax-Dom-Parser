package com.transporte.carga.parser;

import com.transporte.carga.model.Carga;
import com.transporte.carga.service.XMLFileService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.net.URISyntaxException;

public class CargaJAXBParser {

    private JAXBContext getJAXBContext() throws JAXBException {
        return JAXBContext.newInstance(Carga.class);
    }

    private Unmarshaller createUnmarshaller() throws JAXBException {
        return getJAXBContext().createUnmarshaller();
    }

    public Carga unMarshallCarga(String fileName) throws JAXBException, IOException, URISyntaxException {
        return (Carga) createUnmarshaller().unmarshal(new XMLFileService(fileName).toBufferedReader());
    }

}
