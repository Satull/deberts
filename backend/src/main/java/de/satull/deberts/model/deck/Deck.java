package de.satull.deberts.model.deck;

import de.satull.deberts.model.enums.Suit;
import de.satull.deberts.model.enums.Value;
import de.satull.deberts.model.web.Card;
import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An abstract class that contains and performs cards. Cards are grouped by suits. A suit exists
 * only if it has at least one card.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 */
public abstract class Deck {

  private static final Logger LOG =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

  /**
   * Returns true if this deck contains the specified Card. Returns: true if this string contains s,
   * false otherwise
   *
   * @param card the card to search for
   * @return true if this Deck contains card, false otherwise
   */
  public abstract boolean contains(Card card);

  /**
   * Returns the countCards of cards in this deck.
   *
   * @return number of cards
   */
  public abstract int countCards();

  /**
   * Returns the card from the deck using suit and value.
   *
   * @param suit  of the card
   * @param value of the card
   * @return card founded card
   */
  public abstract Card getCard(Suit suit, Value value);

  /**
   * Removes card from the deck.
   *
   * @param card card to remove
   */
  public abstract void removeCard(Card card);

  /**
   * Reset the deck to the init values.
   */
  public abstract void resetDeck();

  /**
   * Returns a random card from the deck.
   *
   * @return random card from the deck
   */
  public abstract Card getRandomCard();

  /**
   * Returns a random card from the specified suit of the deck.
   *
   * @param suit suit to get a card
   * @return random card from the suit
   */
  public abstract Card getRandomCardFromSuit(Suit suit);

  /**
   * Add card into the deck.
   *
   * @param card to add
   */
  public abstract void addCard(Card card);
}
