package de.satull.deberts.model;

import java.util.Objects;

public class Comparator {
  private ComparedCard attacker;
  private ComparedCard defender;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Comparator)) {
      return false;
    }
    Comparator that = (Comparator) o;
    return Objects.equals(getAttacker(), that.getAttacker()) &&
        Objects.equals(getDefender(), that.getDefender());
  }

  public ComparedCard getAttacker() {
    return attacker;
  }

  public void setAttacker(ComparedCard attacker) {
    this.attacker = attacker;
  }

  public ComparedCard getDefender() {
    return defender;
  }

  public void setDefender(ComparedCard defender) {
    this.defender = defender;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getAttacker(), getDefender());
  }

  @Override
  public String toString() {
    return "Comparator{" +
        "attacker=" + attacker +
        ", defender=" + defender +
        '}';
  }
}