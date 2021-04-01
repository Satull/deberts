package de.satull.deberts.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Owner {
  @JsonProperty("bot")
  BOT,
  @JsonProperty("player")
  PLAYER,
  @JsonProperty("undefined")
  UNDEFINED,
  @JsonProperty("trump")
  TRUMP
}
