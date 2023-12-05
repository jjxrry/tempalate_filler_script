package problem1;

/**
 * Exception for args not satisfy the requirements
 */
public class ValidationErrorException extends Exception {
  /**
   * Constructor
   * @param message - message to call out error
   */
  public ValidationErrorException(String message) {
    super(message);
  }
}