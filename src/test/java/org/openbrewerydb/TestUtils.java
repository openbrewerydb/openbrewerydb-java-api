package org.openbrewerydb;

import static org.openbrewerydb.utils.BreweryUtils.createObdbId;

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
      createObdbId("Golden Road Brewing", "Los Angeles"),
      "Golden Road Brewing",
      "large",
      "5430 W San Fernando Rd",
      "",
      "",
      "Los Angeles",
      "California",
      "",
      "United States",
      "90039-1015",
      "2135426039",
      "http://www.goldenroad.la",
      new Location(34.15058802297996, -118.27449054603123),
      "",
      FAKE_CLOCK.instant().toString(),
      FAKE_CLOCK.instant().toString()
  );

  /**
   * Creates a test brewery in the db.
   */
  public static BreweryInternal createTestBrewery(final BreweryDao breweryDao) {
    return createTestBrewery(
        TEST_BREWERY.name(),
        TEST_BREWERY.breweryType(),
        TEST_BREWERY.street(),
        TEST_BREWERY.address2(),
        TEST_BREWERY.address3(),
        TEST_BREWERY.city(),
        TEST_BREWERY.state(),
        TEST_BREWERY.countyProvince(),
        TEST_BREWERY.country(),
        TEST_BREWERY.postalCode(),
        TEST_BREWERY.phone(),
        TEST_BREWERY.websiteUrl(),
        TEST_BREWERY.location(),
        TEST_BREWERY.tags(),
        breweryDao
    );
  }

  /**
   * Creates a test brewery in the db.
   */
  public static BreweryInternal createTestBrewery(
      final String name,
      final String breweryType,
      final String street,
      final String address2,
      final String address3,
      final String city,
      final String state,
      final String countyProvince,
      final String country,
      final String postalCode,
      final String phone,
      final String websiteUrl,
      final Location location,
      final String tags,
      final BreweryDao breweryDao) {
    return breweryDao.createBrewery(
        name,
        breweryType,
        street,
        address2,
        address3,
        city,
        state,
        countyProvince,
        country,
        postalCode,
        phone,
        websiteUrl,
        location,
        tags
    );
  }
}
