public class Player implements IPlayer {
    private final static String HIT     = "H";
    private final static String STAND   = "S";
    private final static String MSG     = "Press H to Hit or S to Stay";
    private final static String DEFAULT = "Not a valid response";
    private Hand                hand;
    private IController         _playerController;

    public Player(IController playerController) {
        this.hand              = new Hand();
        this._playerController = playerController;
    }

    public void addCard(Card card) {
        hand.addCard(card);

        // Tell player what their new card is.
    }

    public boolean hit() {
        String response = null;

        try {
            response = this._playerController.getInput(MSG, hand);
        } catch (Exception e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (response.equalsIgnoreCase(HIT)) {
            return true;
        } else if (response.equalsIgnoreCase(STAND)) {
            return false;
        } else {
            this._playerController.sendOutput(DEFAULT);

            return hit();
        }
    }

    public int getHandValue() {
        return hand.total();
    }

    public void reset() {
        hand = new Hand();
    }

    public Hand getHand() {
        return hand;
    }

    @Override
    public void sendMessage(String message) {
        this._playerController.sendOutput(message);
    }
}
