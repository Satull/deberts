package de.satull.deberts.model;

import java.util.Objects;

/**
 * Represents trumps suit with the owner (person who picked a trump).
 *
 * @author Ievgenii Izrailtenko
 * @version 1.5
 * @since 1.0
 */
public class Trump {

  private Suit suit;
  private Owner owner;

  /**
   * Creates trump with the information about the player
   *
   * @param suit suit of the trump in the round
   * @param owner player who chose the trump
   */
  public Trump(Suit suit, Owner owner) {
    this.suit = suit;
    this.owner = owner;
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
    return Objects.equals(getSuit(), trump.getSuit())
        && Objects.equals(getOwner(), trump.getOwner());
  }

  /**
   * Gets player chose the trump
   *
   * @return player name
   */
  public Owner getOwner() {
    return owner;
  }

  /**
   * Sets player chose the trump
   *
   * @param owner player name
   */
  public void setOwner(Owner owner) {
    this.owner = owner;
  }

  /**
   * Gets an actual suit of the trump
   *
   * @return suit of the trump
   */
  public Suit getSuit() {
    return suit;
  }

  /**
   * Sets suit for the trump
   *
   * @param suit trump suit
   */
  public void setSuit(Suit suit) {
    this.suit = suit;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getSuit(), getOwner());
  }

  @Override
  public String toString() {
    return "Trump{" + "suit='" + suit + '\'' + ", owner='" + owner + '\'' + '}';
  }
}
