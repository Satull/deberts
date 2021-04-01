package de.satull.deberts.model.deck;

import de.satull.deberts.model.web.Card;
import de.satull.deberts.model.enums.Suit;
import de.satull.deberts.model.enums.SuitDeck;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * Represents a trump deck in the round.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.5
 * @since 1.0
 */
public class TrumpDeck extends StockDeck {

  private boolean nativeTrump;

  /** Creates empty trump deck */
  public TrumpDeck() {
    resetDeck();
  }

  /** Creates trump deck with trump card */
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

  /**
   * Gets the only one card from the trump deck.
   *
   * @return trump card
   */
  public Card getCard() {
    return new Card(suitKeys.get(0), stockDeck.get(suitKeys.get(0)).get(0));
  }

  /**
   * Gets the only one suit from the trump deck.
   *
   * @return {@code true} if the trump is the native one
   */
  public Suit getSuit() {
    return stockDeck.isEmpty() ? null : getSuitList().get(0);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), isNativeTrump());
  }

  /**
   * Checks if the actual trump is the native one or was chosed from the player
   *
   * @return
   */
  public boolean isNativeTrump() {
    return nativeTrump;
  }

  /** Reset the deck to the init values. */
  public void resetDeck() {
    stockDeck = new LinkedHashMap<Suit, java.util.List<SuitDeck>>();
    suitKeys = new ArrayList<Suit>(stockDeck.keySet());
    nativeTrump = true;
  }

  /**
   * set trump into the deck.
   *
   * @param card trump to set
   */
  public void setTrump(Card card) {
    if (stockDeck.isEmpty()) {
      stockDeck.put(card.getSuit(), new ArrayList<>());
      stockDeck.get(card.getSuit()).add(card.getValue());
      suitKeys.add(card.getSuit());
    }
  }

  /**
   * Switches a trump seven with the native trump
   *
   * @param card which should switch the trump
   */
  public void switchTrump(Card card) {
    if (!stockDeck.isEmpty()
        && card.getSuit().equals(getSuit())
        && card.getValue().equals(SuitDeck.SEVEN)) {
      resetDeck();
      setTrump(card);
    }
  }

  @Override
  public String toString() {
    return "TrumpDeck{"
        + "stockDeck="
        + stockDeck
        + ", suitKeys="
        + suitKeys
        + ", nativeTrump="
        + nativeTrump
        + '}';
  }
}
