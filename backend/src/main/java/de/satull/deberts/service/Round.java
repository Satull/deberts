package de.satull.deberts.service;


import de.satull.deberts.exception.NoSuchCardException;
import de.satull.deberts.model.Card;
import de.satull.deberts.model.Comparator;
import de.satull.deberts.model.ComparedCard;
import de.satull.deberts.model.Points;
import de.satull.deberts.model.SuitDeck;
import de.satull.deberts.model.deck.CardDeck;
import de.satull.deberts.model.deck.HandDeck;
import de.satull.deberts.model.deck.TrumpDeck;
import de.satull.deberts.util.Game;
import java.util.LinkedHashMap;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Represents the actual round of the party.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.5
 * @since 1.0
 */
public class Round {

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

  /**
   * <p>Decides a turn challenge between two cards</p>
   *
   * @param comparator compared cards
   * @throws NoSuchCardException removed card is not in the deck
   */
  public void decideChallenge(Comparator comparator) throws NoSuchCardException {
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

  /**
   * <p>Gets the round score of each player</p>
   *
   * @return round score
   */
  public LinkedHashMap<String, Integer> getScore() {
    return score;
  }

  /**
   * <p>Gets a player name who picked a trump in the round</p>
   *
   * @return player name
   */
  public String getTrumpPicker() {
    return trumpPicker;
  }

  /**
   * <p>Gets a player name who has an actual turn</p>
   *
   * @return player name
   */
  public String getTurn() {
    return turn;
  }

  /**
   * <p>Sets a player name who will have the next turn</p>
   *
   * @param turn player name
   */
  public void setTurn(String turn) {
    this.turn = turn;
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(botHand, cardDeck, playerHand, trumpDeck, getScore(), getTrumpPicker(), getTurn());
  }

  /**
   * <p>Decides who picked the trump and which one is it.</p>
   *
   * @param trump picked trump
   * @param owner player name who picked the trump
   * @throws NoSuchCardException removed card is not in the deck
   */
  public void playTrump(String trump, String owner) throws NoSuchCardException {
    if (!trump.equals(trumpDeck.getSuit())) {
      trumpDeck.resetDeck();
      trumpDeck.setTrump(cardDeck.getRandomCardFromSuit(trump));
      trumpPicker = owner;
    }
  }

  /**
   * <p>Resets round to the init state.</p>
   */
  public void resetRound() {
    cardDeck.resetDeck();
    trumpDeck.resetDeck();
    playerHand.resetDeck();
    botHand.resetDeck();
    initRound();
  }

  /**
   * <p>Sets new score for the player</p>
   *
   * @param owner  player name
   * @param points new score
   */
  public void setScore(String owner, Integer points) {
    score.replace(owner, points);
  }

  /**
   * <p>Checks if the trump switch is allowed</p>
   *
   * @return {@code true} if the switch is allowed
   */
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

  /**
   * <p>Switches game phase to action (with 9 cards)</p>
   *
   * @throws NoSuchCardException removed card is not in the deck
   */
  public void switchPhaseToAction() throws NoSuchCardException {
    fillHands(Game.ACTION);
  }

  /**
   * <p>Switches game phase to trade (with 6 cards)</p>
   *
   * @throws NoSuchCardException removed card is not in the deck
   */
  public void switchPhaseToTrade() throws NoSuchCardException {
    decideTrump();
    fillHands(Game.TRADE);
  }

  /**
   * <p>Switches the trump in the round</p>
   *
   * @throws NoSuchCardException removed card is not in the deck
   */
  public void switchTrump() throws NoSuchCardException {
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

  private void decideTrump() throws NoSuchCardException {
    trumpDeck.setTrump(cardDeck.getRandomCard());
  }

  private void fillHands(int cards) throws NoSuchCardException {
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

  private void removePlayedCards(ComparedCard attacker, ComparedCard defender)
      throws NoSuchCardException {
    if (attacker.getOwner().equals(Game.BOT)) {
      botHand.removeCard(attacker.getCard());
      playerHand.removeCard(defender.getCard());
    } else {
      playerHand.removeCard(attacker.getCard());
      botHand.removeCard(defender.getCard());
    }
  }

}