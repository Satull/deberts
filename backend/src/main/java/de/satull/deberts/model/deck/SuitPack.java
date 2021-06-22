package de.satull.deberts.model.deck;

import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An object that contains and manages if the suit cards. Each suit is a set of cards. Deal or
 * removed cards will be marked as not active.
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
   * Factory method to create a new suit pack.
   *
   * @return new SuitPack object
   */
  static SuitPack newInstance() {
    return new SuitPack();
  }

  /**
   * Adds a new card to the suit pack.
   *
   * @param card card to add
   */
  void addCard(Card card) {
    cards.add(card);
    activeCars++;
    LOG.debug("Added Card: {}", card);
  }

  /**
   * Returns true if this suit pack contains the specified Card, false otherwise.
   *
   * @param value face value of the card to search for
   * @return true if this suit pack contains Card, false otherwise
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
   * Returns true if this suit pack contains any active Card, false otherwise.
   *
   * @return true if this suit pack contains any active Card, false otherwise
   */
  boolean isEmpty() {
    return activeCars == 0;
  }

  /**
   * Return the number of the active Cards in the SuitPack
   *
   * @return number of Cards
   */
  int getActiveCars() {
    return activeCars;
  }

  /**
   * Returns the card from the suit pack  using suit and value.
   *
   * @param faceValue face value of the card
   * @return founded card
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

}
