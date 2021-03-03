package de.satull.deberts.model.deck;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.satull.deberts.config.DebertsConfigTest;
import de.satull.deberts.exception.NoSuchCardException;
import de.satull.deberts.model.Card;
import de.satull.deberts.model.Suit;
import de.satull.deberts.model.SuitDeck;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(DebertsConfigTest.class)
class TrumpDeckTest {

  @Autowired CardDeck testCardDeck;

  @Autowired TrumpDeck testTrumpDeck;

  @AfterEach
  final void afterEach() {
    testTrumpDeck.resetDeck();
    testCardDeck.resetDeck();
  }

  @Test
  void getSuit_GetSuit_GetDifferentSuit() throws NoSuchCardException {
    testTrumpDeck.setTrump(testCardDeck.getCard(Suit.CLUBS, SuitDeck.SEVEN));
    assertNotEquals(Suit.SPADES, testTrumpDeck.getSuit());
  }

  @Test
  void getSuit_GetSuit_GetTrumpSuit() throws NoSuchCardException {
    testTrumpDeck.setTrump(testCardDeck.getCard(Suit.SPADES, SuitDeck.JACK));
    assertEquals(Suit.SPADES, testTrumpDeck.getSuit());
  }

  @Test
  void resetDeck_EmptyTrumpDeck_TrumpDeckIsNull() throws NoSuchCardException {
    testTrumpDeck.setTrump(testCardDeck.getCard(Suit.HEARTS, SuitDeck.JACK));
    testTrumpDeck.resetDeck();
    assertNull(testTrumpDeck.getSuit());
  }

  @Test
  void setTrump_SetCardAsTrump_TrumpDeckHasOneCard() throws NoSuchCardException {
    testTrumpDeck.setTrump(testCardDeck.getCard(Suit.DIAMONDS, SuitDeck.ACE));
    assertEquals(1, testTrumpDeck.countCards());
  }

  @Test
  void setTrump_SetTrumpWhenTrumpIsBusy_NewTrumpIsNotOverridingTheOldOne()
      throws NoSuchCardException {
    testTrumpDeck.setTrump(testCardDeck.getCard(Suit.SPADES, SuitDeck.EIGHT));
    testTrumpDeck.setTrump(testCardDeck.getCard(Suit.DIAMONDS, SuitDeck.NINE));
    assertEquals(Suit.SPADES, testTrumpDeck.getSuit());
  }

  @Test
  void switchTrump_SwitchTrumpToNotTrumpSeven_FailedOldTrumpStays() throws NoSuchCardException {
    testTrumpDeck.setTrump(testCardDeck.getCard(Suit.HEARTS, SuitDeck.JACK));
    testTrumpDeck.switchTrump(testCardDeck.getCard(Suit.SPADES, SuitDeck.SEVEN));

    assertEquals(
        new Card(Suit.HEARTS, SuitDeck.JACK), testTrumpDeck.getCard(Suit.HEARTS, SuitDeck.JACK));
  }

  @Test
  void switchTrump_SwitchTrumpToNotTrump_FailedOldTrumpStays() throws NoSuchCardException {
    testTrumpDeck.setTrump(testCardDeck.getCard(Suit.HEARTS, SuitDeck.JACK));
    testTrumpDeck.switchTrump(testCardDeck.getCard(Suit.SPADES, SuitDeck.QUEEN));

    assertEquals(
        new Card(Suit.HEARTS, SuitDeck.JACK), testTrumpDeck.getCard(Suit.HEARTS, SuitDeck.JACK));
  }

  @Test
  void switchTrump_SwitchTrumpToOtherTrump_FailedOldTrumpStays() throws NoSuchCardException {
    testTrumpDeck.setTrump(testCardDeck.getCard(Suit.HEARTS, SuitDeck.JACK));
    testTrumpDeck.switchTrump(testCardDeck.getCard(Suit.HEARTS, SuitDeck.QUEEN));

    assertEquals(
        new Card(Suit.HEARTS, SuitDeck.JACK), testTrumpDeck.getCard(Suit.HEARTS, SuitDeck.JACK));
  }

  @Test
  void switchTrump_SwitchTrumpToTrumpSeven_Success() throws NoSuchCardException {
    testTrumpDeck.setTrump(testCardDeck.getCard(Suit.HEARTS, SuitDeck.JACK));
    testTrumpDeck.switchTrump(testCardDeck.getCard(Suit.HEARTS, SuitDeck.SEVEN));

    assertEquals(
        new Card(Suit.HEARTS, SuitDeck.SEVEN), testTrumpDeck.getCard(Suit.HEARTS, SuitDeck.SEVEN));
  }

  @Test
  void trumpDeck_NewTrumpDeck_CreateNewTrumpDeckObject() throws NoSuchCardException {
    TrumpDeck testTrumpParameterDeck =
        new TrumpDeck(testCardDeck.getCard(Suit.SPADES, SuitDeck.JACK));
    assertEquals(Suit.SPADES, testTrumpParameterDeck.getSuit());
  }
}
