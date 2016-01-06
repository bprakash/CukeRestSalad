package org.cukesalad.rest.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
  
  public static void refresh(){
    restProps = new Properties();
    env  = System.getProperty(RestConstants.ENV);
    webresource = null;
    postBody = null;
    headerMapList = new ArrayList<>();
    method = null;
    clientResponse = null;
    restResponse = null;
  }
}
