package org.openbrewerydb.dal;

import static org.openbrewerydb.TestUtils.FAKE_CLOCK;
import static org.openbrewerydb.TestUtils.TEST_BREWERY;
import static org.openbrewerydb.TestUtils.createTestBrewery;

import org.assertj.core.api.WithAssertions;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openbrewerydb.config.DbConfig;
import org.openbrewerydb.models.internal.BreweryInternal;
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

  BreweryDao breweryDao;

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
    this.breweryDao = new BreweryDaoImpl(jdbi, FAKE_CLOCK);
  }

  @BeforeEach
  public void beforeEach() {
    this.breweryDao.deleteAllBreweries();
  }

  @Test
  public void   testCreateBrewery() {
    final BreweryInternal actual = createTestBrewery(this.breweryDao);
    assertThat(actual.name()).isEqualTo(TEST_BREWERY.name());
    assertThat(actual.breweryType()).isEqualTo(TEST_BREWERY.breweryType());
    assertThat(actual.city()).isEqualTo(TEST_BREWERY.city());
    assertThat(actual.state()).isEqualTo(TEST_BREWERY.state());
    assertThat(actual.country()).isEqualTo(TEST_BREWERY.country());
    assertThat(actual.location()).isEqualTo(TEST_BREWERY.location());
  }

  @Test
  public void testGetBrewery() {
    BreweryInternal brewery = createTestBrewery(this.breweryDao);
    assertThat(this.breweryDao.getBrewery(brewery.id()).get()).isEqualTo(brewery);
  }
}
