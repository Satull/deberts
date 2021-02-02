package de.satull.deberts.model.deck;

import static org.junit.jupiter.api.Assertions.assertEquals;


import de.satull.deberts.config.DebertsConfigTest;
import de.satull.deberts.exception.NoSuchCardException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(DebertsConfigTest.class)
class HandDeckTest {

  @Autowired
  CardDeck testCardDeck;

  @Autowired
  HandDeck testPlayerHand;

  @Test
  void addCard_HandDeck_AddTwoCardsIntoHandDeck() throws NoSuchCardException {
    testPlayerHand.addCard(testCardDeck.getRandomCard());
    testPlayerHand.addCard(testCardDeck.getRandomCard());
    assertEquals(2, testPlayerHand.countCards());
    testPlayerHand.resetDeck();
    testCardDeck.resetDeck();
  }

  @Test
  void resetDeck_HandDeck_ZeroCardsAfterReset() throws NoSuchCardException {
    testPlayerHand.addCard(testCardDeck.getRandomCard());
    testPlayerHand.resetDeck();
    assertEquals(0, testPlayerHand.countCards());
  }
}