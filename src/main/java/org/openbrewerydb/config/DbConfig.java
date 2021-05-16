package org.openbrewerydb.config;

/**
 * DB config record.
 */
public record DbConfig(
        String host,
        int port,
        String databaseName,
        String schema,
        String userName,
        String password
) {

  /**
   * Maps the db config to a jdbc postgres url.
   *
   * @return A string url.
   */
  public String toUrl() {
    return String.format("jdbc:postgresql://%s:%s/%s?currentSchema=%s&user=%s&password=%s",
      this.host, this.port, this.databaseName(), this.schema(), this.userName(), this.password());
  }
}
