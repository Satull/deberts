package de.satull.deberts.service;

import de.satull.deberts.exception.NoSuchCardException;
import de.satull.deberts.model.Owner;
import de.satull.deberts.model.Suit;
import de.satull.deberts.model.deck.HandDeck;
import de.satull.deberts.util.Game;
import java.lang.invoke.MethodHandles;
import java.util.LinkedHashMap;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Represents the party which will go until one of the players will get 501 points.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.5
 * @since 1.0
 */
public class Party {

  private static final Logger LOG =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
  private final HandDeck botHand;
  private final HandDeck playerHand;
  private final Round round;
  private int botScore;
  private int roundNumber;
  private LinkedHashMap<Owner, Integer> score;
  private int phase;
  private int playerScore;
  private LinkedHashMap<Integer, LinkedHashMap<Owner, Integer>> roundHistory;
  private boolean trumpDefined = false;

  /**
   * Parametrised constructor for dependency injection.
   *
   * @param botHand bot cards
   * @param playerHand player cards
   * @param round round information in the party
   */
  @Autowired
  public Party(HandDeck botHand, HandDeck playerHand, Round round) {
    this.botHand = botHand;
    this.playerHand = playerHand;
    this.round = round;
    initParty();
  }

  /**
   * decides the turn based on the round history
   *
   * @return player name who starts the round
   */
  public Owner decideTurn() {
    if (roundHistory.isEmpty()
        || roundHistory
            .get(roundNumber)
            .get(Owner.BOT)
            .equals(roundHistory.get(roundNumber).get(Owner.PLAYER))) {
      return Math.random() < 0.5 ? Owner.BOT : Owner.PLAYER;
    } else {
      return roundHistory.get(roundNumber).get(Owner.BOT)
              > roundHistory.get(roundNumber).get(Owner.PLAYER)
          ? Owner.BOT
          : Owner.PLAYER;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Party)) {
      return false;
    }
    Party party = (Party) o;
    return botScore == party.botScore
        && roundNumber == party.roundNumber
        && getPhase() == party.getPhase()
        && playerScore == party.playerScore
        && trumpDefined == party.trumpDefined
        && Objects.equals(botHand, party.botHand)
        && Objects.equals(playerHand, party.playerHand)
        && Objects.equals(round, party.round)
        && Objects.equals(getScore(), party.getScore())
        && Objects.equals(getRoundHistory(), party.getRoundHistory());
  }

  /**
   * Each round has a trade phase with 6 and game phase with 9 cards.
   *
   * @return 6 for the trade phase and 9 for the game phase
   */
  public int getPhase() {
    return phase;
  }

  /**
   * Gets a Map with the round number and its information
   *
   * @return HashMap with the history of all rounds
   */
  public LinkedHashMap<Integer, LinkedHashMap<Owner, Integer>> getRoundHistory() {
    return roundHistory;
  }

  /**
   * Gets the score
   *
   * @return score
   */
  public LinkedHashMap<Owner, Integer> getScore() {
    return score;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        botHand,
        playerHand,
        round,
        botScore,
        roundNumber,
        getScore(),
        getPhase(),
        playerScore,
        getRoundHistory(),
        trumpDefined);
  }

  /**
   * Decides who picked the trump and which one is it.
   *
   * @param trump picked trump
   * @param owner player name who picked the trump
   * @throws NoSuchCardException removed card is not in the deck
   */
  public void playTrump(Suit trump, Owner owner) throws NoSuchCardException {
    round.playTrump(trump, owner);
    trumpDefined = true;
  }

  /** resets party between two players. */
  public void resetParty() {
    initParty();
    round.resetRound();
    phase = Game.START;
    trumpDefined = false;
  }

  /**
   * Switches a round to the next phase.
   *
   * @throws NoSuchCardException removed card is not in the deck
   */
  public void switchPhase() throws NoSuchCardException {
    if (phase == Game.START) {
      round.switchPhaseToTrade();
      round.setTurn(decideTurn());
      phase = Game.TRADE;

    } else if (phase == Game.TRADE && trumpDefined) {
      round.switchPhaseToAction();
      phase = Game.ACTION;

    } else if (phase == Game.ACTION && playerHand.countCards() + botHand.countCards() == 0) {
      finishRound();
      round.resetRound();
      round.switchPhaseToTrade();
      round.setTurn(decideTurn());
      phase = Game.TRADE;
    }
  }

  /**
   * Switches a trump in the actual round.
   *
   * @throws NoSuchCardException removed card is not in the deck
   */
  public void switchTrump() throws NoSuchCardException {
    round.switchTrump();
  }

  @Override
  public String toString() {
    return "Party{"
        + "botHand="
        + botHand
        + ", playerHand="
        + playerHand
        + ", round="
        + round
        + ", botScore="
        + botScore
        + ", roundNumber="
        + roundNumber
        + ", score="
        + score
        + ", phase="
        + phase
        + ", playerScore="
        + playerScore
        + ", roundHistory="
        + roundHistory
        + ", trumpDefined="
        + trumpDefined
        + '}';
  }

  private void finishRound() {
    if (round.getScore().get(playerHand.owner) < round.getScore().get(botHand.owner)
        && round.getTrumpPicker().equals(playerHand.owner)) {
      LOG.debug("player lost his trump-round");
      round.setScore(
          botHand.owner,
          round.getScore().get(playerHand.owner) + round.getScore().get(botHand.owner));
      round.setScore(playerHand.owner, 0);
      botScore += round.getScore().get(botHand.owner) + round.getScore().get(playerHand.owner);

    } else if (round.getScore().get(botHand.owner) < round.getScore().get(playerHand.owner)
        && round.getTrumpPicker().equals(botHand.owner)) {
      LOG.debug("bot lost his trump-round");
      round.setScore(
          playerHand.owner,
          round.getScore().get(playerHand.owner) + round.getScore().get(botHand.owner));
      round.setScore(botHand.owner, 0);
      playerScore += round.getScore().get(playerHand.owner) + round.getScore().get(botHand.owner);

    } else {
      LOG.debug("normal round");
      playerScore += round.getScore().get(playerHand.owner);
      botScore += round.getScore().get(botHand.owner);
    }

    roundHistory.putIfAbsent(++roundNumber, round.getScore());
    LOG.info("Round history: " + roundHistory.toString());
    score.replace(playerHand.owner, playerScore);
    score.replace(botHand.owner, botScore);
  }

  private void initParty() {
    score = new LinkedHashMap<>();
    score.put(Owner.PLAYER, 0);
    score.put(Owner.BOT, 0);
    playerScore = 0;
    botScore = 0;
    roundNumber = 0;
    roundHistory = new LinkedHashMap<>();
  }
}
