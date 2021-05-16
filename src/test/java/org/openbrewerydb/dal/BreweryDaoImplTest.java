package org.openbrewerydb.dal;

import org.assertj.core.api.WithAssertions;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openbrewerydb.config.DbConfig;
import org.openbrewerydb.utils.DatabaseUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BreweryDaoImplTest implements WithAssertions {

  @Container
  public static PostgreSQLContainer<?> postgresDb = new PostgreSQLContainer<>(
      DockerImageName.parse("postgis/postgis:12-2.5-alpine").asCompatibleSubstituteFor("postgres")
  )
      .withCommand("postgres -c max_connections=42")
      .withExposedPorts(5432)
      .withDatabaseName("openbrewerydb")
      .withUsername("admin")
      .withPassword("admin");

  BreweriesDao breweriesDao;

  @BeforeAll
  public void beforeAll() {
    postgresDb.start();
    final DbConfig dbConfig = new DbConfig(
        postgresDb.getHost(),
        postgresDb.getFirstMappedPort(),
        "openbrewerydb",
        postgresDb.getDatabaseName(),
        postgresDb.getUsername(),
        postgresDb.getPassword());

    DatabaseUtils.performMigrations(dbConfig);
    final Jdbi jdbi = Jdbi.create(dbConfig.toUrl());
    this.breweriesDao = new BreweryDaoImpl(jdbi);
  }

  @Test
  public void testGetBrewery() {
    assertThat(this.breweriesDao.getBrewery(1)).isEmpty();
  }
}
