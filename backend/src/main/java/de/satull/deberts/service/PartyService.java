package de.satull.deberts.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.satull.deberts.exception.NoSuchCardException;
import de.satull.deberts.model.db.Party;
import de.satull.deberts.model.db.Player;
import de.satull.deberts.model.db.Round;
import de.satull.deberts.model.enums.Owner;
import de.satull.deberts.model.enums.Suit;
import de.satull.deberts.util.Game;
import java.lang.invoke.MethodHandles;
import java.util.LinkedHashMap;
import java.util.Map;
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
public class PartyService {

  private static final Logger LOG =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
  private final RoundService roundService;
  @Autowired private DataBaseService dbService;
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
   * @param roundService round information in the party
   */
  @Autowired
  public PartyService(RoundService roundService) {
    this.roundService = roundService;
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
    if (!(o instanceof PartyService)) {
      return false;
    }
    PartyService partyService = (PartyService) o;
    return botScore == partyService.botScore
        && roundNumber == partyService.roundNumber
        && getPhase() == partyService.getPhase()
        && playerScore == partyService.playerScore
        && trumpDefined == partyService.trumpDefined
        && Objects.equals(roundService, partyService.roundService)
        && Objects.equals(getScore(), partyService.getScore())
        && Objects.equals(getRoundHistory(), partyService.getRoundHistory());
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
  public Map<Integer, LinkedHashMap<Owner, Integer>> getRoundHistory() {
    return roundHistory;
  }

  /**
   * Gets the score
   *
   * @return score
   */
  public Map<Owner, Integer> getScore() {
    return score;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        roundService,
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
    roundService.playTrump(trump, owner);
    trumpDefined = true;
  }

  /** resets party between two players. */
  public void resetParty() {
    initParty();
    roundService.resetRound();
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
      roundService.switchPhaseToTrade();
      roundService.setTurn(decideTurn());
      phase = Game.TRADE;

    } else if (phase == Game.TRADE && trumpDefined) {
      roundService.switchPhaseToAction();
      phase = Game.ACTION;

    } else if (phase == Game.ACTION
        && roundService.getPlayerHand().countCards() + roundService.getBotHand().countCards()
            == 0) {
      finishRound();
      roundService.resetRound();
      roundService.switchPhaseToTrade();
      roundService.setTurn(decideTurn());
      phase = Game.TRADE;
    }
  }

  /**
   * Switches a trump in the actual round.
   *
   * @throws NoSuchCardException removed card is not in the deck
   */
  public void switchTrump() throws NoSuchCardException {
    roundService.switchTrump();
  }

  public void saveParty() throws JsonProcessingException {
    LOG.info("You are trying to save the game");
    ObjectMapper objectMapper = new ObjectMapper();
    // set player
    Player playerEntity = dbService.listPlayers().get(0);
    // set party
    Party currentParty = dbService.listParties().get(0);
    currentParty.setStatus("ACTIVE");
    currentParty.setBotScore(botScore);
    currentParty.setPlayerScore(playerScore);
    currentParty.setPlayer(playerEntity);
    // set Round
    Round currentRound = dbService.listRounds().get(0);
    currentRound.setPlayer(playerEntity);
    currentRound.setParty(currentParty);
    currentRound.setRoundDeck(objectMapper.writeValueAsString(roundService.getCardDeck()));
    currentRound.setTrumpDeck(objectMapper.writeValueAsString(roundService.getTrumpDeck()));
    currentRound.setPlayerDeck(objectMapper.writeValueAsString(roundService.getPlayerHand()));
    currentRound.setBotDeck(objectMapper.writeValueAsString(roundService.getBotHand()));
    currentRound.setPlayerPoints(roundService.getScore().get(Owner.PLAYER));
    currentRound.setBotPoints(roundService.getScore().get(Owner.BOT));
    currentRound.setTurn(roundService.getTurn().toString());
    currentRound.setTrumpPicker(roundService.getTrumpPicker().toString());
    currentRound.setStatus("ACTIVE");

    dbService.save(currentRound);
  }

  @Override
  public String toString() {
    return "Party{"
        + ", round="
        + roundService
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
    if (roundService.getScore().get(roundService.getPlayerHand().owner)
            < roundService.getScore().get(roundService.getBotHand().owner)
        && roundService.getTrumpPicker().equals(roundService.getPlayerHand().owner)) {
      LOG.debug("player lost his trump-round");
      roundService.setScore(
          roundService.getBotHand().owner,
          roundService.getScore().get(roundService.getPlayerHand().owner)
              + roundService.getScore().get(roundService.getBotHand().owner));
      roundService.setScore(roundService.getPlayerHand().owner, 0);
      botScore +=
          roundService.getScore().get(roundService.getBotHand().owner)
              + roundService.getScore().get(roundService.getPlayerHand().owner);

    } else if (roundService.getScore().get(roundService.getBotHand().owner)
            < roundService.getScore().get(roundService.getPlayerHand().owner)
        && roundService.getTrumpPicker().equals(roundService.getBotHand().owner)) {
      LOG.debug("bot lost his trump-round");
      roundService.setScore(
          roundService.getPlayerHand().owner,
          roundService.getScore().get(roundService.getPlayerHand().owner)
              + roundService.getScore().get(roundService.getBotHand().owner));
      roundService.setScore(roundService.getBotHand().owner, 0);
      playerScore +=
          roundService.getScore().get(roundService.getPlayerHand().owner)
              + roundService.getScore().get(roundService.getBotHand().owner);

    } else {
      LOG.debug("normal round");
      playerScore += roundService.getScore().get(roundService.getPlayerHand().owner);
      botScore += roundService.getScore().get(roundService.getBotHand().owner);
    }

    roundHistory.putIfAbsent(++roundNumber, roundService.getScore());
    LOG.info("Round history: " + roundHistory.toString());
    score.replace(roundService.getPlayerHand().owner, playerScore);
    score.replace(roundService.getBotHand().owner, botScore);
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
