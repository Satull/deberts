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

  private String suit;
  private String owner;

  /**
   * <p>Creates trump with the information about the player</p>
   *
   * @param suit  suit of the trump in the round
   * @param owner player who chose the trump
   */
  public Trump(String suit, String owner) {
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
    return Objects.equals(getSuit(), trump.getSuit()) &&
        Objects.equals(getOwner(), trump.getOwner());
  }

  /**
   * <p>Gets player chose the trump</p>
   *
   * @return player name
   */
  public String getOwner() {
    return owner;
  }

  /**
   * <p>Sets player chose the trump</p>
   *
   * @param owner player name
   */
  public void setOwner(String owner) {
    this.owner = owner;
  }

  /**
   * <p>Gets an actual suit of the trump</p>
   *
   * @return suit of the trump
   */
  public String getSuit() {
    return suit;
  }

  /**
   * <p>Sets suit for the trump</p>
   *
   * @param suit trump suit
   */
  public void setSuit(String suit) {
    this.suit = suit;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getSuit(), getOwner());
  }

  @Override
  public String toString() {
    return "Trump{" +
        "suit='" + suit + '\'' +
        ", owner='" + owner + '\'' +
        '}';
  }
}
