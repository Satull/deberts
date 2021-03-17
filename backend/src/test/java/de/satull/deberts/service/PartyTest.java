package de.satull.deberts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.satull.deberts.config.DebertsConfigTest;
import de.satull.deberts.exception.NoSuchCardException;
import de.satull.deberts.model.Card;
import de.satull.deberts.model.Comparator;
import de.satull.deberts.model.ComparedCard;
import de.satull.deberts.model.Owner;
import de.satull.deberts.model.Suit;
import de.satull.deberts.model.SuitDeck;
import de.satull.deberts.model.deck.CardDeck;
import de.satull.deberts.model.deck.HandDeck;
import de.satull.deberts.model.deck.TrumpDeck;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(DebertsConfigTest.class)
class PartyTest {

  @Autowired HandDeck testBotHand;

  @Autowired CardDeck testCardDeck;

  @Autowired Party testParty;

  @Autowired HandDeck testPlayerHand;

  @Autowired Round testRound;

  @Autowired TrumpDeck testTrumpDeck;

  private void endRoundWithWinner(Card firstCardPlayer, Card secondCardBot)
      throws NoSuchCardException {
    testParty.switchPhase();
    testParty.playTrump(testTrumpDeck.getSuit(), Owner.BOT);
    testParty.switchPhase();
    testPlayerHand.addCard(firstCardPlayer);
    testBotHand.addCard(secondCardBot);

    Comparator comparator = new Comparator();
    comparator.setAttacker(new ComparedCard(Owner.PLAYER, firstCardPlayer));
    comparator.setDefender(new ComparedCard(Owner.BOT, secondCardBot));
    testRound.decideChallenge(comparator);

    testBotHand.resetDeck();
    testPlayerHand.resetDeck();
    testParty.switchPhase();
  }

  @AfterEach
  final void afterEach() {
    testParty.resetParty();
  }

  @Test
  void decideFirstTurn_PartyAndCards_BotsTurn() throws NoSuchCardException {
    final Card firstCardPlayer = testCardDeck.getCard(Suit.DIAMONDS, SuitDeck.QUEEN);
    final Card secondCardBot = testCardDeck.getCard(Suit.DIAMONDS, SuitDeck.KING);

    endRoundWithWinner(firstCardPlayer, secondCardBot);
    Assertions.assertEquals(Owner.BOT, testParty.decideTurn());
  }

  @Test
  void decideFirstTurn_PartyAndCards_NoWinnerRandomTurnIsNotUndefined() throws NoSuchCardException {
    final Card firstCardPlayer = testCardDeck.getCard(Suit.DIAMONDS, SuitDeck.SEVEN);
    final Card secondCardBot = testCardDeck.getCard(Suit.DIAMONDS, SuitDeck.EIGHT);

    endRoundWithWinner(firstCardPlayer, secondCardBot);
    assertNotEquals(Owner.UNDEFINED, testParty.decideTurn());
  }

  @Test
  void decideFirstTurn_PartyAndCards_PlayersTurn() throws NoSuchCardException {
    final Card firstCardPlayer = testCardDeck.getCard(Suit.DIAMONDS, SuitDeck.KING);
    final Card secondCardBot = testCardDeck.getCard(Suit.DIAMONDS, SuitDeck.QUEEN);

    endRoundWithWinner(firstCardPlayer, secondCardBot);
    assertEquals(Owner.PLAYER, testParty.decideTurn());
  }

  @Test
  void decideFirstTurn_PartyFirstRound_RandomTurnIsNotUndefined() throws NoSuchCardException {
    testParty.switchPhase();
    assertNotEquals(Owner.UNDEFINED, testParty.decideTurn());
  }

  @Test
  void getRoundHistory_Party_HandDeck_RoundHistoryHasOneLineInNewGame() throws NoSuchCardException {
    testParty.switchPhase();
    testParty.playTrump(testTrumpDeck.getSuit(), Owner.BOT);
    testParty.switchPhase();
    testBotHand.resetDeck();
    testPlayerHand.resetDeck();
    testParty.switchPhase();

    assertEquals(1, testParty.getRoundHistory().size());
  }

  @Test
  void getScore_Party_AtBeginningScoreEqualsZero() {
    testParty.resetParty();
    assertEquals(0, testParty.getScore().get(Owner.BOT) + testParty.getScore().get(Owner.PLAYER));
  }

  @Test
  void getScore_Party_TwoHandDeck_Round_InTheEndScoreNotEqualsZero() throws NoSuchCardException {
    final Card firstCardPlayer = testCardDeck.getCard(Suit.DIAMONDS, SuitDeck.JACK);
    final Card secondCardBot = testCardDeck.getCard(Suit.HEARTS, SuitDeck.JACK);

    endRoundWithWinner(firstCardPlayer, secondCardBot);

    assertNotEquals(
        0, testParty.getScore().get(Owner.BOT) + testParty.getScore().get(Owner.PLAYER));
  }

  @Test
  void resetParty_PartyTrumpDeck_TrumpAfterResetIsNull() throws NoSuchCardException {
    testParty.switchPhase();
    testParty.switchPhase();
    testParty.resetParty();

    assertNull(testTrumpDeck.getSuit());
  }

  @Test
  void switchPhase_PartyAndBotHand_switchPhaseThreeTimesBotCardsStillEquals9()
      throws NoSuchCardException {
    testParty.switchPhase();
    testParty.playTrump(testTrumpDeck.getSuit(), Owner.BOT);
    testParty.switchPhase();
    testParty.switchPhase();

    assertEquals(9, testBotHand.countCards());
  }

  @Test
  void switchPhase_PartyAndBotHand_switchPhaseThreeTimesNextGameBotCardsEquals6()
      throws NoSuchCardException {
    testParty.switchPhase();
    testParty.playTrump(testTrumpDeck.getSuit(), Owner.BOT);
    testParty.switchPhase();
    testBotHand.resetDeck();
    testPlayerHand.resetDeck();
    testParty.switchPhase();

    assertEquals(6, testBotHand.countCards());
  }

  @Test
  void switchPhase_PartyAndBotHand_switchPhaseToActionBotCardsEquals9() throws NoSuchCardException {
    testParty.switchPhase();
    testParty.playTrump(testTrumpDeck.getSuit(), Owner.BOT);
    testParty.switchPhase();

    assertEquals(9, testBotHand.countCards());
  }

  @Test
  void switchPhase_PartyAndBotHand_switchPhaseToTradeBotCardsEquals6() throws NoSuchCardException {
    testParty.switchPhase();

    assertEquals(6, testBotHand.countCards());
  }
}
