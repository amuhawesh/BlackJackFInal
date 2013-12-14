public class DealerAI implements IController {
    private final static String HIT     = "H";
    private final static String STAND   = "S";
    private final static String MSG     = "Press H to Hit or S to Stay";
    private final static String DEFAULT = "Not a valid response";

    @Override
    public String getInput(String message, Hand hand) throws Exception {
        if (message.equalsIgnoreCase(MSG)) {
            if (hand.total() < 17) {
                return HIT;
            }

            return STAND;
        }

        if (message.equalsIgnoreCase(DEFAULT)) {
            throw new Exception("The game did not recognize my response. I don't know what to do... WHAT AM I!!!");
        }

        return null;
    }

    @Override
    public void sendOutput(String message) {

        // TODO Auto-generated method stub
    }
}
