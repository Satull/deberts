package de.satull.deberts.model.deck;


/**
 * Cards grouped by suit. The user of this interface can manage stored cards inside of this object.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @see CardDeck
 * @since 1.0
 */
public interface Deck {

  /**
   * Adds a {@code Card} to the {@code Deck}.
   *
   * @param card a {@code Card} to add
   */
  void addCard(Card card);

  /**
   * Returns {@code true} if this {@code Deck} contains the specified {@code Card}, {@code false}
   * otherwise.
   *
   * @param card a {@code Card} to search for
   * @return {@code true} if this {@code Deck} contains card, {@code false} otherwise
   */
  boolean contains(Card card);

  /**
   * Returns the number of cards inside this {@code Deck}.
   *
   * @return a number of cards
   */
  int countCards();

  /**
   * Returns the {@code Card} from the {@code Deck} using {@code Suit} and {@code Value}.
   *
   * @param suit      a {@code Suit} of the {@code Card}
   * @param faceValue a {@code faceValue} of the {@code Card}
   * @return card founded {@code Card}
   */
  Card dealCard(Suit suit, FaceValue faceValue);

  /**
   * Returns a random {@code Card} from the {@code Deck}.
   *
   * @return random {@code Card}
   */
  Card dealRandomCard();

  /**
   * Returns a random {@code Card} from the specified suit of the {@code Deck}.
   *
   * @param suit {@code Suit} to get a {@code Card}
   * @return a random {@code Card} from the {@code Suit}
   */
  Card dealRandomCardFromSuit(Suit suit);

  /**
   * Resets a {@code Deck} to its init values.
   */
  void resetDeck();


}
