package org.openbrewerydb.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.Instant;

/**
 * Public Brewery record. To be returned in the API.
 */
@JsonSerialize
public record Brewery(
    int id,
    String obdbId,
    String name,
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
    String tags,
    String createdDate,
    String updatedDate) {
}
