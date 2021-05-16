package org.openbrewerydb.api;

import io.javalin.http.Context;
import org.openbrewerydb.models.Brewery;
import org.openbrewerydb.service.BreweryService;

/**
 * Request handlers for Brewery API.
 */
public class BreweryApi {

  private final BreweryService breweryService;

  public BreweryApi(final BreweryService breweryService) {

    this.breweryService = breweryService;
  }

  /**
   * Request handler for getting a brewery.
   */
  public void getBrewery(final Context context) throws Exception {
    final int id = Integer.parseInt(context.pathParam("id"));
    final Brewery brewery = this.breweryService.getBrewery(id);
    context.json(brewery);
  }
}
