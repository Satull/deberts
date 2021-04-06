package de.satull.deberts.model;

import static org.assertj.core.api.Assertions.assertThat;

import de.satull.deberts.model.enums.Suit;
import de.satull.deberts.model.enums.SuitDeck;
import de.satull.deberts.model.web.Card;
import org.junit.jupiter.api.Test;

class CardTest {

  @Test
  void equals_TwoCards_EqualCards() {
    final Card firstCard = new Card(Suit.DIAMONDS, SuitDeck.ACE);
    final Card secondCard = new Card(Suit.DIAMONDS, SuitDeck.ACE);
    assertThat(firstCard).isEqualTo(secondCard);
  }

  @Test
  void hashCode_TwoCards_SameCardsDifferentHashCodes() {
    final Card firstCard = new Card(Suit.DIAMONDS, SuitDeck.ACE);
    final Card secondCard = new Card(Suit.DIAMONDS, SuitDeck.ACE);

    assertThat(firstCard.hashCode()).isEqualTo(secondCard.hashCode());
  }
}
