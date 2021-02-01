package de.satull.deberts.deck;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


import de.satull.deberts.config.DebertsConfigTest;
import de.satull.deberts.model.Card;
import de.satull.deberts.model.SuitDeck;
import de.satull.deberts.util.Game;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(DebertsConfigTest.class)
class TrumpDeckTest {

  @Autowired
  CardDeck testCardDeck;

  @Autowired
  TrumpDeck testTrumpDeck;

  @AfterEach
  final void afterEach() {
    testTrumpDeck.resetDeck();
    testCardDeck.resetDeck();
  }

  @Test
  void getSuit_GetSuit_GetDifferentSuit() throws Exception {
    testTrumpDeck.setTrump(testCardDeck.getCard(Game.CLUBS, SuitDeck.SEVEN));
    assertNotEquals(Game.SPADES, testTrumpDeck.getSuit());
  }

  @Test
  void getSuit_GetSuit_GetTrumpSuit() throws Exception {
    testTrumpDeck.setTrump(testCardDeck.getCard(Game.SPADES, SuitDeck.JACK));
    assertEquals(Game.SPADES, testTrumpDeck.getSuit());
  }

  @Test
  void resetDeck_EmptyTrumpDeck_TrumpDeckIsNull() throws Exception {
    testTrumpDeck.setTrump(testCardDeck.getCard(Game.HEARTS, SuitDeck.JACK));
    testTrumpDeck.resetDeck();
    assertNull(testTrumpDeck.getSuit());
  }

  @Test
  void setTrump_SetCardAsTrump_TrumpDeckHasOneCard() throws Exception {
    testTrumpDeck.setTrump(testCardDeck.getCard(Game.DIAMONDS, SuitDeck.ACE));
    assertEquals(1, testTrumpDeck.countCards());
  }

  @Test
  void setTrump_SetTrumpWhenTrumpIsBusy_NewTrumpIsNotOverridingTheOldOne() throws Exception {
    testTrumpDeck.setTrump(testCardDeck.getCard(Game.SPADES, SuitDeck.EIGHT));
    testTrumpDeck.setTrump(testCardDeck.getCard(Game.DIAMONDS, SuitDeck.NINE));
    assertEquals(Game.SPADES, testTrumpDeck.getSuit());
  }

  @Test
  void switchTrump_SwitchTrumpToNotTrumpSeven_FailedOldTrumpStays() throws Exception {
    testTrumpDeck.setTrump(testCardDeck.getCard(Game.HEARTS, SuitDeck.JACK));
    testTrumpDeck.switchTrump(testCardDeck.getCard(Game.SPADES, SuitDeck.SEVEN));

    assertEquals(new Card(Game.HEARTS, SuitDeck.JACK),
        testTrumpDeck.getCard(Game.HEARTS, SuitDeck.JACK));

  }

  @Test
  void switchTrump_SwitchTrumpToNotTrump_FailedOldTrumpStays() throws Exception {
    testTrumpDeck.setTrump(testCardDeck.getCard(Game.HEARTS, SuitDeck.JACK));
    testTrumpDeck.switchTrump(testCardDeck.getCard(Game.SPADES, SuitDeck.QUEEN));

    assertEquals(new Card(Game.HEARTS, SuitDeck.JACK),
        testTrumpDeck.getCard(Game.HEARTS, SuitDeck.JACK));

  }

  @Test
  void switchTrump_SwitchTrumpToOtherTrump_FailedOldTrumpStays() throws Exception {
    testTrumpDeck.setTrump(testCardDeck.getCard(Game.HEARTS, SuitDeck.JACK));
    testTrumpDeck.switchTrump(testCardDeck.getCard(Game.HEARTS, SuitDeck.QUEEN));

    assertEquals(new Card(Game.HEARTS, SuitDeck.JACK),
        testTrumpDeck.getCard(Game.HEARTS, SuitDeck.JACK));

  }

  @Test
  void switchTrump_SwitchTrumpToTrumpSeven_Success() throws Exception {
    testTrumpDeck.setTrump(testCardDeck.getCard(Game.HEARTS, SuitDeck.JACK));
    testTrumpDeck.switchTrump(testCardDeck.getCard(Game.HEARTS, SuitDeck.SEVEN));

    assertEquals(new Card(Game.HEARTS, SuitDeck.SEVEN),
        testTrumpDeck.getCard(Game.HEARTS, SuitDeck.SEVEN));
  }

  @Test
  void trumpDeck_NewTrumpDeck_CreateNewTrumpDeckObject() throws Exception {
    TrumpDeck testTrumpParameterDeck =
        new TrumpDeck(testCardDeck.getCard(Game.SPADES, SuitDeck.JACK));
    assertEquals(Game.SPADES, testTrumpParameterDeck.getSuit());
  }
}