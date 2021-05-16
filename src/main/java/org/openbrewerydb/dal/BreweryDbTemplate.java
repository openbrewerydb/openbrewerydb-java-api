package org.openbrewerydb.dal;

/**
 * Class for DB Templates.
 */
public class BreweryDbTemplate {
  public static final String GET_BREWERY = "SELECT * FROM breweries WHERE id = :id";
}
