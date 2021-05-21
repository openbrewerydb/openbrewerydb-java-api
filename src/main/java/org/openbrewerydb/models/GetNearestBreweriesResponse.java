package org.openbrewerydb.models;

import java.util.List;

/**
 * Response model for getting nearby breweries.
 */
public record GetNearestBreweriesResponse(
    List<Brewery> breweries
) {
}
