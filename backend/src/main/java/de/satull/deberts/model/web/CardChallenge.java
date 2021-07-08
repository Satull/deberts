package de.satull.deberts.model.web;

import de.satull.deberts.enums.Owner;
import de.satull.deberts.model.deck.Card;
import de.satull.deberts.model.deck.enums.Suit;

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

  /**
   * Returns {@code Owner} enum of the winner. To decide a winner trump information is needed.
   *
   * @param trump trump {@code Suit}
   * @return winner
   */
  @Override
  public Owner getWinner(Suit trump) {
    if (attackerCard.getSuit().equals(defenderCard.getSuit())) {
      int compareResult =
          Integer.compare(attackerCard.getPoints(trump), defenderCard.getPoints(trump));
      if (compareResult == 0) {
        compareResult = attackerCard.getValue().compareTo(defenderCard.getValue());
      }
      return compareResult > 0 ? attacker : defender;
    }
    return defenderCard.getSuit().equals(trump) ? defender : attacker;
  }

  /**
   * Returns points of the decided challenge. To decide a winner trump information is needed.
   *
   * @param trump trump {@code Suit}
   * @return winner points
   */
  @Override
  public int getPoints(Suit trump) {
    return attackerCard.getPoints(trump) + defenderCard.getPoints(trump);
  }
}
