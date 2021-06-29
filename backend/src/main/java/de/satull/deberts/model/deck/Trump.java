package de.satull.deberts.model.deck;

import de.satull.deberts.model.deck.enums.FaceValue;
import de.satull.deberts.model.deck.enums.Suit;

/**
 * An entity that contains a {@code Trump}. {@code Trump} is in the deck package but doesn't belong
 * to the type {@code Deck}.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public class Trump {

  private Card card;
  private boolean nativeTrump;

  private Trump(Card card) {
    this.card = card;
    nativeTrump = true;
  }

  /**
   * Factory method to create a new {@code Trump}.
   *
   * @param card card for the rump
   * @return new {@code Trump} object
   */
  public static Trump newInstance(Card card) {
    return new Trump(card);
  }

  /**
   * Returns a {@code FaceValue} of the trump card.
   *
   * @return {@code Trump} {@code FaceValue}
   */
  public FaceValue getValue() {
    return card.getValue();
  }

  /**
   * Returns a {@code Suit} of the trump card.
   *
   * @return {@code Suit} {@code Suit}
   */
  public Suit getSuit() {
    return card.getSuit();
  }

  /**
   * Returns {@code true} if this {@code Trump} was dealt from the Deck and wan not picked in the
   * trade-phase, false otherwise.
   *
   * @return {@code true} if trump is played {@code Trump} without trade
   */
  public boolean isNative() {
    return nativeTrump;
  }

  /**
   * Switches a trump seven with trump card. It is possible only by native trump.
   *
   * @param card {@code Card} to switch
   * @return old trump {@code Card}
   */
  public Card switchTrump(Card card) {
    if (card.equals(Card.newInstance(this.card.getSuit(), FaceValue.SEVEN)) && isNative()) {
      Card oldTrump = Card.newInstance(this.card);
      this.card = card;
      nativeTrump = false;
      return oldTrump;
    } else {
      throw new IllegalArgumentException(
          "Only trump seven by playing a native trump can switch the current trump card");
    }
  }

  /**
   * Sets new {@code Card} as trump card. This method is used in the trade phase to set a traded
   * trump.
   *
   * @param card new trump card
   */
  public void setTradedTrump(Card card) {
    if (!this.card.getSuit().equals(card.getSuit()) && isNative()) {
      this.card = card;
      nativeTrump = false;
    } else {
      throw new IllegalArgumentException(
          "New trump should have other suit and current trump should be native.");
    }
  }

  @Override
  public int hashCode() {
    int result = card.hashCode();
    result = 31 * result + (nativeTrump ? 1 : 0);
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Trump)) {
      return false;
    }

    Trump trump = (Trump) o;

    if (nativeTrump != trump.nativeTrump) {
      return false;
    }
    return card.equals(trump.card);
  }

  @Override
  public String toString() {
    return "Trump{" + "card=" + card + ", nativeTrump=" + nativeTrump + '}';
  }
}
