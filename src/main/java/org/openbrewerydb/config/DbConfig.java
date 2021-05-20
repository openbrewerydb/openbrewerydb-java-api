package org.openbrewerydb.config;

/**
 * DB config record.
 */
public class DbConfig {
  private String host;
  private int port;
  private String databaseName;
  private String schema;
  private String userName;
  private String password;

  public DbConfig() {}

  /**
   * Constructor.
   */
  public DbConfig(String host, int port, String databaseName, String schema, String userName, String password) {
    this.host = host;
    this.port = port;
    this.databaseName = databaseName;
    this.schema = schema;
    this.userName = userName;
    this.password = password;
  }

  /**
   * Maps the db config to a jdbc postgres url.
   *
   * @return A string url.
   */
  public String toUrl() {
    return String.format("jdbc:postgresql://%s:%s/%s?currentSchema=%s&user=%s&password=%s",
      this.host, this.port, this.databaseName, this.schema, this.userName, this.password);
  }

  public String getHost() {
    return host;
  }

  public int getPort() {
    return port;
  }

  public String getDatabaseName() {
    return databaseName;
  }

  public String getSchema() {
    return schema;
  }

  public String getUserName() {
    return userName;
  }

  public String getPassword() {
    return password;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public void setDatabaseName(String databaseName) {
    this.databaseName = databaseName;
  }

  public void setSchema(String schema) {
    this.schema = schema;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
