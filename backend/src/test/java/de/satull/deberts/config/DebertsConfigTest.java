package de.satull.deberts.config;

import de.satull.deberts.deck.CardDeck;
import de.satull.deberts.deck.HandDeck;
import de.satull.deberts.deck.TrumpDeck;
import de.satull.deberts.service.Party;
import de.satull.deberts.service.Round;
import de.satull.deberts.util.Game;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class DebertsConfigTest {

  @Bean
  public HandDeck testBotHand() {
    return new HandDeck(Game.BOT);
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
    return new HandDeck(Game.PLAYER);
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