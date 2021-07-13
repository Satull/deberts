package de.satull.deberts.model.web;

import de.satull.deberts.enums.Owner;
import de.satull.deberts.model.deck.enums.Suit;

/**
 * An object that contains values to compare in each game turn. Each challenge contains an attacker
 * and defender with names of the players and values to compare.
 *
 * @param <T> value to challenge
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public interface Challenge<T> {

  /**
   * Returns {@code Owner} of the attacker.
   *
   * @return {@code Owner}
   */
  Owner getAttacker();

  /**
   * Sets attackers {@code Owner}.
   *
   * @param attacker {@code Owner}
   * @throws IllegalArgumentException attacker and defender can't be the same user
   */
  void setAttacker(Owner attacker);

  /**
   * Returns {@code Owner} of the defender.
   *
   * @return {@code Owner}
   */
  Owner getDefender();

  /**
   * Sets attackers {@code Owner}.
   *
   * @param defender {@code Owner}
   * @throws IllegalArgumentException attacker and defender can't be the same user
   */
  void setDefender(Owner defender);

  /**
   * Returns attackers value to compare.
   *
   * @return attackers value to compare
   */
  T getAttackerValue();

  /**
   * Sets attackers value to compare.
   *
   * @param value attackers value
   * @throws IllegalArgumentException attacker and defender can't use same value to challenge
   */
  void setAttackerValue(T value);

  /**
   * Returns defenders value to compare.
   *
   * @return defenders value to compare
   */
  T getDefenderValue();

  /**
   * Sets defenders value to compare.
   *
   * @param value defenders value
   * @throws IllegalArgumentException attacker and defender can't use same value to challenge
   */
  void setDefenderValue(T value);

  /**
   * Returns {@code Owner} enum of the winner. To decide a winner trump information is needed.
   *
   * @param trump trump {@code Suit}
   * @return winner
   */
  Owner getWinner(Suit trump);

  /**
   * Returns points of the decided challenge. To decide a winner trump information is needed.
   *
   * @param trump trump {@code Suit}
   * @return winner points
   */
  int getPoints(Suit trump);
}
