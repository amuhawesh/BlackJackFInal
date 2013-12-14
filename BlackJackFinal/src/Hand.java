import java.util.ArrayList;
import java.util.List;

/**
 * @(#) Hand.java
 */
public class Hand {
    private List<Card> playerCards = new ArrayList<Card>();

    public void addCard(Card card)    // I'm thinking faceupstatus should be part of the deckbox or something, not sure so I'm removing the boolean from here, add it back if you disagree
    {
        playerCards.add(card);
    }

    public int total() {
        int len      = playerCards.size();
        int total    = 0;
        int aceCount = 0;

        for (int i = 0; i < len; i++) {
            int cardrank = this.playerCards.get(i).getRank();

            if (cardrank == Card.ACE) {
                aceCount++;
            } else if ((cardrank == Card.JACK) || (cardrank == Card.KING) || (cardrank == Card.QUEEN)) {
                total += 10;
            } else {
                total += cardrank;
            }
        }

        while (aceCount > 0) {
            if (total < 21) {
                if (total + 11 <= 21 - (aceCount - 1)) {
                    total += 11;
                    aceCount--;
                } else {
                    aceCount--;
                    total++;
                }
            } else {
                aceCount--;
                total++;
            }
        }

        return total;
    }

    public String publicToString() {
        int    cardCount = this.playerCards.size();
        String output    = "";

        for (int i = 0; i < cardCount; i++) {
            Card tempCard = this.playerCards.get(i);

            if (tempCard.getFaceUpStatus()) {
                if (output != "") {
                    output += "\n";
                }

                output += tempCard.toString();
            }
        }

        return output;
    }

    public String toString() {
        int    cardCount = this.playerCards.size();
        String output    = "";

        for (int i = 0; i < cardCount; i++) {
            if (output != "") {
                output += "\n";
            }

            output += this.playerCards.get(i).toString();
        }

        return output;
    }
}

