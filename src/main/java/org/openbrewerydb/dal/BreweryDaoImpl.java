package org.openbrewerydb.dal;

import static org.openbrewerydb.dal.BreweryDbTemplate.GET_BREWERY;

import java.sql.ResultSet;
import java.util.Optional;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.openbrewerydb.models.BreweryInternal;


/**
 * Postgres implementation for {@link BreweriesDao}.
 */
public class BreweryDaoImpl implements BreweriesDao {

  private final Jdbi jdbi;

  public BreweryDaoImpl(final Jdbi jdbi) {

    this.jdbi = jdbi;
  }

  @Override
  public Optional<BreweryInternal> getBrewery(final int id) {
    return jdbi.withHandle(handle -> handle.createQuery(GET_BREWERY)
      .bind("id", id)
      .map(BreweryMapper.INSTANCE)
      .findFirst());
  }

  private static class BreweryMapper implements RowMapper<BreweryInternal> {

    static final BreweryMapper INSTANCE = new BreweryMapper();

    @Override
    public BreweryInternal map(final ResultSet rs, final StatementContext ctx) {
      return new BreweryInternal(1);
    }
  }
}
