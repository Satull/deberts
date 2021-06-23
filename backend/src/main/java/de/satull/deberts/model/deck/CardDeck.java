package de.satull.deberts.model.deck;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * An entity that contains and manages cards. Cards are grouped by {@code SuitPack}. All suits are
 * inside of {@code ArrayList}.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public class CardDeck implements Deck {

  private static final Logger LOG =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
  private final ArrayList<SuitPack> suitList = new ArrayList<>(4);
  private final LinkedList<Suit> fullSuits = new LinkedList<>();

  /**
   * Creates new {@code CardDeck} entity. During initialisation creates cards using cartesian
   * product of Suit and FaceValue. Visibility of constructor is package private in order to create
   * single creation point in {@code DeckFactory}.
   */
  CardDeck() {
    for (Suit suit : Suit.values()) {
      suitList.add(suit.getValue(), SuitPack.newInstance());
      SuitPack suitPack = suitList.get(suit.getValue());
      for (FaceValue value : FaceValue.values()) {
        Card newCard = Card.newInstance(suit, value);
        suitPack.addCard(newCard);
        LOG.debug("Card successfully added: {}", newCard);
      }
      if (!suitPack.isEmpty()) {
        fullSuits.add(suit);
      }
    }
  }

  /**
   * Adds a {@code Card} to the {@code Deck}.
   *
   * @param card a {@code Card} to add
   */
  @Override
  public void addCard(Card card) {
    SuitPack suitCardList = suitList.get(card.getSuitValue());
    suitCardList.addCard(card);
  }

  /**
   * Returns {@code true} if this {@code Deck} contains the specified {@code Card}, {@code false}
   * otherwise.
   *
   * @param card a {@code Card} to search for
   * @return {@code true} if this {@code Deck} contains card, {@code false} otherwise
   */
  @Override
  public boolean contains(Card card) {
    SuitPack suitPack = suitList.get(card.getSuitValue());
    return suitPack.contains(card.getValue());
  }

  /**
   * Returns the number of cards inside this {@code Deck}.
   *
   * @return a number of cards
   */
  @Override
  public int countCards() {
    var activeCards = 0;
    for (SuitPack suitPack : suitList) {
      activeCards += suitPack.getActiveCars();
    }
    return activeCards;
  }

  /**
   * Returns the {@code Card} from the {@code Deck} using {@code Suit} and {@code Value}.
   *
   * <p>Deals a {@code Card} from its {@code SuitPack}. Removes the {@code suitPack} from full
   * suits if it gets empty.
   *
   * @param suit      a {@code Suit} of the {@code Card}
   * @param faceValue a {@code faceValue} of the {@code Card}
   * @return card founded {@code Card}
   */
  @Override
  public Card dealCard(Suit suit, FaceValue faceValue) {
    SuitPack suitPack = suitList.get(suit.getValue());
    Card dealtCard = suitPack.dealCard(faceValue);
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
  @Override
  public Card dealRandomCard() {
    var suitIndex = new Random().nextInt(fullSuits.size());
    SuitPack suitPack = suitList.get(suitIndex);
    return suitPack.dealRandomCard();
  }

  /**
   * Returns a random {@code Card} from the specified suit of the {@code Deck}.
   *
   * @param suit {@code Suit} to get a {@code Card}
   * @return a random {@code Card} from the {@code Suit}
   */
  @Override
  public Card dealRandomCardFromSuit(Suit suit) {
    return suitList.get(suit.getValue()).dealRandomCard();
  }

  /**
   * Resets a {@code Deck} to its init values.
   */
  @Override
  public void resetDeck() {
    for (Suit suit : Suit.values()) {
      SuitPack suitPack = suitList.get(suit.getValue());
      suitPack.resetPack();
      if (!suitPack.isEmpty()) {
        fullSuits.add(suit);
      }
    }
    LOG.debug("Deck successfully reset!");
  }
}
