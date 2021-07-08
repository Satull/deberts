package de.satull.deberts.model.web;

import de.satull.deberts.enums.Owner;
import de.satull.deberts.model.deck.Card;

/**
 * An entity that contains cards to compare and their owners.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public class CardChallenge implements Challenge<Card> {

  private Owner attacker;
  private Owner defender;
  private Card attackerCard;
  private Card defenderCard;

  /**
   * Returns {@code Owner} of the attacker.
   *
   * @return {@code Owner}
   */
  @Override
  public Owner getAttacker() {
    return attacker;
  }

  /**
   * Sets attackers {@code Owner}.
   *
   * @param attacker {@code Owner}
   * @throws IllegalArgumentException attacker and defender can't be the same user
   */
  @Override
  public void setAttacker(Owner attacker) {
    if (!attacker.equals(defender)) {
      this.attacker = attacker;
    } else {
      throw new IllegalArgumentException(
          String.format("Attacker can't be the same owner like defender: %s", attacker));
    }
  }

  /**
   * Returns {@code Owner} of the defender.
   *
   * @return {@code Owner}
   */
  @Override
  public Owner getDefender() {
    return defender;
  }

  /**
   * Sets attackers {@code Owner}.
   *
   * @param defender {@code Owner}
   * @throws IllegalArgumentException attacker and defender can't be the same user
   */
  @Override
  public void setDefender(Owner defender) {
    if (!attacker.equals(defender)) {
      this.defender = defender;
    } else {
      throw new IllegalArgumentException(
          String.format("Defender can't be the same owner like attacker: %s", defender));
    }
  }

  /**
   * Returns attackers value to compare.
   *
   * @return attackers value to compare
   */
  @Override
  public Card getAttackerValue() {
    return attackerCard;
  }

  /**
   * Sets attackers value to compare.
   *
   * @param value attackers value
   * @throws IllegalArgumentException attacker and defender can't use same value to challenge
   */
  @Override
  public void setAttackerValue(Card value) {
    if (!defenderCard.equals(value)) {
      attackerCard = value;
    } else {
      throw new IllegalArgumentException(
          String.format(
              "Attacker can't have same card: %s like defender: %s", value, defenderCard));
    }
  }

  /**
   * Returns defenders value to compare.
   *
   * @return defenders value to compare
   */
  @Override
  public Card getDefenderValue() {
    return defenderCard;
  }

  /**
   * Sets defenders value to compare.
   *
   * @param value defenders value
   * @throws IllegalArgumentException attacker and defender can't use same value to challenge
   */
  @Override
  public void setDefenderValue(Card value) {
    if (!attackerCard.equals(value)) {
      defenderCard = value;
    } else {
      throw new IllegalArgumentException(
          String.format(
              "Defender can't have same card: %s like attacker: %s", value, attackerCard));
    }
  }
}
