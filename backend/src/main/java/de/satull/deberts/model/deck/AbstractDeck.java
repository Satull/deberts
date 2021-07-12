package de.satull.deberts.model.deck;

import de.satull.deberts.enums.Owner;
import de.satull.deberts.model.deck.enums.FaceValue;
import de.satull.deberts.model.deck.enums.Suit;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;
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
public abstract class AbstractDeck implements Deck {

  static final Logger LOG =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
  final ArrayList<SuitPack> suitList = new ArrayList<>(4);
  final LinkedList<Suit> fullSuits = new LinkedList<>();
  Owner owner;

  @Override
  public boolean contains(Card card) {
    var suitPack = suitList.get(card.getSuitValue());
    return suitPack.contains(card.getValue());
  }

  @Override
  public int countCards() {
    var activeCards = 0;
    for (SuitPack suitPack : suitList) {
      activeCards += suitPack.getActiveCards();
    }
    return activeCards;
  }

  @Override
  public Card dealCard(Suit suit, FaceValue faceValue) {
    var suitPack = suitList.get(suit.getValue());
    var dealtCard = suitPack.dealCard(faceValue);
    if (suitPack.isEmpty()) {
      fullSuits.remove(suit);
    }
    return dealtCard;
  }

  @Override
  public Card dealRandomCard() {
    try {
      LOG.debug("FullSuits: {}", fullSuits);
      var suitIndex = new Random().nextInt(fullSuits.size());
      Suit suit = fullSuits.get(suitIndex);
      var suitPack = suitList.get(suit.getValue());
      LOG.debug("SuitIndex: {}, SuitPack: {}", suitIndex, suitPack);
      Card cardToDeal = suitPack.dealRandomCard();
      if (suitPack.isEmpty()) {
        fullSuits.remove(suit);
      }
      return cardToDeal;
    } catch (IllegalArgumentException e) {
      throw new NoSuchElementException("Deck does not contain any cards");
    }
  }

  @Override
  public Card dealRandomCardFromSuit(Suit suit) {
    return suitList.get(suit.getValue()).dealRandomCard();
  }

  @Override
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

  @Override
  public Owner getOwner() {
    return owner;
  }

  @Override
  public boolean isEmpty() {
    return fullSuits.isEmpty();
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
    if (!(o instanceof AbstractDeck)) {
      return false;
    }

    var deck = (AbstractDeck) o;

    if (!suitList.equals(deck.suitList)) {
      return false;
    }
    return fullSuits.equals(deck.fullSuits);
  }

  @Override
  public String toString() {
    return "Deck{" + "suitList=" + suitList + ", fullSuits=" + fullSuits + ", owner=" + owner + '}';
  }
}
