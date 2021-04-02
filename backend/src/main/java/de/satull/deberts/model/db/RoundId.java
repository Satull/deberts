package de.satull.deberts.model.db;

import java.io.Serializable;

public class RoundId implements Serializable {
  private Long id;
  private int roundNumber;

  public RoundId(Long id, int roundNumber) {
    this.id = id;
    this.roundNumber = roundNumber;
  }

  private RoundId() {
    throw new AssertionError();
  }
}
