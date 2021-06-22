package de.satull.deberts.model.deck;

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

  private Card(Card card) {
    this.suit = card.getSuit();
    this.faceValue = card.getValue();
    this.active = card.isActive();
  }

  /**
   * Factory method to create a new card entity. Each new card is by default active.
   *
   * @param suit          suit of the card
   * @param cardFaceValue value of the card
   * @return new card object
   */
  public static Card newInstance(Suit suit, FaceValue cardFaceValue) {
    return new Card(suit, cardFaceValue);
  }

  /**
   * Factory method to copy the card entity.
   *
   * @param card card to copy
   * @return new card object
   */
  public static Card newInstance(Card card) {
    return new Card(card);
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
   * Gets a suit int value of the card
   *
   * @return suit value
   */
  public int getSuitValue() {
    return suit.getValue();
  }

  /**
   * Gets a value of the card
   *
   * @return card value
   */
  public FaceValue getValue() {
    return faceValue;
  }

  /**
   * Returns true if this card has the same suit, false otherwise.
   *
   * @param suit suit to check
   * @return true if the same suit, false otherwise
   */
  public boolean hasSuit(Suit suit) {
    return this.suit.equals(suit);
  }

  /**
   * Returns true if this card has the same face value, false otherwise.
   *
   * @param faceValue faceValue to check
   * @return true if the same faceValue, false otherwise
   */
  public boolean hasFaceValue(FaceValue faceValue) {
    return this.faceValue.equals(faceValue);
  }
}
