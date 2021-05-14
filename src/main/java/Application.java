/**
 * Application class.
 */
public class Application {

  /**
   * Main method. Entry point to the application.
   */
  public static void main(String[] args) {
    AppServer appServer = new AppServer();
    appServer.run(10000);
  }
}
