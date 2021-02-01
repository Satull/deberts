package de.satull.deberts.model;

import java.util.Objects;

public class ComparedCard {

  private final String owner;
  private final Card card;

  public ComparedCard(String owner, Card card) {
    this.owner = owner;
    this.card = card;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ComparedCard)) {
      return false;
    }
    ComparedCard that = (ComparedCard) o;
    return getOwner().equals(that.getOwner()) && getCard().equals(that.getCard());
  }

  public Card getCard() {
    return card;
  }

  public String getOwner() {
    return owner;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getOwner(), getCard());
  }

  @Override
  public String toString() {
    return "ComparedCard{" +
        "owner='" + owner + '\'' +
        ", card=" + card +
        '}';
  }
}
