package org.openbrewerydb;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import org.openbrewerydb.dal.BreweryDao;
import org.openbrewerydb.models.Location;
import org.openbrewerydb.models.internal.BreweryInternal;

/**
 * Test utils class.
 */
public class TestUtils {

  public static final Clock FAKE_CLOCK = Clock.fixed(Instant.now(), ZoneId.systemDefault());
  public static final BreweryInternal TEST_BREWERY = new BreweryInternal(
      0,
      "Golden Road",
      "large",
      "Los Angeles",
      "California",
      "United States",
      new Location(34.15058802297996, -118.27449054603123),
      FAKE_CLOCK.instant(),
      FAKE_CLOCK.instant()
  );

  /**
   * Creates a test brewery in the db.
   */
  public static BreweryInternal createTestBrewery(final BreweryDao breweryDao) {
    return createTestBrewery(
        TEST_BREWERY.name(),
        TEST_BREWERY.breweryType(),
        TEST_BREWERY.city(),
        TEST_BREWERY.state(),
        TEST_BREWERY.country(),
        TEST_BREWERY.location(),
        breweryDao
    );
  }

  /**
   * Creates a test brewery in the db.
   */
  public static BreweryInternal createTestBrewery(
      final String name,
      final String breweryType,
      final String city,
      final String state,
      final String country,
      final Location location,
      final BreweryDao breweryDao) {
    return breweryDao.createBrewery(
        name,
        breweryType,
        city,
        state,
        country,
        location
    );
  }
}
