package de.satull.deberts.model.deck;

import static org.assertj.core.api.Assertions.assertThat;

import de.satull.deberts.config.DebertsConfigTest;
import de.satull.deberts.exception.NoSuchCardException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(DebertsConfigTest.class)
class HandDeckTest {

  @Autowired CardDeck testCardDeck;

  @Autowired HandDeck testPlayerHand;

  @Test
  void addCard_HandDeck_AddTwoCardsIntoHandDeck() throws NoSuchCardException {
    final int expectedCardsNumber = 2;
    testPlayerHand.addCard(testCardDeck.getRandomCard());
    testPlayerHand.addCard(testCardDeck.getRandomCard());
    assertThat(testPlayerHand.countCards()).isEqualTo(expectedCardsNumber);
  }

  @Test
  void resetDeck_HandDeck_ZeroCardsAfterReset() throws NoSuchCardException {
    testPlayerHand.addCard(testCardDeck.getRandomCard());
    testPlayerHand.resetDeck();
    assertThat(testPlayerHand.countCards()).isZero();
  }

  @AfterEach
  final void afterEach() {
    testPlayerHand.resetDeck();
    testCardDeck.resetDeck();
  }
}
