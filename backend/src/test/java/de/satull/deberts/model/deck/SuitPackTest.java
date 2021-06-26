package de.satull.deberts.model.deck;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import de.satull.deberts.model.deck.enums.FaceValue;
import de.satull.deberts.model.deck.enums.Suit;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SuppressWarnings("javadoc")
@SpringBootTest
class SuitPackTest {

  private SuitPack firstSuitPack;

  @BeforeEach
  void beforeEach() {
    firstSuitPack = SuitPack.newInstance();
  }

  @Test
  void hashCode_createTwoNewEmptyInstances_sameHashCodeByDefault() {
    SuitPack secondSuitPack = SuitPack.newInstance();

    assertThat(firstSuitPack).hasSameHashCodeAs(secondSuitPack);
  }

  @Test
  void hashCode_createTwoInstancesAddCardToOtherInstance_differentHashCodes() {
    SuitPack secondSuitPack = SuitPack.newInstance();

    Card card = Card.newInstance(Suit.SPADES, FaceValue.QUEEN);
    secondSuitPack.addCard(card);

    assertThat(firstSuitPack.hashCode()).isNotEqualTo(secondSuitPack.hashCode());
  }

  @Test
  void equals_createTwoNewEmptyInstances_trueByDefault() {
    SuitPack secondSuitPack = SuitPack.newInstance();

    assertThat(firstSuitPack).isEqualTo(secondSuitPack);
  }

  @Test
  void equals_createTwoInstancesAddCardToOtherInstance_false() {
    SuitPack secondSuitPack = SuitPack.newInstance();

    Card card = Card.newInstance(Suit.SPADES, FaceValue.QUEEN);
    secondSuitPack.addCard(card);

    assertThat(firstSuitPack).isNotEqualTo(secondSuitPack);
  }

  @Test
  void toString_suitPackWithCardSpadesQueen_expectedToString() {
    String expectedString =
        "SuitPack{cards=[Card{suit=SPADES, faceValue=QUEEN, active=true}], activeCars=1}";

    Card card = Card.newInstance(Suit.SPADES, FaceValue.QUEEN);
    firstSuitPack.addCard(card);

    assertThat(firstSuitPack).hasToString(expectedString);
  }

  @Test
  void isEmpty_newInstance_trueByDefault() {
    assertThat(firstSuitPack.isEmpty()).isTrue();
  }

  @Test
  void isEmpty_newInstanceAddOneCard_false() {
    Card card = Card.newInstance(Suit.SPADES, FaceValue.QUEEN);
    firstSuitPack.addCard(card);

    assertThat(firstSuitPack.isEmpty()).isFalse();
  }

  @Test
  void contains_newEmptyInstanceCheckNewCard_falseByDefault() {
    Card card = Card.newInstance(Suit.CLUBS, FaceValue.JACK);

    assertThat(firstSuitPack.contains(card.getValue())).isFalse();
  }

  @Test
  void contains_newInstanceWithCardSpadesQueenCheckNewCardDiamondsKing_false() {
    Card cardToCheck = Card.newInstance(Suit.DIAMONDS, FaceValue.KING);

    Card card = Card.newInstance(Suit.SPADES, FaceValue.QUEEN);
    firstSuitPack.addCard(card);

    assertThat(firstSuitPack.contains(cardToCheck.getValue())).isFalse();
  }

  @Test
  void contains_newInstanceWithNotActiveCardSpadesQueenCheckSameActiveCard_false() {
    Card cardToCheck = Card.newInstance(Suit.SPADES, FaceValue.QUEEN);

    Card card = Card.newInstance(cardToCheck);
    firstSuitPack.addCard(card);
    firstSuitPack.dealRandomCard();

    assertThat(firstSuitPack.contains(cardToCheck.getValue())).isFalse();
  }

  @Test
  void contains_newInstanceCardSpadesQueenCheckSameActiveCard_true() {
    Card cardToCheck = Card.newInstance(Suit.SPADES, FaceValue.QUEEN);

    Card card = Card.newInstance(cardToCheck);
    firstSuitPack.addCard(card);

    assertThat(firstSuitPack.contains(cardToCheck.getValue())).isTrue();
  }

  @Test
  void addCard_newInstanceAddTwoTimesSameCard_throwsIllegalArgumentException() {
    Card firstCardToAdd = Card.newInstance(Suit.SPADES, FaceValue.QUEEN);
    Card secondCardToAdd = Card.newInstance(firstCardToAdd);

    firstSuitPack.addCard(firstCardToAdd);

    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> firstSuitPack.addCard(secondCardToAdd));
  }

  @Test
  void
      addCard_newInstanceAddNotActiveCardAndActiveCardWithSameValue_throwsIllegalArgumentException() {
    Card firstCardToAdd = Card.newInstance(Suit.SPADES, FaceValue.QUEEN);
    Card secondCardToAdd = Card.newInstance(firstCardToAdd);

    firstCardToAdd.setActive(false);
    firstSuitPack.addCard(firstCardToAdd);

    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> firstSuitPack.addCard(secondCardToAdd));
  }

  @Test
  void getActiveCards_newInstance_0ByDefault() {

    assertThat(firstSuitPack.getActiveCards()).isZero();
  }

  @Test
  void getActiveCards_suitPackWith4Cards3Active_3ActiveCards() {
    Card firstCardToAdd = Card.newInstance(Suit.SPADES, FaceValue.QUEEN);
    Card secondCardToAdd = Card.newInstance(Suit.SPADES, FaceValue.JACK);
    Card thirdCardToAdd = Card.newInstance(Suit.SPADES, FaceValue.TEN);
    Card fourthCardToAdd = Card.newInstance(Suit.SPADES, FaceValue.NINE);

    firstSuitPack.addCard(firstCardToAdd);
    firstSuitPack.addCard(secondCardToAdd);
    firstSuitPack.addCard(thirdCardToAdd);
    firstSuitPack.addCard(fourthCardToAdd);
    firstSuitPack.dealRandomCard();

    assertThat(firstSuitPack.getActiveCards()).isEqualTo(3);
  }

  @Test
  void dealCard_newInstance_throwNoSuchElementException() {

    assertThatExceptionOfType(NoSuchElementException.class)
        .isThrownBy(() -> firstSuitPack.dealCard(FaceValue.QUEEN));
  }

  @Test
  void dealCard_suitPackWith4CardsDealSpadesNine_dealCard() {
    Card firstCardToAdd = Card.newInstance(Suit.SPADES, FaceValue.QUEEN);
    Card secondCardToAdd = Card.newInstance(Suit.SPADES, FaceValue.JACK);
    Card thirdCardToAdd = Card.newInstance(Suit.SPADES, FaceValue.TEN);
    Card fourthCardToAdd = Card.newInstance(Suit.SPADES, FaceValue.NINE);

    firstSuitPack.addCard(firstCardToAdd);
    firstSuitPack.addCard(secondCardToAdd);
    firstSuitPack.addCard(thirdCardToAdd);
    firstSuitPack.addCard(fourthCardToAdd);
    Card dealtCard = firstSuitPack.dealCard(firstCardToAdd.getValue());

    assertThat(firstSuitPack.contains(dealtCard.getValue())).isFalse();
  }

  @Test
  void dealRandomCard_newInstance_throwNoSuchElementException() {

    assertThatExceptionOfType(NoSuchElementException.class)
        .isThrownBy(firstSuitPack::dealRandomCard);
  }

  @Test
  void resetPack_suitPackWith4CardsDealAllCardsResetDeck_4ActiveCards() {
    Card firstCardToAdd = Card.newInstance(Suit.SPADES, FaceValue.QUEEN);
    Card secondCardToAdd = Card.newInstance(Suit.SPADES, FaceValue.JACK);
    Card thirdCardToAdd = Card.newInstance(Suit.SPADES, FaceValue.TEN);
    Card fourthCardToAdd = Card.newInstance(Suit.SPADES, FaceValue.NINE);

    firstSuitPack.addCard(firstCardToAdd);
    firstSuitPack.addCard(secondCardToAdd);
    firstSuitPack.addCard(thirdCardToAdd);
    firstSuitPack.addCard(fourthCardToAdd);
    firstSuitPack.dealRandomCard();
    firstSuitPack.dealRandomCard();
    firstSuitPack.dealRandomCard();
    firstSuitPack.dealRandomCard();
    firstSuitPack.resetPack();

    assertThat(firstSuitPack.getActiveCards()).isEqualTo(4);
  }
}
