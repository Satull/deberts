package de.satull.deberts.exception;

/**
 * Exceptions which will thrown when the deck does not contain a card.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public class NoSuchCardException extends Exception {
  /**
   * <p>Exception constructor</p>
   *
   * @param errorMessage Message which should be thrown
   */
  public NoSuchCardException(String errorMessage) {
    super(errorMessage);
  }
}


