package de.satull.deberts.util;

import de.satull.deberts.model.enums.SuitDeck;
import java.util.Map;

/**
 * Util class for general game methods.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.5
 * @since 1.0
 */
public class Game {

  public static final int ACTION = 3;
  public static final int LAST_CARD = 10;
  public static final int START = 0;
  public static final int TRADE = 6;

  private static final Map<SuitDeck, Integer> cardPointMap =
      Map.of(SuitDeck.SEVEN, 0, SuitDeck.EIGHT, 0,
          SuitDeck.NINE, 0, SuitDeck.TEN, 10, SuitDeck.JACK, 2, SuitDeck.QUEEN, 3, SuitDeck.KING, 4,
          SuitDeck.ACE,
          11);

  private static final Map<SuitDeck, Integer> trumpPointMap =
      Map.of(SuitDeck.SEVEN, 0, SuitDeck.EIGHT, 0,
          SuitDeck.NINE, 14, SuitDeck.TEN, 10, SuitDeck.JACK, 20, SuitDeck.QUEEN, 3, SuitDeck.KING,
          4, SuitDeck.ACE,
          11);

  private Game() {
    throw new AssertionError();
  }

  /**
   * <p>Calculates how many points has each card.</p>
   *
   * @param cardValue the card for calculation
   * @param {@code    true} if it is a trump card
   * @return points of the card
   */
  public static int getCardPoints(SuitDeck cardValue, boolean trump) {
    return trump ? trumpPointMap.get(cardValue) : cardPointMap.get(cardValue);
  }

}