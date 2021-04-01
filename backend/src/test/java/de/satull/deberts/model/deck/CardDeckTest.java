package de.satull.deberts.model.deck;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.satull.deberts.config.DebertsConfigTest;
import de.satull.deberts.exception.NoSuchCardException;
import de.satull.deberts.model.web.Card;
import de.satull.deberts.model.enums.Constant;
import de.satull.deberts.model.enums.Suit;
import de.satull.deberts.model.enums.SuitDeck;
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
    assertEquals(startCardsNumber, testCardDeck.countCards());
  }

  @Test
  void getCard_CardDeck_FailGetCardCardIsNotInTheDeck() throws NoSuchCardException {
    testCardDeck.getCard(Suit.SPADES, SuitDeck.QUEEN);
    NoSuchCardException exception;
    exception =
        assertThrows(
            NoSuchCardException.class, () -> testCardDeck.getCard(Suit.SPADES, SuitDeck.QUEEN));

    String expectedMessage =
        "Deck does not contain a card: " + Suit.SPADES + " " + SuitDeck.QUEEN.toString();
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void getCard_CardDeck_GetQueenOfSpadesFromTheDeckAsExpected() throws NoSuchCardException {
    assertEquals(
        testCardDeck.getCard(Suit.SPADES, SuitDeck.QUEEN), new Card(Suit.SPADES, SuitDeck.QUEEN));
  }

  @Test
  void getCard_CardDeck_GetTenOfSpadesInsteadOfAceOfSpades() throws NoSuchCardException {
    assertNotEquals(
        new Card(Suit.SPADES, SuitDeck.ACE), testCardDeck.getCard(Suit.SPADES, SuitDeck.TEN));
  }

  @Test
  void getDeck_CardDeck_DummyDeck_GetDifferentDeckThanDummy() {
    LinkedHashMap<Suit, List<SuitDeck>> testStockDeck = new LinkedHashMap<>();
    testStockDeck.put(Suit.CLUBS, new ArrayList<>(EnumSet.allOf(SuitDeck.class)));
    testStockDeck.put(Suit.HEARTS, new ArrayList<>(EnumSet.allOf(SuitDeck.class)));
    testStockDeck.put(Suit.SPADES, new ArrayList<>(EnumSet.allOf(SuitDeck.class)));

    LinkedHashMap<Constant, Object> expectedDeck = new LinkedHashMap<>();
    expectedDeck.put(Constant.CARDS, startCardsNumber);
    expectedDeck.put(Constant.CARD_DECK, testStockDeck);

    assertNotEquals(expectedDeck, testCardDeck.getDeck());
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

    assertEquals(expectedDeck, testCardDeck.getDeck());
  }

  @Test
  void getRandomCardFormSuit_CardDeck_FailGetRandomCardFromSuit() throws NoSuchCardException {
    for (int i = 0; i < startCardsNumber / 4 + 1; i++) {
      testCardDeck.getRandomCardFromSuit(Suit.CLUBS);
    }
    assertEquals(3, testCardDeck.getSuitList().size());
  }

  @Test
  void getRandomCardFromSuit_CardDeckAndCard_NewCardHasExpectedSuit() throws NoSuchCardException {
    Card testcard = testCardDeck.getRandomCardFromSuit(Suit.SPADES);
    assertEquals(Suit.SPADES, testcard.getSuit());
  }

  @Test
  void getRandomCardFromSuit_CardDeck_GetAllCardsFromTheSuit() throws NoSuchCardException {
    for (int i = 0; i < startCardsNumber / 4; i++) {
      testCardDeck.getRandomCardFromSuit(Suit.HEARTS);
    }
    assertEquals(3, testCardDeck.getSuitList().size());
  }

  @Test
  void getRandomCard_CardDeck_DeckHas31CardsAfterGet() throws NoSuchCardException {
    testCardDeck.getRandomCard();
    assertEquals(startCardsNumber - 1, testCardDeck.countCards());
  }

  @Test
  void getRandomCard_CardDeck_FailGetRandomCard() {
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              for (int i = 0; i < startCardsNumber + 1; i++) {
                testCardDeck.getRandomCard();
              }
            });

    String expectedMessage = "bound must be positive";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void getRandomCard_CardDeck_GetAllCardsWithGetRandomCard() throws NoSuchCardException {
    for (int i = 0; i < startCardsNumber; i++) {
      testCardDeck.getRandomCard();
    }
    assertEquals(0, testCardDeck.countCards());
  }

  @Test
  void getSuitList_CardDeckDummyArray_GetDifferentSuitListThanDummy() {
    List<Suit> suitArray = new ArrayList<>();
    suitArray.add(Suit.DIAMONDS);
    suitArray.add(Suit.SPADES);

    assertNotEquals(suitArray, testCardDeck.getSuitList());
  }

  @Test
  void getSuitList_CardDeckDummyArray_GetSameSuitsFromDeckLikeDummy() {
    List<Suit> suitArray = new ArrayList<>();
    suitArray.add(Suit.CLUBS);
    suitArray.add(Suit.DIAMONDS);
    suitArray.add(Suit.HEARTS);
    suitArray.add(Suit.SPADES);

    assertEquals(suitArray, testCardDeck.getSuitList());
  }

  @Test
  void getSuitList_CardDeck_GetEmptySuitList() throws NoSuchCardException {
    List<Suit> suitArray = new ArrayList<>();
    for (int i = 0; i < startCardsNumber; i++) {
      testCardDeck.getRandomCard();
    }

    assertEquals(suitArray, testCardDeck.getSuitList());
  }

  @Test
  void removeCard_CardDeck_DeckHas31Cards() throws NoSuchCardException {
    testCardDeck.removeCard(new Card(Suit.SPADES, SuitDeck.QUEEN));
    assertEquals(startCardsNumber - 1, testCardDeck.countCards());
  }

  @Test
  void removeCard_CardDeck_FailRemovingCard() throws NoSuchCardException {
    testCardDeck.removeCard(new Card(Suit.SPADES, SuitDeck.QUEEN));
    Exception exception =
        assertThrows(
            Exception.class, () -> testCardDeck.removeCard(new Card(Suit.SPADES, SuitDeck.QUEEN)));

    String expectedMessage =
        "Deck does not contain a card: " + Suit.SPADES + " " + SuitDeck.QUEEN.toString();
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void resetDeck_CardDeck_DeckHas32CardsAfterReset() throws NoSuchCardException {
    testCardDeck.getRandomCard();
    testCardDeck.resetDeck();
    assertEquals(startCardsNumber, testCardDeck.countCards());
  }
}
