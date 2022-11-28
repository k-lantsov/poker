package com.company.poker.processor;

import com.company.poker.domain.Card;
import com.company.poker.domain.PokerHand;
import com.company.poker.domain.Rank;
import com.company.poker.domain.Suit;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class PokerProcessorTest {

    private final ComboProcessor comboProcessor = new ComboProcessor();
    private final PokerProcessor pokerProcessor = new PokerProcessor(comboProcessor);

    @ParameterizedTest
    @MethodSource
    void testProcess_whenPokerHandsProvided(PokerHand pokerHand1, PokerHand pokerHand2, boolean expected) {
        boolean actual = pokerProcessor.process(pokerHand1, pokerHand2);
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> testProcess_whenPokerHandsProvided() {

        Card aS = new Card(Rank.ACE, Suit.SPADES);
        Card aD = new Card(Rank.ACE, Suit.DIAMONDS);
        Card aC = new Card(Rank.ACE, Suit.CLUBS);
        Card kS = new Card(Rank.KING, Suit.SPADES);
        Card kH = new Card(Rank.KING, Suit.HEARTS);
        Card kC = new Card(Rank.KING, Suit.CLUBS);
        Card kD = new Card(Rank.KING, Suit.DIAMONDS);
        Card qS = new Card(Rank.QUEEN, Suit.SPADES);
        Card qC = new Card(Rank.QUEEN, Suit.CLUBS);
        Card qH = new Card(Rank.QUEEN, Suit.HEARTS);
        Card qD = new Card(Rank.QUEEN, Suit.DIAMONDS);
        Card jS = new Card(Rank.JACK, Suit.SPADES);
        Card jD = new Card(Rank.JACK, Suit.DIAMONDS);
        Card tS = new Card(Rank.TEN, Suit.SPADES);
        Card tD = new Card(Rank.TEN, Suit.DIAMONDS);
        Card nS = new Card(Rank.NINE, Suit.SPADES);
        Card nD = new Card(Rank.NINE, Suit.DIAMONDS);
        Card eD = new Card(Rank.EIGHT, Suit.DIAMONDS);
        Card eS = new Card(Rank.EIGHT, Suit.SPADES);
        Card twS = new Card(Rank.TWO, Suit.SPADES);
        Card thrS = new Card(Rank.THREE, Suit.SPADES);
        Card fourD = new Card(Rank.FOUR, Suit.DIAMONDS);
        Card fiveD = new Card(Rank.FIVE, Suit.DIAMONDS);

        PokerHand royalFlushS = new PokerHand(List.of(aS, kS, qS, jS, tS));
        PokerHand royalFlushD = new PokerHand(List.of(aD, kD, qD, jD, tD));
        PokerHand straightFlushKD = new PokerHand(List.of(kD, qD, jD, tD, nD));
        PokerHand straightFlushKS = new PokerHand(List.of(kS, qS, jS, tS, nS));
        PokerHand straightFlushQD = new PokerHand(List.of(qD, jD, tD, nD, eD));
        PokerHand fourOfKT = new PokerHand(List.of(kS, kD, kC, kH, tD));
        PokerHand fourOfKQ = new PokerHand(List.of(kS, kD, kC, kH, qD));
        PokerHand fourOfQT = new PokerHand(List.of(qS, qD, qC, qH, tD));
        PokerHand fourOfQA = new PokerHand(List.of(qS, qD, qC, qH, aD));
        PokerHand fullHouseKT = new PokerHand(List.of(kS, kD, kC, tS, tD));
        PokerHand fullHouseKQ = new PokerHand(List.of(kS, kD, kC, qS, qD));
        PokerHand fullHouseQK = new PokerHand(List.of(kS, kD, qS, qC, qD));
        PokerHand flushA = new PokerHand(List.of(kS, qS, tS, nS, aS));
        PokerHand flushK = new PokerHand(List.of(kS, qS, tS, nS, eS));
        PokerHand flushAKQ = new PokerHand(List.of(kS, qS, tS, nS, aS));
        PokerHand flushAKJ = new PokerHand(List.of(kS, jS, tS, nS, aS));
        PokerHand straightTA = new PokerHand(List.of(tS, jD, qS, kS, aS));
        PokerHand straightNK = new PokerHand(List.of(tS, jD, qS, kS, nS));
        PokerHand straightEQ = new PokerHand(List.of(tS, jD, qS, eS, nS));
        PokerHand straightA5 = new PokerHand(List.of(twS, thrS, aD, fourD, fiveD));
        PokerHand threeOfAKQ = new PokerHand(List.of(aS, aD, aC, kS, qC));
        PokerHand threeOfAKT = new PokerHand(List.of(aS, aD, aC, kS, tS));
        PokerHand threeOfA = new PokerHand(List.of(aS, aD, aC, kS, tS));
        PokerHand threeOfK = new PokerHand(List.of(kS, kD, kC, aS, tS));

        return Stream.of(
                Arguments.of(royalFlushS, royalFlushD, false),
                Arguments.of(royalFlushD, royalFlushS, false),
                Arguments.of(royalFlushD, straightFlushKD, true),
                Arguments.of(royalFlushD, straightFlushKS, true),
                Arguments.of(straightFlushKD, straightFlushKS, false),
                Arguments.of(straightFlushKS, straightFlushKD, false),
                Arguments.of(straightFlushKD, straightFlushQD, true),
                Arguments.of(straightFlushQD, straightFlushKD, false),
                Arguments.of(straightFlushKD, straightFlushKD, false),
                Arguments.of(fourOfKT, fourOfKQ, false),
                Arguments.of(fourOfKQ, fourOfKT, true),
                Arguments.of(fourOfKT, fourOfQT, true),
                Arguments.of(fourOfQA, fourOfKT, false),
                Arguments.of(fullHouseKT, fullHouseKQ, false),
                Arguments.of(fullHouseKQ, fullHouseKT, true),
                Arguments.of(fullHouseQK, fullHouseKT, false),
                Arguments.of(flushA, flushK, true),
                Arguments.of(flushK, flushA, false),
                Arguments.of(flushAKJ, flushAKQ, false),
                Arguments.of(flushAKQ, flushAKJ, true),
                Arguments.of(straightTA, straightEQ, true),
                Arguments.of(straightA5, straightEQ, false),
                Arguments.of(straightNK, straightEQ, true),
                Arguments.of(straightEQ, straightTA, false),
                Arguments.of(straightTA, straightA5, true)
//                Arguments.of(threeOfA, threeOfK, true),
//                Arguments.of(threeOfK, threeOfA, false)
        );
    }
}