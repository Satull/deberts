package de.satull.deberts.model.deck;

import de.satull.deberts.enums.CombinationName;
import de.satull.deberts.model.deck.enums.FaceValue;
import de.satull.deberts.model.deck.enums.Suit;
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

  private Combination(Set<Card> combination) {
    this.combination = new TreeSet<>(combination);
  }

  /**
   * Factory method to create a new {@code Combination} entity.
   *
   * @param combination {@code Set<Card>} for combination
   * @return Combination new combination entity
   */
  public static Combination newInstance(Set<Card> combination) {
    return new Combination(combination);
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

  /**
   * Returns a {@code Suit} value of the {@code Combination}.
   *
   * @return {@code Suit} value
   */
  public Suit getSuit() {
    Card combinationCard = combination.first();
    return combinationCard != null ? combinationCard.getSuit() : Suit.NO_SUIT;
  }

  @Override
  public int compareTo(Combination combination) {
    int comparedName = compareName(combination.getName());
    if (comparedName != 0) {
      return comparedName;
    }

    return compareHeight(combination.getHeight());
  }

  private int compareName(CombinationName combinationName) {
    return this.getName().compareTo(combinationName);
  }

  private int compareHeight(FaceValue height) {
    return this.getHeight().compareTo(height);
  }
}
