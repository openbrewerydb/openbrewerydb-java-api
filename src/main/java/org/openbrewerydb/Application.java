package org.openbrewerydb;

import java.time.Clock;
import org.jdbi.v3.core.Jdbi;
import org.openbrewerydb.api.BreweryApi;
import org.openbrewerydb.config.DbConfig;
import org.openbrewerydb.dal.BreweryDao;
import org.openbrewerydb.dal.BreweryDaoImpl;
import org.openbrewerydb.service.BreweryService;
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

    final Jdbi jdbi = Jdbi.create(dbConfig.toUrl());
    final BreweryDao breweryDao = new BreweryDaoImpl(jdbi, Clock.systemDefaultZone());
    final BreweryService breweryService = new BreweryService(breweryDao);
    final BreweryApi breweryApi = new BreweryApi(breweryService);

    final AppServer appServer = new AppServer(breweryApi);
    appServer.run(10000);
  }
}
