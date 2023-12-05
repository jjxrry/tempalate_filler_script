package problem1;

/**
 * EmptyCSVException  for empty CSV file
 */
public class EmptyCSVException extends Exception {
  /**
   * Constructor
   * @param message - message to call out error
   */
  public EmptyCSVException(String message) {
    super(message);
  }
}
