
//This is mostly so we can mock dealers for unit tests.
public interface IDealer extends IPlayer {
    public void newGameInit();

    public Card getNextCard();

    public Card getNextCard(boolean faceUp);
}

