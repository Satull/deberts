package de.satull.deberts.config;

import de.satull.deberts.model.Owner;
import de.satull.deberts.model.deck.CardDeck;
import de.satull.deberts.model.deck.HandDeck;
import de.satull.deberts.model.deck.TrumpDeck;
import de.satull.deberts.service.Party;
import de.satull.deberts.service.Round;
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

  @Bean
  public HandDeck testBotHand() {
    return new HandDeck(Owner.BOT);
  }

  @Bean
  public CardDeck testCardDeck() {
    return new CardDeck();
  }

  @Bean
  public Party testParty() {
    return new Party(testBotHand(), testPlayerHand(), testRound());
  }

  @Bean
  public HandDeck testPlayerHand() {
    return new HandDeck(Owner.PLAYER);
  }

  @Bean
  public Round testRound() {
    return new Round(testBotHand(), testCardDeck(), testPlayerHand(), testTrumpDeck());
  }

  @Bean
  public TrumpDeck testTrumpDeck() {
    return new TrumpDeck();
  }
}
