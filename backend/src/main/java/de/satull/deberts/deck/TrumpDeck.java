package de.satull.deberts.deck;

import de.satull.deberts.model.Card;
import de.satull.deberts.model.SuitDeck;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Objects;

public class TrumpDeck extends StockDeck {

  private boolean nativeTrump;

  public TrumpDeck() {
    resetDeck();
  }

  public TrumpDeck(Card card) {
    resetDeck();
    setTrump(card);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof TrumpDeck)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    TrumpDeck trumpDeck = (TrumpDeck) o;
    return isNativeTrump() == trumpDeck.isNativeTrump();
  }

  public Card getCard() {
    return new Card(suitKeys.get(0), stockDeck.get(suitKeys.get(0)).get(0));
  }

  public String getSuit() {
    return stockDeck.isEmpty() ? null : getSuitList().get(0);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), isNativeTrump());
  }

  public boolean isNativeTrump() {
    return nativeTrump;
  }

  public void resetDeck() {
    stockDeck = new LinkedHashMap<>();
    suitKeys = new ArrayList<>(stockDeck.keySet());
    nativeTrump = true;
  }

  /**
   * <p>set trump into the deck.</p>
   *
   * @param card trump
   */
  public void setTrump(Card card) {
    if (stockDeck.isEmpty()) {
      stockDeck.put(card.getSuit(), new ArrayList<>());
      stockDeck.get(card.getSuit()).add(card.getValue());
      suitKeys.add(card.getSuit());
    }
  }

  public void switchTrump(Card card) {
    if (!stockDeck.isEmpty() && card.getSuit().equals(getSuit()) &&
        card.getValue().equals(SuitDeck.SEVEN)) {
      resetDeck();
      setTrump(card);
    }
  }

  @Override
  public String toString() {
    return "TrumpDeck{" +
        "stockDeck=" + stockDeck +
        ", suitKeys=" + suitKeys +
        ", nativeTrump=" + nativeTrump +
        '}';
  }

}