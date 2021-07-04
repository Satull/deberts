package de.satull.deberts.model.deck;

import de.satull.deberts.enums.Owner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * Factory class to create different {@code Deck} implementations.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @see CardDeck
 * @see HandDeck
 * @since 1.0
 */
public class DeckFactory {

  private static final Logger LOG =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
  private static final int DEBERTS_MAX_CARDS_NUMBER = 9;

  private DeckFactory() {}

  /**
   * Returns new Deck entity depends of the input parameter. If the input parameter belongs to the
   * player or bot method creates a HandDeck for the player with ownership. Otherwise returns a
   * CardDeck
   *
   * @param owner owner of the generated deck
   * @return new Deck entity
   */
  public static Deck createDeck(Owner owner) {
    if (owner.equals(Owner.BOT) || owner.equals(Owner.PLAYER)) {
      LOG.debug("Try to create a HandDeck for: {}", owner);
      return new HandDeck(owner, DEBERTS_MAX_CARDS_NUMBER);
    } else {
      LOG.debug("Try to create a CardDeck");
      return new CardDeck();
    }
  }
}
