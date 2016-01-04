package com.bdd.support;

import static org.junit.Assert.*;

import java.io.IOException;

import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class RestHookTest {

  @Test
  public void testRefresh() {
    RestHook.refresh();
    assertNull(RestContext.clientResponse);
    assertNull(RestContext.method);
    assertNull(RestContext.postBody);
    assertNull(RestContext.restResponse);
    assertNull(RestContext.webresource);
    assertThat(RestContext.headerMapList.toArray(), arrayWithSize(0));
    assertTrue(RestContext.restProps.isEmpty());
  }
  
  @Test
  public void testBefore() {
    System.setProperty("env", "junit");
    System.setProperty("prop3", "restsalad.sysprop");

    RestHook restHook = new RestHook();
    restHook.beforeHook();
    
    assertNull(RestContext.clientResponse);
    assertNull(RestContext.method);
    assertNull(RestContext.postBody);
    assertNull(RestContext.restResponse);
    assertNull(RestContext.webresource);
    assertThat(RestContext.headerMapList.toArray(), arrayWithSize(0));
    assertEquals("restsalad", RestContext.restProps.get("prop1"));
    assertEquals("restsalad.junit", RestContext.restProps.get("prop2"));
    assertEquals("restsalad.sysprop", RestContext.restProps.get("prop3"));
  }
  
  
  @Test
  public void testAfter() throws IOException {

    RestHook restHook = new RestHook();
    restHook.afterHook();
    
    assertNull(RestContext.clientResponse);
    assertNull(RestContext.method);
    assertNull(RestContext.postBody);
    assertNull(RestContext.restResponse);
    assertNull(RestContext.webresource);
    assertThat(RestContext.headerMapList.toArray(), arrayWithSize(0));
    assertTrue(RestContext.restProps.isEmpty());
  }

}
