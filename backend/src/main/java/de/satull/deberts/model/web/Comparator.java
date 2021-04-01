package de.satull.deberts.model.web;

import java.util.Objects;

/**
 * Represents a comparator DTO which contains two cards to compare.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.5
 * @since 1.0
 */
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

  /**
   * <p>Gets a card from attacker</p>
   *
   * @return compared card from attacker
   */
  public ComparedCard getAttacker() {
    return attacker;
  }

  /**
   * <p>Sets a card from attacker</p>
   *
   * @param attacker compared card from attacker
   */
  public void setAttacker(ComparedCard attacker) {
    this.attacker = attacker;
  }

  /**
   * <p>Gets a card from defender</p>
   *
   * @return compared card from defender
   */
  public ComparedCard getDefender() {
    return defender;
  }

  /**
   * <p>Sets a card from defender</p>
   *
   * @param defender compared card from defender
   */
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