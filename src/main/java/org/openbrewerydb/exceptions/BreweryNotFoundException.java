package org.openbrewerydb.exceptions;

/**
 * Custom Exception for mapping to 404 http status codes.
 */
public class BreweryNotFoundException extends RuntimeException {

  public BreweryNotFoundException(final String errorMessage) {
    super(errorMessage);
  }
}
