package de.satull.deberts.web;

import de.satull.deberts.exception.NoSuchCardException;
import de.satull.deberts.model.db.Player;
import de.satull.deberts.model.web.Comparator;
import de.satull.deberts.model.enums.Constant;
import de.satull.deberts.model.enums.Owner;
import de.satull.deberts.model.web.Trump;
import de.satull.deberts.model.deck.CardDeck;
import de.satull.deberts.model.deck.HandDeck;
import de.satull.deberts.model.deck.TrumpDeck;
import de.satull.deberts.service.PartyService;
import de.satull.deberts.service.PlayerService;
import de.satull.deberts.service.RoundService;
import java.lang.invoke.MethodHandles;
import java.util.LinkedHashMap;
import java.util.Map;
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
  private final PartyService partyService;
  private final HandDeck playerHand;
  private final RoundService roundService;
  private final TrumpDeck trumpDeck;

  @Autowired
  private PlayerService playerService;

  /**
   * Creates controller class with dependencies
   *
   * @param botHand opponents cards
   * @param cardDeck card deck for the round
   * @param partyService played party
   * @param playerHand player cards
   * @param roundService actual round
   * @param trumpDeck trump deck for the round
   */
  public DebertsController(
      HandDeck botHand,
      CardDeck cardDeck,
      PartyService partyService,
      HandDeck playerHand,
      RoundService roundService,
      TrumpDeck trumpDeck) {
    this.botHand = botHand;
    this.cardDeck = cardDeck;
    this.partyService = partyService;
    this.playerHand = playerHand;
    this.roundService = roundService;
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
    roundService.decideChallenge(cardComparator);
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
    partyMap.put(Constant.ROUND_HISTORY, partyService.getRoundHistory());
    partyMap.put(Constant.TURN, getTurn());
    partyMap.put(Constant.PHASE, partyService.getPhase());
    partyMap.put(Constant.SWITCH_ALLOWED, roundService.switchAllowed());
    return partyMap;
  }

  /**
   * GetEndpoint for runtime information.
   *
   * @return runtime information
   */
  @GetMapping("/runtime")
  public Map<String, Object> getRuntime() {
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
  public Map<Enum, Object> getTurn() {
    LinkedHashMap<Enum, Object> turnMap = new LinkedHashMap<>();
    turnMap.put(Constant.PARTY, partyService.getScore());
    turnMap.put(Constant.ROUND, roundService.getScore());
    turnMap.put(Constant.TURN, roundService.getTurn());
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
    partyService.playTrump(trump.getSuit(), trump.getOwner());
    partyService.switchPhase();
  }

  /** PostEndpoint to reset the party with all rounds */
  @PostMapping("/reset")
  public void resetParty() {
    partyService.resetParty();
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
    partyService.switchPhase();
  }

  /**
   * PostEndpoint to save the game on the current round
   */
  @PostMapping("/save")
  public void save() {
    LOG.info("You are trying to save the game " + playerService.list().get(0).getName());
    Player playerTable = playerService.list().get(0);
    playerTable.setBestWinStreak(99);
    playerService.save(playerTable);
  }

  @PostMapping("/load")
  public void load() {
    LOG.info("You are trying to load the game");
  }

  /**
   * PostEndpoint to switch trump seven to the native trump
   *
   * @throws NoSuchCardException removed card is not in the deck
   */
  @PostMapping(value = "/switchTrump")
  public void switchTrump() throws NoSuchCardException {
    partyService.switchTrump();
  }
}
