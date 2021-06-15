package org.openbrewerydb.api;

import static org.openbrewerydb.TestUtils.createTestBrewery;

import io.javalin.plugin.json.JavalinJson;
import java.time.Clock;
import java.util.List;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.assertj.core.api.WithAssertions;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openbrewerydb.AppServer;
import org.openbrewerydb.config.DbConfig;
import org.openbrewerydb.dal.BreweryDao;
import org.openbrewerydb.dal.BreweryDaoImpl;
import org.openbrewerydb.models.Brewery;
import org.openbrewerydb.models.GetNearestBreweriesResponse;
import org.openbrewerydb.models.internal.BreweryInternal;
import org.openbrewerydb.service.BreweryService;
import org.openbrewerydb.utils.DatabaseUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BreweryApiTest implements WithAssertions {

  @Container
  public static PostgreSQLContainer<?> postgresDb = new PostgreSQLContainer<>(
      DockerImageName.parse("postgis/postgis:12-2.5-alpine").asCompatibleSubstituteFor("postgres")
  )
      .withCommand("postgres -c max_connections=42")
      .withExposedPorts(5432)
      .withDatabaseName("openbrewerydb")
      .withUsername("admin")
      .withPassword("admin");

  final int port = 9990;
  BreweryDao breweryDao;
  BreweryService breweryService;
  BreweryApi breweryApi;
  private final String apiUrl = String.format("http://localhost:%s/breweries", port);

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
    this.breweryDao = new BreweryDaoImpl(jdbi, Clock.systemDefaultZone());
    this.breweryService = new BreweryService(this.breweryDao);
    this.breweryApi = new BreweryApi(this.breweryService);

    new AppServer(this.breweryApi).run(port);
  }

  @BeforeEach
  public void beforeEach() {
    this.breweryDao.deleteAllBreweries();
  }

  @Test
  public void testGetBrewery() {
    Brewery expected = createTestBrewery(this.breweryDao).toPublic();
    HttpResponse<String> response = Unirest.get(String.format("%s/%s", this.apiUrl, expected.id()))
        .asString();
    assertThat(response.getStatus()).isEqualTo(200);
    Brewery actual = JavalinJson.fromJson(response.getBody(), Brewery.class);
    assertThat(actual.obdbId()).isEqualTo("golden-road-brewing-los-angeles");
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void testGetNearestBreweries() {
    final BreweryInternal brewery = createTestBrewery(this.breweryDao);
    String url = String.format("%s/search-nearest?latitude=%s&longitude=%s&radius=%s",
        this.apiUrl,
        brewery.location().latitude(),
        brewery.location().longitude(),
        1);
    HttpResponse<String> response = Unirest.get(url)
        .asString();

    assertThat(response.getStatus()).isEqualTo(200);
    List<Brewery> breweries = JavalinJson.fromJson(response.getBody(), GetNearestBreweriesResponse.class).breweries();
    assertThat(breweries.size()).isEqualTo(1);

    // not in radius
    url = String.format("%s/search-nearest?latitude=%s&longitude=%s&radius=%s", this.apiUrl, 1.0, 1.0, 1);
    response = Unirest.get(url)
        .asString();

    assertThat(response.getStatus()).isEqualTo(200);
    breweries = JavalinJson.fromJson(response.getBody(), GetNearestBreweriesResponse.class).breweries();
    assertThat(breweries).isEmpty();
  }

  @Test
  public void testGetNearestBreweriesBadRequest() {
    final BreweryInternal brewery = createTestBrewery(this.breweryDao);
    String url = String.format("%s/search-nearest?latitude=%s&longitude=%s&radius=%s",
        this.apiUrl,
        brewery.location().latitude(),
        brewery.location().longitude(),
        1.3); // not a valid integer
    HttpResponse<String> response = Unirest.get(url)
        .asString();

    assertThat(response.getStatus()).isEqualTo(400);
  }

}
