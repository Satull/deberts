package de.satull.deberts.enums;

import java.util.Arrays;

/**
 * Represents all possible combinations at the beginning of action {@code Phase}.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public enum CombinationName {
  FIFTY(4, 50, 50),
  TERTZ(3, 20, 20),
  BELLA(2, 0, 20),
  EMPTY(0, 0, 0);

  private final int cards;
  private final int standardPoints;
  private final int trumpPoints;

  CombinationName(int cards, int standardPoints, int trumpPoints) {
    this.cards = cards;
    this.standardPoints = standardPoints;
    this.trumpPoints = trumpPoints;
  }

  /**
   * Returns a {@code CombinationName} by the number of cards value.
   *
   * @param cards {@code int} number of cards in the combination
   * @return {@code CombinationName}
   */
  public static CombinationName getNameByCards(int cards) {
    return Arrays.stream(values()).filter(name -> name.cards == cards).findFirst().orElse(null);
  }

  /**
   * Returns how much cards has a combination.
   *
   * @return number of cards in the combination
   */
  public int getNumberOfCards() {
    return cards;
  }

  /**
   * Return how much points brings a combination. Trump is needed to decide if combination is BELLA
   * or just kind and queen.
   *
   * @param trump is this a trump combination
   * @return points of combination
   */
  public int getPoints(boolean trump) {
    return trump ? trumpPoints : standardPoints;
  }
}
