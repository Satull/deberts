package de.satull.deberts.model.deck;

import static org.assertj.core.api.Assertions.assertThat;

import de.satull.deberts.model.deck.enums.Owner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.boot.test.context.SpringBootTest;

@SuppressWarnings("javadoc")
@SpringBootTest
class DeckFactoryTest {

  @Test
  void createDeck_OwnerNobody_newCardDeck() {
    Deck deck = DeckFactory.createDeck(Owner.NOBODY);

    assertThat(deck).isInstanceOf(CardDeck.class);
  }

  @ParameterizedTest
  @EnumSource(value = Owner.class, names = {"BOT", "PLAYER"})
  void createDeck_OwnerBot_newHandDeck(Owner owner) {
    Deck deck = DeckFactory.createDeck(owner);

    assertThat(deck).isInstanceOf(HandDeck.class);
  }
}
