package com.company.poker.processor;

import com.company.poker.domain.PokerHand;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HandProducerTest {

    private final HandProducer handProducer = new HandProducer();

    @Test
    void testProduceHands_whenDataProvided() {
        String line = "QD 2D AD QH 2H QD TD AD QH TH";
        List<PokerHand> pokerHands = handProducer.produce(line);
        assertAll(
                () -> assertEquals(
                        2,
                        pokerHands.size(),
                        "Producer must generate 2 hands from characters sequence"),
                () -> assertThat(pokerHands.get(0).getCards().size()).isEqualTo(5));
    }

    @Test
    void testProduceHands_whenDataNotProvided() {
        assertThrows(
                NullPointerException.class,
                () -> handProducer.produce(null),
                "It was expecting a NullPointer exception to be thrown");
    }

}