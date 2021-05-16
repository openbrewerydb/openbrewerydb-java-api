package org.openbrewerydb.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * A lat/lon pair.
 */
@JsonSerialize
public record Location(double latitude, double longitude) {
}
