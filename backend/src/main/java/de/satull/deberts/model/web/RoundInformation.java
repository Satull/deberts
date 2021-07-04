package de.satull.deberts.model.web;

import de.satull.deberts.enums.Owner;
import de.satull.deberts.enums.Phase;
import de.satull.deberts.model.deck.Deck;
import de.satull.deberts.model.deck.Trump;
import lombok.Builder;

/**
 * Contains information about current {@code Round}: {@code CardDeck} which contains all {@code
 * Cards} at the beginning, trump deck with {@code Trump}, two {@code HandDecks} for each player and
 * actual turn.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
@Builder
public class RoundInformation {
  private Deck cardDeck;
  private Deck playerDeck;
  private Deck botDeck;
  private Trump trumpDeck;
  @Builder.Default private Owner turn = Owner.PLAYER;
  @Builder.Default private Phase phase = Phase.TRADE;

  /**
   * Returns {@code Phase}.
   *
   * @return {@code Phase}
   */
  public Phase getPhase() {
    return phase;
  }

  /**
   * Sets {@code Phase}.
   *
   * @param phase new {@code Phase}
   */
  public void setPhase(Phase phase) {
    this.phase = phase;
  }

  /**
   * Returns {@code CardDeck}.
   *
   * @return {@code CardDeck}
   */
  public Deck getCardDeck() {
    return cardDeck;
  }

  /**
   * Sets {@code CardDeck}.
   *
   * @param cardDeck new {@code Deck}
   */
  public void setCardDeck(Deck cardDeck) {
    this.cardDeck = cardDeck;
  }

  /**
   * Returns {@code HandDeck} of the player.
   *
   * @return players {@code HandDeck}
   */
  public Deck getPlayerDeck() {
    return playerDeck;
  }

  /**
   * Sets {@code HandDeck} of the player.
   *
   * @param playerDeck {@code HandDeck} of the bot
   */
  public void setPlayerDeck(Deck playerDeck) {
    this.playerDeck = playerDeck;
  }

  /**
   * Returns {@code HandDeck} of the bot.
   *
   * @return bots {@code HandDeck}
   */
  public Deck getBotDeck() {
    return botDeck;
  }

  /**
   * Sets {@code HandDeck} of the bot.
   *
   * @param botDeck {@code HandDeck} of the bot
   */
  public void setBotDeck(Deck botDeck) {
    this.botDeck = botDeck;
  }

  /**
   * Returns the Deck of the {@code Trump}.
   *
   * @return {@code Trump}
   */
  public Trump getTrumpDeck() {
    return trumpDeck;
  }

  /**
   * Sets {@code Trump}.
   *
   * @param trumpDeck {@code Trump} to set
   */
  public void setTrumpDeck(Trump trumpDeck) {
    this.trumpDeck = trumpDeck;
  }

  /**
   * Returns actual turn in the round.
   *
   * @return actual turn
   */
  public Owner getTurn() {
    return turn;
  }

  /**
   * Sets actual turn in the round.
   *
   * @param turn whose turn is now
   */
  public void setTurn(Owner turn) {
    this.turn = turn;
  }
}
