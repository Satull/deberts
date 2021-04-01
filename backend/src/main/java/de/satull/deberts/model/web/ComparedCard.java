package de.satull.deberts.model.web;

import de.satull.deberts.model.enums.Owner;
import java.util.Objects;

/**
 * Represents a compared card which contains a card and its owner.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.5
 * @since 1.0
 */
public class ComparedCard {

  private final Owner owner;
  private final Card card;

  /**
   * Creates ComparedCard card and owner
   *
   * @param owner owner of the card
   * @param card card which will be compared
   */
  public ComparedCard(Owner owner, Card card) {
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

  /**
   * Gets card
   *
   * @return card entity
   */
  public Card getCard() {
    return card;
  }

  /**
   * Gets owner name
   *
   * @return owner name
   */
  public Owner getOwner() {
    return owner;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getOwner(), getCard());
  }

  @Override
  public String toString() {
    return "ComparedCard{" + "owner='" + owner + '\'' + ", card=" + card + '}';
  }
}
