package com.company.poker;

import com.company.poker.domain.PokerHand;
import com.company.poker.processor.ComboProcessor;
import com.company.poker.processor.HandProducer;
import com.company.poker.processor.game.GameProcessor;
import com.company.poker.processor.game.context.ComboContext;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class Main {

    private static final String PATH = "poker.txt";

    public static void main(String[] args) {
        HandProducer handProducer = new HandProducer();
        ComboContext comboContext = new ComboContext();
        ComboProcessor comboProcessor = new ComboProcessor(comboContext);
        GameProcessor gameProcessor = new GameProcessor(comboProcessor);
        int counter = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH))) {
            while (reader.ready()) {
                String line = reader.readLine();
                List<PokerHand> pokerHands = handProducer.produce(line);
                if (gameProcessor.process(pokerHands)) {
                    counter++;
                }
            }
            System.out.format("First player won %d times", counter);
        } catch (IOException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
