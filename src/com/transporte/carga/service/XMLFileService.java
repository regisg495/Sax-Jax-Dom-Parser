package com.transporte.carga.service;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class XMLFileService {

    private static final String PATH = "../static/resources/xml/";
    private final String fileName;

    public XMLFileService(String fileName) {
        this.fileName = fileName;
    }

    private URI getUrl() throws URISyntaxException {
        return this.getClass().getResource(PATH + fileName).toURI();
    }

    public File toFile() throws URISyntaxException {
        return new File(this.getUrl());
    }

    public BufferedReader toBufferedReader(Charset charset) throws URISyntaxException, IOException {
        return Files.newBufferedReader(Paths.get(getUrl()), charset);
    }

    public BufferedReader toBufferedReader() throws URISyntaxException, IOException {
        return Files.newBufferedReader(Paths.get(getUrl()), StandardCharsets.UTF_8);
    }

    public Document getDocument() throws IOException, URISyntaxException, ParserConfigurationException, SAXException {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(toFile());
    }

}
