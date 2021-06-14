package org.openbrewerydb.dal;

/**
 * Class for DB Templates.
 */
public class BreweryDbTemplate {
  public static final String CREATE_BREWERY = "INSERT INTO breweries "
      + "VALUES(DEFAULT, :obdb_id, :name, :brewery_type, :street, :address2, :address3, :city, :state, "
      + ":county_province, :country, :postal_code, :phone, :website_url, :tags, "
      + "public.ST_SetSRID(public.ST_MakePoint(:longitude, :latitude), '4326'), :created_at, :updated_at) "
      + "RETURNING *";

  public static final String GET_BREWERY = "SELECT * FROM breweries WHERE id = :id";

  public static final String GET_NEAREST_BREWERIES = "SELECT * FROM breweries "
      + "WHERE public.ST_DWithin(location::public.geography, "
      + "public.ST_SetSRID(public.ST_MakePoint(:longitude, :latitude), '4326')::public.geography, :radiusMeters)";

  public static final String DELETE_ALL_BREWERIES = "DELETE FROM breweries";
}
