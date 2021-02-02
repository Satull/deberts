package de.satull.deberts.model.deck;

import de.satull.deberts.model.Card;
import de.satull.deberts.model.SuitDeck;
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

  /**
   * <p>Creates empty trump deck</p>
   */
  public TrumpDeck() {
    resetDeck();
  }

  /**
   * <p>Creates trump deck with trump card</p>
   */
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
   * <p>Gets the only one card from the trump deck.</p>
   *
   * @return trump card
   */
  public Card getCard() {
    return new Card(suitKeys.get(0), stockDeck.get(suitKeys.get(0)).get(0));
  }

  /**
   * <p>Gets the only one suit from the trump deck.</p>
   *
   * @return {@code true} if the trump is the native one
   */
  public String getSuit() {
    return stockDeck.isEmpty() ? null : getSuitList().get(0);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), isNativeTrump());
  }

  /**
   * <p>Checks if the actual trump is the native one or was chosed from the player</p>
   *
   * @return
   */
  public boolean isNativeTrump() {
    return nativeTrump;
  }

  /**
   * <p>Reset the deck to the init values.</p>
   */
  public void resetDeck() {
    stockDeck = new LinkedHashMap<>();
    suitKeys = new ArrayList<>(stockDeck.keySet());
    nativeTrump = true;
  }

  /**
   * <p>set trump into the deck.</p>
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
   * <p>Switches a trump seven with the native trump</p>
   *
   * @param card which should switch the trump
   */
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