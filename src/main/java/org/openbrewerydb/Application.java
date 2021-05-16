package org.openbrewerydb;

import org.openbrewerydb.config.DbConfig;
import org.openbrewerydb.utils.DatabaseUtils;

/**
 * Application class.
 */
public class Application {

  /**
   * Main method. Entry point to the application.
   */
  public static void main(String[] args) {

    // TODO: Add typesafe config

    // get db configuration and run migrations.
    final String dbHost = System.getenv("DB_HOST");
    final int dbPort = Integer.parseInt(System.getenv("DB_PORT"));
    final String dbName = System.getenv("DB_NAME");
    final String dbSchema = System.getenv("DB_SCHEMA");
    final String dbUserName = System.getenv("DB_USER");
    final String dbPassword = System.getenv("DB_PASSWORD");
    final DbConfig dbConfig = new DbConfig(dbHost, dbPort, dbSchema, dbName, dbUserName, dbPassword);
    DatabaseUtils.performMigrations(dbConfig);

    final AppServer appServer = new AppServer();
    appServer.run(10000);
  }
}
