package de.satull.deberts.model.web;

import de.satull.deberts.model.enums.FaceValue;
import de.satull.deberts.model.enums.Suit;

/**
 * Represents a single card. Each cards has a suit and face value. Active parameter defines if card
 * is ready to play or was already dealt.
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

  /**
   * Factory method to create a new card entity. Each new card is by default active.
   *
   * @param suit          suit of the card
   * @param cardFaceValue value of the card
   * @return card entity
   */
  public static Card newInstance(Suit suit, FaceValue cardFaceValue) {
    return new Card(suit, cardFaceValue);
  }

  /**
   * Returns true if the card is ready to play.
   *
   * @return true if card is active
   */
  public boolean isActive() {
    return active;
  }

  /**
   * Sets active status for the card.
   *
   * @param active new active status
   */
  public void setActive(boolean active) {
    this.active = active;
  }

  /**
   * Gets a suit of the card
   *
   * @return suit name
   */
  public Suit getSuit() {
    return suit;
  }

  /**
   * Gets a value of the card
   *
   * @return card value
   */
  public FaceValue getValue() {
    return faceValue;
  }
}
