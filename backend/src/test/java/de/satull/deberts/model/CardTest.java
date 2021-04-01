package de.satull.deberts.model;

import static org.junit.jupiter.api.Assertions.assertEquals;


import de.satull.deberts.model.enums.Suit;
import de.satull.deberts.model.enums.SuitDeck;
import de.satull.deberts.model.web.Card;
import org.junit.jupiter.api.Test;

class CardTest {

  @Test
  void equals_TwoCards_EqualCards() {
    Card firstCard = new Card(Suit.DIAMONDS, SuitDeck.ACE);
    Card secondCard = new Card(Suit.DIAMONDS, SuitDeck.ACE);

    assertEquals(firstCard, secondCard);
  }

  @Test
  void hashCode_TwoCards_SameCardsDifferentHashCodes() {
    Card firstCard = new Card(Suit.DIAMONDS, SuitDeck.ACE);
    Card secondCard = new Card(Suit.DIAMONDS, SuitDeck.ACE);

    assertEquals(firstCard.hashCode(), secondCard.hashCode());
  }
}
