package de.satull.deberts.model.deck;

import de.satull.deberts.enums.Owner;
import de.satull.deberts.model.deck.enums.FaceValue;
import de.satull.deberts.model.deck.enums.Suit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SuppressWarnings("javadoc")
@SpringBootTest
class CardDeckTest {

  private Deck firstDeck;

  @BeforeEach
  public void beforeEach() {
    firstDeck = DeckFactory.createDeck(Owner.NOBODY);
  }

  @Test
  void equals_twoNewCardDecks_trueByDefault() {
    Deck secondDeck = DeckFactory.createDeck(Owner.NOBODY);

    assertThat(firstDeck).isEqualTo(secondDeck);
  }

  @Test
  void equals_twoNewCardDecksFirstDeckDealsOneCard_false() {
    Deck secondDeck = DeckFactory.createDeck(Owner.NOBODY);

    firstDeck.dealRandomCard();

    assertThat(firstDeck).isNotEqualTo(secondDeck);
  }

  @Test
  void hashcode_twoNewCardDecks_sameHashCode() {
    Deck secondDeck = DeckFactory.createDeck(Owner.NOBODY);

    assertThat(firstDeck).hasSameHashCodeAs(secondDeck);
  }

  @Test
  void hashcode_twoNewCardDecksFirstDeckDealsOneCard_differentHashCode() {
    Deck secondDeck = DeckFactory.createDeck(Owner.NOBODY);

    firstDeck.dealRandomCard();

    assertThat(firstDeck.hashCode()).isNotEqualTo(secondDeck.hashCode());
  }

  @Test
  void toString_newCardDeck_StringContainsSuitsAndFullSuits() {

    assertThat(firstDeck.toString()).contains("suitList").contains("fullSuits");
  }

  @Test
  void addCard_newFullCardDeck_throwsIllegalArgumentException() {
    Card card = Card.newInstance(Suit.HEARTS, FaceValue.SEVEN);

    assertThatThrownBy(() -> firstDeck.addCard(card)).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void contains_newFullCardDeckDiamondsJack_trueByDefault() {
    Card card = Card.newInstance(Suit.DIAMONDS, FaceValue.JACK);

    assertThat(firstDeck.contains(card)).isTrue();
  }

  @Test
  void contains_newCardDeckDealtDiamondsJackAndCheckSameCard_false() {
    Card card = Card.newInstance(Suit.DIAMONDS, FaceValue.JACK);
    firstDeck.dealCard(Suit.DIAMONDS, FaceValue.JACK);

    assertThat(firstDeck.contains(card)).isFalse();
  }

  @Test
  void countCards_newFullCardDeck_32ByDefault() {
    assertThat(firstDeck.countCards()).isEqualTo(32);
  }

  @Test
  void countCards_newCardDeckDealtRandomCard_31() {
    firstDeck.dealRandomCard();
    assertThat(firstDeck.countCards()).isEqualTo(31);
  }

  @Test
  void dealCard_newCardDeckDealtSpadesSeven_dealExpectedCard() {
    Card expectedCard = Card.newInstance(Suit.SPADES, FaceValue.SEVEN);
    Card dealtCard = firstDeck.dealCard(Suit.SPADES, FaceValue.SEVEN);

    assertThat(dealtCard).hasSameHashCodeAs(expectedCard);
  }

  @Test
  void dealCard_newCardDeckDealtAllDiamondsSuit_throwsNoSuchElementException() {
    firstDeck.dealCard(Suit.DIAMONDS, FaceValue.SEVEN);
    firstDeck.dealCard(Suit.DIAMONDS, FaceValue.EIGHT);
    firstDeck.dealCard(Suit.DIAMONDS, FaceValue.NINE);
    firstDeck.dealCard(Suit.DIAMONDS, FaceValue.TEN);
    firstDeck.dealCard(Suit.DIAMONDS, FaceValue.JACK);
    firstDeck.dealCard(Suit.DIAMONDS, FaceValue.QUEEN);
    firstDeck.dealCard(Suit.DIAMONDS, FaceValue.KING);
    firstDeck.dealCard(Suit.DIAMONDS, FaceValue.ACE);

    assertThatThrownBy(() -> firstDeck.dealCard(Suit.DIAMONDS, FaceValue.ACE))
        .isInstanceOf(NoSuchElementException.class);
  }

  @Test
  void dealRandomCard_newCardDeckDealTwoCards_30CardsInTheDeck() {
    firstDeck.dealRandomCard();
    firstDeck.dealRandomCard();

    assertThat(firstDeck.countCards()).isEqualTo(30);
  }

  @Test
  void dealRandomCard_newCardDeckDealAllCards_0CardsInTheDeck() {
    while (firstDeck.countCards() != 0) {
      firstDeck.dealRandomCard();
    }

    assertThat(firstDeck.isEmpty()).isTrue();
  }

  @Test
  void dealRandomCard_newCardDeckDealAllCards_throwsNoSuchElementException() {
    while (firstDeck.countCards() != 0) {
      firstDeck.dealRandomCard();
    }

    assertThatThrownBy(() -> firstDeck.dealRandomCard()).isInstanceOf(NoSuchElementException.class);
  }

  @Test
  void dealRandomCardFromSuit_newCardDeckDealAllCardsFromSuit_throwsNoSuchElementException() {
    for (int i = 0; i < 8; i++) {
      firstDeck.dealRandomCardFromSuit(Suit.DIAMONDS);
    }

    assertThatThrownBy(() -> firstDeck.dealRandomCardFromSuit(Suit.DIAMONDS))
        .isInstanceOf(NoSuchElementException.class);
  }

  @Test
  void dealRandomCardFromSuit_newCardDeckDeal2SpadesCards_deal2SpadesCards() {
    Card firstCard = firstDeck.dealRandomCardFromSuit(Suit.SPADES);
    Card secondCard = firstDeck.dealRandomCardFromSuit(Suit.SPADES);

    assertThat(firstCard.getSuit()).isEqualTo(secondCard.getSuit()).isEqualTo(Suit.SPADES);
  }

  @Test
  void resetDeck_newCardDeckDealAllCardsThenReset_32CardsInTheDeck() {
    while (firstDeck.countCards() != 0) {
      firstDeck.dealRandomCard();
    }

    firstDeck.resetDeck();

    assertThat(firstDeck.countCards()).isEqualTo(32);
  }
}
