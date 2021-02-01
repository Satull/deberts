package de.satull.deberts.model;

import java.util.Objects;

public class Points {

  private final String owner;
  private final int points;

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

  public String getOwner() {
    return owner;
  }

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