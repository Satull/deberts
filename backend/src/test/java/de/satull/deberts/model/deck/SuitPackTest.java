package de.satull.deberts.model.deck;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import de.satull.deberts.model.deck.enums.FaceValue;
import de.satull.deberts.model.deck.enums.Suit;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SuppressWarnings("javadoc")
@SpringBootTest
class SuitPackTest {

  @Test
  void hashCode_createTwoNewEmptyInstances_sameHashCodeByDefault() {
    SuitPack firstSuitPack = SuitPack.newInstance();
    SuitPack secondSuitPack = SuitPack.newInstance();

    assertThat(firstSuitPack).hasSameHashCodeAs(secondSuitPack);
  }

  @Test
  void hashCode_createTwoInstancesAddCardToOtherInstance_differentHashCodes() {
    SuitPack firstSuitPack = SuitPack.newInstance();
    SuitPack secondSuitPack = SuitPack.newInstance();

    Card card = Card.newInstance(Suit.SPADES, FaceValue.QUEEN);
    secondSuitPack.addCard(card);

    assertThat(firstSuitPack.hashCode()).isNotEqualTo(secondSuitPack.hashCode());
  }

  @Test
  void equals_createTwoNewEmptyInstances_trueByDefault() {
    SuitPack firstSuitPack = SuitPack.newInstance();
    SuitPack secondSuitPack = SuitPack.newInstance();

    assertThat(firstSuitPack).isEqualTo(secondSuitPack);
  }

  @Test
  void equals_createTwoInstancesAddCardToOtherInstance_false() {
    SuitPack firstSuitPack = SuitPack.newInstance();
    SuitPack secondSuitPack = SuitPack.newInstance();

    Card card = Card.newInstance(Suit.SPADES, FaceValue.QUEEN);
    secondSuitPack.addCard(card);

    assertThat(firstSuitPack).isNotEqualTo(secondSuitPack);
  }

  @Test
  void toString_suitPackWithCardSpadesQueen_expectedToString() {
    String expectedString = "SuitPack{cards=[Card{suit=SPADES, faceValue=QUEEN, active=true}], activeCars=1}";
    SuitPack suitPack = SuitPack.newInstance();

    Card card = Card.newInstance(Suit.SPADES, FaceValue.QUEEN);
    suitPack.addCard(card);

    assertThat(suitPack).hasToString(expectedString);
  }

  @Test
  void isEmpty_newInstance_trueByDefault() {
    SuitPack suitPack = SuitPack.newInstance();

    assertThat(suitPack.isEmpty()).isTrue();
  }

  @Test
  void isEmpty_newInstanceAddOneCard_false() {
    SuitPack suitPack = SuitPack.newInstance();

    Card card = Card.newInstance(Suit.SPADES, FaceValue.QUEEN);
    suitPack.addCard(card);

    assertThat(suitPack.isEmpty()).isFalse();
  }

  @Test
  void contains_newEmptyInstanceCheckNewCard_falseByDefault() {
    SuitPack emptySuitPack = SuitPack.newInstance();
    Card card = Card.newInstance(Suit.CLUBS, FaceValue.JACK);

    assertThat(emptySuitPack.contains(card.getValue())).isFalse();
  }

  @Test
  void contains_newInstanceWithCardSpadesQueenCheckNewCardDiamondsKing_false() {
    SuitPack suitPack = SuitPack.newInstance();
    Card cardToCheck = Card.newInstance(Suit.DIAMONDS, FaceValue.KING);

    Card card = Card.newInstance(Suit.SPADES, FaceValue.QUEEN);
    suitPack.addCard(card);

    assertThat(suitPack.contains(cardToCheck.getValue())).isFalse();
  }


  @Test
  void contains_newInstanceWithNotActiveCardSpadesQueenCheckSameActiveCard_false() {
    SuitPack suitPack = SuitPack.newInstance();
    Card cardToCheck = Card.newInstance(Suit.SPADES, FaceValue.QUEEN);

    Card card = Card.newInstance(cardToCheck);
    suitPack.addCard(card);
    suitPack.dealRandomCard();

    assertThat(suitPack.contains(cardToCheck.getValue())).isFalse();
  }

  @Test
  void contains_newInstanceCardSpadesQueenCheckSameActiveCard_true() {
    SuitPack suitPack = SuitPack.newInstance();
    Card cardToCheck = Card.newInstance(Suit.SPADES, FaceValue.QUEEN);

    Card card = Card.newInstance(cardToCheck);
    suitPack.addCard(card);

    assertThat(suitPack.contains(cardToCheck.getValue())).isTrue();
  }

  @Test
  void addCard_newInstanceAddTwoTimesSameCard_throwIllegalArgumentException() {
    SuitPack suitPack = SuitPack.newInstance();
    Card firstCardToAdd = Card.newInstance(Suit.SPADES, FaceValue.QUEEN);
    Card secondCardToAdd = Card.newInstance(firstCardToAdd);

    suitPack.addCard(firstCardToAdd);

    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> suitPack.addCard(secondCardToAdd));
  }

  @Test
  void addCard_newInstanceAddNotActiveCardAndActiveCardWithSameValue_throwIllegalArgumentException() {
    SuitPack suitPack = SuitPack.newInstance();
    Card firstCardToAdd = Card.newInstance(Suit.SPADES, FaceValue.QUEEN);
    Card secondCardToAdd = Card.newInstance(firstCardToAdd);

    firstCardToAdd.setActive(false);
    suitPack.addCard(firstCardToAdd);

    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> suitPack.addCard(secondCardToAdd));
  }

  @Test
  void getActiveCards_newInstance_0ByDefault() {
    SuitPack suitPack = SuitPack.newInstance();

    assertThat(suitPack.getActiveCards()).isZero();
  }

  @Test
  void getActiveCards_suitPackWith4Cards3Active_3ActiveCards() {
    SuitPack suitPack = SuitPack.newInstance();
    Card firstCardToAdd = Card.newInstance(Suit.SPADES, FaceValue.QUEEN);
    Card secondCardToAdd = Card.newInstance(Suit.SPADES, FaceValue.JACK);
    Card thirdCardToAdd = Card.newInstance(Suit.SPADES, FaceValue.TEN);
    Card fourthCardToAdd = Card.newInstance(Suit.SPADES, FaceValue.NINE);

    suitPack.addCard(firstCardToAdd);
    suitPack.addCard(secondCardToAdd);
    suitPack.addCard(thirdCardToAdd);
    suitPack.addCard(fourthCardToAdd);
    suitPack.dealRandomCard();

    assertThat(suitPack.getActiveCards()).isEqualTo(3);
  }

  @Test
  void dealCard_newInstance_throwNoSuchElementException() {
    SuitPack suitPack = SuitPack.newInstance();

    assertThatExceptionOfType(NoSuchElementException.class)
        .isThrownBy(() -> suitPack.dealCard(FaceValue.QUEEN));
  }

  @Test
  void dealCard_suitPackWith4CardsDealSpadesNine_dealCard() {
    SuitPack suitPack = SuitPack.newInstance();
    Card firstCardToAdd = Card.newInstance(Suit.SPADES, FaceValue.QUEEN);
    Card secondCardToAdd = Card.newInstance(Suit.SPADES, FaceValue.JACK);
    Card thirdCardToAdd = Card.newInstance(Suit.SPADES, FaceValue.TEN);
    Card fourthCardToAdd = Card.newInstance(Suit.SPADES, FaceValue.NINE);

    suitPack.addCard(firstCardToAdd);
    suitPack.addCard(secondCardToAdd);
    suitPack.addCard(thirdCardToAdd);
    suitPack.addCard(fourthCardToAdd);
    Card dealtCard = suitPack.dealCard(firstCardToAdd.getValue());

    assertThat(suitPack.contains(dealtCard.getValue())).isFalse();
  }

  @Test
  void dealRandomCard_newInstance_throwNoSuchElementException() {
    SuitPack suitPack = SuitPack.newInstance();

    assertThatExceptionOfType(NoSuchElementException.class)
        .isThrownBy(suitPack::dealRandomCard);
  }

  @Test
  void resetDeck_suitPackWith4CardsDealAllCardsResetDeck_4ActiveCards() {
    SuitPack suitPack = SuitPack.newInstance();
    Card firstCardToAdd = Card.newInstance(Suit.SPADES, FaceValue.QUEEN);
    Card secondCardToAdd = Card.newInstance(Suit.SPADES, FaceValue.JACK);
    Card thirdCardToAdd = Card.newInstance(Suit.SPADES, FaceValue.TEN);
    Card fourthCardToAdd = Card.newInstance(Suit.SPADES, FaceValue.NINE);

    suitPack.addCard(firstCardToAdd);
    suitPack.addCard(secondCardToAdd);
    suitPack.addCard(thirdCardToAdd);
    suitPack.addCard(fourthCardToAdd);
    suitPack.dealRandomCard();
    suitPack.dealRandomCard();
    suitPack.dealRandomCard();
    suitPack.dealRandomCard();
    suitPack.resetPack();

    assertThat(suitPack.getActiveCards()).isEqualTo(4);
  }
}



/*
resetPack
 */