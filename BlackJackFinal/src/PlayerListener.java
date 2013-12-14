import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @(#) PlayerListener.java
 */
public class PlayerListener implements IController {
    public String getInput(String message, Hand hand) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println(message);
        System.out.println(hand.toString());

        try {
            return br.readLine();
        } catch (IOException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();

            return null;
        }
    }

    public void sendOutput(String message) {
        System.out.println(message);
    }
}
