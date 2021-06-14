package org.openbrewerydb.dal;

import com.google.common.annotations.VisibleForTesting;
import java.util.List;
import java.util.Optional;
import org.openbrewerydb.models.Location;
import org.openbrewerydb.models.internal.BreweryInternal;


/**
 * Interface for Breweries Database Access.
 */
public interface BreweryDao {

  /**
   * Creates a Brewery record in the database.
   */
  @VisibleForTesting
  BreweryInternal createBrewery(String name,
                                String breweryType,
                                String street,
                                String address2,
                                String address3,
                                String city,
                                String state,
                                String countyProvince,
                                String country,
                                String postalCode,
                                String phone,
                                String websiteUrl,
                                Location location,
                                String tags);

  /**
   * Gets a brewery from the database.
   *
   * @param id id of the brewery
   * @return a {@link BreweryInternal}
   */
  Optional<BreweryInternal> getBrewery(int id);

  /**
   * Gets the nearest breweries for the given point radiusMeters.
   *
   * @param location Center of circle.
   * @param radiusMeters Radius of circle.
   * @return {@link BreweryInternal} list.
   */
  List<BreweryInternal> getNearestBreweries(Location location, int radiusMeters);

  @VisibleForTesting
  void deleteAllBreweries();
}
