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

  private final String owner;
  private final int points;

  /**
   * <p>Creates points for the player</p>
   *
   * @param owner  player which will own the points
   * @param points score points
   */
  public Points(String owner, int points) {
    this.owner = owner;
    this.points = points;
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
    return getPoints() == points1.getPoints() && getOwner().equals(points1.getOwner());
  }

  /**
   * <p>Gets owner of the points</p>
   *
   * @return points owner
   */
  public String getOwner() {
    return owner;
  }

  /**
   * <p>Gets score points</p>
   *
   * @return score points
   */

  public int getPoints() {
    return points;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getOwner(), getPoints());
  }

  @Override
  public String toString() {
    return "Points{" +
        "owner='" + owner + '\'' +
        ", points=" + points +
        '}';
  }
}