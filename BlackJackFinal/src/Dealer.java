import java.util.ArrayList;
import java.util.List;

/**
 * @(#) Dealer.java
 */
public class Dealer extends Player implements IDealer {
    private List<Card> _deck      = new ArrayList<Card>();
    private final int  numOfDecks = 4;

    public Dealer() {
        super(new DealerAI());
        buildBlackjackDeck();
        DeckShufflerMachine.shuffle(this._deck);
    }

    public Dealer(List<Card> deck) throws Exception {
        super(new DealerAI());

        boolean             fromUnitTests      = false;
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        for (int i = 0; i < stackTraceElements.length; i++) {
            if (stackTraceElements[i].getFileName() == "UnitTests.java") {
                fromUnitTests = true;
            }
        }

        if (!fromUnitTests) {
            throw new Exception("This method should only be called from UnitTests.java");
        }

        this._deck = deck;
    }

    public List<Card> GetDeckForTests() throws Exception {
        boolean             fromUnitTests      = false;
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        for (int i = 0; i < stackTraceElements.length; i++) {
            if (stackTraceElements[i].getFileName() == "UnitTests.java") {
                fromUnitTests = true;
            }
        }

        if (!fromUnitTests) {
            throw new Exception("This method should only be called from UnitTests.java");
        }

        return this._deck;
    }

    public void newGameInit() {
        if (this._deck.size() < 52 * (numOfDecks / 2)) {
            _deck = new ArrayList<Card>();
            buildBlackjackDeck();
            DeckShufflerMachine.shuffle(this._deck);
        }
    }

    public Card getNextCard() {
        if (this._deck.size() < 1) {
            throw new ArrayIndexOutOfBoundsException("The deck is empty.");
        }

        Card card = this._deck.get(0);

        this._deck.remove(0);

        return card;
    }

    private void buildBlackjackDeck() {
        for (int i = 0; i < numOfDecks; i++) {
            _deck.addAll(DeckBox.openNewDeckBox());
        }
    }

    @Override
    public Card getNextCard(boolean faceUp) {
        if (this._deck.size() < 1) {
            throw new ArrayIndexOutOfBoundsException("The deck is empty.");
        }

        Card card = this._deck.get(0);

        card.setFaceUpStatus(faceUp);
        this._deck.remove(0);

        return card;
    }
}
