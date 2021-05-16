package org.openbrewerydb.dal;

import java.util.Optional;
import org.openbrewerydb.models.BreweryInternal;


/**
 * Interface for Breweries Database Access.
 */
public interface BreweriesDao {

  /**
   * Gets a brewery from the database.
   *
   * @param id id of the brewery
   * @return a {@link BreweryInternal}
   */
  Optional<BreweryInternal> getBrewery(int id);
}
