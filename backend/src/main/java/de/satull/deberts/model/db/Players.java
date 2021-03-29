package de.satull.deberts.model.db;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Players {

  @Id
  @GeneratedValue
  @Column(name = "plr_id", updatable = false, nullable = false)
  private Long id;
  @Column(name = "plr_name")
  private String name;
  @Column(name = "plr_last_game_result")
  private int  lastGameResult;
  @Column(name = "plr_total_wins")
  private int totalWins;
  @Column(name = "plr_total_loses")
  private int totalLoses;
  @Column(name = "plr_win_rate")
  private long winRate;
  @Column(name = "plr_actual_win_streak")
  private int actualWinStreak;
  @Column(name = "plr_best_win_streak")
  private int bestWinStreak;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getLastGameResult() {
    return lastGameResult;
  }

  public void setLastGameResult(int lastGameResult) {
    this.lastGameResult = lastGameResult;
  }

  public int getTotalWins() {
    return totalWins;
  }

  public void setTotalWins(int totalWins) {
    this.totalWins = totalWins;
  }

  public int getTotalLoses() {
    return totalLoses;
  }

  public void setTotalLoses(int totalLoses) {
    this.totalLoses = totalLoses;
  }

  public long getWinRate() {
    return winRate;
  }

  public void setWinRate(long winRate) {
    this.winRate = winRate;
  }

  public int getActualWinStreak() {
    return actualWinStreak;
  }

  public void setActualWinStreak(int actualWinStreak) {
    this.actualWinStreak = actualWinStreak;
  }

  public int getBestWinStreak() {
    return bestWinStreak;
  }

  public void setBestWinStreak(int bestWinStreak) {
    this.bestWinStreak = bestWinStreak;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Players)) {
      return false;
    }

    Players players = (Players) o;

    if (getLastGameResult() != players.getLastGameResult()) {
      return false;
    }
    if (getTotalWins() != players.getTotalWins()) {
      return false;
    }
    if (getTotalLoses() != players.getTotalLoses()) {
      return false;
    }
    if (getWinRate() != players.getWinRate()) {
      return false;
    }
    if (getActualWinStreak() != players.getActualWinStreak()) {
      return false;
    }
    if (getBestWinStreak() != players.getBestWinStreak()) {
      return false;
    }
    if (!getId().equals(players.getId())) {
      return false;
    }
    return getName() != null ? getName().equals(players.getName()) : players.getName() == null;
  }

  @Override
  public int hashCode() {
    int result = getId().hashCode();
    result = 31 * result + (getName() != null ? getName().hashCode() : 0);
    result = 31 * result + getLastGameResult();
    result = 31 * result + getTotalWins();
    result = 31 * result + getTotalLoses();
    result = 31 * result + (int) (getWinRate() ^ (getWinRate() >>> 32));
    result = 31 * result + getActualWinStreak();
    result = 31 * result + getBestWinStreak();
    return result;
  }

  @Override
  public String toString() {
    return "Players{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", lastGameResult=" + lastGameResult +
        ", totalWins=" + totalWins +
        ", totalLoses=" + totalLoses +
        ", winRate=" + winRate +
        ", actualWinStreak=" + actualWinStreak +
        ", bestWinStreak=" + bestWinStreak +
        '}';
  }
}
