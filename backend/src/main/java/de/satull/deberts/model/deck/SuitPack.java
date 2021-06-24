package de.satull.deberts.model.deck;

import de.satull.deberts.model.deck.enums.FaceValue;
import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An object that contains and manages the suit {@code Cards}. Each suit is a {@code Set} of {@code
 * Cards}. Deal or removed cards will be marked as not active.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
class SuitPack {

  private static final Logger LOG =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

  private final Set<Card> cards;
  private int activeCars;

  private SuitPack() {
    cards = new HashSet<>();
    activeCars = 0;
  }


  /**
   * Factory method to create a new {@code SuitPack}.
   *
   * @return new {@code SuitPack} object
   */
  static SuitPack newInstance() {
    return new SuitPack();
  }

  /**
   * Adds a new {@code Card} to the {@code SuitPack}.
   *
   * @param card {@code Card} to add
   */
  void addCard(Card card) {
    cards.add(card);
    activeCars++;
    LOG.debug("Added Card: {}", card);
  }

  /**
   * Returns {@code true} if this {@code SuitPack} contains the specified {@code Card}, {@code
   * false} otherwise.
   *
   * @param value {@code FaceValue} of the {@code Card} to search for
   * @return {@code true} if this {@code SuitPack} contains {@code Card}, {@code false} otherwise
   */
  boolean contains(FaceValue value) {
    if (activeCars == 0) {
      return false;
    }

    for (Card deckCard : cards) {
      if (deckCard.hasFaceValue(value)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns {@code true} if this {@code SuitPack} contains any active {@code Card}, {@code false}
   * otherwise.
   *
   * @return {@code true}  if {@code SuitPack} has active {@code Card}, {@code false} otherwise
   */
  boolean isEmpty() {
    return activeCars != 0;
  }

  /**
   * Return the number of the active {@code Cards} in the {@code SuitPack}
   *
   * @return number of {@code Cards}
   */
  int getActiveCars() {
    return activeCars;
  }

  /**
   * Returns the {@code Card} from the {@code SuitPack} using {@code Suit} and {@code FaceValue}.
   *
   * @param faceValue value of the {@code Card}
   * @return founded {@code Card}
   */
  Card dealCard(FaceValue faceValue) {
    if (!isEmpty()) {
      for (Card deckCard : cards) {
        if (deckCard.hasFaceValue(faceValue) && deckCard.isActive()) {
          LOG.debug("Dealt Card: {}", deckCard);
          Card dealtCard = Card.newInstance(deckCard);
          deckCard.setActive(false);
          activeCars--;
          return dealtCard;
        }
      }
    }
    throw new NoSuchElementException("Deck does not contain the card");
  }

  /**
   * Returns a random {@code Card} from the {@code SuitPack}.
   *
   * @return random {@code Card} from the {@code SuitPack}
   */
  public Card dealRandomCard() {
    if (!isEmpty()) {
      List<Card> activeCards = cards.stream().filter(Card::isActive).collect(Collectors.toList());
      var cardIndex = new Random().nextInt(activeCards.size());
      Card dealtCard = Card.newInstance(activeCards.get(cardIndex));
      LOG.debug("Dealt Card: {}", dealtCard);
      dealtCard.setActive(false);
      activeCars--;
      return dealtCard;
    }
    throw new NoSuchElementException("Deck does not contain any cards");
  }


  /**
   * Resets the {@code SuitPack} to the init values.
   *
   * <p>Each contained {@code Card} is active.
   */
  void resetPack() {
    if (!isEmpty()) {
      activeCars = 0;
      for (Card deckCard : cards) {
        deckCard.setActive(true);
        activeCars++;
      }
    }
    LOG.debug("SuitPack reset");
  }

  @Override
  public int hashCode() {
    int result = cards.hashCode();
    result = 31 * result + getActiveCars();
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SuitPack)) {
      return false;
    }

    var suitPack = (SuitPack) o;

    if (getActiveCars() != suitPack.getActiveCars()) {
      return false;
    }
    return cards.equals(suitPack.cards);
  }

  @Override
  public String toString() {
    return "SuitPack{" +
        "cards=" + cards +
        ", activeCars=" + activeCars +
        '}';
  }
}
