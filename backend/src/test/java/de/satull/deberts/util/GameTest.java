package de.satull.deberts.util;

import static org.assertj.core.api.Assertions.assertThat;

import de.satull.deberts.model.enums.Suit;
import de.satull.deberts.model.enums.SuitDeck;
import de.satull.deberts.model.web.Card;
import org.junit.jupiter.api.Test;

class GameTest {

  @Test
  void getCardPoints_CardNotTrumpJack_CardPointsEquals2() {
    final Card card = new Card(Suit.DIAMONDS, SuitDeck.JACK);
    final int expectedPoints = 2;
    assertThat(Game.getCardPoints(card.getValue(), false)).isEqualTo(expectedPoints);
  }

  @Test
  void getCardPoints_CardNotTrumpNine_CardPointsEquals0() {
    Card card = new Card(Suit.DIAMONDS, SuitDeck.NINE);
    assertThat(Game.getCardPoints(card.getValue(), false)).isZero();
  }

  @Test
  void getCardPoints_CardTrumpJack_CardPointsEquals20() {
    Card card = new Card(Suit.DIAMONDS, SuitDeck.JACK);
    final int expectedPoints = 20;
    assertThat(Game.getCardPoints(card.getValue(), true)).isEqualTo(expectedPoints);
  }

  @Test
  void getCardPoints_CardTrumpNine_CardPointsEquals14() {
    Card card = new Card(Suit.DIAMONDS, SuitDeck.NINE);
    final int expectedPoints = 14;
    assertThat(Game.getCardPoints(card.getValue(), true)).isEqualTo(expectedPoints);
  }

  @Test
  void getCardPoints_TwoCards_PointsInTwoCardsAreEqual() {
    Card firstCard = new Card(Suit.DIAMONDS, SuitDeck.ACE);
    Card secondCard = new Card(Suit.SPADES, SuitDeck.ACE);
    assertThat(Game.getCardPoints(firstCard.getValue(), false))
        .isEqualTo(Game.getCardPoints(secondCard.getValue(), false));
  }

  @Test
  void getCardPoints_TwoCards_PointsInTwoCardsAreNotEqual() {
    Card firstCard = new Card(Suit.DIAMONDS, SuitDeck.ACE);
    Card secondCard = new Card(Suit.SPADES, SuitDeck.KING);
    assertThat(Game.getCardPoints(firstCard.getValue(), false))
        .isGreaterThan(Game.getCardPoints(secondCard.getValue(), false));
  }
}
