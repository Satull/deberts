package de.satull.deberts.model.deck;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import de.satull.deberts.config.DebertsConfigTest;
import de.satull.deberts.exception.NoSuchCardException;
import de.satull.deberts.model.enums.Constant;
import de.satull.deberts.model.enums.Suit;
import de.satull.deberts.model.enums.SuitDeck;
import de.satull.deberts.model.web.Card;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(DebertsConfigTest.class)
class CardDeckTest {

  static final int startCardsNumber = 32;
  @Autowired CardDeck testCardDeck;

  @AfterEach
  final void afterEach() {
    testCardDeck.resetDeck();
  }

  @Test
  void countCards_CardDeck_NewDeckWith32Cards() {
    testCardDeck.resetDeck();
    assertThat(testCardDeck.countCards()).isEqualTo(startCardsNumber);
  }

  @Test
  void getCard_CardDeck_FailGetCardCardIsNotInTheDeck() throws NoSuchCardException {
    testCardDeck.getCard(Suit.SPADES, SuitDeck.QUEEN);
    String expectedMessage =
        "Deck does not contain a card: " + Suit.SPADES + " " + SuitDeck.QUEEN.toString();

    assertThatExceptionOfType(NoSuchCardException.class)
        .isThrownBy(() -> testCardDeck.getCard(Suit.SPADES, SuitDeck.QUEEN))
        .withMessageContaining(expectedMessage);
  }

  @Test
  void getCard_CardDeck_GetQueenOfSpadesFromTheDeckAsExpected() throws NoSuchCardException {
    final Card expectedCard = new Card(Suit.SPADES, SuitDeck.QUEEN);
    assertThat(testCardDeck.getCard(Suit.SPADES, SuitDeck.QUEEN)).isEqualTo(expectedCard);
  }

  @Test
  void getCard_CardDeck_GetTenOfSpadesInsteadOfAceOfSpades() throws NoSuchCardException {
    final Card otherCard = new Card(Suit.SPADES, SuitDeck.ACE);
    assertThat(testCardDeck.getCard(Suit.SPADES, SuitDeck.TEN)).isNotEqualTo(otherCard);
  }

  @Test
  void getDeck_CardDeck_DummyDeck_GetDifferentDeckThanDummy() {
    LinkedHashMap<Suit, List<SuitDeck>> testStockDeck = new LinkedHashMap<>();
    testStockDeck.put(Suit.CLUBS, new ArrayList<>(EnumSet.allOf(SuitDeck.class)));
    testStockDeck.put(Suit.HEARTS, new ArrayList<>(EnumSet.allOf(SuitDeck.class)));
    testStockDeck.put(Suit.SPADES, new ArrayList<>(EnumSet.allOf(SuitDeck.class)));

    LinkedHashMap<Constant, Object> otherDeck = new LinkedHashMap<>();
    otherDeck.put(Constant.CARDS, startCardsNumber);
    otherDeck.put(Constant.CARD_DECK, testStockDeck);

    assertThat(testCardDeck.getDeck()).isNotEqualTo(otherDeck);
  }

  @Test
  void getDeck_CardDeck_DummyDeck_GetSameDeckLikeDummy() {
    LinkedHashMap<Suit, List<SuitDeck>> testStockDeck = new LinkedHashMap<>();
    testStockDeck.put(Suit.CLUBS, new ArrayList<>(EnumSet.allOf(SuitDeck.class)));
    testStockDeck.put(Suit.DIAMONDS, new ArrayList<>(EnumSet.allOf(SuitDeck.class)));
    testStockDeck.put(Suit.HEARTS, new ArrayList<>(EnumSet.allOf(SuitDeck.class)));
    testStockDeck.put(Suit.SPADES, new ArrayList<>(EnumSet.allOf(SuitDeck.class)));

    LinkedHashMap<Constant, Object> expectedDeck = new LinkedHashMap<>();
    expectedDeck.put(Constant.CARDS, startCardsNumber);
    expectedDeck.put(Constant.CARD_DECK, testStockDeck);

    assertThat(testCardDeck.getDeck()).isEqualTo(expectedDeck);
  }

  @Test
  void getRandomCardFromSuit_CardDeckAndCard_NewCardHasExpectedSuit() throws NoSuchCardException {
    final Card testcard = testCardDeck.getRandomCardFromSuit(Suit.SPADES);
    assertThat(testcard.getSuit()).isEqualTo(Suit.SPADES);
  }

  @Test
  void getRandomCardFromSuit_CardDeck_RemoveSuitIfNoCards() throws NoSuchCardException {
    final int expectedSuitNumber = 3;
    for (int i = 0; i < startCardsNumber / 4; i++) {
      testCardDeck.getRandomCardFromSuit(Suit.HEARTS);
    }
    assertThat(testCardDeck.getSuitList().size()).isEqualTo(expectedSuitNumber);
  }

  @Test
  void getRandomCard_CardDeck_DeckHas31CardsAfterGet() throws NoSuchCardException {
    testCardDeck.getRandomCard();
    assertThat(testCardDeck.countCards()).isEqualTo(startCardsNumber - 1);
  }

  @Test
  void getRandomCard_CardDeck_FailGetRandomCard() {
    final String expectedMessage = "bound must be positive";

    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(
            () -> {
              for (int i = 0; i < startCardsNumber + 1; i++) {
                testCardDeck.getRandomCard();
              }
            })
        .withMessageContaining(expectedMessage);
  }

  @Test
  void getRandomCard_CardDeck_GetAllCardsWithGetRandomCard() throws NoSuchCardException {
    for (int i = 0; i < startCardsNumber; i++) {
      testCardDeck.getRandomCard();
    }
    assertThat(testCardDeck.countCards()).isZero();
  }

  @Test
  void getSuitList_CardDeckDummyArray_GetDifferentSuitListThanDummy() {
    List<Suit> suitArray = new ArrayList<>();
    suitArray.add(Suit.DIAMONDS);
    suitArray.add(Suit.SPADES);

    assertThat(testCardDeck.getSuitList()).isNotEqualTo(suitArray);
  }

  @Test
  void getSuitList_CardDeckDummyArray_GetSameSuitsFromDeckLikeDummy() {
    List<Suit> suitArray = new ArrayList<>();
    suitArray.add(Suit.CLUBS);
    suitArray.add(Suit.DIAMONDS);
    suitArray.add(Suit.HEARTS);
    suitArray.add(Suit.SPADES);

    assertThat(testCardDeck.getSuitList()).isEqualTo(suitArray);
  }

  @Test
  void getSuitList_CardDeck_GetEmptySuitList() throws NoSuchCardException {
    for (int i = 0; i < startCardsNumber; i++) {
      testCardDeck.getRandomCard();
    }

    assertThat(testCardDeck.getSuitList()).isEmpty();
  }

  @Test
  void removeCard_CardDeck_DeckHas31Cards() throws NoSuchCardException {
    testCardDeck.removeCard(new Card(Suit.SPADES, SuitDeck.QUEEN));
    assertThat(testCardDeck.countCards()).isLessThan(startCardsNumber);
  }

  @Test
  void removeCard_CardDeck_FailRemovingCard() throws NoSuchCardException {
    testCardDeck.removeCard(new Card(Suit.SPADES, SuitDeck.QUEEN));

    String expectedMessage =
        "Deck does not contain a card: " + Suit.SPADES + " " + SuitDeck.QUEEN.toString();

    assertThatExceptionOfType(NoSuchCardException.class)
        .isThrownBy(() -> testCardDeck.removeCard(new Card(Suit.SPADES, SuitDeck.QUEEN)))
        .withMessageContaining(expectedMessage);
  }

  @Test
  void resetDeck_CardDeck_DeckHas32CardsAfterReset() throws NoSuchCardException {
    testCardDeck.getRandomCard();
    testCardDeck.resetDeck();
    assertThat(testCardDeck.countCards()).isEqualTo(startCardsNumber);
  }
}
