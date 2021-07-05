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
  public Card getAttackerValue() {
    return attackerCard;
  }

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

  @Override
  public Card getDefenderValue() {
    return defenderCard;
  }

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
