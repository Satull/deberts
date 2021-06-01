package de.satull.deberts.model.deck;

import de.satull.deberts.exception.NoSuchCardException;
import de.satull.deberts.model.enums.Constant;
import de.satull.deberts.model.enums.Suit;
import de.satull.deberts.model.enums.SuitDeck;
import de.satull.deberts.model.web.Card;
import java.util.LinkedHashMap;
import java.util.List;

public interface Deck {

  /**
   * Returns a random card from the deck.
   *
   * @return random card from the deck
   * @throws NoSuchCardException the deck is empty
   */
  Card getRandomCard() throws NoSuchCardException;

  /**
   * Returns a random card from the specified of the deck
   *
   * @param suit suit to get a card
   * @return random card from the suit
   * @throws NoSuchCardException the suit is empty
   */
  Card getRandomCardFromSuit(Suit suit) throws NoSuchCardException;

  /**
   * Returns true if this deck contains the specified Card. Returns: true if this string contains s,
   * false otherwise
   *
   * @param card the card to search for
   * @return true if this Deck contains card, false otherwise
   */
  boolean contains(Card card);

  /**
   * Returns the count of cards in this deck.
   *
   * @return number of cards
   */
  int count();

  /**
   * Returns the card card from the deck using suit and value.
   *
   * @param suit  of the card
   * @param value of the card
   * @return card founded card
   * @throws NoSuchCardException removed card is not in the deck
   */
  Card getCard(Suit suit, SuitDeck value) throws NoSuchCardException;

  /**
   * Returns deck information with the actual number of cards.
   *
   * @return information about cards in the deck and its number
   */
  LinkedHashMap<Constant, Object> getDeck();

  /**
   * Returns the list of actual suits in the deck
   *
   * @return list of all suits in the deck
   */
  List<Suit> getSuitList();


  /**
   * Removes card from the deck.
   *
   * @param card to remove
   * @throws NoSuchCardException card to remove is not in the deck
   */
  void removeCard(Card card) throws NoSuchCardException;

  /**
   * Reset the deck to the init values.
   */
  void resetDeck();


}
