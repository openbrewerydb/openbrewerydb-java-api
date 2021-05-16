package org.openbrewerydb.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Public Brewery record. To be returned in the API.
 */
@JsonSerialize
public record Brewery(
    int id,
    String name,
    String breweryType,
    String city,
    String state,
    String country,
    Location location
) {
}
