package de.satull.deberts.model.deck;


import de.satull.deberts.model.deck.enums.FaceValue;
import de.satull.deberts.model.deck.enums.Owner;
import de.satull.deberts.model.deck.enums.Suit;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An abstract class to contain and manage cards. Cards are grouped by {@code SuitPack}. All suits
 * are inside of {@code ArrayList}.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public abstract class Deck {

  static final Logger LOG =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
  final ArrayList<SuitPack> suitList = new ArrayList<>(4);
  final LinkedList<Suit> fullSuits = new LinkedList<>();
  Owner owner;


  /**
   * Adds a {@code Card} to the {@code Deck}.
   *
   * @param card a {@code Card} to add
   */
  public abstract void addCard(Card card);

  /**
   * Returns {@code true} if this {@code Deck} contains the specified {@code Card}, {@code false}
   * otherwise.
   *
   * @param card a {@code Card} to search for
   * @return {@code true} if this {@code Deck} contains card, {@code false} otherwise
   */
  public boolean contains(Card card) {
    var suitPack = suitList.get(card.getSuitValue());
    return suitPack.contains(card.getValue());
  }

  /**
   * Returns the number of cards inside this {@code Deck}.
   *
   * @return a number of cards
   */
  public int countCards() {
    var activeCards = 0;
    for (SuitPack suitPack : suitList) {
      activeCards += suitPack.getActiveCards();
    }
    return activeCards;
  }

  /**
   * Returns the {@code Card} from the {@code Deck} using {@code Suit} and {@code Value}.
   *
   * @param suit      a {@code Suit} of the {@code Card}
   * @param faceValue a {@code faceValue} of the {@code Card}
   * @return card founded {@code Card}
   */
  public Card dealCard(Suit suit, FaceValue faceValue) {
    var suitPack = suitList.get(suit.getValue());
    var dealtCard = suitPack.dealCard(faceValue);
    if (suitPack.isEmpty()) {
      fullSuits.remove(suit);
    }
    return dealtCard;
  }

  /**
   * Returns a random {@code Card} from the {@code Deck}.
   *
   * @return a random {@code Card}
   */
  public Card dealRandomCard() {
    var suitIndex = new Random().nextInt(fullSuits.size());
    var suitPack = suitList.get(suitIndex);
    return suitPack.dealRandomCard();
  }

  /**
   * Returns a random {@code Card} from the specified suit of the {@code Deck}.
   *
   * @param suit {@code Suit} to get a {@code Card}
   * @return a random {@code Card} from the {@code Suit}
   */
  public Card dealRandomCardFromSuit(Suit suit) {
    return suitList.get(suit.getValue()).dealRandomCard();
  }

  /**
   * Resets a {@code Deck} to its init values.
   */
  public void resetDeck() {
    for (Suit suit : Suit.values()) {
      var suitPack = suitList.get(suit.getValue());
      suitPack.resetPack();
      if (!suitPack.isEmpty()) {
        fullSuits.add(suit);
      }
    }
    LOG.debug("Deck successfully reset!");
  }

  /**
   * Returns owner of this {@code Deck}
   *
   * @return owner
   */
  public Owner getOwner() {
    return owner;
  }

  @Override
  public int hashCode() {
    int result = suitList.hashCode();
    result = 31 * result + fullSuits.hashCode();
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Deck)) {
      return false;
    }

    var deck = (Deck) o;

    if (!suitList.equals(deck.suitList)) {
      return false;
    }
    return fullSuits.equals(deck.fullSuits);
  }

  @Override
  public String toString() {
    return "Deck{" +
        "suitList=" + suitList +
        ", fullSuits=" + fullSuits +
        ", owner=" + owner +
        '}';
  }
}
