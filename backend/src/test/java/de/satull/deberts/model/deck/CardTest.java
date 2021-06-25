package de.satull.deberts.model.deck;

import static org.assertj.core.api.Assertions.assertThat;

import de.satull.deberts.model.deck.enums.FaceValue;
import de.satull.deberts.model.deck.enums.Suit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CardTest {

  @SuppressWarnings("javadoc")
  @ParameterizedTest
  @EnumSource(value = FaceValue.class, names = {"SEVEN", "EIGHT", "NINE", "TEN", "JACK", "QUEEN",
      "KING", "ACE"})
  void newInstance_diamondsSuitEachFaceValue_byDefault(FaceValue faceValue) {
    Card card = Card.newInstance(Suit.DIAMONDS, faceValue);
    assertThat(card.getValue()).isEqualTo(faceValue);
  }

  @SuppressWarnings("javadoc")
  @ParameterizedTest
  @EnumSource(value = FaceValue.class, names = {"SEVEN", "EIGHT", "NINE", "TEN", "JACK", "QUEEN",
      "KING", "ACE"})
  void newInstance_heartsSuitEachFaceValue_byDefault(FaceValue faceValue) {
    Card card = Card.newInstance(Suit.HEARTS, faceValue);
    assertThat(card.getValue()).isEqualTo(faceValue);
  }

  @SuppressWarnings("javadoc")
  @ParameterizedTest
  @EnumSource(value = FaceValue.class, names = {"SEVEN", "EIGHT", "NINE", "TEN", "JACK", "QUEEN",
      "KING", "ACE"})
  void newInstance_spadesSuitEachFaceValue_byDefault(FaceValue faceValue) {
    Card card = Card.newInstance(Suit.SPADES, faceValue);
    assertThat(card.getValue()).isEqualTo(faceValue);
  }

  @SuppressWarnings("javadoc")
  @ParameterizedTest
  @EnumSource(value = FaceValue.class, names = {"SEVEN", "EIGHT", "NINE", "TEN", "JACK", "QUEEN",
      "KING", "ACE"})
  void newInstance_clubsSuitEachFaceValue_byDefault(FaceValue faceValue) {
    Card card = Card.newInstance(Suit.CLUBS, faceValue);
    assertThat(card.getValue()).isEqualTo(faceValue);
  }

  @SuppressWarnings("javadoc")
  @ParameterizedTest
  @EnumSource(value = Suit.class, names = {"DIAMONDS", "HEARTS", "SPADES", "CLUBS"})
  void newInstance_faceValueSevenEachSuit_byDefault(Suit suit) {
    Card card = Card.newInstance(suit, FaceValue.SEVEN);
    assertThat(card.getSuit()).isEqualTo(suit);
  }

  @SuppressWarnings("javadoc")
  @Test
  void newInstance_newInstanceFromCardDiamondsSeven_isEqualByDefault() {
    Card firstCard = Card.newInstance(Suit.DIAMONDS, FaceValue.SEVEN);
    Card secondCard = Card.newInstance(firstCard);
    assertThat(firstCard).isEqualTo(secondCard);
  }

  @SuppressWarnings("javadoc")
  @Test
  void equals_diamondsSevenRandomObject_isNotEqual() {
    Card card = Card.newInstance(Suit.DIAMONDS, FaceValue.SEVEN);
    Object object = new Object();
    assertThat(card).isNotEqualTo(object);
  }

  @SuppressWarnings("javadoc")
  @Test
  void equals_diamondsSevenWithSameCard_equalByDefault() {
    Card card = Card.newInstance(Suit.DIAMONDS, FaceValue.SEVEN);
    assertThat(card).isEqualTo(card);
  }

  @SuppressWarnings("javadoc")
  @Test
  void equals_diamondsSevenNewInstanceFromCardDiamondsSevenNotActive_isNotEqual() {
    Card firstCard = Card.newInstance(Suit.DIAMONDS, FaceValue.SEVEN);
    Card secondCard = Card.newInstance(firstCard);
    secondCard.setActive(false);
    assertThat(firstCard).isNotEqualTo(secondCard);
  }

  @SuppressWarnings("javadoc")
  @Test
  void equals_diamondsSevenHeartsEight_isNotEqual() {
    Card firstCard = Card.newInstance(Suit.DIAMONDS, FaceValue.SEVEN);
    Card secondCard = Card.newInstance(Suit.HEARTS, FaceValue.EIGHT);
    assertThat(firstCard).isNotEqualTo(secondCard);
  }

  @SuppressWarnings("javadoc")
  @Test
  void equals_diamondsSevenDiamondsEight_isNotEqual() {
    Card firstCard = Card.newInstance(Suit.DIAMONDS, FaceValue.SEVEN);
    Card secondCard = Card.newInstance(Suit.DIAMONDS, FaceValue.EIGHT);
    assertThat(firstCard).isNotEqualTo(secondCard);
  }

  @SuppressWarnings("javadoc")
  @Test
  void hashcode_diamondsSevenNewInstanceFromCardDiamondsSeven_isEqual() {
    Card firstCard = Card.newInstance(Suit.DIAMONDS, FaceValue.SEVEN);
    Card secondCard = Card.newInstance(firstCard);
    assertThat(firstCard).hasSameHashCodeAs(secondCard);
  }

  @SuppressWarnings("javadoc")
  @Test
  void hashcode_diamondsSevenHeartsEight_isNotEqual() {
    Card firstCard = Card.newInstance(Suit.DIAMONDS, FaceValue.SEVEN);
    Card secondCard = Card.newInstance(Suit.HEARTS, FaceValue.EIGHT);
    assertThat(firstCard.hashCode()).isNotEqualTo(secondCard.hashCode());
  }

  @SuppressWarnings("javadoc")
  @ParameterizedTest
  @EnumSource(value = Suit.class, names = {"DIAMONDS", "HEARTS", "SPADES", "CLUBS"})
  void getValue_faceValueEightEachSuit_suitValueByDefault(Suit suit) {
    Card card = Card.newInstance(suit, FaceValue.SEVEN);
    assertThat(card.getSuitValue()).isEqualTo(suit.getValue());
  }

  @SuppressWarnings("javadoc")
  @Test
  void isActive_newInstance_trueByDefault() {
    Card card = Card.newInstance(Suit.DIAMONDS, FaceValue.ACE);
    assertThat(card.isActive()).isTrue();
  }

  @SuppressWarnings("javadoc")
  @Test
  void isActive_newInstanceSetFalse_false() {
    Card card = Card.newInstance(Suit.DIAMONDS, FaceValue.ACE);
    card.setActive(false);
    assertThat(card.isActive()).isFalse();
  }

  @SuppressWarnings("javadoc")
  @Test
  void hasSuit_suitDiamondsCheckWithDiamondsSuit_true() {
    Card card = Card.newInstance(Suit.DIAMONDS, FaceValue.ACE);
    assertThat(card.hasSuit(Suit.DIAMONDS)).isTrue();
  }

  @SuppressWarnings("javadoc")
  @ParameterizedTest
  @EnumSource(value = Suit.class, names = {"HEARTS", "SPADES", "CLUBS"})
  void hasSuit_suitDiamondsCheckWithOtherSuits_false(Suit suit) {
    Card card = Card.newInstance(suit, FaceValue.SEVEN);
    assertThat(card.hasSuit(suit)).isTrue();
  }

  @SuppressWarnings("javadoc")
  @Test
  void hasFaceValue_faceValueNineCheckWithNineFaceValue_true() {
    Card card = Card.newInstance(Suit.DIAMONDS, FaceValue.NINE);
    assertThat(card.hasFaceValue(FaceValue.NINE)).isTrue();
  }

  @SuppressWarnings("javadoc")
  @ParameterizedTest
  @EnumSource(value = FaceValue.class, names = {"SEVEN", "EIGHT", "TEN", "JACK", "QUEEN", "KING",
      "ACE"})
  void hasFaceValue_suitDiamondsCheckWithOtherSuits_false(FaceValue faceValue) {
    Card card = Card.newInstance(Suit.DIAMONDS, faceValue);
    assertThat(card.hasFaceValue(faceValue)).isTrue();
  }
}
