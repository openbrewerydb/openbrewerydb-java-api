package org.openbrewerydb;

import java.time.Clock;
import org.jdbi.v3.core.Jdbi;
import org.openbrewerydb.api.BreweryApi;
import org.openbrewerydb.config.Configuration;
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

    final Configuration config = new Configuration();
    config.load();
    final DbConfig dbConfig = config.getDbConfig();
    DatabaseUtils.performMigrations(dbConfig);

    final Jdbi jdbi = Jdbi.create(dbConfig.toUrl());
    final BreweryDao breweryDao = new BreweryDaoImpl(jdbi, Clock.systemDefaultZone());
    final BreweryService breweryService = new BreweryService(breweryDao);
    final BreweryApi breweryApi = new BreweryApi(breweryService);

    final AppServer appServer = new AppServer(breweryApi);
    appServer.run(config.getPort());
  }
}
