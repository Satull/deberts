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
public class CardChallenge extends AbstractChallenge<Card> {

  @Override
  public Owner getWinner(Suit trump) {
    if (attackerValue.getSuit().equals(defenderValue.getSuit())) {
      int compareResult =
          Integer.compare(attackerValue.getPoints(trump), defenderValue.getPoints(trump));
      if (compareResult == 0) {
        compareResult = attackerValue.getValue().compareTo(defenderValue.getValue());
      }
      return compareResult > 0 ? attacker : defender;
    }
    return defenderValue.getSuit().equals(trump) ? defender : attacker;
  }

  @Override
  public int getPoints(Suit trump) {
    return attackerValue.getPoints(trump) + defenderValue.getPoints(trump);
  }
}
