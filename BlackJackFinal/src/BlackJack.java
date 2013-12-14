
/**
 * @(#) BlackJack.java
 */
public class BlackJack {
    protected final static int blackJackValueLimit = 21;
    private IPlayer            _player;
    private IDealer            _dealer;
    private PlayerListener     _newPlayerQueue;

    public BlackJack() {
        this._dealer         = new Dealer();
        this._newPlayerQueue = new PlayerListener();
        this._player         = new Player(_newPlayerQueue);
    }

    // This Constructor is Purely for Unit tests.
    // ONLY THE UNIT TEST CLASS SHOULD USE THIS!!!
    public BlackJack(IPlayer player, IDealer dealer) throws Exception {
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

        this._dealer = dealer;
        this._player = player;
    }

    public void run() {
        gameStart();
        this._player.sendMessage("The Dealer Has:\n" + this._dealer.getHand().publicToString());

        // Begin a loop that checks if the player wants to hit and hasn't busted
        // It will go through each player starting with player then dealer.
        while (!playerBust(this._player) &&!playerBlackJack(this._player) && this._player.hit()) {
            this._player.addCard(this._dealer.getNextCard());
        }

        if (playerBlackJack(this._player)) {
            playerWin();

            return;
        } else if (playerBust(this._player)) {
            playerLose();

            return;
        }

        // some logic for quiting since it's over.
        while (!playerBust(this._dealer) &&!playerBlackJack(this._dealer) && this._dealer.hit()) {
            this._dealer.addCard(this._dealer.getNextCard());
        }

        this._player.sendMessage("The Dealer Has:\n" + this._dealer.getHand().toString());

        if (playerBlackJack(this._dealer)) {
            playerLose();

            return;
        } else if (playerBust(this._dealer)) {
            playerWin();

            return;
        } else if (this._player.getHandValue() > this._dealer.getHandValue()) {
            playerWin();

            return;
        } else {
            playerLose();

            return;
        }
    }

    protected void gameStart() {

        // If we have more then one player we will wan't to change this.
        // 2 Cards per player with dealer being a player.
        this._dealer.newGameInit();
        this._dealer.reset();
        this._player.reset();

        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                this._player.addCard(this._dealer.getNextCard());
                this._dealer.addCard(this._dealer.getNextCard());
            } else {
                this._player.addCard(this._dealer.getNextCard(true));
                this._dealer.addCard(this._dealer.getNextCard(true));
            }
        }
    }

    private boolean playerBust(IPlayer player) {
        int playerHandValue = player.getHand().total();

        if (playerHandValue > blackJackValueLimit) {
            return true;
        }

        return false;
    }

    private boolean playerBlackJack(IPlayer player) {
        int playerHandValue = player.getHand().total();

        if (playerHandValue == blackJackValueLimit) {
            return true;
        }

        return false;
    }

    private void playerWin() {
        this._player.sendMessage("You Win!");
        this._player.sendMessage("You have:\n" + this._player.getHand().toString());
        this._player.sendMessage("The dealer has:\n" + this._dealer.getHand().toString());
    }

    private void playerLose() {
        this._player.sendMessage("You Lose :(!");
        this._player.sendMessage("You have:\n" + this._player.getHand().toString());
        this._player.sendMessage("The dealer has:\n" + this._dealer.getHand().toString());
    }
}
