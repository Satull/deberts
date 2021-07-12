package de.satull.deberts.model.deck;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import de.satull.deberts.enums.Owner;
import de.satull.deberts.model.deck.enums.FaceValue;
import de.satull.deberts.model.deck.enums.Suit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SuppressWarnings("javadoc")
@SpringBootTest
public class HandDeckTest {

  Deck firstDeck;

  @BeforeEach
  void beforeEach() {
    firstDeck = DeckFactory.createDeck(Owner.PLAYER);
  }

  @Test
  void addCard_newHandDeckAdd5Cards_handDeckWith5Cards() {
    Card firstCard = Card.newInstance(Suit.HEARTS, FaceValue.SEVEN);
    Card secondCard = Card.newInstance(Suit.DIAMONDS, FaceValue.SEVEN);
    Card thirdCard = Card.newInstance(Suit.SPADES, FaceValue.SEVEN);
    Card fourthCard = Card.newInstance(Suit.CLUBS, FaceValue.SEVEN);
    Card fifthCard = Card.newInstance(Suit.HEARTS, FaceValue.EIGHT);

    firstDeck.addCard(firstCard);
    firstDeck.addCard(secondCard);
    firstDeck.addCard(thirdCard);
    firstDeck.addCard(fourthCard);
    firstDeck.addCard(fifthCard);

    assertThat(firstDeck.countCards()).isEqualTo(5);
  }

  @Test
  void addCard_newHandDeckAdd10Cards_throwIllegalArgumentExceptionOn10thCard() {
    Card firstCard = Card.newInstance(Suit.HEARTS, FaceValue.SEVEN);
    Card secondCard = Card.newInstance(Suit.HEARTS, FaceValue.EIGHT);
    Card thirdCard = Card.newInstance(Suit.HEARTS, FaceValue.NINE);
    Card fourthCard = Card.newInstance(Suit.HEARTS, FaceValue.TEN);
    Card fifthCard = Card.newInstance(Suit.HEARTS, FaceValue.JACK);
    Card sixthCard = Card.newInstance(Suit.HEARTS, FaceValue.QUEEN);
    Card seventhCard = Card.newInstance(Suit.HEARTS, FaceValue.KING);
    Card eightCard = Card.newInstance(Suit.HEARTS, FaceValue.ACE);
    Card ninthCard = Card.newInstance(Suit.DIAMONDS, FaceValue.SEVEN);
    Card tenthCard = Card.newInstance(Suit.DIAMONDS, FaceValue.EIGHT);

    firstDeck.addCard(firstCard);
    firstDeck.addCard(secondCard);
    firstDeck.addCard(thirdCard);
    firstDeck.addCard(fourthCard);
    firstDeck.addCard(fifthCard);
    firstDeck.addCard(sixthCard);
    firstDeck.addCard(seventhCard);
    firstDeck.addCard(eightCard);
    firstDeck.addCard(ninthCard);

    assertThatThrownBy(() -> firstDeck.addCard(tenthCard))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void equals_twoNewHandDecks_trueByDefault() {
    Deck secondDeck = DeckFactory.createDeck(Owner.PLAYER);

    assertThat(firstDeck).isEqualTo(secondDeck);
  }

  @Test
  void equals_twoNewCHandDecksDifferentOwners_false() {
    Deck secondDeck = DeckFactory.createDeck(Owner.BOT);

    assertThat(firstDeck).isNotEqualTo(secondDeck);
  }

  @Test
  void hashcode_twoNewHandDecks_sameHashCode() {
    Deck secondDeck = DeckFactory.createDeck(Owner.PLAYER);

    assertThat(firstDeck).hasSameHashCodeAs(secondDeck);
  }

  @Test
  void hashcode_twoNewHandDecksDifferentOwners_differentHashCode() {
    Deck secondDeck = DeckFactory.createDeck(Owner.BOT);

    assertThat(firstDeck.hashCode()).isNotEqualTo(secondDeck.hashCode());
  }

  @Test
  void toString_newCardDeck_StringContainsSuitsAndFullSuits() {

    assertThat(firstDeck.toString())
        .contains("suitList")
        .contains("fullSuits")
        .contains("owner")
        .contains("maxCardsNumber")
        .contains("containedCards");
  }
}
