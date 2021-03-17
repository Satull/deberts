package de.satull.deberts.model;

import java.util.Objects;

/**
 * Represents score points of the player.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.5
 * @since 1.0
 */
public class Points {

  private final Owner owner;
  private final int number;

  /**
   * Creates points for the player
   *
   * @param owner player which will own the points
   * @param number score points
   */
  public Points(Owner owner, int number) {
    this.owner = owner;
    this.number = number;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Points)) {
      return false;
    }
    Points points1 = (Points) o;
    return getNumber() == points1.getNumber() && getOwner().equals(points1.getOwner());
  }

  /**
   * Gets score points
   *
   * @return score points
   */
  public int getNumber() {
    return number;
  }

  /**
   * Gets owner of the points
   *
   * @return points owner
   */
  public Owner getOwner() {
    return owner;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getOwner(), getNumber());
  }

  @Override
  public String toString() {
    return "Points{" + "owner='" + owner + '\'' + ", points=" + number + '}';
  }
}
