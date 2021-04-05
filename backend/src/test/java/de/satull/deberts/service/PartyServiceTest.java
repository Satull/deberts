package de.satull.deberts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.satull.deberts.config.DebertsConfigTest;
import de.satull.deberts.exception.NoSuchCardException;
import de.satull.deberts.model.deck.CardDeck;
import de.satull.deberts.model.deck.HandDeck;
import de.satull.deberts.model.deck.TrumpDeck;
import de.satull.deberts.model.enums.Owner;
import de.satull.deberts.model.enums.Suit;
import de.satull.deberts.model.enums.SuitDeck;
import de.satull.deberts.model.web.Card;
import de.satull.deberts.model.web.Comparator;
import de.satull.deberts.model.web.ComparedCard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(DebertsConfigTest.class)
class PartyServiceTest {

  @Autowired HandDeck testBotHand;

  @Autowired CardDeck testCardDeck;

  @Autowired PartyService testPartyService;

  @Autowired HandDeck testPlayerHand;

  @Autowired RoundService testRoundService;

  @Autowired TrumpDeck testTrumpDeck;

  private void endRoundWithWinner(Card firstCardPlayer, Card secondCardBot)
      throws NoSuchCardException {
    testPartyService.switchPhase();
    testPartyService.playTrump(testTrumpDeck.getSuit(), Owner.BOT);
    testPartyService.switchPhase();
    testPlayerHand.addCard(firstCardPlayer);
    testBotHand.addCard(secondCardBot);

    Comparator comparator = new Comparator();
    comparator.setAttacker(new ComparedCard(Owner.PLAYER, firstCardPlayer));
    comparator.setDefender(new ComparedCard(Owner.BOT, secondCardBot));
    testRoundService.decideChallenge(comparator);

    testBotHand.resetDeck();
    testPlayerHand.resetDeck();
    testPartyService.switchPhase();
  }

  @AfterEach
  final void afterEach() {
    testPartyService.resetParty();
  }

  @Test
  void decideFirstTurn_PartyAndCards_BotsTurn() throws NoSuchCardException {
    final Card firstCardPlayer = testCardDeck.getCard(Suit.DIAMONDS, SuitDeck.QUEEN);
    final Card secondCardBot = testCardDeck.getCard(Suit.DIAMONDS, SuitDeck.KING);

    endRoundWithWinner(firstCardPlayer, secondCardBot);
    Assertions.assertEquals(Owner.BOT, testPartyService.decideTurn());
  }

  @Test
  void decideFirstTurn_PartyAndCards_NoWinnerRandomTurnIsNotUndefined() throws NoSuchCardException {
    final Card firstCardPlayer = testCardDeck.getCard(Suit.DIAMONDS, SuitDeck.SEVEN);
    final Card secondCardBot = testCardDeck.getCard(Suit.DIAMONDS, SuitDeck.EIGHT);

    endRoundWithWinner(firstCardPlayer, secondCardBot);
    assertNotEquals(Owner.UNDEFINED, testPartyService.decideTurn());
  }

  @Test
  void decideFirstTurn_PartyAndCards_PlayersTurn() throws NoSuchCardException {
    final Card firstCardPlayer = testCardDeck.getCard(Suit.DIAMONDS, SuitDeck.KING);
    final Card secondCardBot = testCardDeck.getCard(Suit.DIAMONDS, SuitDeck.QUEEN);

    endRoundWithWinner(firstCardPlayer, secondCardBot);
    assertEquals(Owner.PLAYER, testPartyService.decideTurn());
  }

  @Test
  void decideFirstTurn_PartyFirstRound_RandomTurnIsNotUndefined() throws NoSuchCardException {
    testPartyService.switchPhase();
    assertNotEquals(Owner.UNDEFINED, testPartyService.decideTurn());
  }

  @Test
  void getRoundHistory_Party_HandDeck_RoundHistoryHasOneLineInNewGame() throws NoSuchCardException {
    testPartyService.switchPhase();
    testPartyService.playTrump(testTrumpDeck.getSuit(), Owner.BOT);
    testPartyService.switchPhase();
    testBotHand.resetDeck();
    testPlayerHand.resetDeck();
    testPartyService.switchPhase();

    assertEquals(1, testPartyService.getRoundHistory().size());
  }

  @Test
  void getScore_Party_AtBeginningScoreEqualsZero() {
    testPartyService.resetParty();
    assertEquals(
        0,
        testPartyService.getScore().get(Owner.BOT) + testPartyService.getScore().get(Owner.PLAYER));
  }

  @Test
  void getScore_Party_TwoHandDeck_Round_InTheEndScoreNotEqualsZero() throws NoSuchCardException {
    final Card firstCardPlayer = testCardDeck.getCard(Suit.DIAMONDS, SuitDeck.JACK);
    final Card secondCardBot = testCardDeck.getCard(Suit.HEARTS, SuitDeck.JACK);

    endRoundWithWinner(firstCardPlayer, secondCardBot);

    assertNotEquals(
        0,
        testPartyService.getScore().get(Owner.BOT) + testPartyService.getScore().get(Owner.PLAYER));
  }

  @Test
  void resetParty_PartyTrumpDeck_TrumpAfterResetIsNull() throws NoSuchCardException {
    testPartyService.switchPhase();
    testPartyService.switchPhase();
    testPartyService.resetParty();

    assertNull(testTrumpDeck.getSuit());
  }

  @Test
  void switchPhase_PartyAndBotHand_switchPhaseThreeTimesBotCardsStillEquals9()
      throws NoSuchCardException {
    testPartyService.switchPhase();
    testPartyService.playTrump(testTrumpDeck.getSuit(), Owner.BOT);
    testPartyService.switchPhase();
    testPartyService.switchPhase();

    assertEquals(9, testBotHand.countCards());
  }

  @Test
  void switchPhase_PartyAndBotHand_switchPhaseThreeTimesNextGameBotCardsEquals6()
      throws NoSuchCardException {
    testPartyService.switchPhase();
    testPartyService.playTrump(testTrumpDeck.getSuit(), Owner.BOT);
    testPartyService.switchPhase();
    testBotHand.resetDeck();
    testPlayerHand.resetDeck();
    testPartyService.switchPhase();

    assertEquals(6, testBotHand.countCards());
  }

  @Test
  void switchPhase_PartyAndBotHand_switchPhaseToActionBotCardsEquals9() throws NoSuchCardException {
    testPartyService.switchPhase();
    testPartyService.playTrump(testTrumpDeck.getSuit(), Owner.BOT);
    testPartyService.switchPhase();

    assertEquals(9, testBotHand.countCards());
  }

  @Test
  void switchPhase_PartyAndBotHand_switchPhaseToTradeBotCardsEquals6() throws NoSuchCardException {
    testPartyService.switchPhase();

    assertEquals(6, testBotHand.countCards());
  }
}
