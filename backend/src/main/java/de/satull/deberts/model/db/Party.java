package de.satull.deberts.model.db;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "parties", schema = "public")
public class Party {

  @Id
  @GeneratedValue
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

  public void setBotScore(int botScore) {
    this.botScore = botScore;
  }
}
