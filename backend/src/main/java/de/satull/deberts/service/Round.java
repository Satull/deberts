package de.satull.deberts.service;


import de.satull.deberts.deck.CardDeck;
import de.satull.deberts.deck.HandDeck;
import de.satull.deberts.deck.TrumpDeck;
import de.satull.deberts.model.Card;
import de.satull.deberts.model.Comparator;
import de.satull.deberts.model.ComparedCard;
import de.satull.deberts.model.Points;
import de.satull.deberts.model.SuitDeck;
import de.satull.deberts.util.Game;
import java.lang.invoke.MethodHandles;
import java.util.LinkedHashMap;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class Round {

  private static final Logger LOG =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
  private final HandDeck botHand;
  private final CardDeck cardDeck;
  private final HandDeck playerHand;
  private final TrumpDeck trumpDeck;
  private LinkedHashMap<String, Integer> score;
  private String trumpPicker = "undefined";
  private String turn = Game.UNDEFINED;

  /**
   * <p>Parametrised constructor for dependency injection.</p>
   *
   * @param botHand    bot cards
   * @param cardDeck   starting deck
   * @param playerHand player cards
   * @param trumpDeck  trump in the round
   */
  @Autowired
  public Round(HandDeck botHand, CardDeck cardDeck, HandDeck playerHand, TrumpDeck trumpDeck) {
    this.botHand = botHand;
    this.cardDeck = cardDeck;
    this.playerHand = playerHand;
    this.trumpDeck = trumpDeck;
    initRound();
  }

  public void decideChallenge(Comparator comparator) throws Exception {
    Points winnerPoints = getWinnerPoints(comparator.getAttacker(), comparator.getDefender());
    addScore(winnerPoints);
    setTurn(winnerPoints.getOwner());
    removePlayedCards(comparator.getAttacker(), comparator.getDefender());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Round)) {
      return false;
    }
    Round round = (Round) o;
    return Objects.equals(botHand, round.botHand) &&
        Objects.equals(cardDeck, round.cardDeck) &&
        Objects.equals(playerHand, round.playerHand) &&
        Objects.equals(trumpDeck, round.trumpDeck) &&
        Objects.equals(getScore(), round.getScore()) &&
        Objects.equals(getTrumpPicker(), round.getTrumpPicker()) &&
        Objects.equals(getTurn(), round.getTurn());
  }

  public LinkedHashMap<String, Integer> getScore() {
    return score;
  }

  public String getTrumpPicker() {
    return trumpPicker;
  }

  public String getTurn() {
    return turn;
  }

  public void setTurn(String turn) {
    this.turn = turn;
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(botHand, cardDeck, playerHand, trumpDeck, getScore(), getTrumpPicker(), getTurn());
  }

  public void playTrump(String trump, String owner) throws Exception {
    if (!trump.equals(trumpDeck.getSuit())) {
      trumpDeck.resetDeck();
      trumpDeck.setTrump(cardDeck.getRandomCardFromSuit(trump));
      trumpPicker = owner;
    }
  }

  /**
   * <p>reset round to the init state.</p>
   */
  public void resetRound() {
    cardDeck.resetDeck();
    trumpDeck.resetDeck();
    playerHand.resetDeck();
    botHand.resetDeck();
    initRound();
  }

  public void setScore(String owner, Integer points) {
    score.replace(owner, points);
  }

  public boolean switchAllowed() {
    try {
      Card card = new Card(trumpDeck.getSuit(), SuitDeck.SEVEN);
      return
          (playerHand.contains(card) || botHand.contains(card)) &&
              trumpPicker.equals(Game.UNDEFINED) && playerHand.countCards() == 9;
    } catch (NullPointerException e) {
      return false;
    }
  }

  public void switchPhaseToAction() throws Exception {
    fillHands(Game.ACTION);
  }

  public void switchPhaseToTrade() throws Exception {
    decideTrump();
    fillHands(Game.TRADE);
  }

  public void switchTrump() throws Exception {
    Card card = new Card(trumpDeck.getSuit(), SuitDeck.SEVEN);
    if (playerHand.contains(card) && trumpPicker.equals(Game.UNDEFINED)) {
      playerHand.addCard(trumpDeck.getCard());
      trumpDeck.switchTrump(card);
      playerHand.removeCard(card);
    } else if (botHand.contains(card) && trumpPicker.equals(Game.UNDEFINED)) {
      botHand.addCard(trumpDeck.getCard());
      trumpDeck.switchTrump(card);
      botHand.removeCard(card);
    }
  }

  @Override
  public String toString() {
    return "Round{" +
        "botHand=" + botHand +
        ", cardDeck=" + cardDeck +
        ", playerHand=" + playerHand +
        ", trumpDeck=" + trumpDeck +
        ", score=" + score +
        ", trumpPicker='" + trumpPicker + '\'' +
        ", turn='" + turn + '\'' +
        '}';
  }

  private void addScore(Points points) {
    int newScore = score.get(points.getOwner()) + points.getPoints();
    score.replace(points.getOwner(), newScore);
  }

  private void decideTrump() throws Exception {
    trumpDeck.setTrump(cardDeck.getRandomCard());
  }

  private void fillHands(int cards) throws Exception {
    for (int i = 0; i < cards; i++) {
      playerHand.addCard(cardDeck.getRandomCard());
      botHand.addCard(cardDeck.getRandomCard());
    }
  }

  private Points getWinnerPoints(ComparedCard attacker, ComparedCard defender) {
    int attackerPoints = Game.getCardPoints(attacker.getCard().getValue(),
        attacker.getCard().getSuit().equals(trumpDeck.getSuit()));
    int defenderPoints = Game.getCardPoints(defender.getCard().getValue(),
        defender.getCard().getSuit().equals(trumpDeck.getSuit()));
    int points = (botHand.countCards() > 1) ? attackerPoints + defenderPoints
        : attackerPoints + defenderPoints + Game.LAST_CARD;

    if (attacker.getCard().getSuit().equals(defender.getCard().getSuit())) {
      int result = Integer.compare(attackerPoints, defenderPoints);
      result = result == 0
          ? Integer
          .compare(attacker.getCard().getValue().ordinal(), defender.getCard().getValue().ordinal())
          : result;
      return new Points(result > 0 ? attacker.getOwner() : defender.getOwner(), points);
    } else if (defender.getCard().getSuit().equals(trumpDeck.getSuit())) {
      return new Points(defender.getOwner(), points);
    } else {
      return new Points(attacker.getOwner(), points);
    }
  }

  private void initRound() {
    score = new LinkedHashMap<>();
    score.put(Game.PLAYER, 0);
    score.put(Game.BOT, 0);
    trumpPicker = "undefined";
  }

  private void removePlayedCards(ComparedCard attacker, ComparedCard defender) throws Exception {
    if (attacker.getOwner().equals(Game.BOT)) {
      botHand.removeCard(attacker.getCard());
      playerHand.removeCard(defender.getCard());
    } else {
      playerHand.removeCard(attacker.getCard());
      botHand.removeCard(defender.getCard());
    }
  }

}