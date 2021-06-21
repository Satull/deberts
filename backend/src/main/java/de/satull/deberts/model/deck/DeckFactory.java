package de.satull.deberts.model.deck;

import de.satull.deberts.model.enums.Owner;

/**
 * Factory class to create different Deck implementations.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @see CardDeck
 * @since 1.0
 */
public class DeckFactory {

  private DeckFactory() {
  }

  /**
   * Returns new Deck entity depends of the input parameter. If the input parameter belongs to the
   * player or bot method creates a HandDeck for the player with ownership. Otherwise returns a
   * CardDeck
   *
   * @param owner owner of the generated deck
   * @return new Deck entity
   */

  public static Deck createDeck(Owner owner) {
    if (owner.equals(Owner.NOBODY)) {
      return new CardDeck();
    } else {
      return null;
    }
  }
}
