package de.satull.deberts.model.deck;

import de.satull.deberts.model.deck.enums.FaceValue;
import de.satull.deberts.model.deck.enums.Suit;

/**
 * Represents a single card. Each card has a {@code Suit} and {@code FaceValue}. Active parameter
 * defines if {@code Card} is ready to play or was already dealt.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public class Card {

  private final Suit suit;
  private final FaceValue faceValue;
  private boolean active;

  private Card(Suit suit, FaceValue cardFaceValue) {
    this.suit = suit;
    this.faceValue = cardFaceValue;
    this.active = true;
  }

  private Card(Card card) {
    this.suit = card.getSuit();
    this.faceValue = card.getValue();
    this.active = card.isActive();
  }

  /**
   * Factory method to create a new {@code Card} entity. Each new {@code Card} is by default
   * active.
   *
   * @param suit      {@code Suit} of the {@code Card}
   * @param faceValue {@code faceValue} of the {@code Card}
   * @return new {@code Card} object
   */
  public static Card newInstance(Suit suit, FaceValue faceValue) {
    return new Card(suit, faceValue);
  }

  /**
   * Factory method to copy a {@code Card}.
   *
   * @param card a {@code Card} to copy
   * @return a new {@code Card} object
   */
  public static Card newInstance(Card card) {
    return new Card(card);
  }

  /**
   * Returns {@code true} if the {@code Card} is ready to play, {@code false} otherwise.
   *
   * @return {@code true} if {@code Card} is active, {@code false} otherwise
   */
  public boolean isActive() {
    return active;
  }

  /**
   * Sets active status for the {@code Card}.
   *
   * @param active new active status
   */
  public void setActive(boolean active) {
    this.active = active;
  }

  /**
   * Gets a {@code Suit} of the {@code Card}.
   *
   * @return {@code Suit} name
   */
  public Suit getSuit() {
    return suit;
  }

  /**
   * Gets a {@code Suit int} value of the {@code Card}.
   *
   * @return {@code Suit} value
   */
  public int getSuitValue() {
    return suit.getValue();
  }

  /**
   * Gets a {@code faceValue} of the {@code Card}.
   *
   * @return {@code Card faceValue}
   */
  public FaceValue getValue() {
    return faceValue;
  }

  /**
   * Returns {@code true} if this {@code Card} has the same {@code Suit}, {@code false} otherwise.
   *
   * @param suit {@code Suit} to check
   * @return {@code true} if the same {@code Suit}, {@code false} otherwise
   */
  public boolean hasSuit(Suit suit) {
    return this.suit.equals(suit);
  }

  /**
   * Returns {@code true} if this {@code Card} has the same {@code FaceValue}, {@code false}
   * otherwise.
   *
   * @param faceValue {@code FaceValue} to check
   * @return {@code true} if the same {@code FaceValue}, {@code false} otherwise
   */
  public boolean hasFaceValue(FaceValue faceValue) {
    return this.faceValue.equals(faceValue);
  }
}
