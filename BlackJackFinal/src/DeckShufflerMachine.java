import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @(#) DeckShufflerMachine.java
 */

public class DeckShufflerMachine
{
	public static void shuffle( List<Card> deck )
	{
		//Shuffle 7 times since that is what is considered to be statistically shuffled
		for(int i = 0; i < 7; i++){
			randomize(deck);
		}
	}
	
	private static void randomize(List<Card> deck){
		Random generator = new Random();
		List<Integer> shuffledSlots = new ArrayList<Integer>();
		int first = 0;
		int second = 0;
		Card temp = null;
		while(shuffledSlots.size() < deck.size()){
			first = generator.nextInt(deck.size());
			second = generator.nextInt(deck.size());
			if(shuffledSlots.contains(first) || shuffledSlots.contains(second) || first == second){
				continue;
			}
			temp = deck.get(first);
			deck.set(first, deck.get(second));
			deck.set(second, temp);
			shuffledSlots.add(first);
			shuffledSlots.add(second);
		}
	}
	
}