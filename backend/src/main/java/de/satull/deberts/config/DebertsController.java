package de.satull.deberts.config;

import de.satull.deberts.deck.CardDeck;
import de.satull.deberts.deck.HandDeck;
import de.satull.deberts.deck.TrumpDeck;
import de.satull.deberts.model.Comparator;
import de.satull.deberts.model.Trump;
import de.satull.deberts.service.Party;
import de.satull.deberts.service.Round;
import de.satull.deberts.util.Game;
import java.lang.invoke.MethodHandles;
import java.util.LinkedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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

  /**
   * <p>Parametrised constructor for dependency injection.</p>
   *
   * @param botHand    bot cards
   * @param cardDeck   starting deck
   * @param party      party information
   * @param playerHand player cards
   * @param round      round information in the party
   * @param trumpDeck  trump in the round
   */
  public DebertsController(HandDeck botHand, CardDeck cardDeck, Party party,
                           HandDeck playerHand,
                           Round round, TrumpDeck trumpDeck) {
    this.botHand = botHand;
    this.cardDeck = cardDeck;
    this.party = party;
    this.playerHand = playerHand;
    this.round = round;
    this.trumpDeck = trumpDeck;
  }

  @PostMapping(value = "/compare", consumes = "application/json")
  public void compareCards(@RequestBody Comparator cardComparator) throws Exception {
    round.decideChallenge(cardComparator);
  }

  /**
   * <p>GetEndpoint for party information.</p>
   *
   * @return party information
   */
  @GetMapping("/party")
  public LinkedHashMap<String, Object> getParty() {
    LinkedHashMap<String, Object> partyMap = new LinkedHashMap<>();
    partyMap.put(Game.CARD_DECK, cardDeck.getDeck());
    partyMap.put(Game.TRUMP, trumpDeck.getDeck());
    partyMap.put(Game.BOT, botHand.getDeck());
    partyMap.put(Game.PLAYER, playerHand.getDeck());
    partyMap.put(Game.ROUND_HISTORY, party.getRoundHistory());
    partyMap.put(Game.TURN, getTurn());
    partyMap.put(Game.PHASE, party.getPhase());
    partyMap.put(Game.SWITCH_ALLOWED, round.switchAllowed());
    return partyMap;
  }

  /**
   * <p>GetEndpoint for runtime information.</p>
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
   * <p>GetEndpoint for score information.</p>
   *
   * @return score information
   */
  @GetMapping("/turn")
  public LinkedHashMap<String, Object> getTurn() {
    LinkedHashMap<String, Object> turnMap = new LinkedHashMap<>();
    turnMap.put(Game.PARTY, party.getScore());
    turnMap.put(Game.ROUND, round.getScore());
    turnMap.put(Game.TURN, round.getTurn());
    return turnMap;
  }

  @PostMapping(value = "/playTrump", consumes = "application/json")
  public void playTrump(@RequestBody Trump trump) throws Exception {
    LOG.info(trump.toString());
    party.playTrump(trump.getSuit(), trump.getOwner());
    party.switchPhase();
  }

  @PostMapping("/reset")
  public void resetParty() {
    party.resetParty();
  }

  @PostMapping("/shutdown")
  public void shutdown() {
    Runtime runtime = Runtime.getRuntime();
    runtime.exit(1);
  }

  @PostMapping("/switchPhase")
  public void switchPhase() throws Exception {
    party.switchPhase();
  }

  @PostMapping(value = "/switchTrump")
  public void switchTrump() throws Exception {
    party.switchTrump();
  }

}