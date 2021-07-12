package de.satull.deberts.model.deck;

import de.satull.deberts.enums.Owner;
import de.satull.deberts.model.deck.enums.FaceValue;
import de.satull.deberts.model.deck.enums.Suit;

/**
 * An entity that contains and manages cards. Cards are grouped by {@code SuitPack}. All suits are
 * inside of {@code ArrayList}.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public class CardDeck extends AbstractDeck {

  /**
   * Creates new {@code CardDeck} entity. During initialisation creates cards using cartesian
   * product of {@code Suit} and {@code FaceValue}. Visibility of constructor is package private in
   * order to create single creation point in {@code DeckFactory}.
   */
  CardDeck() {
    owner = Owner.NOBODY;
    for (Suit suit : Suit.values()) {
      suitList.add(suit.getValue(), SuitPack.newInstance());
      var suitPack = suitList.get(suit.getValue());
      for (FaceValue value : FaceValue.values()) {
        var newCard = Card.newInstance(suit, value);
        suitPack.addCard(newCard);
        LOG.debug("Card successfully added: {}", newCard);
      }
      if (!suitPack.isEmpty()) {
        fullSuits.add(suit);
      }
    }
  }

  @Override
  public void addCard(Card card) {
    SuitPack suitCardList = suitList.get(card.getSuitValue());
    suitCardList.addCard(card);
  }

  @Override
  public String toString() {
    return "CardDeck{" + "suitList=" + suitList + ", fullSuits=" + fullSuits + '}';
  }
}
