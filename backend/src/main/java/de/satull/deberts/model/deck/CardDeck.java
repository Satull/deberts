package de.satull.deberts.model.deck;

import edu.emory.mathcs.backport.java.util.LinkedList;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * An object that contains and manages cards. Cards are grouped by suits. Each suit is a set of
 * cards.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public class CardDeck implements Deck {

  private static final Logger LOG =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
  private final ArrayList<SuitPack> suitList = new ArrayList<>(4);
  private final LinkedList fullSuits = new LinkedList();

  /**
   * Creates new CardDeck entity. During initialisation creates cards using cartesian product of
   * Suit and FaceValue. Visibility of constructor is package private in order to create single
   * creation point in DeckFactory.
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
   * Returns true if this deck contains the specified Card false otherwise.
   *
   * @param card the card to search for
   * @return true if this Deck contains card, false otherwise
   */
  @Override
  public boolean contains(Card card) {
    SuitPack suitPack = suitList.get(card.getSuitValue());
    return suitPack.contains(card.getValue());
  }

  /**
   * Returns the number of cards in this deck.
   *
   * @return number of cards
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
   * Returns the card from the deck using suit and value.
   *
   * @param suit      of the card
   * @param faceValue of the card
   * @return card founded card
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
   * Resets the deck to the init values.
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

  /**
   * Returns a random card from the deck.
   *
   * @return random card from the deck
   */
  @Override
  public Card getRandomCard() {
    var suitIndex = new Random().nextInt(fullSuits.size());
    SuitPack suitPack = suitList.get(suitIndex);
    return suitPack.dealRandomCard();
  }

  /**
   * Returns a random card from the specified suit of the deck.
   *
   * @param suit suit to get a card
   * @return random card from the suit
   */
  @Override
  public Card getRandomCardFromSuit(Suit suit) {
    return suitList.get(suit.getValue()).dealRandomCard();
  }

  /**
   * Add card into the deck.
   *
   * @param card to add
   */
  @Override
  public void addCard(Card card) {
    SuitPack suitCardList = suitList.get(card.getSuitValue());
    suitCardList.addCard(card);
  }
}
