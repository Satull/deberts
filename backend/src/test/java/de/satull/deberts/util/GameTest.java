package de.satull.deberts.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import de.satull.deberts.model.Card;
import de.satull.deberts.model.Suit;
import de.satull.deberts.model.SuitDeck;
import org.junit.jupiter.api.Test;

class GameTest {

  @Test
  void getCardPoints_CardNotTrumpJack_CardPointsEquals2() {
    Card card = new Card(Suit.DIAMONDS, SuitDeck.JACK);
    assertEquals(2, Game.getCardPoints(card.getValue(), false));
  }

  @Test
  void getCardPoints_CardNotTrumpNine_CardPointsEquals0() {
    Card card = new Card(Suit.DIAMONDS, SuitDeck.NINE);
    assertEquals(0, Game.getCardPoints(card.getValue(), false));
  }

  @Test
  void getCardPoints_CardTrumpJack_CardPointsEquals20() {
    Card card = new Card(Suit.DIAMONDS, SuitDeck.JACK);
    assertEquals(20, Game.getCardPoints(card.getValue(), true));
  }

  @Test
  void getCardPoints_CardTrumpNine_CardPointsEquals14() {
    Card card = new Card(Suit.DIAMONDS, SuitDeck.NINE);
    assertEquals(14, Game.getCardPoints(card.getValue(), true));
  }

  @Test
  void getCardPoints_TwoCards_PointsInTwoCardsAreEqual() {
    Card firstCard = new Card(Suit.DIAMONDS, SuitDeck.ACE);
    Card secondCard = new Card(Suit.SPADES, SuitDeck.ACE);
    assertEquals(
        Game.getCardPoints(secondCard.getValue(), false),
        Game.getCardPoints(firstCard.getValue(), false));
  }

  @Test
  void getCardPoints_TwoCards_PointsInTwoCardsAreNotEqual() {
    Card firstCard = new Card(Suit.DIAMONDS, SuitDeck.ACE);
    Card secondCard = new Card(Suit.SPADES, SuitDeck.KING);
    assertNotEquals(
        Game.getCardPoints(secondCard.getValue(), false),
        Game.getCardPoints(firstCard.getValue(), false));
  }
}
