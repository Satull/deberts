package de.satull.deberts.model.deck;

import de.satull.deberts.enums.CombinationName;
import de.satull.deberts.model.deck.enums.FaceValue;
import java.util.Set;
import java.util.TreeSet;

/**
 * Combination entity for {@code PlayerCombination}.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public class Combination implements Comparable<Combination> {
  private final TreeSet<Card> combination;
  private final boolean trump;

  private Combination(Set<Card> combination, boolean trump) {
    this.combination = new TreeSet<>(combination);
    this.trump = trump;
  }

  /**
   * Factory method to create a new {@code Combination} entity.
   *
   * @param combination {@code Set<Card>} for combination
   * @param trump declares if the combination is from the trump suit
   * @return Combination new combination entity
   */
  public static Combination newInstance(Set<Card> combination, boolean trump) {
    return new Combination(combination, trump);
  }

  /**
   * Returns {@code CombinationName} of stored card combination.
   *
   * @return {@code CombinationName} of cards
   */
  public CombinationName getName() {
    return CombinationName.getNameByCards(combination.size());
  }

  /**
   * Returns {@code FaceValue} height of stored card combination.
   *
   * @return {@code FaceValue} height
   */
  public FaceValue getHeight() {
    return combination.last().getValue();
  }

  @Override
  public int compareTo(Combination combination) {
    int comparedName = compareName(combination.getName());
    if (comparedName != 0) {
      return comparedName;
    }

    int comparedHeight = compareHeight(combination.getHeight());
    if (comparedHeight != 0) {
      return comparedHeight;
    }

    return compareTrump(combination.trump);
  }

  private int compareName(CombinationName combinationName) {
    return this.getName().compareTo(combinationName);
  }

  private int compareHeight(FaceValue height) {
    return this.getHeight().compareTo(height);
  }

  private int compareTrump(boolean trump) {
    if (this.trump == trump) {
      return 0;
    } else if (this.trump) {
      return 1;
    } else {
      return -1;
    }
  }
}
