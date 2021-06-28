package de.satull.deberts.model.deck;

import de.satull.deberts.model.deck.enums.Owner;
import de.satull.deberts.model.deck.enums.Suit;

/**
 * An entity that contains and manages cards. Cards are grouped by {@code SuitPack}. All suits are
 * inside of {@code ArrayList}.
 *
 * <p>Each {@code HandDeck} has limited size.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public class HandDeck extends Deck {

  private final int maxCardsNumber;
  private int containedCards = 0;

  /**
   * Creates new {@code CardDeck} entity. During initialisation creates empty {@code SuitPack}s to
   * collect {@code Card}s Visibility of constructor is package private in order to create single
   * creation point in {@code DeckFactory}.
   *
   * @param owner {@code Owner} of this {@code HandDeck}
   * @param maxCardsNumber how much {@code Cards} could contain the {@code HandDeck}
   */
  HandDeck(Owner owner, int maxCardsNumber) {
    this.owner = owner;
    for (Suit suit : Suit.values()) {
      suitList.add(suit.getValue(), SuitPack.newInstance());
    }
    this.maxCardsNumber = maxCardsNumber;
    LOG.debug("Max cards number: {}", maxCardsNumber);
  }

  /**
   * Adds a {@code Card} to the {@code Deck}.
   *
   * <p>{@code HandDeck} con not contain more {@code Card}s then {@code maxCardsNumber}
   *
   * @param card a {@code Card} to add
   */
  @Override
  public void addCard(Card card) {
    if (containedCards < maxCardsNumber) {
      SuitPack suitCardList = suitList.get(card.getSuitValue());
      suitCardList.addCard(card);
      containedCards++;
    } else {
      throw new IllegalArgumentException(
          "HandDeck can not contain more then" + maxCardsNumber + "Cards");
    }
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + maxCardsNumber;
    result = 31 * result + containedCards;
    result = 31 * result + owner.hashCode();
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof HandDeck)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }

    var handDeck = (HandDeck) o;

    if (maxCardsNumber != handDeck.maxCardsNumber) {
      return false;
    }
    return containedCards == handDeck.containedCards && owner == handDeck.getOwner();
  }

  @Override
  public String toString() {
    return "HandDeck{"
        + "suitList="
        + suitList
        + ", fullSuits="
        + fullSuits
        + ", owner="
        + owner
        + ", maxCardsNumber="
        + maxCardsNumber
        + ", containedCards="
        + containedCards
        + '}';
  }
}
