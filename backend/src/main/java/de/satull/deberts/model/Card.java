package de.satull.deberts.model;

import java.util.Objects;

public class Card {
  private final String suit;
  private final SuitDeck value;

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

  public String getSuit() {
    return suit;
  }

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