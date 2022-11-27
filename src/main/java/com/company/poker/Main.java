package com.company.poker;

import com.company.poker.domain.Hand;
import com.company.poker.processor.ComboProcessor;
import com.company.poker.processor.HandProducer;
import com.company.poker.processor.PokerProcessor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Main {

    private static final String PATH = "poker.txt";

    public static void main(String[] args) {
        HandProducer handProducer = new HandProducer();
        PokerProcessor pp = new PokerProcessor(new ComboProcessor());
        int counter = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH))) {
            while (reader.ready()) {
                String line = reader.readLine();
                List<Hand> hands = handProducer.produce(line);
                if (pp.process(hands.get(0), hands.get(1))) {
                    counter++;
                }
            }
            System.out.format("First player won %d times", counter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
