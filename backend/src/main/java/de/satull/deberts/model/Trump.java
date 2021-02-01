package de.satull.deberts.model;

import java.util.Objects;

public class Trump {

  private String suit;
  private String owner;

  public Trump(String suit, String owner) {
    this.suit = suit;
    this.owner = owner;
  }

  @Override
  public String toString() {
    return "Trump{" +
        "suit='" + suit + '\'' +
        ", owner='" + owner + '\'' +
        '}';
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

  public String getSuit() {
    return suit;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public void setSuit(String suit) {
    this.suit = suit;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getSuit(), getOwner());
  }
}
