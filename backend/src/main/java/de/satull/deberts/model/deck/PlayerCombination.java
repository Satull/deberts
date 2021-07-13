package de.satull.deberts.model.deck;

import de.satull.deberts.enums.CombinationName;
import de.satull.deberts.enums.Owner;
import de.satull.deberts.model.deck.enums.FaceValue;
import de.satull.deberts.model.deck.enums.Suit;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Combinations of each player for the beginning of the action phase. Each {@code PlayerCombination}
 * consists of player name and the List of his card combinations.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public class PlayerCombination {
  private final Owner player;
  private final List<Combination> combinationList = new LinkedList<>();

  private PlayerCombination(Owner player) {
    this.player = player;
  }

  /**
   * Factory method to create a new {@code PlayerCombination} entity.
   *
   * @param owner player name
   * @return PlayerCombination object for the player combination
   */
  public static PlayerCombination newInstance(Owner owner) {
    return new PlayerCombination(owner);
  }

  /**
   * Adds new {@code Combination} to the list using the {@code Set<Card>}.
   *
   * @param combination set of cards
   */
  public void addCombination(Set<Card> combination) {
    if (isCombination(combination)) {
      combinationList.add(Combination.newInstance(combination));
    } else {
      throw new IllegalArgumentException(
          "Combination: " + combination + "has wrong number of cards or not the same suit");
    }
  }

  /**
   * Returns the highest {@code Combination} of all combinations of the player.
   *
   * @param trump trump {@code Suit}
   * @return highest {@code Combination}
   */
  public Combination getHighestCombination(Suit trump) {
    Combination result = Combination.newInstance(new HashSet<>());
    for (Combination combination : combinationList) {
      int compareValue = result.compareTo(combination);

      if (compareValue == 0) {
        result = combination.getSuit().equals(trump) ? combination : result;
      } else if (compareValue < 1) {
        result = combination;
      }
    }
    return result;
  }

  /**
   * Returns a list of all {@code Combinations} of a player.
   *
   * @return List of combinations
   */
  public List<Combination> getCombinationList() {
    return combinationList;
  }

  /**
   * Gets player name of the combination.
   *
   * @return player name
   */
  public Owner getPlayer() {
    return player;
  }

  private boolean isCombination(Set<Card> combination) {
    boolean result;
    CombinationName name = CombinationName.getNameByCards(combination.size());
    result = name.equals(CombinationName.EMPTY);
    if (combination.stream().findFirst().isPresent()) {
      Suit suit = combination.stream().findFirst().get().getSuit();
      for (Card card : combination) {
        result = isPossibleBella(name, card.getValue());
        if (!result) {
          break;
        }
        result = suit.equals(card.getSuit());
      }
    }
    return result;
  }

  private boolean isPossibleBella(CombinationName name, FaceValue cardValue) {
    if (name.equals(CombinationName.BELLA)) {
      return cardValue.equals(FaceValue.QUEEN) || cardValue.equals(FaceValue.KING);
    }
    return false;
  }
}
