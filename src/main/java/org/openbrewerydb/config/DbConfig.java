package org.openbrewerydb.config;

/**
 * DB config record.
 */
public record DbConfig(
        String host,
        int port,
        String databaseName,
        String userName,
        String password
) {
}
