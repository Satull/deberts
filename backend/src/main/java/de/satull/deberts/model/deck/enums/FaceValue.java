package de.satull.deberts.model.deck.enums;

/**
 * Represents all relevant face values of cards.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public enum FaceValue {
  SEVEN(0, 0),
  EIGHT(0, 0),
  NINE(0, 14),
  TEN(10, 10),
  JACK(2, 20),
  QUEEN(3, 3),
  KING(4, 4),
  ACE(11, 11);

  private final int standardPoints;
  private final int trumpPoints;

  FaceValue(int standardPoints, int trumpPoints) {
    this.standardPoints = standardPoints;
    this.trumpPoints = trumpPoints;
  }

  /**
   * Returns points of the card {@code FaceValue}. Each {@code FaceValue} has a pair of points,
   * first one is for the standard suit, the second one is for the trump. trump variable determines
   * which points will be used.
   *
   * @param trump determines which points should be returned.
   * @return points of the {@code FaceValue}
   */
  public int getPoints(boolean trump) {
    return trump ? trumpPoints : standardPoints;
  }
}
