package org.openbrewerydb.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigBeanFactory;
import com.typesafe.config.ConfigFactory;

/**
 * Main application configuration class.
 */
public class Configuration {

  private int port;
  private DbConfig dbConfig;

  /**
   * Loads application configuration.
   */
  public void load() {
    final Config config = ConfigFactory.load();
    this.port = config.getInt("port");
    this.dbConfig = ConfigBeanFactory.create(config.getConfig("db-config"), DbConfig.class);
  }

  public int getPort() {
    return port;
  }

  public DbConfig getDbConfig() {
    return dbConfig;
  }
}
