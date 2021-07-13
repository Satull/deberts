package de.satull.deberts.model.web;

import de.satull.deberts.enums.Owner;
import de.satull.deberts.model.deck.Combination;
import de.satull.deberts.model.deck.PlayerCombination;
import de.satull.deberts.model.deck.enums.Suit;

/**
 * An entity that contains decks to compare and their owners.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public class CombinationChallenge extends AbstractChallenge<PlayerCombination> {

  /**
   * Returns {@code Owner} enum of the winner. To decide a winner trump information is needed.
   *
   * @param trump trump {@code Suit}
   * @return winner
   */
  @Override
  public Owner getWinner(Suit trump) {
    int compareResult =
        attackerValue
            .getHighestCombination(trump)
            .compareTo(defenderValue.getHighestCombination(trump));
    return compareResult >= 0 ? attacker : defender;
  }

  /**
   * Returns points of the decided challenge. To decide a winner trump information is needed.
   *
   * @param trump trump {@code Suit}
   * @return winner points
   */
  @Override
  public int getPoints(Suit trump) {
    return countPlayerPoints(attackerValue, trump) + countPlayerPoints(defenderValue, trump);
  }

  private int countPlayerPoints(PlayerCombination playerCombination, Suit trump) {
    int points = 0;
    for (Combination combination : playerCombination.getCombinationList()) {
      boolean isTrump = combination.getSuit().equals(trump);
      points += combination.getName().getPoints(isTrump);
    }
    return points;
  }
}
