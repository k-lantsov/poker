import domain.Hand;
import processor.ComboProcessor;
import processor.PokerProcessor;
import processor.HandGenerator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Main {

    private static final String PATH = "poker.txt";

    public static void main(String[] args) {
        HandGenerator handGenerator = new HandGenerator();
        PokerProcessor hpp = new PokerProcessor(new ComboProcessor());
        int counter = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH))) {
            while (reader.ready()) {
                String line = reader.readLine();
                List<Hand> hands = handGenerator.generate(line);
                if (hpp.process(hands.get(0), hands.get(1))) {
                    counter++;
                }
            }
            System.out.format("First player won %d times", counter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
