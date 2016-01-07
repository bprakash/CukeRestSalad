package org.cukesalad.rest.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ws.rs.core.MultivaluedMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class RestContext {
  public static Properties restProps = new Properties();
  public static String     env  = System.getProperty(RestConstants.ENV);
  public static WebResource webresource;
  public static String postBody;
  public static List<Map<String, String>> headerMapList = new ArrayList<>();
  public static String method;
  public static ClientResponse clientResponse;
  public static String restResponse;
  public static MultivaluedMap<String, String> responseHeader;
  public static String responseType;
  public static DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
  public static DocumentBuilder docBuilder = null;
  public static Document xmlResponseDocument = null;
  public static XPath xPath =  XPathFactory.newInstance().newXPath();
  
  public static void refresh() throws ParserConfigurationException{
    restProps = new Properties();
    env  = System.getProperty(RestConstants.ENV);
    webresource = null;
    postBody = null;
    headerMapList = new ArrayList<>();
    method = null;
    clientResponse = null;
    restResponse = null;
    responseHeader = null;
    responseType = null;
    docBuilder = docBuilderFactory.newDocumentBuilder();
    xmlResponseDocument = null;
  }
}
