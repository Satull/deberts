package de.satull.deberts.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import java.util.Optional;

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
   * Returns optional suit enum using its int value. Int value is used to determine array position
   * by storing cards in the Deck.class
   *
   * @param value int value of the suit
   * @return optional suit
   */
  public static Optional<Suit> valueOf(int value) {
    return Arrays.stream(values()).filter(suit -> suit.value == value).findFirst();
  }

  /**
   * Return the int value of Suit. Int value is necessary to determine an array position to store
   * cards grouped by suit.
   *
   * @return int value of the suit
   */
  public int getValue() {
    return value;
  }

}
