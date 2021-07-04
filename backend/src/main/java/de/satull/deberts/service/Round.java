package de.satull.deberts.service;

import de.satull.deberts.enums.Owner;
import de.satull.deberts.model.deck.Card;
import de.satull.deberts.model.deck.Deck;
import de.satull.deberts.model.deck.DeckFactory;
import de.satull.deberts.model.deck.Trump;
import de.satull.deberts.model.web.RoundInformation;

/**
 * An entity that contains and manages all {@code Decks} for the round. Each round has a {@code
 * CardDeck} which contains all {@code Cards} at the beginning, trump deck with {@code Trump} and
 * two {@code HandDecks} for each player. This information contains inside of {@code
 * RoundInformation} object. Each round has two phases: trade and action. In the trade-phase each
 * player has 6 cards, in the action 9.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
public class Round {

  RoundInformation information;

  private Round() {
    Deck cardDeck = DeckFactory.createDeck(Owner.NOBODY);
    Card trumpCard = cardDeck.dealRandomCard();
    int ownerValue = (Math.random() <= 0.5) ? 1 : 2;

    information =
        RoundInformation.builder()
            .cardDeck(cardDeck)
            .playerDeck(DeckFactory.createDeck(Owner.PLAYER))
            .botDeck(DeckFactory.createDeck(Owner.BOT))
            .trumpDeck(Trump.newInstance(trumpCard))
            .turn(Owner.getOwnerByValue(ownerValue))
            .build();
  }

  private Round(Owner beginner) {
    Deck cardDeck = DeckFactory.createDeck(Owner.NOBODY);
    Card trumpCard = cardDeck.dealRandomCard();

    information =
        RoundInformation.builder()
            .cardDeck(cardDeck)
            .playerDeck(DeckFactory.createDeck(Owner.PLAYER))
            .botDeck(DeckFactory.createDeck(Owner.BOT))
            .trumpDeck(Trump.newInstance(trumpCard))
            .turn(beginner)
            .build();
  }

  /**
   * Factory method to create a new {@code Round}. If beginner is not a bot or player it will be
   * decided randomly.
   *
   * @param beginner of the new Round.
   * @return new {@code Round} object
   */
  public static Round newInstance(Owner beginner) {
    if (beginner.equals(Owner.BOT) || beginner.equals(Owner.PLAYER)) {
      return new Round(beginner);
    } else {
      return new Round();
    }
  }

  /**
   * Returns information about the current state of the {@code Round}.
   *
   * @return current {@code Round} information
   */
  public RoundInformation getInformation() {
    return information;
  }
}
