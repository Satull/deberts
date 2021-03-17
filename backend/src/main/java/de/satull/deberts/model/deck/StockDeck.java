package de.satull.deberts.model.deck;

import de.satull.deberts.exception.NoSuchCardException;
import de.satull.deberts.model.Card;
import de.satull.deberts.model.Constant;
import de.satull.deberts.model.Suit;
import de.satull.deberts.model.SuitDeck;
import java.lang.invoke.MethodHandles;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract calls with functions which are necessary for each possible deck type.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.5
 * @since 1.0
 */
public abstract class StockDeck {
  private static final Logger LOG =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

  protected LinkedHashMap<Suit, List<SuitDeck>> stockDeck;
  protected List<Suit> suitKeys;

  /**
   * >Returns {@code true} if this deck contains the specified card.</p.
   *
   * @param card card whose presence in this deck is to be tested
   * @return {@code true} if this deck contains the specified card
   */
  public boolean contains(Card card) {
    return stockDeck.get(card.getSuit()).contains(card.getValue());
  }

  /**
   * Counts cards in the deck and returns its number.
   *
   * @return number of cards
   */
  public int countCards() {
    int[] size = {0};
    stockDeck.forEach((suit, cards) -> size[0] += cards.size());
    LOG.debug("cards counted: " + size[0]);
    return size[0];
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof StockDeck)) {
      return false;
    }
    StockDeck stockDeck1 = (StockDeck) o;
    return Objects.equals(stockDeck, stockDeck1.stockDeck)
        && Objects.equals(suitKeys, stockDeck1.suitKeys);
  }

  /**
   * Gets a card from the deck.
   *
   * @param suit of the card
   * @param value of the card
   * @return card founded card
   * @throws NoSuchCardException removed card is not in the deck
   */
  public Card getCard(Suit suit, SuitDeck value) throws NoSuchCardException {
    Card card = new Card(suit, value);
    removeCard(card);
    return card;
  }

  /**
   * Gets the deck information and the actual number of cards.
   *
   * @return information about cards in the deck and its number
   */
  public LinkedHashMap<Constant, Object> getDeck() {
    LinkedHashMap<Constant, Object> result = new LinkedHashMap<>();
    result.put(Constant.CARDS, countCards());
    result.put(Constant.CARD_DECK, stockDeck);
    return result;
  }

  /**
   * Gets a list of actual suits in the deck
   *
   * @return list of all suits in the deck
   */
  public List<Suit> getSuitList() {
    return suitKeys;
  }

  @Override
  public int hashCode() {
    return Objects.hash(stockDeck, suitKeys);
  }

  /**
   * Removes card from the deck.
   *
   * @param card to remove
   * @throws NoSuchCardException removed card is not in the deck
   */
  public void removeCard(Card card) throws NoSuchCardException {
    if (stockDeck.get(card.getSuit()).contains(card.getValue())) {
      stockDeck.get(card.getSuit()).remove(card.getValue());
      if (stockDeck.get(card.getSuit()).isEmpty()) {
        removeSuit(card.getSuit());
      }
    } else {
      throw new NoSuchCardException(
          "Deck does not contain a card: " + card.getSuit() + " " + card.getValue().toString());
    }
  }

  /** Reset the deck to the init values. */
  public abstract void resetDeck();

  @Override
  public String toString() {
    return "StockDeck{" + "stockDeck=" + stockDeck + ", suitKeys=" + suitKeys + '}';
  }

  private void removeSuit(Suit suitKey) {
    stockDeck.remove(suitKey);
    suitKeys.remove(suitKey);
  }
}
