package de.satull.deberts.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.satull.deberts.model.deck.CardDeck;
import de.satull.deberts.model.deck.HandDeck;
import de.satull.deberts.model.deck.TrumpDeck;
import de.satull.deberts.model.enums.Owner;
import de.satull.deberts.service.PartyService;
import de.satull.deberts.service.RoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Config class for the test-bean-creation
 *
 * @author Ievgenii Izrailtenko
 * @version 1.5
 * @since 1.0
 */
@TestConfiguration
public class DebertsConfigTest {

  @Autowired ObjectMapper objectMapper;

  @Bean
  public HandDeck testBotHand() {
    return new HandDeck(Owner.BOT);
  }

  @Bean
  public CardDeck testCardDeck() {
    return new CardDeck();
  }

  @Bean
  public PartyService testPartyService() {
    return new PartyService(testRoundService(), objectMapper);
  }

  @Bean
  public HandDeck testPlayerHand() {
    return new HandDeck(Owner.PLAYER);
  }

  @Bean
  public RoundService testRoundService() {
    return new RoundService(testBotHand(), testCardDeck(), testPlayerHand(), testTrumpDeck());
  }

  @Bean
  public TrumpDeck testTrumpDeck() {
    return new TrumpDeck();
  }
}
