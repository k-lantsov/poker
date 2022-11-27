package com.company.poker.processor;

import com.company.poker.domain.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ComboProcessorTest {
    ComboProcessor processor = new ComboProcessor();

    @ParameterizedTest()
    @MethodSource
    void testProcess_whenPokerHandProvided(PokerHand pokerHand, Combo expected) {
        Combo actual = processor.process(pokerHand);
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> testProcess_whenPokerHandProvided() {
        Card aS = new Card(Rank.ACE, Suit.SPADES);
        Card kS = new Card(Rank.KING, Suit.SPADES);
        Card kH = new Card(Rank.KING, Suit.HEARTS);
        Card kC = new Card(Rank.KING, Suit.CLUBS);
        Card kD = new Card(Rank.KING, Suit.DIAMONDS);
        Card qS = new Card(Rank.QUEEN, Suit.SPADES);
        Card qD = new Card(Rank.QUEEN, Suit.DIAMONDS);
        Card jS = new Card(Rank.JACK, Suit.SPADES);
        Card tS = new Card(Rank.TEN, Suit.SPADES);
        Card nS = new Card(Rank.NINE, Suit.SPADES);

        return Stream.of(
                Arguments.of(new PokerHand(List.of(aS, kS, qS, jS, tS)), Combo.ROYAL_FLUSH),
                Arguments.of(new PokerHand(List.of(kS, qS, jS, tS, nS)), Combo.STRAIGHT_FLUSH),
                Arguments.of(new PokerHand(List.of(kS, kC, kD, kH, nS)), Combo.FOUR_OF_A_KIND),
                Arguments.of(new PokerHand(List.of(kS, kC, kD, qS, qD)), Combo.FULL_HOUSE),
                Arguments.of(new PokerHand(List.of(aS, qS, jS, tS, nS)), Combo.FLUSH)
                );
    }
}