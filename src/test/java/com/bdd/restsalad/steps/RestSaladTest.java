package com.bdd.restsalad.steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.verify.VerificationTimes.once;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;

import com.bdd.support.RestConstants;
import com.bdd.support.RestContext;
import com.bdd.support.RestHook;
import com.sun.jersey.api.client.Client;

import cucumber.api.DataTable;

public class RestSaladTest {

  private static ClientAndServer mockServer;
  RestSalad restSalad;
  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {}

  @Before
  public void setUp() throws Exception {
    restSalad = new RestSalad();
    RestHook.setupJsonConfig();
    RestContext.refresh();
  }

  @After
  public void tearDown() throws Exception {
    restSalad=null;
  }

  public static ClientAndServer createClientServer(Integer port) {
    mockServer = startClientAndServer(port);
    return mockServer;
  }

  public static void stopClientServer() {
    mockServer.stop();
  }
  
  @Test
  public void testI_start_building_a_request_with_method_and_URL() throws Throwable {
    restSalad.i_start_building_a_request_with_method_and_URL("post", "http://someUrl.com");
    assertEquals("POST", RestContext.method);
    assertEquals("http://someUrl.com", RestContext.webresource.getURI().toString());
  }

  @Test
  public void testI_add_equal_to_as_parameter_to_request() throws Throwable {
    restSalad.i_start_building_a_request_with_method_and_URL("post", "http://someUrl.com");
    restSalad.i_add_equal_to_as_parameter_to_request("key", "value");
    assertEquals("POST", RestContext.method);
    assertEquals("http://someUrl.com?key=value", RestContext.webresource.getURI().toString());
  }

  @Test
  public void testI_add_post_body_to_the_request_as() throws Throwable {
    restSalad.i_add_post_body_to_the_request_as("postBody");
    assertEquals("postBody", RestContext.postBody);
  }

  @Test
  public void testI_add_the_below_values_as_headers_to_the_request() throws Throwable {
    List<List<String>> raw = new ArrayList<List<String>>();
    raw.add(Arrays.asList("headerName","headerValue"));
    raw.add(Arrays.asList("Content-Type","application/json"));
    DataTable headerTable = DataTable.create(raw );
    restSalad.i_add_the_below_values_as_headers_to_the_request(headerTable );
    assertEquals(RestContext.headerMapList.size(), 1);
    assertEquals(RestContext.headerMapList.get(0).get("headerName"), "Content-Type");
    assertEquals(RestContext.headerMapList.get(0).get("headerValue"), "application/json");
  }
  
  

  @Test
  public void testI_retrieve_the_resource_GET() throws Throwable {
    String method;
    String serviceUrl = "http://localhost:8080/";
    createClientServer(8080);
    String response = "Success";
    HttpRequest request = request().withPath("/");
    mockServer.when(request).respond(response(response));
    RestContext.webresource = Client.create().resource(serviceUrl);
    RestContext.method = "GET";
    
    // test get request without header
    restSalad.i_retrieve_the_resource();
    mockServer.verify(request, once());
    stopClientServer();
  }

  @Test
  public void testI_retrieve_the_resource_GET_withHeader() throws Throwable {
    String method;
    String serviceUrl = "http://localhost:8080/";
    createClientServer(8080);
    String response = "Success";
    HttpRequest request = request().withPath("/");
    mockServer.when(request).respond(response(response));
    RestContext.webresource = Client.create().resource(serviceUrl);
    RestContext.method = "GET";
    Map<String, String> headermap = new HashMap<String, String>();
    headermap.put(RestConstants.HEADER_NAME, "testheader");
    headermap.put(RestConstants.HEADER_VALUE, "testheadervalue");
    
    RestContext.headerMapList.add(headermap);
    // test get request with header
    restSalad.i_retrieve_the_resource();
    mockServer.verify(request);
    stopClientServer();
  }
  
  @Test
  public void testI_retrieve_the_resource_POST_withHeader() throws Throwable {
    String serviceUrl = "http://localhost:8080/";
    RestContext.webresource = Client.create().resource(serviceUrl);
    RestContext.method = "POST";
    RestContext.postBody="postBody";
    Map<String, String> headermap = new HashMap<String, String>();
    headermap.put(RestConstants.HEADER_NAME, "testheader");
    headermap.put(RestConstants.HEADER_VALUE, "testheadervalue");
    
    RestContext.headerMapList.add(headermap);

    createClientServer(8080);
    String response = "Success";
    HttpRequest request = request().withPath("/").withBody(RestContext.postBody).withMethod("POST");
    mockServer.when(request).respond(response(response));

    
    // test POST request with header
    restSalad.i_retrieve_the_resource();
    mockServer.verify(request);
    stopClientServer();
  }
  
  @Test
  public void testI_retrieve_the_resource_DELETE_withHeader() throws Throwable {
    String serviceUrl = "http://localhost:8080/";
    RestContext.webresource = Client.create().resource(serviceUrl);
    RestContext.method = "DELETE";
    RestContext.postBody="postBody";
    Map<String, String> headermap = new HashMap<String, String>();
    headermap.put(RestConstants.HEADER_NAME, "testheader");
    headermap.put(RestConstants.HEADER_VALUE, "testheadervalue");
    
    RestContext.headerMapList.add(headermap);

    createClientServer(8080);
    String response = "Success";
    HttpRequest request = request().withPath("/").withBody(RestContext.postBody).withMethod("DELETE");
    mockServer.when(request).respond(response(response));

    
    // test DELETE request with header
    restSalad.i_retrieve_the_resource();
    mockServer.verify(request);
    stopClientServer();
  }

  @Test
  public void testThe_status_code_returned_should_be() throws Throwable {
    String serviceUrl = "http://localhost:8080/";
    RestContext.webresource = Client.create().resource(serviceUrl);
    RestContext.method = "DELETE";
    RestContext.postBody="postBody";
    Map<String, String> headermap = new HashMap<String, String>();
    headermap.put(RestConstants.HEADER_NAME, "testheader");
    headermap.put(RestConstants.HEADER_VALUE, "testheadervalue");
    RestContext.headerMapList.add(headermap);
    createClientServer(8080);
    String response = "Success";
    HttpRequest request = request().withPath("/").withBody(RestContext.postBody).withMethod("DELETE");
    mockServer.when(request).respond(response(response));
    
    // test DELETE request with header
    restSalad.i_retrieve_the_resource();
    restSalad.the_status_code_returned_should_be(200);;
    mockServer.verify(request);
    stopClientServer();
  }

  @Test
  public void testThe_response_should_contain_with_value() throws Throwable {
    RestContext.restResponse = getFile("testResponse.json");
    restSalad.the_response_should_contain_with_value("$.store..color", "red");
  }

  @Test
  public void testThe_response_should_contain() throws Throwable {
    RestContext.restResponse = getFile("testResponse.json");
    restSalad.the_response_should_contain("$..category");
  }

  @Test
  public void testThe_response_should_contain_with_values() throws Throwable {
    RestContext.restResponse = getFile("testResponse.json");
    List<List<String>> row = new ArrayList<List<String>>();
    row.add(Arrays.asList("reference", "fiction", "fiction", "fiction"));
    DataTable exptectedTable = DataTable.create(row);
    restSalad.the_response_should_contain_with_values("$..category", exptectedTable);
  }

  @Test
  public void testThe_array_has_element_with_below_attributes() throws Throwable {
    RestContext.restResponse = getFile("testResponse.json");
    List<List<String>> row = new ArrayList<List<String>>();
    row.add(Arrays.asList("category", "author", "title", "price"));
    row.add(Arrays.asList("reference", "Nigel Rees", "Sayings of the Century", "8.95"));
    row.add(Arrays.asList("fiction", "Evelyn Waugh", "Sword of Honour", "12.99"));
    row.add(Arrays.asList("fiction", "Herman Melville", "Moby Dick", "8.99"));
    row.add(Arrays.asList("fiction", "J. R. R. Tolkien", "The Lord of the Rings", "22.99"));
    DataTable exptectedTable = DataTable.create(row);
    restSalad.the_array_has_element_with_below_attributes("$..book", exptectedTable);
  }

  @Test
  public void testThe_response_should_contain_as_empty_array() throws Throwable {
    RestContext.restResponse = getFile("testResponse.json");
    restSalad.the_response_should_contain_as_empty_array("$..unicorn[*]");
  }

  @Test
  public void testThe_response_should_contain_with_elements() throws Throwable {
    RestContext.restResponse = getFile("testResponse.json");
    restSalad.the_response_should_contain_with_elements("$..book[*]", 4);
  }

  @Test
  public void testThe_response_is_empty() throws Throwable {
    RestContext.restResponse = "";
    restSalad.the_response_is_empty();
  }

  private String getFile(String fileName) {
    String result = "";
    ClassLoader classLoader = getClass().getClassLoader();
    try {
      result = IOUtils.toString(classLoader.getResourceAsStream(fileName));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }
}
