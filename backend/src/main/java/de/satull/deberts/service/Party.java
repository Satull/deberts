package de.satull.deberts.service;

import de.satull.deberts.deck.HandDeck;
import de.satull.deberts.util.Game;
import java.lang.invoke.MethodHandles;
import java.util.LinkedHashMap;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class Party {

  private static final Logger LOG =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
  private final HandDeck botHand;
  private final HandDeck playerHand;
  private final Round round;
  private int botScore;
  private int roundNumber;
  private LinkedHashMap<String, Integer> score;
  private int phase;
  private int playerScore;
  private LinkedHashMap<Integer, LinkedHashMap<String, Integer>> roundHistory;
  private boolean trumpDefined = false;

  /**
   * <p>Parametrised constructor for dependency injection.</p>
   *
   * @param botHand    bot cards
   * @param playerHand player cards
   * @param round      round information in the party
   */
  @Autowired
  public Party(HandDeck botHand, HandDeck playerHand, Round round) {
    this.botHand = botHand;
    this.playerHand = playerHand;
    this.round = round;
    initParty();
  }

  public String decideTurn() {
    if (roundHistory.isEmpty() ||
        roundHistory.get(roundNumber).get(Game.BOT)
            .equals(roundHistory.get(roundNumber).get(Game.PLAYER))
    ) {
      return Math.random() < 0.5 ? Game.BOT : Game.PLAYER;
    } else {
      return roundHistory.get(roundNumber).get(Game.BOT) >
          roundHistory.get(roundNumber).get(Game.PLAYER) ? Game.BOT : Game.PLAYER;
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
    return botScore == party.botScore && roundNumber == party.roundNumber &&
        getPhase() == party.getPhase() && playerScore == party.playerScore &&
        trumpDefined == party.trumpDefined && Objects.equals(botHand, party.botHand) &&
        Objects.equals(playerHand, party.playerHand) &&
        Objects.equals(round, party.round) &&
        Objects.equals(getScore(), party.getScore()) &&
        Objects.equals(getRoundHistory(), party.getRoundHistory());
  }

  public int getPhase() {
    return phase;
  }

  public LinkedHashMap<Integer, LinkedHashMap<String, Integer>> getRoundHistory() {
    return roundHistory;
  }

  public LinkedHashMap<String, Integer> getScore() {
    return score;
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(botHand, playerHand, round, botScore, roundNumber, getScore(), getPhase(),
            playerScore,
            getRoundHistory(), trumpDefined);
  }

  public void playTrump(String trump, String owner) throws Exception {
    round.playTrump(trump, owner);
    trumpDefined = true;
  }

  /**
   * <p>resets party between two players.</p>
   */
  public void resetParty() {
    initParty();
    round.resetRound();
    phase = Game.START;
    trumpDefined = false;
  }

  /**
   * <p>switch the round to the next phase.</p>
   *
   * @throws Exception Deck does not contain a card
   */
  public void switchPhase() throws Exception {
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

  public void switchTrump() throws Exception {
    round.switchTrump();
  }

  @Override
  public String toString() {
    return "Party{" +
        "botHand=" + botHand +
        ", playerHand=" + playerHand +
        ", round=" + round +
        ", botScore=" + botScore +
        ", roundNumber=" + roundNumber +
        ", score=" + score +
        ", phase=" + phase +
        ", playerScore=" + playerScore +
        ", roundHistory=" + roundHistory +
        ", trumpDefined=" + trumpDefined +
        '}';
  }

  private void finishRound() {
    if (round.getScore().get(playerHand.owner) < round.getScore().get(botHand.owner) &&
        round.getTrumpPicker().equals(playerHand.owner)) {
      LOG.debug("player lost his trump-round");
      round.setScore(botHand.owner,
          round.getScore().get(playerHand.owner) + round.getScore().get(botHand.owner));
      round.setScore(playerHand.owner,
          0);
      botScore += round.getScore().get(botHand.owner) + round.getScore().get(playerHand.owner);

    } else if (round.getScore().get(botHand.owner) < round.getScore().get(playerHand.owner) &&
        round.getTrumpPicker().equals(botHand.owner)) {
      LOG.debug("bot lost his trump-round");
      round.setScore(playerHand.owner,
          round.getScore().get(playerHand.owner) + round.getScore().get(botHand.owner));
      round.setScore(botHand.owner,
          0);
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
    score.put(Game.PLAYER, 0);
    score.put(Game.BOT, 0);
    playerScore = 0;
    botScore = 0;
    roundNumber = 0;
    roundHistory = new LinkedHashMap<>();
  }

}