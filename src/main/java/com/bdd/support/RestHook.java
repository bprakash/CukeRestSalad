package com.bdd.support;

import static com.bdd.support.RestContext.env;
import static com.bdd.support.RestContext.restProps;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.java.After;
import cucumber.api.java.Before;

public class RestHook {

  static final Logger LOG = LoggerFactory.getLogger(RestHook.class);
  
  public static final int ORDER = 0;

  public static void refresh() {
    LOG.debug("Refreshing restProperties");
    restProps = new Properties();
  }

  @Before(order = ORDER)
  public void beforeHook() {
    refresh();
    loadProperties();
  }

  @After
  public void afterHook() throws IOException {
    refresh();
  }

  public static void loadProperties() {
    try {
      LOG.debug("loading rest salad Properties");
      ClassLoader loader = Thread.currentThread().getContextClassLoader();
      InputStream stream = loader.getResourceAsStream("restsalad.properties");
      restProps.load(stream);
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
