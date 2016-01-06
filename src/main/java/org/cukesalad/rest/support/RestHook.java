package org.cukesalad.rest.support;

import static org.cukesalad.rest.support.RestContext.env;
import static org.cukesalad.rest.support.RestContext.restProps;

import java.io.IOException;
import java.io.InputStream;
import java.util.EnumSet;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.json.JsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.jsonpath.spi.mapper.MappingProvider;

import cucumber.api.java.After;
import cucumber.api.java.Before;

public class RestHook {

  static final Logger LOG = LoggerFactory.getLogger(RestHook.class);
  
  public static final int ORDER = 0;

  public static void refresh() {
    LOG.debug("Refreshing restProperties");
    RestContext.refresh();
  }

  @Before(order = ORDER)
  public void beforeHook() {
    refresh();
    loadProperties();
    setupJsonConfig();
  }

  @After
  public void afterHook() throws IOException {
    refresh();
  }

  public static void setupJsonConfig() {
    Configuration.setDefaults(new Configuration.Defaults() {

      private final JsonProvider jsonProvider = new JacksonJsonProvider();
      private final MappingProvider mappingProvider = new JacksonMappingProvider();

      @Override
      public JsonProvider jsonProvider() {
        return jsonProvider;
      }

      @Override
      public MappingProvider mappingProvider() {
        return mappingProvider;
      }

      @Override
      public Set<Option> options() {
        return EnumSet.noneOf(Option.class);
      }
    });
  }
  
  public static void loadProperties() {
    try {
      LOG.debug("loading rest salad Properties");
      ClassLoader loader = Thread.currentThread().getContextClassLoader();
      InputStream stream = loader.getResourceAsStream("restsalad.properties");
      if (stream != null) {
        restProps.load(stream);
      }
      if (env != null) {
        stream = loader.getResourceAsStream("restsalad.{env}.properties".replace("{env}", env));
        if (stream != null) {
          restProps.load(stream);
        }
      }
      restProps.putAll(System.getProperties());
      LOG.debug("rest salad Properties successfully loaded");
    } catch (IOException ex) {
      LOG.error("Error loading rest salad Properties", ex);
    }
  }

}
