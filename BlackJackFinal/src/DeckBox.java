import java.util.ArrayList;
import java.util.List;

/**
 * @(#) Deck.java
 */
public class DeckBox {
    public static List<Card> openNewDeckBox() {
        List<Card> cards = new ArrayList<Card>();

        for (int i = 0; i < 4; i++) {
            for (int u = 1; u <= 13; u++) {
                cards.add(new Card(u, i));
            }
        }

        return cards;
    }
}

