package org.openbrewerydb.api;

import io.javalin.http.Context;
import java.util.List;
import org.openbrewerydb.models.Brewery;
import org.openbrewerydb.models.GetNearestBreweriesResponse;
import org.openbrewerydb.models.Location;
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

  /**
   * Request handler for getting nearby breweries by point radiusMeters.
   */
  public void getNearestBreweries(final Context context) {
    // parse query params
    final double latitude = context.queryParam("latitude", Double.class).get();
    final double longitude = context.queryParam("longitude", Double.class).get();
    final int radius = context.queryParam("radius", Integer.class).get();

    final List<Brewery> breweries = this.breweryService.getNearestBreweries(new Location(latitude, longitude), radius);
    context.json(new GetNearestBreweriesResponse(breweries));
  }
}
