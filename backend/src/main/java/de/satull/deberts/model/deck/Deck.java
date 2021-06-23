package de.satull.deberts.model.deck;


/**
 * An object that contains and manages cards. This interface provides more methods then business
 * logic to improve test capability of the object.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @see CardDeck
 * @since 1.0
 */
public interface Deck {

  /**
   * Returns true if this deck contains the specified Card false otherwise.
   *
   * @param card the card to search for
   * @return true if this Deck contains card, false otherwise
   */
  boolean contains(Card card);

  /**
   * Returns the number of cards in this deck.
   *
   * @return number of cards
   */
  int countCards();

  /**
   * Returns the card from the deck using suit and value.
   *
   * @param suit      of the card
   * @param faceValue of the card
   * @return card founded card
   */
  Card dealCard(Suit suit, FaceValue faceValue);

  /**
   * Reset the deck to the init values.
   */
  void resetDeck();

  /**
   * Returns a random card from the deck.
   *
   * @return random card from the deck
   */
  Card getRandomCard();

  /**
   * Returns a random card from the specified suit of the deck.
   *
   * @param suit suit to get a card
   * @return random card from the suit
   */
  Card getRandomCardFromSuit(Suit suit);

  /**
   * Add card into the deck.
   *
   * @param card to add
   */
  void addCard(Card card);


}
