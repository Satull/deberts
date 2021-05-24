package de.satull.deberts.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.satull.deberts.model.deck.CardDeck;
import de.satull.deberts.model.deck.HandDeck;
import de.satull.deberts.model.deck.TrumpDeck;
import de.satull.deberts.model.enums.Owner;
import de.satull.deberts.service.DataBaseService;
import de.satull.deberts.service.PartyService;
import de.satull.deberts.service.RoundService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Config class for the bean-creation
 *
 * @author Ievgenii Izrailtenko
 * @version 1.5
 * @since 1.0
 */
@Configuration
public class DebertsConfig {

  @Bean
  public ObjectMapper objectMapper(){
    return new ObjectMapper();
  }

  @Bean
  public HandDeck botHand() {
    return new HandDeck(Owner.BOT);
  }

  @Bean
  public CardDeck cardDeck() {
    return new CardDeck();
  }

  @Bean
  public PartyService partyService() {
    return new PartyService(roundService(), objectMapper());
  }

  @Bean
  public HandDeck playerHand() {
    return new HandDeck(Owner.PLAYER);
  }

  @Bean
  public RoundService roundService() {
    return new RoundService(botHand(), cardDeck(), playerHand(), trumpDeck());
  }

  @Bean
  public TrumpDeck trumpDeck() {
    return new TrumpDeck();
  }
}
