package de.satull.deberts.config;

import de.satull.deberts.deck.CardDeck;
import de.satull.deberts.deck.HandDeck;
import de.satull.deberts.deck.TrumpDeck;
import de.satull.deberts.service.Party;
import de.satull.deberts.service.Round;
import de.satull.deberts.util.Game;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DebertsConfig {

  @Bean
  public HandDeck botHand() {
    return new HandDeck(Game.BOT);
  }

  @Bean
  public CardDeck cardDeck() {
    return new CardDeck();
  }

  @Bean
  public Party party() {
    return new Party(botHand(), playerHand(), round());
  }

  @Bean
  public HandDeck playerHand() {
    return new HandDeck(Game.PLAYER);
  }

  @Bean
  public Round round() {
    return new Round(botHand(), cardDeck(), playerHand(), trumpDeck());
  }

  @Bean
  public TrumpDeck trumpDeck() {
    return new TrumpDeck();
  }
}