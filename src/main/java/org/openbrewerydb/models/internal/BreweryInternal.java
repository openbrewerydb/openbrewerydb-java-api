package org.openbrewerydb.models.internal;

import java.time.Instant;
import org.openbrewerydb.models.Brewery;
import org.openbrewerydb.models.Location;

/**
 * Brewery Internal record.
 */
public record BreweryInternal(
    int id,
    String name,
    String breweryType,
    String city,
    String state,
    String country,
    Location location,
    Instant createdAt,
    Instant updatedAt
) {

  /**
   * Maps {@link BreweryInternal} to {@link Brewery}.
   */
  public Brewery toPublic() {
    return new Brewery(this.id,
        this.name,
        this.breweryType,
        this.city,
        this.state,
        this.country,
        this.location);
  }
}
