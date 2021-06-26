package de.satull.deberts.model.deck.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents all suits of cards.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public enum Suit {
  @JsonProperty("diamonds")
  DIAMONDS(0),
  @JsonProperty("hearts")
  HEARTS(1),
  @JsonProperty("spades")
  SPADES(2),
  @JsonProperty("clubs")
  CLUBS(3);

  private final int value;

  Suit(int value) {
    this.value = value;
  }

  /**
   * Return the int value of the {@code Suit}. {@code Int} value is necessary to determine an array
   * position to store {@code Cards} grouped by {@code Suit}.
   *
   * @return {@code int} value of the {@code Suit}
   */
  public int getValue() {
    return value;
  }
}
