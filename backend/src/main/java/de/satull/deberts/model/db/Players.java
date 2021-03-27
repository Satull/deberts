package de.satull.deberts.model.db;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Players {

  @Id
  @GeneratedValue
  private Long plr_id;
  private String plr_name;
  private int  plr_last_game_result;
  private int plr_total_wins;
  private int plr_total_loses;
  private Long plr_win_rate;
  private int plr_actual_win_streak;
  private int plr_best_win_streak;

  public Long getPlr_id() {
    return plr_id;
  }

  public void setPlr_id(Long plr_id) {
    this.plr_id = plr_id;
  }

  public String getPlr_name() {
    return plr_name;
  }

  public void setPlr_name(String plr_name) {
    this.plr_name = plr_name;
  }

  public int getPlr_last_game_result() {
    return plr_last_game_result;
  }

  public void setPlr_last_game_result(int plr_last_game_result) {
    this.plr_last_game_result = plr_last_game_result;
  }

  public int getPlr_total_wins() {
    return plr_total_wins;
  }

  public void setPlr_total_wins(int plr_total_wins) {
    this.plr_total_wins = plr_total_wins;
  }

  public int getPlr_total_loses() {
    return plr_total_loses;
  }

  public void setPlr_total_loses(int plr_total_loses) {
    this.plr_total_loses = plr_total_loses;
  }

  public Long getPlr_win_rate() {
    return plr_win_rate;
  }

  public void setPlr_win_rate(Long plr_win_rate) {
    this.plr_win_rate = plr_win_rate;
  }

  public int getPlr_actual_win_streak() {
    return plr_actual_win_streak;
  }

  public void setPlr_actual_win_streak(int plr_actual_win_streak) {
    this.plr_actual_win_streak = plr_actual_win_streak;
  }

  public int getPlr_best_win_streak() {
    return plr_best_win_streak;
  }

  public void setPlr_best_win_streak(int plr_best_win_streak) {
    this.plr_best_win_streak = plr_best_win_streak;
  }



}
