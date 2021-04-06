package de.satull.deberts.model.deck;

import static org.assertj.core.api.Assertions.assertThat;

import de.satull.deberts.config.DebertsConfigTest;
import de.satull.deberts.exception.NoSuchCardException;
import de.satull.deberts.model.enums.Suit;
import de.satull.deberts.model.enums.SuitDeck;
import de.satull.deberts.model.web.Card;
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
    assertThat(testTrumpDeck.getSuit()).isNotEqualTo(Suit.SPADES);
  }

  @Test
  void getSuit_GetSuit_GetTrumpSuit() throws NoSuchCardException {
    testTrumpDeck.setTrump(testCardDeck.getCard(Suit.SPADES, SuitDeck.JACK));
    assertThat(testTrumpDeck.getSuit()).isEqualTo(Suit.SPADES);
  }

  @Test
  void resetDeck_EmptyTrumpDeck_TrumpDeckIsNull() throws NoSuchCardException {
    testTrumpDeck.setTrump(testCardDeck.getCard(Suit.HEARTS, SuitDeck.JACK));
    testTrumpDeck.resetDeck();
    assertThat(testTrumpDeck.getSuit()).isNull();
  }

  @Test
  void setTrump_SetCardAsTrump_TrumpDeckHasOneCard() throws NoSuchCardException {
    testTrumpDeck.setTrump(testCardDeck.getCard(Suit.DIAMONDS, SuitDeck.ACE));
    assertThat(testTrumpDeck.countCards()).isOne();
  }

  @Test
  void setTrump_SetTrumpWhenTrumpIsBusy_NewTrumpIsNotOverridingTheOldOne()
      throws NoSuchCardException {
    testTrumpDeck.setTrump(testCardDeck.getCard(Suit.SPADES, SuitDeck.EIGHT));
    testTrumpDeck.setTrump(testCardDeck.getCard(Suit.DIAMONDS, SuitDeck.NINE));
    assertThat(testTrumpDeck.getSuit()).isEqualTo(Suit.SPADES);
  }

  @Test
  void switchTrump_SwitchTrumpToNotTrumpSeven_FailedOldTrumpStays() throws NoSuchCardException {
    final Card expectedCard = new Card(Suit.HEARTS, SuitDeck.JACK);

    testTrumpDeck.setTrump(testCardDeck.getCard(Suit.HEARTS, SuitDeck.JACK));
    testTrumpDeck.switchTrump(testCardDeck.getCard(Suit.SPADES, SuitDeck.SEVEN));

    assertThat(testTrumpDeck.getCard(Suit.HEARTS, SuitDeck.JACK)).isEqualTo(expectedCard);
  }

  @Test
  void switchTrump_SwitchTrumpToNotTrump_FailedOldTrumpStays() throws NoSuchCardException {
    final Card expectedCard = new Card(Suit.HEARTS, SuitDeck.JACK);

    testTrumpDeck.setTrump(testCardDeck.getCard(Suit.HEARTS, SuitDeck.JACK));
    testTrumpDeck.switchTrump(testCardDeck.getCard(Suit.SPADES, SuitDeck.QUEEN));

    assertThat(testTrumpDeck.getCard(Suit.HEARTS, SuitDeck.JACK)).isEqualTo(expectedCard);
  }

  @Test
  void switchTrump_SwitchTrumpToTrumpSeven_Success() throws NoSuchCardException {
    final Card expectedCard = new Card(Suit.HEARTS, SuitDeck.SEVEN);

    testTrumpDeck.setTrump(testCardDeck.getCard(Suit.HEARTS, SuitDeck.JACK));
    testTrumpDeck.switchTrump(testCardDeck.getCard(Suit.HEARTS, SuitDeck.SEVEN));

    assertThat(testTrumpDeck.getCard(Suit.HEARTS, SuitDeck.SEVEN)).isEqualTo(expectedCard);
  }

  @Test
  void trumpDeck_NewTrumpDeck_CreateNewTrumpDeckObject() throws NoSuchCardException {
    TrumpDeck testTrumpParameterDeck =
        new TrumpDeck(testCardDeck.getCard(Suit.SPADES, SuitDeck.JACK));
    assertThat(testTrumpParameterDeck.getSuit()).isEqualTo(Suit.SPADES);
  }
}
