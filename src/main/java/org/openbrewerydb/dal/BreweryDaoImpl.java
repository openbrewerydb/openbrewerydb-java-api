package org.openbrewerydb.dal;

import static org.openbrewerydb.dal.BreweryDbTemplate.CREATE_BREWERY;
import static org.openbrewerydb.dal.BreweryDbTemplate.DELETE_ALL_BREWERIES;
import static org.openbrewerydb.dal.BreweryDbTemplate.GET_BREWERY;
import static org.openbrewerydb.dal.BreweryDbTemplate.GET_NEAREST_BREWERIES;
import static org.openbrewerydb.utils.BreweryUtils.createObdbId;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.openbrewerydb.models.Brewery;
import org.openbrewerydb.models.Location;
import org.openbrewerydb.models.internal.BreweryInternal;
import org.postgis.PGgeometry;
import org.postgis.Point;
import org.postgresql.util.PGobject;

/**
 * Postgres implementation for {@link BreweryDao}.
 */
public class BreweryDaoImpl implements BreweryDao {

  private final Jdbi jdbi;
  private final Clock clock;

  /**
   * Constructor.
   */
  public BreweryDaoImpl(final Jdbi jdbi, final Clock clock) {

    this.jdbi = jdbi;
    this.clock = clock;
  }

  @Override
  public BreweryInternal createBrewery(
      final String name,
      final String breweryType,
      final String street,
      final String address2,
      final String address3,
      final String city,
      final String state,
      final String countyProvince,
      final String country,
      final String postalCode,
      final String phone,
      final String websiteUrl,
      final Location location,
      final String tags) {
    Instant now = this.clock.instant();
    return jdbi.withHandle(handle -> handle.createQuery(CREATE_BREWERY)
        .bind("obdb_id", createObdbId(name, city))
        .bind("name", name)
        .bind("brewery_type", breweryType)
        .bind("street", street)
        .bind("address2", address2)
        .bind("address3", address3)
        .bind("city", city)
        .bind("state", state)
        .bind("county_province", countyProvince)
        .bind("country", country)
        .bind("postal_code", postalCode)
        .bind("phone", phone)
        .bind("website_url", websiteUrl)
        .bind("tags", tags)
        .bind("latitude", location.latitude())
        .bind("longitude", location.longitude())
        .bind("created_at", now.toString())
        .bind("updated_at", now.toString())
        .map(BreweryMapper.INSTANCE)
        .first());
  }

  @Override
  public Optional<BreweryInternal> getBrewery(final int id) {
    return jdbi.withHandle(handle -> handle.createQuery(GET_BREWERY)
        .bind("id", id)
        .map(BreweryMapper.INSTANCE)
        .findFirst());
  }

  @Override
  public List<BreweryInternal> getNearestBreweries(Location location, int radiusMeters) {
    return jdbi.withHandle(handle -> handle.createQuery(GET_NEAREST_BREWERIES)
        .bind("latitude", location.latitude())
        .bind("longitude", location.longitude())
        .bind("radiusMeters", radiusMeters)
        .map(BreweryMapper.INSTANCE)
        .list());
  }

  @Override
  public void deleteAllBreweries() {
    jdbi.withHandle(handle -> handle.createUpdate(DELETE_ALL_BREWERIES).execute());
  }

  private static class BreweryMapper implements RowMapper<BreweryInternal> {

    static final BreweryMapper INSTANCE = new BreweryMapper();

    @Override
    public BreweryInternal map(final ResultSet rs, final StatementContext ctx) throws SQLException {
      final int id = rs.getInt("id");
      final String obdbId = rs.getString("obdb_id");
      final String name = rs.getString("name");
      final String breweryType = rs.getString("brewery_type");
      final String street = rs.getString("street");
      final String address2 = rs.getString("address2");
      final String address3 = rs.getString("address3");
      final String city = rs.getString("city");
      final String state = rs.getString("state");
      final String countyProvince = rs.getString("county_province");
      final String country = rs.getString("country");
      final String postalCode = rs.getString("postal_code");
      final String phone = rs.getString("phone");
      final String websiteUrl = rs.getString("website_url");
      final String tags = rs.getString("tags");

      // parse location
      final PGobject pgObject = (PGobject) rs.getObject("location");
      final Point postGisPoint = (Point) new PGgeometry(pgObject.getValue()).getGeometry();
      final Location location = new Location(postGisPoint.getY(), postGisPoint.getX());

      final Instant createdAt = rs.getTimestamp("created_at").toInstant();
      final Instant updatedAt = rs.getTimestamp("updated_at").toInstant();

      return new BreweryInternal(
          id,
          obdbId,
          name,
          breweryType,
          street,
          address2,
          address3,
          city,
          state,
          countyProvince,
          country,
          postalCode,
          phone,
          websiteUrl,
          location,
          tags,
          createdAt.toString(),
          updatedAt.toString()
      );
    }
  }
}
