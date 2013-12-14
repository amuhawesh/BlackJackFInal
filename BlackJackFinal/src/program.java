import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.Arrays;
import java.util.List;

public class program {
    private static List<String> validInputs = Arrays.asList("yes", "y", "no", "n");

    public static void main(String[] args) {
        BlackJack blackJack = new BlackJack();
        String    response;
        boolean   keepPlaying = false;

        response = GetUserInput();

        if (response.toLowerCase().equals("yes") || response.toLowerCase().equals("y")) {
            keepPlaying = true;
        }

        while (keepPlaying) {
            blackJack.run();
            response = GetUserInput();

            if (response.toLowerCase().equals("yes") || response.toLowerCase().equals("y")) {
                keepPlaying = true;
            }
        }
    }

    private static String GetUserInput() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String         response;

        System.out.println("Do you wish to play blackjack?((y)es, (n)o)");

        try {
            response = br.readLine();

            if (!validInputs.contains(response)) {
                System.out.println("Invalid input");

                return GetUserInput();
            }
        } catch (IOException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Invalid input");

            return GetUserInput();
        }

        return response;
    }
}
