package org.cukesalad.rest.support;

import static org.hamcrest.Matchers.arrayWithSize;
import static org.junit.Assert.*;

import javax.xml.parsers.ParserConfigurationException;

import org.cukesalad.rest.support.RestContext;
import org.junit.Test;

public class RestContextTest {

  @Test
  public void testRefresh() throws ParserConfigurationException {
    RestContext.refresh();
    assertNull(RestContext.clientResponse);
    assertNull(RestContext.method);
    assertNull(RestContext.postBody);
    assertNull(RestContext.restResponse);
    assertNull(RestContext.webresource);
    assertThat(RestContext.headerMapList.toArray(), arrayWithSize(0));
    assertTrue(RestContext.restProps.isEmpty());
  }

}
