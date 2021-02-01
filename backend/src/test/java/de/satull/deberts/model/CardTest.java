package de.satull.deberts.model;

import static org.junit.jupiter.api.Assertions.assertEquals;


import de.satull.deberts.util.Game;
import org.junit.jupiter.api.Test;


class CardTest {

  @Test
  void equals_TwoCards_EqualCards() {
    Card firstCard = new Card(Game.DIAMONDS, SuitDeck.ACE);
    Card secondCard = new Card(Game.DIAMONDS, SuitDeck.ACE);

    assertEquals(firstCard, secondCard);
  }

  @Test
  void hashCode_TwoCards_SameCardsDifferentHashCodes() {
    Card firstCard = new Card(Game.DIAMONDS, SuitDeck.ACE);
    Card secondCard = new Card(Game.DIAMONDS, SuitDeck.ACE);

    assertEquals(firstCard.hashCode(), secondCard.hashCode());
  }
}