package de.satull.deberts.model.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@IdClass(RoundId.class)
@Table(name = "rounds", schema = "public")
public class Round {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "rnd_id", updatable = false, nullable = false)
  private Long id;

  @Id
  @Column(name = "rnd_number")
  private int roundNumber;

  public RoundId getId() {
    return new RoundId(id, roundNumber);
  }

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

  @Override
  public String toString() {
    return "Round{"
        + "id="
        + id
        + ", roundNumber="
        + roundNumber
        + ", player="
        + player
        + ", party="
        + party
        + ", roundDeck='"
        + roundDeck
        + '\''
        + ", trumpDeck='"
        + trumpDeck
        + '\''
        + ", playerDeck='"
        + playerDeck
        + '\''
        + ", botDeck='"
        + botDeck
        + '\''
        + ", playerPoints="
        + playerPoints
        + ", botPoints="
        + botPoints
        + ", turn='"
        + turn
        + '\''
        + ", trumpPicker='"
        + trumpPicker
        + '\''
        + ", status='"
        + status
        + '\''
        + '}';
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

    if (getRoundNumber() != round.getRoundNumber()) {
      return false;
    }
    if (getPlayerPoints() != round.getPlayerPoints()) {
      return false;
    }
    if (getBotPoints() != round.getBotPoints()) {
      return false;
    }
    if (!getId().equals(round.getId())) {
      return false;
    }
    if (!getPlayer().equals(round.getPlayer())) {
      return false;
    }
    if (!getParty().equals(round.getParty())) {
      return false;
    }
    if (getRoundDeck() != null
        ? !getRoundDeck().equals(round.getRoundDeck())
        : round.getRoundDeck() != null) {
      return false;
    }
    if (getTrumpDeck() != null
        ? !getTrumpDeck().equals(round.getTrumpDeck())
        : round.getTrumpDeck() != null) {
      return false;
    }
    if (getPlayerDeck() != null
        ? !getPlayerDeck().equals(round.getPlayerDeck())
        : round.getPlayerDeck() != null) {
      return false;
    }
    if (getBotDeck() != null
        ? !getBotDeck().equals(round.getBotDeck())
        : round.getBotDeck() != null) {
      return false;
    }
    if (!getTurn().equals(round.getTurn())) {
      return false;
    }
    if (getTrumpPicker() != null
        ? !getTrumpPicker().equals(round.getTrumpPicker())
        : round.getTrumpPicker() != null) {
      return false;
    }
    return getStatus().equals(round.getStatus());
  }

  @Override
  public int hashCode() {
    int result = getId().hashCode();
    result = 31 * result + getRoundNumber();
    result = 31 * result + getPlayer().hashCode();
    result = 31 * result + getParty().hashCode();
    result = 31 * result + (getRoundDeck() != null ? getRoundDeck().hashCode() : 0);
    result = 31 * result + (getTrumpDeck() != null ? getTrumpDeck().hashCode() : 0);
    result = 31 * result + (getPlayerDeck() != null ? getPlayerDeck().hashCode() : 0);
    result = 31 * result + (getBotDeck() != null ? getBotDeck().hashCode() : 0);
    result = 31 * result + getPlayerPoints();
    result = 31 * result + getBotPoints();
    result = 31 * result + getTurn().hashCode();
    result = 31 * result + (getTrumpPicker() != null ? getTrumpPicker().hashCode() : 0);
    result = 31 * result + getStatus().hashCode();
    return result;
  }

  @Column(name = "rnd_turn", nullable = false)
  private String turn;

  @Column(name = "rnd_trump_picker")
  private String trumpPicker;

  @Column(name = "rnd_status", nullable = false)
  private String status;

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
