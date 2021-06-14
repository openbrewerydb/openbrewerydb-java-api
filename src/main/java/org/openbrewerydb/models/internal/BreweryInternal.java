package org.openbrewerydb.models.internal;

import java.time.Instant;
import org.openbrewerydb.models.Brewery;
import org.openbrewerydb.models.Location;

/**
 * Brewery Internal record.
 */
public record BreweryInternal(
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
    String createdAt,
    String updatedAt
) {

  /**
   * Maps {@link BreweryInternal} to {@link Brewery}.
   */
  public Brewery toPublic() {
    return new Brewery(this.id,
        this.obdbId,
        this.name,
        this.breweryType,
        this.street,
        this.address2,
        this.address3,
        this.city,
        this.state,
        this.countyProvince,
        this.country,
        this.postalCode,
        this.phone,
        this.websiteUrl,
        this.location,
        this.tags,
        this.createdAt,
        this.updatedAt);
  }
}
