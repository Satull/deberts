package de.satull.deberts.model;

import java.util.Objects;

/**
 * Represents a single card.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.5
 * @since 1.0
 */
public class Card {
  private final String suit;
  private final SuitDeck value;

  /**
   * <p>Creates a card entity</p>
   *
   * @param suit      suit of the card
   * @param cardValue value of the card
   */
  public Card(String suit, SuitDeck cardValue) {
    this.suit = suit;
    this.value = cardValue;
  }

  @Override
  public boolean equals(Object o) {
    try {
      Card card = (Card) o;
      return this.suit.equals(card.suit) && this.value == card.value;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Gets a suit of the card
   *
   * @return suit name
   */
  public String getSuit() {
    return suit;
  }


  /**
   * Gets a value of the card
   *
   * @return card value
   */
  public SuitDeck getValue() {
    return value;
  }

  @Override
  public int hashCode() {
    return Objects.hash(suit, value);
  }

  @Override
  public String toString() {
    return "Card{" +
        "suit='" + suit + '\'' +
        ", value=" + value +
        '}';
  }

}