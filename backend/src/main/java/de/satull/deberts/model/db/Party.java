package de.satull.deberts.model.db;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "parties", schema = "public")
public class Party {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "prt_id", updatable = false, nullable = false)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "prt_plr_id")
  private Player player;

  @Column(name = "prt_player_score")
  private int playerScore;

  @Column(name = "prt_bot_score")
  private int botScore;

  @OneToMany(mappedBy = "party")
  private List<Round> rounds = new ArrayList<Round>();

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Column(name = "prt_status", nullable = false)
  private String status;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Player getPlayer() {
    return player;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  public int getPlayerScore() {
    return playerScore;
  }

  public void setPlayerScore(int playerScore) {
    this.playerScore = playerScore;
  }

  public int getBotScore() {
    return botScore;
  }

  @Override
  public String toString() {
    return "Party{"
        + "id="
        + id
        + ", player="
        + player
        + ", playerScore="
        + playerScore
        + ", botScore="
        + botScore
        + ", rounds="
        + rounds
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
    if (!(o instanceof Party)) {
      return false;
    }

    Party party = (Party) o;

    if (getPlayerScore() != party.getPlayerScore()) {
      return false;
    }
    if (getBotScore() != party.getBotScore()) {
      return false;
    }
    if (!getId().equals(party.getId())) {
      return false;
    }
    if (!getPlayer().equals(party.getPlayer())) {
      return false;
    }
    if (!rounds.equals(party.rounds)) {
      return false;
    }
    return getStatus().equals(party.getStatus());
  }

  @Override
  public int hashCode() {
    int result = getId().hashCode();
    result = 31 * result + getPlayer().hashCode();
    result = 31 * result + getPlayerScore();
    result = 31 * result + getBotScore();
    result = 31 * result + rounds.hashCode();
    result = 31 * result + getStatus().hashCode();
    return result;
  }

  public void setBotScore(int botScore) {
    this.botScore = botScore;
  }
}
