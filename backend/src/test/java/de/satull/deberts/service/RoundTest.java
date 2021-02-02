package de.satull.deberts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;


import de.satull.deberts.config.DebertsConfigTest;
import de.satull.deberts.exception.NoSuchCardException;
import de.satull.deberts.model.Card;
import de.satull.deberts.model.Comparator;
import de.satull.deberts.model.ComparedCard;
import de.satull.deberts.model.SuitDeck;
import de.satull.deberts.model.deck.CardDeck;
import de.satull.deberts.model.deck.HandDeck;
import de.satull.deberts.model.deck.TrumpDeck;
import de.satull.deberts.util.Game;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(DebertsConfigTest.class)
class RoundTest {

  @Autowired
  HandDeck testBotHand;

  @Autowired
  CardDeck testCardDeck;

  @Autowired
  Party testParty;

  @Autowired
  HandDeck testPlayerHand;

  @Autowired
  Round testRound;

  @Autowired
  TrumpDeck testTrumpDeck;

  private void simulateCards(Card trumpCard, Card attackerCard, Card defenderCard)
      throws NoSuchCardException {
    testParty.switchPhase();
    testParty.switchPhase();
    testPlayerHand.addCard(defenderCard);
    testBotHand.addCard(attackerCard);
    testTrumpDeck.resetDeck();
    testTrumpDeck.setTrump(trumpCard);
  }

  @AfterEach
  final void afterEach() {
    testParty.resetParty();
  }

  @Test
  void decideChallenge_TwoCardsAttackerHasTrump_AttackerWins() throws NoSuchCardException {
    final Card trumpCard = testCardDeck.getCard("spades", SuitDeck.QUEEN);
    final Card attackerCard = testCardDeck.getCard("spades", SuitDeck.TEN);
    final Card defenderCard = testCardDeck.getCard("hearts", SuitDeck.ACE);

    simulateCards(trumpCard, defenderCard, attackerCard);

    Comparator comparator = new Comparator();
    comparator.setAttacker(new ComparedCard(Game.PLAYER, attackerCard));
    comparator.setDefender(new ComparedCard(Game.BOT, defenderCard));
    testRound.decideChallenge(comparator);

    assertEquals(21, testRound.getScore().get(Game.PLAYER));
    assertEquals(Game.PLAYER, testRound.getTurn());
  }

  @Test
  void decideChallenge_TwoCardsDefenderHasTrump_DefenderWins() throws NoSuchCardException {
    final Card trumpCard = testCardDeck.getCard("spades", SuitDeck.QUEEN);
    final Card attackerCard = testCardDeck.getCard("hearts", SuitDeck.TEN);
    final Card defenderCard = testCardDeck.getCard("spades", SuitDeck.ACE);

    simulateCards(trumpCard, defenderCard, attackerCard);

    Comparator comparator = new Comparator();
    comparator.setAttacker(new ComparedCard(Game.PLAYER, attackerCard));
    comparator.setDefender(new ComparedCard(Game.BOT, defenderCard));
    testRound.decideChallenge(comparator);

    assertEquals(21, testRound.getScore().get(Game.BOT));
    assertEquals(Game.BOT, testRound.getTurn());
  }

  @Test
  void decideChallenge_TwoCardsDifferentSuitsNotTrump_AttackerWins() throws NoSuchCardException {
    final Card trumpCard = testCardDeck.getCard("clubs", SuitDeck.QUEEN);
    final Card attackerCard = testCardDeck.getCard(Game.DIAMONDS, SuitDeck.TEN);
    final Card defenderCard = testCardDeck.getCard("spades", SuitDeck.ACE);

    simulateCards(trumpCard, defenderCard, attackerCard);

    Comparator comparator = new Comparator();
    comparator.setAttacker(new ComparedCard(Game.PLAYER, attackerCard));
    comparator.setDefender(new ComparedCard(Game.BOT, defenderCard));
    testRound.decideChallenge(comparator);

    assertEquals(21, testRound.getScore().get(Game.PLAYER));
    assertEquals(Game.PLAYER, testRound.getTurn());
  }

  @Test
  void decideChallenge_TwoCardsSameSuitsNotTrump_AttackerWins() throws NoSuchCardException {
    final Card trumpCard = testCardDeck.getCard("clubs", SuitDeck.QUEEN);
    final Card attackerCard = testCardDeck.getCard(Game.DIAMONDS, SuitDeck.TEN);
    final Card defenderCard = testCardDeck.getCard(Game.DIAMONDS, SuitDeck.JACK);

    simulateCards(trumpCard, attackerCard, defenderCard);

    Comparator comparator = new Comparator();
    comparator.setAttacker(new ComparedCard(Game.BOT, attackerCard));
    comparator.setDefender(new ComparedCard(Game.PLAYER, defenderCard));
    testRound.decideChallenge(comparator);

    assertEquals(12, testRound.getScore().get(Game.BOT));
    assertEquals(Game.BOT, testRound.getTurn());
  }

  @Test
  void decideChallenge_TwoCardsSameSuitsNotTrump_DefenderWins() throws NoSuchCardException {
    final Card trumpCard = testCardDeck.getCard("spades", SuitDeck.QUEEN);
    final Card attackerCard = testCardDeck.getCard("hearts", SuitDeck.TEN);
    final Card defenderCard = testCardDeck.getCard("hearts", SuitDeck.ACE);

    simulateCards(trumpCard, defenderCard, attackerCard);

    Comparator comparator = new Comparator();
    comparator.setAttacker(new ComparedCard(Game.PLAYER, attackerCard));
    comparator.setDefender(new ComparedCard(Game.BOT, defenderCard));
    testRound.decideChallenge(comparator);

    assertEquals(21, testRound.getScore().get(Game.BOT));
    assertEquals(Game.BOT, testRound.getTurn());
  }

  @Test
  void decideChallenge_TwoCardsWithoutPointsSameSuit_DefenderWinsGetsLastCard()
      throws NoSuchCardException {
    final Card trumpCard = testCardDeck.getCard("clubs", SuitDeck.QUEEN);
    final Card attackerCard = testCardDeck.getCard(Game.DIAMONDS, SuitDeck.SEVEN);
    final Card defenderCard = testCardDeck.getCard(Game.DIAMONDS, SuitDeck.EIGHT);

    testParty.switchPhase();
    testParty.switchPhase();
    testPlayerHand.resetDeck();
    testPlayerHand.addCard(attackerCard);
    testBotHand.resetDeck();
    testBotHand.addCard(defenderCard);
    testTrumpDeck.resetDeck();
    testTrumpDeck.setTrump(trumpCard);

    Comparator comparator = new Comparator();
    comparator.setAttacker(new ComparedCard(Game.PLAYER, attackerCard));
    comparator.setDefender(new ComparedCard(Game.BOT, defenderCard));
    testRound.decideChallenge(comparator);

    assertEquals(10, testRound.getScore().get(Game.BOT));
    assertEquals(Game.BOT, testRound.getTurn());
  }

  @Test
  void decideChallenge_TwoTrumpCards_AttackerWins() throws NoSuchCardException {
    final Card trumpCard = testCardDeck.getCard(Game.DIAMONDS, SuitDeck.QUEEN);
    final Card attackerCard = testCardDeck.getCard(Game.DIAMONDS, SuitDeck.JACK);
    final Card defenderCard = testCardDeck.getCard(Game.DIAMONDS, SuitDeck.NINE);

    simulateCards(trumpCard, defenderCard, attackerCard);

    Comparator comparator = new Comparator();
    comparator.setAttacker(new ComparedCard(Game.PLAYER, attackerCard));
    comparator.setDefender(new ComparedCard(Game.BOT, defenderCard));
    testRound.decideChallenge(comparator);

    assertEquals(34, testRound.getScore().get(Game.PLAYER));
    assertEquals(Game.PLAYER, testRound.getTurn());
  }

  @Test
  void resetRound_PartyAndRound_SwitchPhaseToActionPhaseAndResetRound() throws NoSuchCardException {
    testParty.switchPhase();
    testParty.switchPhase();
    testRound.resetRound();

    assertEquals(32, testCardDeck.countCards());
  }

  @Test
  void switchPhaseToAction_Round_SwitchPhaseToActionPhase() throws NoSuchCardException {
    testRound.switchPhaseToTrade();
    testRound.switchPhaseToAction();

    assertEquals(13, testCardDeck.countCards());
  }

  @Test
  void switchPhaseToTrade_Round_SwitchPhaseToTradePhase() throws NoSuchCardException {
    testRound.switchPhaseToTrade();

    assertEquals(19, testCardDeck.countCards());
  }

}