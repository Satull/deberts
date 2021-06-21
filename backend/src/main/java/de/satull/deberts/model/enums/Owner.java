package de.satull.deberts.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents all owners for card decks.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public enum Owner {
  @JsonProperty("bot")
  BOT,
  @JsonProperty("player")
  PLAYER,
  @JsonProperty("nobody")
  NOBODY,
  @JsonProperty("trump")
  TRUMP
}
