package com.company.poker.processor;

import com.company.poker.domain.*;
import com.company.poker.processor.game.context.ComboContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ComboProcessorTest {
    ComboProcessor processor = new ComboProcessor(new ComboContext());

    @ParameterizedTest()
    @MethodSource
    void testProcess_whenPokerHandProvided(PokerHand pokerHand, Combo expected) throws InvocationTargetException, IllegalAccessException {
        Combo actual = processor.process(pokerHand);
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> testProcess_whenPokerHandProvided() {
        Card aS = new Card(Rank.ACE, Suit.SPADES);
        Card aD = new Card(Rank.ACE, Suit.DIAMONDS);
        Card kS = new Card(Rank.KING, Suit.SPADES);
        Card kH = new Card(Rank.KING, Suit.HEARTS);
        Card kC = new Card(Rank.KING, Suit.CLUBS);
        Card kD = new Card(Rank.KING, Suit.DIAMONDS);
        Card qS = new Card(Rank.QUEEN, Suit.SPADES);
        Card qD = new Card(Rank.QUEEN, Suit.DIAMONDS);
        Card jS = new Card(Rank.JACK, Suit.SPADES);
        Card tS = new Card(Rank.TEN, Suit.SPADES);
        Card nS = new Card(Rank.NINE, Suit.SPADES);
        Card twS = new Card(Rank.TWO, Suit.SPADES);
        Card twD = new Card(Rank.TWO, Suit.DIAMONDS);
        Card thrD = new Card(Rank.THREE, Suit.DIAMONDS);
        Card fourS = new Card(Rank.FOUR, Suit.SPADES);
        Card fourD = new Card(Rank.FOUR, Suit.DIAMONDS);
        Card fiveS = new Card(Rank.FIVE, Suit.SPADES);
        Card fiveD = new Card(Rank.FIVE, Suit.DIAMONDS);

        return Stream.of(
                Arguments.of(new PokerHand(List.of(aS, kS, qS, jS, tS)), Combo.ROYAL_FLUSH),
                Arguments.of(new PokerHand(List.of(kS, qS, jS, tS, nS)), Combo.STRAIGHT_FLUSH),
                Arguments.of(new PokerHand(List.of(fiveD, fourD, thrD, twD, aD)), Combo.STRAIGHT_FLUSH),
                Arguments.of(new PokerHand(List.of(kS, kC, kD, kH, nS)), Combo.FOUR_OF_A_KIND),
                Arguments.of(new PokerHand(List.of(kS, kC, kD, qS, qD)), Combo.FULL_HOUSE),
                Arguments.of(new PokerHand(List.of(aS, qS, jS, tS, nS)), Combo.FLUSH),
                Arguments.of(new PokerHand(List.of(aS, twS, thrD, fourS, fiveS)), Combo.STRAIGHT),
                Arguments.of(new PokerHand(List.of(nS, kS, qD, jS, tS)), Combo.STRAIGHT),
                Arguments.of(new PokerHand(List.of(kD, kS, kH, jS, tS)), Combo.THREE_OF_A_KIND),
                Arguments.of(new PokerHand(List.of(kD, kS, qS, qD, tS)), Combo.TWO_PAIRS),
                Arguments.of(new PokerHand(List.of(kD, kS, jS, qD, tS)), Combo.ONE_PAIR),
                Arguments.of(new PokerHand(List.of(kD, aS, nS, qD, tS)), Combo.NO_COMBO)
        );
    }
}