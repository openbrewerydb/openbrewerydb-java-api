package org.openbrewerydb.utils;

import org.flywaydb.core.Flyway;
import org.openbrewerydb.config.DbConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Database utilities class.
 */
public class DatabaseUtils {

  private static final Logger logger = LoggerFactory.getLogger(DatabaseUtils.class);

  /**
   * Performs Flyway database migrations for a postgres database.
   *
   * @param dbConfig Database configuration object.
   */
  public static void performMigrations(final DbConfig dbConfig) {
    final String url = String.format(
        "jdbc:postgresql://%s:%s/%s",
        dbConfig.getHost(),
        dbConfig.getPort(),
        dbConfig.getDatabaseName()
    );

    logger.info(url);
    final Flyway flyway = Flyway.configure()
        .dataSource(url, dbConfig.getUserName(), dbConfig.getPassword())
        .load();
    flyway.migrate();
  }
}
