package de.satull.deberts.web;

import de.satull.deberts.exception.NoSuchCardException;
import de.satull.deberts.model.Comparator;
import de.satull.deberts.model.Constant;
import de.satull.deberts.model.Owner;
import de.satull.deberts.model.Trump;
import de.satull.deberts.model.deck.CardDeck;
import de.satull.deberts.model.deck.HandDeck;
import de.satull.deberts.model.deck.TrumpDeck;
import de.satull.deberts.service.Party;
import de.satull.deberts.service.PlayerService;
import de.satull.deberts.service.Round;
import java.lang.invoke.MethodHandles;
import java.util.LinkedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Main controller of the deberts-api.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/")
public class DebertsController {

  private static final Logger LOG =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
  private final HandDeck botHand;
  private final CardDeck cardDeck;
  private final Party party;
  private final HandDeck playerHand;
  private final Round round;
  private final TrumpDeck trumpDeck;

  @Autowired
  private PlayerService playerService;

  /**
   * Creates controller class with dependencies
   *
   * @param botHand opponents cards
   * @param cardDeck card deck for the round
   * @param party played party
   * @param playerHand player cards
   * @param round actual round
   * @param trumpDeck trump deck for the round
   */
  public DebertsController(
      HandDeck botHand,
      CardDeck cardDeck,
      Party party,
      HandDeck playerHand,
      Round round,
      TrumpDeck trumpDeck) {
    this.botHand = botHand;
    this.cardDeck = cardDeck;
    this.party = party;
    this.playerHand = playerHand;
    this.round = round;
    this.trumpDeck = trumpDeck;
  }

  /**
   * PostEndpoint to compare opponents cards.
   *
   * @param cardComparator two cards of both players to compare
   * @throws NoSuchCardException removed card is not in the deck
   */
  @PostMapping(value = "/compare", consumes = "application/json")
  public void compareCards(@RequestBody Comparator cardComparator) throws NoSuchCardException {
    round.decideChallenge(cardComparator);
  }

  /**
   * GetEndpoint for party information.
   *
   * @return party information
   */
  @GetMapping("/party")
  public LinkedHashMap<Enum, Object> getParty() {
    LinkedHashMap<Enum, Object> partyMap = new LinkedHashMap<>();
    partyMap.put(Constant.CARD_DECK, cardDeck.getDeck());
    partyMap.put(Owner.TRUMP, trumpDeck.getDeck());
    partyMap.put(Owner.BOT, botHand.getDeck());
    partyMap.put(Owner.PLAYER, playerHand.getDeck());
    partyMap.put(Constant.ROUND_HISTORY, party.getRoundHistory());
    partyMap.put(Constant.TURN, getTurn());
    partyMap.put(Constant.PHASE, party.getPhase());
    partyMap.put(Constant.SWITCH_ALLOWED, round.switchAllowed());
    return partyMap;
  }

  /**
   * GetEndpoint for runtime information.
   *
   * @return runtime information
   */
  @GetMapping("/runtime")
  public LinkedHashMap<String, Object> getRuntime() {
    LinkedHashMap<String, Object> runtimeMap = new LinkedHashMap<>();
    Runtime runtime = Runtime.getRuntime();
    runtimeMap.put("availableProcessors", runtime.availableProcessors());
    runtimeMap.put("totalMemory", runtime.totalMemory());
    runtimeMap.put("usedMemory", runtime.totalMemory() - runtime.freeMemory());
    runtimeMap.put("freeMemory", runtime.freeMemory());
    runtimeMap.put("maxMemory", runtime.maxMemory());
    return runtimeMap;
  }

  /**
   * GetEndpoint for score information.
   *
   * @return score information
   */
  @GetMapping("/turn")
  public LinkedHashMap<Enum, Object> getTurn() {
    LinkedHashMap<Enum, Object> turnMap = new LinkedHashMap<>();
    turnMap.put(Constant.PARTY, party.getScore());
    turnMap.put(Constant.ROUND, round.getScore());
    turnMap.put(Constant.TURN, round.getTurn());
    return turnMap;
  }

  /**
   * PostEndpoint to choose a trump in the round
   *
   * @param trump picked trump
   * @throws NoSuchCardException removed card is not in the deck
   */
  @PostMapping(value = "/playTrump", consumes = "application/json")
  public void playTrump(@RequestBody Trump trump) throws NoSuchCardException {
    LOG.debug(trump.toString());
    party.playTrump(trump.getSuit(), trump.getOwner());
    party.switchPhase();
  }

  /** PostEndpoint to reset the party with all rounds */
  @PostMapping("/reset")
  public void resetParty() {
    party.resetParty();
  }

  /** PostEndpoint to shut down the API */
  @PostMapping("/shutdown")
  public void shutdown() {
    Runtime runtime = Runtime.getRuntime();
    runtime.exit(1);
  }

  /**
   * PostEndpoint to switch the phase of a round
   *
   * @throws NoSuchCardException removed card is not in the deck
   */
  @PostMapping("/switchPhase")
  public void switchPhase() throws NoSuchCardException {
    LOG.info("Hello Players: " + playerService.list().get(0).getPlr_name());
    party.switchPhase();
  }

  /**
   * PostEndpoint to switch trump seven to the native trump
   *
   * @throws NoSuchCardException removed card is not in the deck
   */
  @PostMapping(value = "/switchTrump")
  public void switchTrump() throws NoSuchCardException {
    party.switchTrump();
  }
}
