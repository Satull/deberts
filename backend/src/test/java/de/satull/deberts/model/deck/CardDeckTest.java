package de.satull.deberts.model.deck;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


import de.satull.deberts.config.DebertsConfigTest;
import de.satull.deberts.exception.NoSuchCardException;
import de.satull.deberts.model.Card;
import de.satull.deberts.model.SuitDeck;
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
  @Autowired
  CardDeck testCardDeck;

  @AfterEach
  final void afterEach() {
    testCardDeck.resetDeck();
  }

  @Test
  void countCards_CardDeck_NewDeckWith32Cards() {
    testCardDeck.resetDeck();
    assertEquals(testCardDeck.countCards(), startCardsNumber);
  }

  @Test
  void getCard_CardDeck_FailGetCardCardIsNotInTheDeck() throws NoSuchCardException {
    testCardDeck.getCard("spades", SuitDeck.QUEEN);
    NoSuchCardException exception;
    exception = assertThrows(NoSuchCardException.class,
        () -> testCardDeck.getCard("spades", SuitDeck.QUEEN));

    String expectedMessage =
        "Deck does not contain a card: " + "spades" + " " + SuitDeck.QUEEN.toString();
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void getCard_CardDeck_GetQueenOfSpadesFromTheDeckAsExpected() throws NoSuchCardException {
    assertEquals(testCardDeck.getCard("spades", SuitDeck.QUEEN),
        new Card("spades", SuitDeck.QUEEN));
  }

  @Test
  void getCard_CardDeck_GetTenOfSpadesInsteadOfAceOfSpades() throws NoSuchCardException {
    assertNotEquals(new Card("spades", SuitDeck.ACE), testCardDeck.getCard("spades", SuitDeck.TEN));
  }

  @Test
  void getDeck_CardDeck_DummyDeck_GetDifferentDeckThanDummy() {
    LinkedHashMap<String, List<SuitDeck>> testStockDeck =
        new LinkedHashMap<>();
    testStockDeck.put("clubs", new ArrayList<>(EnumSet.allOf(SuitDeck.class)));
    testStockDeck.put("hearts", new ArrayList<>(EnumSet.allOf(SuitDeck.class)));
    testStockDeck.put("spades", new ArrayList<>(EnumSet.allOf(SuitDeck.class)));

    LinkedHashMap<String, Object> expectedDeck = new LinkedHashMap<>();
    expectedDeck.put("cards", startCardsNumber);
    expectedDeck.put("deck", testStockDeck);

    assertNotEquals(expectedDeck, testCardDeck.getDeck());
  }

  @Test
  void getDeck_CardDeck_DummyDeck_GetSameDeckLikeDummy() {
    LinkedHashMap<String, List<SuitDeck>> testStockDeck =
        new LinkedHashMap<>();
    testStockDeck.put("clubs", new ArrayList<>(EnumSet.allOf(SuitDeck.class)));
    testStockDeck.put("diamonds", new ArrayList<>(EnumSet.allOf(SuitDeck.class)));
    testStockDeck.put("hearts", new ArrayList<>(EnumSet.allOf(SuitDeck.class)));
    testStockDeck.put("spades", new ArrayList<>(EnumSet.allOf(SuitDeck.class)));

    LinkedHashMap<String, Object> expectedDeck = new LinkedHashMap<>();
    expectedDeck.put("cards", startCardsNumber);
    expectedDeck.put("deck", testStockDeck);

    assertEquals(expectedDeck, testCardDeck.getDeck());
  }

  @Test
  void getRandomCardFormSuit_CardDeck_FailGetRandomCardFromSuit() throws NoSuchCardException {
    for (int i = 0; i < startCardsNumber / 4 + 1; i++) {
      testCardDeck.getRandomCardFromSuit("clubs");
    }
    assertEquals(3, testCardDeck.getSuitList().size());
  }

  @Test
  void getRandomCardFromSuit_CardDeckAndCard_NewCardHasExpectedSuit() throws NoSuchCardException {
    Card testcard = testCardDeck.getRandomCardFromSuit("spades");
    assertEquals("spades", testcard.getSuit());
  }

  @Test
  void getRandomCardFromSuit_CardDeck_GetAllCardsFromTheSuit() throws NoSuchCardException {
    for (int i = 0; i < startCardsNumber / 4; i++) {
      testCardDeck.getRandomCardFromSuit("hearts");
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
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
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
    List<String> suitArray = new ArrayList<>();
    suitArray.add("diamonds");
    suitArray.add("spades");

    assertNotEquals(suitArray, testCardDeck.getSuitList());
  }

  @Test
  void getSuitList_CardDeckDummyArray_GetSameSuitsFromDeckLikeDummy() {
    List<String> suitArray = new ArrayList<>();
    suitArray.add("clubs");
    suitArray.add("diamonds");
    suitArray.add("hearts");
    suitArray.add("spades");

    assertEquals(suitArray, testCardDeck.getSuitList());
  }

  @Test
  void getSuitList_CardDeck_GetEmptySuitList() throws NoSuchCardException {
    List<String> suitArray = new ArrayList<>();
    for (int i = 0; i < startCardsNumber; i++) {
      testCardDeck.getRandomCard();
    }

    assertEquals(suitArray, testCardDeck.getSuitList());
  }

  @Test
  void removeCard_CardDeck_DeckHas31Cards() throws NoSuchCardException {
    testCardDeck.removeCard(new Card("spades", SuitDeck.QUEEN));
    assertEquals(startCardsNumber - 1, testCardDeck.countCards());
  }

  @Test
  void removeCard_CardDeck_FailRemovingCard() throws NoSuchCardException {
    testCardDeck.removeCard(new Card("spades", SuitDeck.QUEEN));
    Exception exception = assertThrows(Exception.class,
        () -> testCardDeck.removeCard(new Card("spades", SuitDeck.QUEEN)));

    String expectedMessage =
        "Deck does not contain a card: " + "spades" + " " + SuitDeck.QUEEN.toString();
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
