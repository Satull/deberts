package de.satull.deberts.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Suit {
  @JsonProperty("clubs")
  CLUBS,
  @JsonProperty("diamonds")
  DIAMONDS,
  @JsonProperty("hearts")
  HEARTS,
  @JsonProperty("spades")
  SPADES
}
