package mataffi;

import java.io.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class BankDB {
    private Map<String, Card> cards;
    private final String FILE;

    public BankDB() {
        cards = new HashMap<>();
        FILE = "src/main/resources/Cards.txt";
        loadCards();
    }

    public Card getCard(String cardNumber) {
        return cards.get(cardNumber);
    }

    public void saveCards() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE))) {
            for (Card card : cards.values()) {
                bufferedWriter.write(card.toString());
                bufferedWriter.newLine();
            }
        } catch (IOException ex) {
            System.out.println("Error saving file");
        }
    }

    public void loadCards() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE))) {
            String data;
            while ((data = bufferedReader.readLine()) != null) {
                String[] cardData = data.split(" ");
                Card card = new Card(cardData[0], Integer.parseInt(cardData[1]), Double.parseDouble(cardData[2]));
                card.setAttempts(Integer.parseInt(cardData[3]));
                card.setBlocked("null".equals(cardData[4]) ? null : LocalDateTime.parse(cardData[4]));
                cards.put(cardData[0], card);
            }
        } catch (IOException ex) {
            System.out.println("Error loading file");
        }
    }
}
