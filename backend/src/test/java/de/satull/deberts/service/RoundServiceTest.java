package de.satull.deberts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.satull.deberts.config.DebertsConfigTest;
import de.satull.deberts.exception.NoSuchCardException;
import de.satull.deberts.model.web.Card;
import de.satull.deberts.model.web.Comparator;
import de.satull.deberts.model.web.ComparedCard;
import de.satull.deberts.model.enums.Owner;
import de.satull.deberts.model.enums.Suit;
import de.satull.deberts.model.enums.SuitDeck;
import de.satull.deberts.model.deck.CardDeck;
import de.satull.deberts.model.deck.HandDeck;
import de.satull.deberts.model.deck.TrumpDeck;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(DebertsConfigTest.class)
class RoundServiceTest {

  @Autowired HandDeck testBotHand;

  @Autowired CardDeck testCardDeck;

  @Autowired
  PartyService testPartyService;

  @Autowired HandDeck testPlayerHand;

  @Autowired
  RoundService testRoundService;

  @Autowired TrumpDeck testTrumpDeck;

  private void simulateCards(Card trumpCard, Card attackerCard, Card defenderCard)
      throws NoSuchCardException {
    testPartyService.switchPhase();
    testPartyService.switchPhase();
    testPlayerHand.addCard(defenderCard);
    testBotHand.addCard(attackerCard);
    testTrumpDeck.resetDeck();
    testTrumpDeck.setTrump(trumpCard);
  }

  @AfterEach
  final void afterEach() {
    testPartyService.resetParty();
  }

  @Test
  void decideChallenge_TwoCardsAttackerHasTrump_AttackerWins() throws NoSuchCardException {
    final Card trumpCard = testCardDeck.getCard(Suit.SPADES, SuitDeck.QUEEN);
    final Card attackerCard = testCardDeck.getCard(Suit.SPADES, SuitDeck.TEN);
    final Card defenderCard = testCardDeck.getCard(Suit.HEARTS, SuitDeck.ACE);

    simulateCards(trumpCard, defenderCard, attackerCard);

    Comparator comparator = new Comparator();
    comparator.setAttacker(new ComparedCard(Owner.PLAYER, attackerCard));
    comparator.setDefender(new ComparedCard(Owner.BOT, defenderCard));
    testRoundService.decideChallenge(comparator);

    assertEquals(21, testRoundService.getScore().get(Owner.PLAYER));
    assertEquals(Owner.PLAYER, testRoundService.getTurn());
  }

  @Test
  void decideChallenge_TwoCardsDefenderHasTrump_DefenderWins() throws NoSuchCardException {
    final Card trumpCard = testCardDeck.getCard(Suit.SPADES, SuitDeck.QUEEN);
    final Card attackerCard = testCardDeck.getCard(Suit.HEARTS, SuitDeck.TEN);
    final Card defenderCard = testCardDeck.getCard(Suit.SPADES, SuitDeck.ACE);

    simulateCards(trumpCard, defenderCard, attackerCard);

    Comparator comparator = new Comparator();
    comparator.setAttacker(new ComparedCard(Owner.PLAYER, attackerCard));
    comparator.setDefender(new ComparedCard(Owner.BOT, defenderCard));
    testRoundService.decideChallenge(comparator);

    assertEquals(21, testRoundService.getScore().get(Owner.BOT));
    assertEquals(Owner.BOT, testRoundService.getTurn());
  }

  @Test
  void decideChallenge_TwoCardsDifferentSuitsNotTrump_AttackerWins() throws NoSuchCardException {
    final Card trumpCard = testCardDeck.getCard(Suit.CLUBS, SuitDeck.QUEEN);
    final Card attackerCard = testCardDeck.getCard(Suit.DIAMONDS, SuitDeck.TEN);
    final Card defenderCard = testCardDeck.getCard(Suit.SPADES, SuitDeck.ACE);

    simulateCards(trumpCard, defenderCard, attackerCard);

    Comparator comparator = new Comparator();
    comparator.setAttacker(new ComparedCard(Owner.PLAYER, attackerCard));
    comparator.setDefender(new ComparedCard(Owner.BOT, defenderCard));
    testRoundService.decideChallenge(comparator);

    assertEquals(21, testRoundService.getScore().get(Owner.PLAYER));
    assertEquals(Owner.PLAYER, testRoundService.getTurn());
  }

  @Test
  void decideChallenge_TwoCardsSameSuitsNotTrump_AttackerWins() throws NoSuchCardException {
    final Card trumpCard = testCardDeck.getCard(Suit.CLUBS, SuitDeck.QUEEN);
    final Card attackerCard = testCardDeck.getCard(Suit.DIAMONDS, SuitDeck.TEN);
    final Card defenderCard = testCardDeck.getCard(Suit.DIAMONDS, SuitDeck.JACK);

    simulateCards(trumpCard, attackerCard, defenderCard);

    Comparator comparator = new Comparator();
    comparator.setAttacker(new ComparedCard(Owner.BOT, attackerCard));
    comparator.setDefender(new ComparedCard(Owner.PLAYER, defenderCard));
    testRoundService.decideChallenge(comparator);

    assertEquals(12, testRoundService.getScore().get(Owner.BOT));
    assertEquals(Owner.BOT, testRoundService.getTurn());
  }

  @Test
  void decideChallenge_TwoCardsSameSuitsNotTrump_DefenderWins() throws NoSuchCardException {
    final Card trumpCard = testCardDeck.getCard(Suit.SPADES, SuitDeck.QUEEN);
    final Card attackerCard = testCardDeck.getCard(Suit.HEARTS, SuitDeck.TEN);
    final Card defenderCard = testCardDeck.getCard(Suit.HEARTS, SuitDeck.ACE);

    simulateCards(trumpCard, defenderCard, attackerCard);

    Comparator comparator = new Comparator();
    comparator.setAttacker(new ComparedCard(Owner.PLAYER, attackerCard));
    comparator.setDefender(new ComparedCard(Owner.BOT, defenderCard));
    testRoundService.decideChallenge(comparator);

    assertEquals(21, testRoundService.getScore().get(Owner.BOT));
    assertEquals(Owner.BOT, testRoundService.getTurn());
  }

  @Test
  void decideChallenge_TwoCardsWithoutPointsSameSuit_DefenderWinsGetsLastCard()
      throws NoSuchCardException {
    final Card trumpCard = testCardDeck.getCard(Suit.CLUBS, SuitDeck.QUEEN);
    final Card attackerCard = testCardDeck.getCard(Suit.DIAMONDS, SuitDeck.SEVEN);
    final Card defenderCard = testCardDeck.getCard(Suit.DIAMONDS, SuitDeck.EIGHT);

    testPartyService.switchPhase();
    testPartyService.switchPhase();
    testPlayerHand.resetDeck();
    testPlayerHand.addCard(attackerCard);
    testBotHand.resetDeck();
    testBotHand.addCard(defenderCard);
    testTrumpDeck.resetDeck();
    testTrumpDeck.setTrump(trumpCard);

    Comparator comparator = new Comparator();
    comparator.setAttacker(new ComparedCard(Owner.PLAYER, attackerCard));
    comparator.setDefender(new ComparedCard(Owner.BOT, defenderCard));
    testRoundService.decideChallenge(comparator);

    assertEquals(10, testRoundService.getScore().get(Owner.BOT));
    assertEquals(Owner.BOT, testRoundService.getTurn());
  }

  @Test
  void decideChallenge_TwoTrumpCards_AttackerWins() throws NoSuchCardException {
    final Card trumpCard = testCardDeck.getCard(Suit.DIAMONDS, SuitDeck.QUEEN);
    final Card attackerCard = testCardDeck.getCard(Suit.DIAMONDS, SuitDeck.JACK);
    final Card defenderCard = testCardDeck.getCard(Suit.DIAMONDS, SuitDeck.NINE);

    simulateCards(trumpCard, defenderCard, attackerCard);

    Comparator comparator = new Comparator();
    comparator.setAttacker(new ComparedCard(Owner.PLAYER, attackerCard));
    comparator.setDefender(new ComparedCard(Owner.BOT, defenderCard));
    testRoundService.decideChallenge(comparator);

    assertEquals(34, testRoundService.getScore().get(Owner.PLAYER));
    assertEquals(Owner.PLAYER, testRoundService.getTurn());
  }

  @Test
  void resetRound_PartyAndRound_SwitchPhaseToActionPhaseAndResetRound() throws NoSuchCardException {
    testPartyService.switchPhase();
    testPartyService.switchPhase();
    testRoundService.resetRound();

    assertEquals(32, testCardDeck.countCards());
  }

  @Test
  void switchPhaseToAction_Round_SwitchPhaseToActionPhase() throws NoSuchCardException {
    testRoundService.switchPhaseToTrade();
    testRoundService.switchPhaseToAction();

    assertEquals(13, testCardDeck.countCards());
  }

  @Test
  void switchPhaseToTrade_Round_SwitchPhaseToTradePhase() throws NoSuchCardException {
    testRoundService.switchPhaseToTrade();

    assertEquals(19, testCardDeck.countCards());
  }
}
