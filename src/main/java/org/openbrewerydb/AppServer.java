package org.openbrewerydb;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import io.javalin.Javalin;
import org.openbrewerydb.api.BreweryApi;
import org.openbrewerydb.exceptions.BreweryNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * App server class.
 */
public class AppServer {

  private static final Logger logger = LoggerFactory.getLogger(AppServer.class);
  private final BreweryApi breweryApi;

  public AppServer(final BreweryApi breweryApi) {

    this.breweryApi = breweryApi;
  }

  /**
   * Runs the app server.
   *
   * @param port The port that the server exposes.
   */
  public void run(final int port) {
    final Javalin app = Javalin.create()
        .start(port);

    app.routes(() -> {
      path("breweries", () -> {
        path("search-nearest", () -> {
          get(this.breweryApi::getNearestBreweries);
        });
        path(":id", () -> {
          get(this.breweryApi::getBrewery);
        });
      });
    });

    app.exception(BreweryNotFoundException.class, (((exception, ctx) -> {
      ctx.status(404);
      ctx.result(exception.getMessage());
    })));

    logger.info("Application started and is listening on port " + port);
  }
}
