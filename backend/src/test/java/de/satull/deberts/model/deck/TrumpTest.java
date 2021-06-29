package de.satull.deberts.model.deck;

import de.satull.deberts.model.deck.enums.FaceValue;
import de.satull.deberts.model.deck.enums.Suit;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SuppressWarnings("javadoc")
@SpringBootTest
class TrumpTest {

  @Test
  void newInstance_randomCard_newTrumpByDefault() {
    Card card = Card.newInstance(Suit.HEARTS, FaceValue.SEVEN);
    Trump trump = Trump.newInstance(card);

    assertThat(trump.getSuit()).isEqualTo(card.getSuit());
    assertThat(trump.getValue()).isEqualTo(card.getValue());
  }

  @Test
  void isNative_newInstance_trueByDefault() {
    Card card = Card.newInstance(Suit.DIAMONDS, FaceValue.SEVEN);
    Trump trump = Trump.newInstance(card);

    assertThat(trump.isNative()).isTrue();
  }

  @Test
  void isNative_newInstanceSetNewTrump_false() {
    Card card = Card.newInstance(Suit.SPADES, FaceValue.ACE);
    Trump trump = Trump.newInstance(card);

    trump.setTradedTrump(Card.newInstance(Suit.CLUBS, FaceValue.ACE));

    assertThat(trump.isNative()).isFalse();
  }

  @Test
  void isNative_newInstanceSwitchTrump_false() {
    Card card = Card.newInstance(Suit.SPADES, FaceValue.KING);
    Trump trump = Trump.newInstance(card);
    Card newTrumpCard = Card.newInstance(Suit.SPADES, FaceValue.SEVEN);

    trump.switchTrump(newTrumpCard);

    assertThat(trump.isNative()).isFalse();
  }

  @Test
  void switchTrump_newInstanceSwitchToTheOtherTrump_throwsIllegalArgumentException() {
    Card card = Card.newInstance(Suit.CLUBS, FaceValue.QUEEN);
    Trump trump = Trump.newInstance(card);
    Card newTrumpCard = Card.newInstance(Suit.DIAMONDS, FaceValue.SEVEN);

    assertThatThrownBy(() -> trump.switchTrump(newTrumpCard))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void switchTrump_newInstanceSetOtherTrumpSwitchTrump_throwsIllegalArgumentException() {
    Card card = Card.newInstance(Suit.CLUBS, FaceValue.QUEEN);
    Trump trump = Trump.newInstance(card);
    Card newTrumpCardToSet = Card.newInstance(Suit.HEARTS, FaceValue.KING);
    Card newTrumpCardToSwitch = Card.newInstance(Suit.DIAMONDS, FaceValue.SEVEN);

    trump.setTradedTrump(newTrumpCardToSet);

    assertThatThrownBy(() -> trump.switchTrump(newTrumpCardToSwitch))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void switchTrump_newInstanceSwitchToTheTrumpSeven_cardIsSwitched() {
    Card card = Card.newInstance(Suit.CLUBS, FaceValue.QUEEN);
    Trump trump = Trump.newInstance(card);
    Card newTrumpCard = Card.newInstance(Suit.CLUBS, FaceValue.SEVEN);

    Card trumpCard = trump.switchTrump(newTrumpCard);

    assertThat(trumpCard).isEqualTo((card));
    assertThat(trump.getSuit()).isEqualTo(newTrumpCard.getSuit());
    assertThat(trump.getValue()).isEqualTo(newTrumpCard.getValue());
  }

  @Test
  void setTradeTrump_newInstanceSetNewTrump_trumpIsSet() {
    Card card = Card.newInstance(Suit.DIAMONDS, FaceValue.JACK);
    Trump trump = Trump.newInstance(card);
    Card newTrumpCard = Card.newInstance(Suit.HEARTS, FaceValue.NINE);

    trump.setTradedTrump(newTrumpCard);

    assertThat(trump.getSuit()).isEqualTo(newTrumpCard.getSuit());
  }

  @Test
  void setTradeTrump_newInstanceSetTrumpWithSameSuit_throwsIllegalArgumentException() {
    Card card = Card.newInstance(Suit.SPADES, FaceValue.TEN);
    Trump trump = Trump.newInstance(card);
    Card newTrumpCard = Card.newInstance(Suit.SPADES, FaceValue.EIGHT);

    assertThatThrownBy(() -> trump.setTradedTrump(newTrumpCard))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void setTradeTrump_newInstanceSetTrumpAfterSetTrump_throwsIllegalArgumentException() {
    Card card = Card.newInstance(Suit.SPADES, FaceValue.TEN);
    Trump trump = Trump.newInstance(card);
    Card newTrumpCard = Card.newInstance(Suit.CLUBS, FaceValue.EIGHT);
    Card secondNewTrumpCard = Card.newInstance(Suit.DIAMONDS, FaceValue.EIGHT);

    trump.setTradedTrump(newTrumpCard);

    assertThatThrownBy(() -> trump.setTradedTrump(secondNewTrumpCard))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void hashCode_newInstanceWithSameCard_sameHashCode() {
    Card card = Card.newInstance(Suit.SPADES, FaceValue.TEN);
    Trump trump = Trump.newInstance(card);
    Trump secondTrump = Trump.newInstance(card);

    assertThat(trump).hasSameHashCodeAs(secondTrump);
  }

  @Test
  void hashCode_newInstanceWithDifferentCards_differentHashCode() {
    Card card = Card.newInstance(Suit.SPADES, FaceValue.TEN);
    Card secondCard = Card.newInstance(Suit.DIAMONDS, FaceValue.TEN);
    Trump trump = Trump.newInstance(card);
    Trump secondTrump = Trump.newInstance(secondCard);

    assertThat(trump.hashCode()).isNotEqualTo(secondTrump.hashCode());
  }

  @Test
  void equals_newInstanceWithSameCard_true() {
    Card card = Card.newInstance(Suit.SPADES, FaceValue.TEN);
    Trump trump = Trump.newInstance(card);
    Trump secondTrump = Trump.newInstance(card);

    assertThat(trump).isEqualTo(secondTrump);
  }

  @Test
  void equals_newInstanceWithDifferentCards_false() {
    Card card = Card.newInstance(Suit.SPADES, FaceValue.TEN);
    Card secondCard = Card.newInstance(Suit.DIAMONDS, FaceValue.TEN);
    Trump trump = Trump.newInstance(card);
    Trump secondTrump = Trump.newInstance(secondCard);

    assertThat(trump).isNotEqualTo(secondTrump);
  }

  @Test
  void toString_newCardDeck_StringContainsSuitsAndFullSuits() {
    Card card = Card.newInstance(Suit.CLUBS, FaceValue.JACK);
    Trump trump = Trump.newInstance(card);

    assertThat(trump.toString()).contains("card").contains("nativeTrump");
  }
}
