package de.satull.deberts.model.db;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "rounds", schema = "public")
public class Round implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "rnd_id", updatable = false, nullable = false)
  private Long id;

  @Column(name = "rnd_number", nullable = false)
  private int roundNumber;

  @ManyToOne
  @JoinColumn(name = "rnd_plr_id")
  private Player player;

  @ManyToOne
  @JoinColumn(name = "rnd_prt_id")
  private Party party;

  @Column(name = "rnd_round_deck")
  private String roundDeck;

  @Column(name = "rnd_trump_deck")
  private String trumpDeck;

  @Column(name = "rnd_player_deck")
  private String playerDeck;

  @Column(name = "rnd_bot_deck")
  private String botDeck;

  @Column(name = "rnd_player_points")
  private int playerPoints;

  @Column(name = "rnd_bot_points")
  private int botPoints;

  @Column(name = "rnd_turn", nullable = false)
  private String turn;

  @Column(name = "rnd_trump_picker")
  private String trumpPicker;

  @Column(name = "rnd_status", nullable = false)
  private String status;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getRoundNumber() {
    return roundNumber;
  }

  public void setRoundNumber(int roundNumber) {
    this.roundNumber = roundNumber;
  }

  public Player getPlayer() {
    return player;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  public Party getParty() {
    return party;
  }

  public void setParty(Party party) {
    this.party = party;
  }

  public String getRoundDeck() {
    return roundDeck;
  }

  public void setRoundDeck(String roundDeck) {
    this.roundDeck = roundDeck;
  }

  public String getTrumpDeck() {
    return trumpDeck;
  }

  public void setTrumpDeck(String trumpDeck) {
    this.trumpDeck = trumpDeck;
  }

  public String getPlayerDeck() {
    return playerDeck;
  }

  public void setPlayerDeck(String playerDeck) {
    this.playerDeck = playerDeck;
  }

  public String getBotDeck() {
    return botDeck;
  }

  public void setBotDeck(String botDeck) {
    this.botDeck = botDeck;
  }

  public int getPlayerPoints() {
    return playerPoints;
  }

  public void setPlayerPoints(int playerPoints) {
    this.playerPoints = playerPoints;
  }

  public int getBotPoints() {
    return botPoints;
  }

  public void setBotPoints(int botPoints) {
    this.botPoints = botPoints;
  }

  public String getTurn() {
    return turn;
  }

  public void setTurn(String turn) {
    this.turn = turn;
  }

  public String getTrumpPicker() {
    return trumpPicker;
  }

  public void setTrumpPicker(String trumpPicker) {
    this.trumpPicker = trumpPicker;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
