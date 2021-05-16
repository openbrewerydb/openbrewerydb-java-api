package org.openbrewerydb.dal;

import static org.openbrewerydb.dal.BreweryDbTemplate.CREATE_BREWERY;
import static org.openbrewerydb.dal.BreweryDbTemplate.DELETE_ALL_BREWERIES;
import static org.openbrewerydb.dal.BreweryDbTemplate.GET_BREWERY;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Clock;
import java.time.Instant;
import java.util.Optional;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
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
      final String city,
      final String state,
      final String country,
      final Location location) {
    Instant now = this.clock.instant();
    return jdbi.withHandle(handle -> handle.createQuery(CREATE_BREWERY)
        .bind("name", name)
        .bind("brewery_type", breweryType)
        .bind("city", city)
        .bind("state", state)
        .bind("country", country)
        .bind("latitude", location.latitude())
        .bind("longitude", location.longitude())
        .bind("created_at", now)
        .bind("updated_at", now)
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
  public void deleteAllBreweries() {
    jdbi.withHandle(handle -> handle.createUpdate(DELETE_ALL_BREWERIES).execute());
  }

  private static class BreweryMapper implements RowMapper<BreweryInternal> {

    static final BreweryMapper INSTANCE = new BreweryMapper();

    @Override
    public BreweryInternal map(final ResultSet rs, final StatementContext ctx) throws SQLException {
      final int id = rs.getInt("id");
      final String name = rs.getString("name");
      final String breweryType = rs.getString("brewery_type");
      final String city = rs.getString("city");
      final String state = rs.getString("state");
      final String country = rs.getString("country");

      // parse location
      final PGobject pgObject = (PGobject) rs.getObject("location");
      final Point postGisPoint = (Point) new PGgeometry(pgObject.getValue()).getGeometry();
      final Location location = new Location(postGisPoint.getY(), postGisPoint.getX());

      final Instant createdAt = rs.getTimestamp("created_at").toInstant();
      final Instant updatedAt = rs.getTimestamp("updated_at").toInstant();

      return new BreweryInternal(id, name, breweryType, city, state, country, location, createdAt, updatedAt);
    }
  }
}
