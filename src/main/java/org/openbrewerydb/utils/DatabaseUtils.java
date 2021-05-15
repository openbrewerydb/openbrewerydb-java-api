package org.openbrewerydb.utils;

import org.flywaydb.core.Flyway;
import org.openbrewerydb.config.DbConfig;

/**
 * Database utilities class.
 */
public class DatabaseUtils {

    /**
     * Performs Flyway database migrations for a postgres database.
     *
     * @param dbConfig Database configuration object.
     */
    public static void performMigrations(final DbConfig dbConfig) {
        final String url = String.format(
                "jdbc:postgresql://%s:%s/%s",
                dbConfig.host(),
                dbConfig.port(),
                dbConfig.databaseName()
        );

        final Flyway flyway = Flyway.configure()
                .dataSource(url, dbConfig.userName(), dbConfig.password())
                .load();
        flyway.migrate();
    }
}
