package de.satull.deberts.deck;

import de.satull.deberts.model.Card;
import de.satull.deberts.model.SuitDeck;
import java.lang.invoke.MethodHandles;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class StockDeck {
  private static final Logger LOG =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

  protected LinkedHashMap<String, List<SuitDeck>> stockDeck;
  protected List<String> suitKeys;

  public boolean contains(Card card) {
    return stockDeck.get(card.getSuit()).contains(card.getValue());
  }

  /**
   * <p>count cards in the deck.</p>
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
    return Objects.equals(stockDeck, stockDeck1.stockDeck) &&
        Objects.equals(suitKeys, stockDeck1.suitKeys);
  }

  /**
   * <p>get a card from the deck.</p>
   *
   * @param suit  of the card
   * @param value of the card
   * @return card
   * @throws Exception card does not exist
   */
  public Card getCard(String suit, SuitDeck value) throws Exception {
    Card card = new Card(suit, value);
    removeCard(card);
    return card;
  }

  /**
   * <p>get the deck information and number of cards.</p>
   *
   * @return cards in the deck and number of cards
   */
  public LinkedHashMap<String, Object> getDeck() {
    LinkedHashMap<String, Object> result = new LinkedHashMap<>();
    result.put("cards", countCards());
    result.put("deck", stockDeck);
    return result;
  }

  public List<String> getSuitList() {
    return suitKeys;
  }

  @Override
  public int hashCode() {
    return Objects.hash(stockDeck, suitKeys);
  }

  /**
   * <p>remove card from the deck.</p>
   *
   * @param card to remove
   * @throws Exception remove not existing card
   */
  public void removeCard(Card card) throws Exception {
    if (stockDeck.get(card.getSuit()).contains(card.getValue())) {
      stockDeck.get(card.getSuit()).remove(card.getValue());
      if (stockDeck.get(card.getSuit()).isEmpty()) {
        removeSuit(card.getSuit());
      }
    } else {
      throw new Exception(
          "Deck does not contain a card: " + card.getSuit() + " " + card.getValue().toString());
    }
  }

  public abstract void resetDeck();

  @Override
  public String toString() {
    return "StockDeck{" +
        "stockDeck=" + stockDeck +
        ", suitKeys=" + suitKeys +
        '}';
  }

  private void removeSuit(String suitKey) {
    stockDeck.remove(suitKey);
    suitKeys.remove(suitKey);

  }

}