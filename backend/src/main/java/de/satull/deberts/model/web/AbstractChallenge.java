package de.satull.deberts.model.web;

import de.satull.deberts.enums.Owner;

/**
 * An object that contains values to compare in each game turn. Each challenge contains an attacker
 * and defender with names of the players and values to compare. Abstract class contains
 * implementation to manage attacker and defender of the challenge.
 *
 * @param <T> value to challenge
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractChallenge<T> implements Challenge<T> {
  Owner attacker;
  Owner defender;
  T attackerValue;
  T defenderValue;

  @Override
  public Owner getAttacker() {
    return attacker;
  }

  @Override
  public void setAttacker(Owner attacker) {
    if (!attacker.equals(defender)) {
      this.attacker = attacker;
    } else {
      throw new IllegalArgumentException(
          String.format("Attacker can't be the same owner like defender: %s", attacker));
    }
  }

  @Override
  public Owner getDefender() {
    return defender;
  }

  @Override
  public void setDefender(Owner defender) {
    if (!attacker.equals(defender)) {
      this.defender = defender;
    } else {
      throw new IllegalArgumentException(
          String.format("Defender can't be the same owner like attacker: %s", defender));
    }
  }

  @Override
  public T getAttackerValue() {
    return attackerValue;
  }

  @Override
  public void setAttackerValue(T value) {
    if (!defenderValue.equals(value)) {
      attackerValue = value;
    } else {
      throw new IllegalArgumentException(
          String.format(
              "Attacker can't have same value: %s like defender: %s", value, defenderValue));
    }
  }

  @Override
  public T getDefenderValue() {
    return defenderValue;
  }

  @Override
  public void setDefenderValue(T value) {
    if (!attackerValue.equals(value)) {
      defenderValue = value;
    } else {
      throw new IllegalArgumentException(
          String.format(
              "Defender can't have same value: %s like attacker: %s", value, attackerValue));
    }
  }
}
