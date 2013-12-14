
//This is mostly so we can mock players for unit tests.
public interface IPlayer {
    public void addCard(Card card);

    public boolean hit();

    public int getHandValue();

    public void reset();

    public void sendMessage(String message);

    public Hand getHand();
}
