package de.satull.deberts.service;

import de.satull.deberts.exception.NoSuchCardException;
import de.satull.deberts.model.Card;
import de.satull.deberts.model.Comparator;
import de.satull.deberts.model.ComparedCard;
import de.satull.deberts.model.Owner;
import de.satull.deberts.model.Points;
import de.satull.deberts.model.Suit;
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
  private LinkedHashMap<Owner, Integer> score;
  private Owner trumpPicker = Owner.UNDEFINED;
  private Owner turn = Owner.UNDEFINED;

  /**
   * Parametrised constructor for dependency injection.
   *
   * @param botHand bot cards
   * @param cardDeck starting deck
   * @param playerHand player cards
   * @param trumpDeck trump in the round
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
   * Decides a turn challenge between two cards
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
    return Objects.equals(botHand, round.botHand)
        && Objects.equals(cardDeck, round.cardDeck)
        && Objects.equals(playerHand, round.playerHand)
        && Objects.equals(trumpDeck, round.trumpDeck)
        && Objects.equals(getScore(), round.getScore())
        && Objects.equals(getTrumpPicker(), round.getTrumpPicker())
        && Objects.equals(getTurn(), round.getTurn());
  }

  /**
   * Gets the round score of each player
   *
   * @return round score
   */
  public LinkedHashMap<Owner, Integer> getScore() {
    return score;
  }

  /**
   * Gets a player name who picked a trump in the round
   *
   * @return player name
   */
  public Owner getTrumpPicker() {
    return trumpPicker;
  }

  /**
   * Gets a player name who has an actual turn
   *
   * @return player name
   */
  public Owner getTurn() {
    return turn;
  }

  /**
   * Sets a player name who will have the next turn
   *
   * @param turn player name
   */
  public void setTurn(Owner turn) {
    this.turn = turn;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        botHand, cardDeck, playerHand, trumpDeck, getScore(), getTrumpPicker(), getTurn());
  }

  /**
   * Decides who picked the trump and which one is it.
   *
   * @param trump picked trump
   * @param owner player name who picked the trump
   * @throws NoSuchCardException removed card is not in the deck
   */
  public void playTrump(Suit trump, Owner owner) throws NoSuchCardException {
    if (!trump.equals(trumpDeck.getSuit())) {
      trumpDeck.resetDeck();
      trumpDeck.setTrump(cardDeck.getRandomCardFromSuit(trump));
      trumpPicker = owner;
    }
  }

  /** Resets round to the init state. */
  public void resetRound() {
    cardDeck.resetDeck();
    trumpDeck.resetDeck();
    playerHand.resetDeck();
    botHand.resetDeck();
    initRound();
  }

  /**
   * Sets new score for the player
   *
   * @param owner player name
   * @param points new score
   */
  public void setScore(Owner owner, Integer points) {
    score.replace(owner, points);
  }

  /**
   * Checks if the trump switch is allowed
   *
   * @return {@code true} if the switch is allowed
   */
  public boolean switchAllowed() {
    try {
      Card card = new Card(trumpDeck.getSuit(), SuitDeck.SEVEN);
      return (playerHand.contains(card) || botHand.contains(card))
          && trumpPicker.equals(Owner.UNDEFINED)
          && playerHand.countCards() == 9;
    } catch (NullPointerException e) {
      return false;
    }
  }

  /**
   * Switches game phase to action (with 9 cards)
   *
   * @throws NoSuchCardException removed card is not in the deck
   */
  public void switchPhaseToAction() throws NoSuchCardException {
    fillHands(Game.ACTION);
  }

  /**
   * Switches game phase to trade (with 6 cards)
   *
   * @throws NoSuchCardException removed card is not in the deck
   */
  public void switchPhaseToTrade() throws NoSuchCardException {
    decideTrump();
    fillHands(Game.TRADE);
  }

  /**
   * Switches the trump in the round
   *
   * @throws NoSuchCardException removed card is not in the deck
   */
  public void switchTrump() throws NoSuchCardException {
    Card card = new Card(trumpDeck.getSuit(), SuitDeck.SEVEN);
    if (playerHand.contains(card) && trumpPicker.equals(Owner.UNDEFINED)) {
      playerHand.addCard(trumpDeck.getCard());
      trumpDeck.switchTrump(card);
      playerHand.removeCard(card);
    } else if (botHand.contains(card) && trumpPicker.equals(Owner.UNDEFINED)) {
      botHand.addCard(trumpDeck.getCard());
      trumpDeck.switchTrump(card);
      botHand.removeCard(card);
    }
  }

  @Override
  public String toString() {
    return "Round{"
        + "botHand="
        + botHand
        + ", cardDeck="
        + cardDeck
        + ", playerHand="
        + playerHand
        + ", trumpDeck="
        + trumpDeck
        + ", score="
        + score
        + ", trumpPicker='"
        + trumpPicker
        + '\''
        + ", turn='"
        + turn
        + '\''
        + '}';
  }

  private void addScore(Points points) {
    int newScore = score.get(points.getOwner()) + points.getNumber();
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
    int attackerPoints =
        Game.getCardPoints(
            attacker.getCard().getValue(),
            attacker.getCard().getSuit().equals(trumpDeck.getSuit()));
    int defenderPoints =
        Game.getCardPoints(
            defender.getCard().getValue(),
            defender.getCard().getSuit().equals(trumpDeck.getSuit()));
    int points =
        (botHand.countCards() > 1)
            ? attackerPoints + defenderPoints
            : attackerPoints + defenderPoints + Game.LAST_CARD;

    if (attacker.getCard().getSuit().equals(defender.getCard().getSuit())) {
      int result = Integer.compare(attackerPoints, defenderPoints);
      result =
          result == 0
              ? Integer.compare(
                  attacker.getCard().getValue().ordinal(), defender.getCard().getValue().ordinal())
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
    score.put(Owner.PLAYER, 0);
    score.put(Owner.BOT, 0);
    trumpPicker = Owner.UNDEFINED;
  }

  private void removePlayedCards(ComparedCard attacker, ComparedCard defender)
      throws NoSuchCardException {
    if (attacker.getOwner().equals(Owner.BOT)) {
      botHand.removeCard(attacker.getCard());
      playerHand.removeCard(defender.getCard());
    } else {
      playerHand.removeCard(attacker.getCard());
      botHand.removeCard(defender.getCard());
    }
  }
}
