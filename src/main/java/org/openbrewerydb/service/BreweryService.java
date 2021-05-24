package org.openbrewerydb.service;

import java.util.List;
import java.util.stream.Collectors;
import org.openbrewerydb.dal.BreweryDao;
import org.openbrewerydb.exceptions.BreweryNotFoundException;
import org.openbrewerydb.models.Brewery;
import org.openbrewerydb.models.Location;
import org.openbrewerydb.models.internal.BreweryInternal;


/**
 * Service layer for breweries.
 */
public class BreweryService {

  private final BreweryDao breweryDao;

  public BreweryService(final BreweryDao breweryDao) {

    this.breweryDao = breweryDao;
  }

  /**
   * Gets a brewery from the data access layer.
   */
  public Brewery getBrewery(final int id) {
    return this.breweryDao.getBrewery(id)
        .orElseThrow(() -> new BreweryNotFoundException(String.format("Brewery %d not found.", id)))
        .toPublic();
  }

  /**
   * Gets nearby breweries.
   */
  public List<Brewery> getNearestBreweries(Location location, int radius) {
    return this.breweryDao.getNearestBreweries(location, radius).stream()
        .map(BreweryInternal::toPublic)
        .collect(Collectors.toList());
  }
}
