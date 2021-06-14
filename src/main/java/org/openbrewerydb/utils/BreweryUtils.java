package org.openbrewerydb.utils;

/**
 * Utils class for brewery api.
  */
public class BreweryUtils {

  /**
   * Creates the obdbId from the brewery name and city
   * Ex. name = Boulevard Brewery
   *     city = Kanasas City
   *     obdbId = boulevard-brewery-kansas-city
   *
   * @param name - String value of the brewery name
   * @param city - String value of the brewery city
   * @return String value for obdbId
   */
  public static String createObdbId(String name, String city) {
    // strip special characters
    String cleanedName = name.replaceAll("/[^A-Za-z0-9 ]/", "");
    String cleanedCity = city.replaceAll("/[^A-Za-z0-9 ]/", "");
    return String.format("%s %s", cleanedName.toLowerCase(), cleanedCity.toLowerCase())
                 .replaceAll(" ", "-");
  }
}
