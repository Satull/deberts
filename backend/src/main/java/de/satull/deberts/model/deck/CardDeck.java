package de.satull.deberts.model.deck;

import de.satull.deberts.exception.NoSuchCardException;
import de.satull.deberts.model.Card;
import de.satull.deberts.model.SuitDeck;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Represents a cards in the hand of the player.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.5
 * @since 1.0
 */

public class CardDeck extends StockDeck {

  /**
   * <p>Creates CardDeck with 32 cards</p>
   */
  @Autowired
  public CardDeck() {
    resetDeck();
  }

  /**
   * <p>Method returns a random card form the deck.</p>
   *
   * @return random card
   * @throws NoSuchCardException removed card is not in the deck
   */
  public Card getRandomCard() throws NoSuchCardException {
    int randomSuitIndex = new Random().nextInt(stockDeck.size());
    String randomSuitKey = suitKeys.get(randomSuitIndex);
    int randomCardIndex = new Random().nextInt(stockDeck.get(randomSuitKey).size());

    return getCard(randomSuitKey, stockDeck.get(randomSuitKey).get(randomCardIndex));
  }

  /**
   * <p>Method returns a random card form the deck.</p>
   *
   * @return random card from the special suit
   * @throws NoSuchCardException removed card is not in the deck
   */
  public Card getRandomCardFromSuit(String suit) throws NoSuchCardException {
    try {
      int randomCardIndex = new Random().nextInt(stockDeck.get(suit).size());
      return getCard(suit, stockDeck.get(suit).get(randomCardIndex));
    } catch (NullPointerException e) {
      return new Card(suit, SuitDeck.ACE);
    }
  }

  /**
   * <p>Reset the deck to the init values.</p>
   */
  public void resetDeck() {
    stockDeck = new LinkedHashMap<>();
    stockDeck.put("clubs", new ArrayList<>(EnumSet.allOf(SuitDeck.class)));
    stockDeck.put("diamonds", new ArrayList<>(EnumSet.allOf(SuitDeck.class)));
    stockDeck.put("hearts", new ArrayList<>(EnumSet.allOf(SuitDeck.class)));
    stockDeck.put("spades", new ArrayList<>(EnumSet.allOf(SuitDeck.class)));
    suitKeys = new ArrayList<>(stockDeck.keySet());
  }

  @Override
  public String toString() {
    return "CardDeck{" +
        "stockDeck=" + stockDeck +
        ", suitKeys=" + suitKeys +
        '}';
  }


}