package org.openbrewerydb.dal;

import com.google.common.annotations.VisibleForTesting;
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
  BreweryInternal createBrewery(String name,
                                String breweryType,
                                String city,
                                String state,
                                String country,
                                Location location);

  /**
   * Gets a brewery from the database.
   *
   * @param id id of the brewery
   * @return a {@link BreweryInternal}
   */
  Optional<BreweryInternal> getBrewery(int id);

  @VisibleForTesting
  void deleteAllBreweries();
}
