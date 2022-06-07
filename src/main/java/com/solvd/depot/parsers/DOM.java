package com.solvd.depot.parsers;

import com.solvd.depot.dao.IBusDAO;
import com.solvd.depot.models.Driver;
import com.solvd.depot.models.Licence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DOM {
    private static final Logger LOGGER = LogManager.getLogger(DOM.class);
    public void getDrivers() {
        File file = new File("src/main/resources/data/xmlmodel.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            Element driversElement = (Element) document.getElementsByTagName("drivers").item(0);
            String licence = driversElement.getAttribute("licence");
            String name = driversElement.getAttribute("name");
            String age = driversElement.getAttribute("age");
            NodeList driverNodeList = document.getElementsByTagName("driver");
            List<Driver> drivers = new ArrayList<>();
            for (int i = 0; i < driverNodeList.getLength(); i++) {
                if (driverNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element driverElement = (Element) driverNodeList.item(i);
                    Driver driver = new Driver();
                    Licence driverLicence = new Licence();
                    //driverLicence.setExpiration(Date.valueOf(driverElement.getAttribute("licence")));
                    //driver.setName(name);
                    //driver.setAge(Integer.valueOf(driverElement.getAttribute("age")));
                    //driver.setLicence(licence);
                    NodeList childNodes = driverElement.getChildNodes();
                    for (int j = 0; j < childNodes.getLength(); j++) {
                        if(childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                            Element childElement = (Element) childNodes.item(j);
                            switch (childElement.getNodeName()) {
                                case "name": {
                                    driver.setName(childElement.getTextContent());
                                } break;
                                case "age": {
                                    driver.setAge(Integer.valueOf(childElement.getTextContent()));
                                } break;
                                case "licence": {
                                    driver.setLicence(driverLicence);
                                }
                            }
                        }
                    }
                    drivers.add(driver);
                }

            }
            drivers.forEach(LOGGER::info);

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }
}
