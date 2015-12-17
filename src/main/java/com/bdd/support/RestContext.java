package com.bdd.support;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class RestContext {
  public static Properties restProps = new Properties();
  public static String     env  = System.getProperty("env");
  public static WebResource webresource;
  public static String postBody;
  public static List<Map<String, String>> headerMapList;
  public static String method;
  public static ClientResponse clientResponse;
  public static String restResponse;
}
