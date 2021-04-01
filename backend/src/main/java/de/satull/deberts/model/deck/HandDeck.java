package de.satull.deberts.model.deck;

import de.satull.deberts.model.web.Card;
import de.satull.deberts.model.enums.Owner;
import de.satull.deberts.model.enums.Suit;
import de.satull.deberts.model.enums.SuitDeck;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a cards in the hand of the player.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.5
 * @since 1.0
 */
public class HandDeck extends StockDeck {
  private static final Logger LOG =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
  public final Owner owner;

  /**
   * Creates card deck for one player
   *
   * @param owner owner of the deck
   */
  public HandDeck(Owner owner) {
    resetDeck();
    this.owner = owner;
  }

  /**
   * Add card into the hand deck.
   *
   * @param card to add
   */
  public void addCard(Card card) {
    LOG.debug(
        "HandDeck owner: " + owner + " try to add card: " + card.getSuit() + " " + card.getValue());
    stockDeck.get(card.getSuit()).add(card.getValue());
    stockDeck.get(card.getSuit()).sort(null);
    LOG.debug(
        "HandDeck owner: " + owner + " card added, cards in the Deck: " + stockDeck.toString());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof HandDeck)) {
      return false;
    }
    HandDeck handDeck = (HandDeck) o;
    return owner.equals(handDeck.owner);
  }

  @Override
  public int hashCode() {
    return Objects.hash(owner);
  }

  /** Reset the deck to the init values. */
  public void resetDeck() {
    stockDeck = new LinkedHashMap<Suit, List<SuitDeck>>();
    for (Suit suit : Suit.values()) {
      stockDeck.put(suit, new ArrayList<>());
    }
    suitKeys = new ArrayList<Suit>(stockDeck.keySet());
  }

  @Override
  public String toString() {
    return "HandDeck{"
        + "owner='"
        + owner
        + '\''
        + ", stockDeck="
        + stockDeck
        + ", suitKeys="
        + suitKeys
        + '}';
  }
}
