package org.openbrewerydb;

import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * App server class.
 */
public class AppServer {

  private static final Logger logger = LoggerFactory.getLogger(AppServer.class);

  /**
   * Runs the app server.
   *
   * @param port The port that the server exposes.
   */
  public void run(final int port) {
    final Javalin app = Javalin.create()
      .start(port);

    logger.info("Application started and is listening on port " + port);
  }
}
