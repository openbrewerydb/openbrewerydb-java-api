package org.openbrewerydb.service;

import org.openbrewerydb.dal.BreweryDao;
import org.openbrewerydb.models.Brewery;

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
  public Brewery getBrewery(final int id) throws Exception {
    return this.breweryDao.getBrewery(id)
        .orElseThrow(() -> new Exception("Brewery not found.")) // TODO: Custom App Exceptions.
        .toPublic();
  }
}
