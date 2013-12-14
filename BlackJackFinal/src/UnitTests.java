import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;


public class UnitTests {

	//Test Card Class
	@Test
	public void TestCardCreation(){
		Card card = new Card(1,1);
		if(card.getRank() != 1){
			fail("Did not store rank correctly");
		}
		if(card.getSuit() != 1){
			fail("Did not store suit correctly.");
		}
	}
	
	@Test
	public void TestGettingRankStringAce(){
		Card card = new Card(1,1);
		if(!card.getRankasString().equals("Ace")){
			fail("Did not get the correct name for Ace");
		}
	}
	
	@Test
	public void TestGettingRankStringNumber(){
		for(int i = 2; i < 11; i++){
			Card card = new Card(i,1);
			if(!card.getRankasString().equals(i+"")){
				fail("Did not get the correct name for a " + i);
			}
		}
	}
	
	@Test
	public void TestGettingRankStringFace(){
		Card card = new Card(12,1);
		if(!card.getRankasString().equals("Queen")){
			fail("Did not get the correct name for a Queen");
		}
	}
	
	@Test
	public void TestGettingRankStringOutOfBounds(){
		Card card = new Card(14,1);
		if(!card.getRankasString().equals("??")){
			fail("Did not get the correct name for an out of bounds rank");
		}
	}

	@Test
	public void TestGettingSuitStringSpades(){
		Card card = new Card(1,0);
		if(!card.getSuitasString().equals("Spades")){
			fail("Did not get the string for spades.");
		}
	}
	
	@Test
	public void TestGettingSuitStringClubs(){
		Card card = new Card(1,3);
		if(!card.getSuitasString().equals("Clubs")){
			fail("Did not get the string for clubs.");
		}
	}
	
	@Test
	public void TestGettingSuitStringHearts(){
		Card card = new Card(1,1);
		if(!card.getSuitasString().equals("Hearts")){
			fail("Did not get the string for hearts.");
		}
	}
	
	@Test
	public void TestGettingSuitStringDiamonds(){
		Card card = new Card(1,2);
		if(!card.getSuitasString().equals("Diamonds")){
			fail("Did not get the string for diamonds.");
		}
	}
	
	@Test
	public void TestCardToString(){
		Card card = new Card(1,0);
		if(!card.toString().equals("Ace of Spades")){
			fail("Did not get the string represention of the card correctly.");
		}
	}

	@Test
	public void TestGettingCardFaceUpStatus(){
		Card card = new Card(1,1);
		if(card.getFaceUpStatus()){
			fail("Did not get the default state of a card correctly.");
		}
	}
	
	@Test
	public void TestSettingFaceUpStatus(){
		Card card = new Card(1,1);
		card.setFaceUpStatus(true);
		if(!card.getFaceUpStatus()){
			fail("Did not update the face up status correctly.");
		}
	}
	
	//Testing Deck Class
	@Test
	public void MakeADeck() {
		List<Card> deck = DeckBox.openNewDeckBox();
		if(deck.size() != 52){
			fail("Deck is not expected size");
		}
		for(int i = 0; i < 4; i++){
			for(int u = 1; u <= 13; u++){
				if(deck.get(u+(13*i)-1).getRank() != u){
					fail("Deck not made correctly");
				}
			}
		}
	}
	
	//Test deck shuffler
	@Test
	public void ShuffleADeck(){
		List<Card> deck = DeckBox.openNewDeckBox();
		List<Card> shuffledDeck = DeckBox.openNewDeckBox();
		DeckShufflerMachine.shuffle(shuffledDeck);
		int matches = 0;
		for(int i = 0; i < deck.size(); i++){
			if(deck.get(i).getRank() == shuffledDeck.get(i).getRank()
					&& deck.get(i).getSuit() == shuffledDeck.get(i).getSuit())
			{
				matches++;
			}
		}
		if(matches > deck.size()/2){
			fail("Deck is not shuffled well enough");
		}
	}
	
	//Test Dealer
	@Test
	public void TestGettingCardsFromDealer(){
		Dealer dealer = new Dealer();
		try{
			while(true){
				Card card = dealer.getNextCard();
				if(card == null){
					fail("did not get the cards correctly");
				}
			}
		}catch(ArrayIndexOutOfBoundsException e){
			
		}catch(Exception e){
			fail("Wrong exception was thrown.");
		}
	}

	@Test
	public void TestNewGameWithEmptyDeck() throws Exception{
		List<Card> deck = new ArrayList<Card>();
		deck.add(new Card(1,1));
		deck.add(new Card(2,2));
		Dealer dealer = new Dealer(deck);
		dealer.newGameInit();
		if(dealer.GetDeckForTests().size() <= deck.size()){
			fail("Dealer did not create a new deck when the deck is almost empty.");
		}
	}
	
	//Test Dealer AI
	@Test
	public void TestDealerAILessThen17() throws Exception{
		DealerAI ai = new DealerAI();
		Hand hand = new Hand();
		
		hand.addCard(new Card(10,1));
		hand.addCard(new Card(6,1));
		String response = ai.getInput("Press H to Hit or S to Stay", hand);
		if(!response.equals("H")){
			fail("dealer failed to hit.");
		}
	}
	
	@Test
	public void TestDealerAIEqualTo17() throws Exception{
		DealerAI ai = new DealerAI();
		Hand hand = new Hand();
		
		hand.addCard(new Card(10,1));
		hand.addCard(new Card(7,1));
		String response = ai.getInput("Press H to Hit or S to Stay", hand);
		if(!response.equals("S")){
			fail("dealer failed to stay.");
		}
	}
	
	@Test
	public void TestDealerAIGreaterThen17() throws Exception{
		DealerAI ai = new DealerAI();
		Hand hand = new Hand();
		
		hand.addCard(new Card(10,1));
		hand.addCard(new Card(10,1));
		String response = ai.getInput("Press H to Hit or S to Stay", hand);
		if(!response.equals("S")){
			fail("dealer failed to Stay.");
		}
	}
	
	//Testing BlackJack
	@Test
	public void initialDeal() throws Exception{
		initialDealMockPlayer player = new initialDealMockPlayer();
		initialDealMockDealer dealer = new initialDealMockDealer();
		
		BlackJack blackJack = new BlackJack(player, dealer);
		blackJack.gameStart();
		
		if(dealer.hand.size() > 2){
			fail("dealer got the wrong number of cards");
		}
		if(player.hand.size() > 2){
			fail("player got the wrong number of cards");
		}
	}
	private class initialDealMockPlayer implements IPlayer{
		public List<Card> hand = new ArrayList<Card>();
		
		@Override
		public void addCard(Card card) {
			hand.add(card);
		}

		@Override
		public boolean hit() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public int getHandValue() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void reset() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Hand getHand() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void sendMessage(String message) {
			// TODO Auto-generated method stub
			
		}
		
	}
	private class initialDealMockDealer implements IDealer{
		private List<Card> _deck = new ArrayList<Card>();
		public List<Card> hand = new ArrayList<Card>();
		
		public initialDealMockDealer(){
			_deck.addAll(DeckBox.openNewDeckBox());
		}
		
		@Override
		public void newGameInit() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Card getNextCard() {
			Card card = this._deck.get(0);
			this._deck.remove(0);
			return card;
		}

		@Override
		public void addCard(Card card) {
			hand.add(card);
		}

		@Override
		public boolean hit() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public int getHandValue() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void reset() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Hand getHand() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void sendMessage(String message) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Card getNextCard(boolean faceUp) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

	//Testing Player
	@Test
	public void askingPlayerIfTheyWantToHitTrue(){
		askingPlayerIfTheyWantToHitTrueMockController controller = new askingPlayerIfTheyWantToHitTrueMockController();
		Player player = new Player(controller);
		
		boolean response = player.hit();
		
		if(!response){
			fail("player should wan't to hit.");
		}
	}
	private class askingPlayerIfTheyWantToHitTrueMockController implements IController{

		@Override
		public String getInput(String message, Hand hand) throws Exception {
			return "H";
		}

		@Override
		public void sendOutput(String message) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	@Test
	public void askingPlayerIfTheyWantToHitFalse(){
		askingPlayerIfTheyWantToHitFalseMockController controller = new askingPlayerIfTheyWantToHitFalseMockController();
		Player player = new Player(controller);
		
		boolean response = player.hit();
		
		if(response){
			fail("player should wan't to stand.");
		}
	}
	private class askingPlayerIfTheyWantToHitFalseMockController implements IController{

		@Override
		public String getInput(String message, Hand hand) throws Exception {
			return "S";
		}

		@Override
		public void sendOutput(String message) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	@Test
	public void gettingPlayerHandValueNoAcesOrFaces(){
		Card[] testingCards = new Card[2];
		PlayerListener playerListener = new PlayerListener();
		Player player = new Player(playerListener);
		
		testingCards[0] = new Card(10, Card.SPADES);
		testingCards[1] = new Card(9, Card.DIAMONDS);
		
		player.addCard(testingCards[0]);
		player.addCard(testingCards[1]);
		
		if(player.getHandValue() != 19){
			fail("Player failed to give the correct hand value.");
		}
	}
	
	@Test
	public void gettingPlayerHandValueNoAces(){
		Card[] testingCards = new Card[2];
		PlayerListener playerListener = new PlayerListener();
		Player player = new Player(playerListener);
		
		testingCards[0] = new Card(Card.JACK, Card.SPADES);
		testingCards[1] = new Card(Card.KING, Card.DIAMONDS);
		
		player.addCard(testingCards[0]);
		player.addCard(testingCards[1]);
		
		if(player.getHandValue() != 20){
			fail("Player failed to give the correct hand value.");
		}
	}

	@Test
	public void gettingPlayerHandValue(){
		Card[] testingCards = new Card[2];
		PlayerListener playerListener = new PlayerListener();
		Player player = new Player(playerListener);
		
		testingCards[0] = new Card(Card.ACE, Card.SPADES);
		testingCards[1] = new Card(9, Card.DIAMONDS);
		
		player.addCard(testingCards[0]);
		player.addCard(testingCards[1]);
		
		if(player.getHandValue() != 20){
			fail("Player failed to give the correct hand value.");
		}
	}
	
	@Test
	public void gettingPlayerHandValueExactle21SingleAce(){
		Card[] testingCards = new Card[2];
		PlayerListener playerListener = new PlayerListener();
		Player player = new Player(playerListener);
		
		testingCards[0] = new Card(Card.ACE, Card.SPADES);
		testingCards[1] = new Card(10, Card.DIAMONDS);
		
		player.addCard(testingCards[0]);
		player.addCard(testingCards[1]);
		
		if(player.getHandValue() != 21){
			fail("Player failed to give the correct hand value.");
		}
	}
	
	@Test
	public void gettingPlayerHandValueExactle21DoubleAce(){
		Card[] testingCards = new Card[3];
		PlayerListener playerListener = new PlayerListener();
		Player player = new Player(playerListener);
		
		testingCards[0] = new Card(Card.ACE, Card.SPADES);
		testingCards[1] = new Card(Card.ACE, Card.SPADES);
		testingCards[2] = new Card(9, Card.DIAMONDS);
		
		player.addCard(testingCards[0]);
		player.addCard(testingCards[1]);
		player.addCard(testingCards[2]);
		
		if(player.getHandValue() != 21){
			fail("Player failed to give the correct hand value.");
		}
	}
	
	@Test
	public void gettingPlayerHandValueExactle21FourAce(){
		Card[] testingCards = new Card[5];
		PlayerListener playerListener = new PlayerListener();
		Player player = new Player(playerListener);
		
		testingCards[0] = new Card(Card.ACE, Card.SPADES);
		testingCards[1] = new Card(Card.ACE, Card.SPADES);
		testingCards[2] = new Card(Card.ACE, Card.SPADES);
		testingCards[3] = new Card(Card.ACE, Card.SPADES);
		testingCards[4] = new Card(7, Card.DIAMONDS);
		
		player.addCard(testingCards[0]);
		player.addCard(testingCards[1]);
		player.addCard(testingCards[2]);
		player.addCard(testingCards[3]);
		player.addCard(testingCards[4]);
		
		if(player.getHandValue() != 21){
			fail("Player failed to give the correct hand value.");
		}
	}
	
	@Test
	public void gettingPlayerHandValueNoAcesOrFacesOver21(){
		Card[] testingCards = new Card[3];
		PlayerListener playerListener = new PlayerListener();
		Player player = new Player(playerListener);
		
		testingCards[0] = new Card(10, Card.SPADES);
		testingCards[1] = new Card(9, Card.DIAMONDS);
		testingCards[2] = new Card(8, Card.HEARTS);
		
		player.addCard(testingCards[0]);
		player.addCard(testingCards[1]);
		player.addCard(testingCards[2]);
		
		if(player.getHandValue() != 27){
			fail("Player failed to give the correct hand value.");
		}
	}
	
	@Test
	public void gettingPlayerHandValueNoAcesOver21(){
		Card[] testingCards = new Card[3];
		PlayerListener playerListener = new PlayerListener();
		Player player = new Player(playerListener);
		
		testingCards[0] = new Card(Card.KING, Card.SPADES);
		testingCards[1] = new Card(Card.JACK, Card.DIAMONDS);
		testingCards[2] = new Card(Card.QUEEN, Card.HEARTS);
		
		player.addCard(testingCards[0]);
		player.addCard(testingCards[1]);
		player.addCard(testingCards[2]);
		
		if(player.getHandValue() != 30){
			fail("Player failed to give the correct hand value.");
		}
	}

	//Test Hand
	@Test
	public void GetHandTotalValueWithOneAceLessThen21(){
		Hand hand = new Hand();
		hand.addCard(new Card(1,1));
		hand.addCard(new Card(2,1));
		
		if(hand.total() != 13){
			fail("failed to get hand value for an ace less then 21");
		}
	}
	
	@Test
	public void GetHandTotalValueNoAce(){
		Hand hand = new Hand();
		hand.addCard(new Card(3,1));
		hand.addCard(new Card(2,1));
		
		if(hand.total() != 5){
			fail("failed to get hand value");
		}
	}
	
	@Test
	public void GetHandTotalValueWithTwoAces(){
		Hand hand = new Hand();
		hand.addCard(new Card(1,1));
		hand.addCard(new Card(1,1));
		hand.addCard(new Card(2,1));
		
		if(hand.total() != 14){
			fail("failed to get hand value for two aces less then 21");
		}
	}

	@Test
	public void TestGettingPlayersHandString(){
		Hand hand = new Hand();
		hand.addCard(new Card(2,0));
		hand.addCard(new Card(3,1));
		
		if(!hand.toString().equals("2 of Spades\n3 of Hearts")){
			fail("Did not get the players private hand string correctly.");
		}
	}
	
	@Test
	public void TestGettingOpponentHandString(){
		Hand hand = new Hand();
		Card card = new Card(2,0);
		card.setFaceUpStatus(true);
		hand.addCard(card);
		hand.addCard(new Card(3,1));
		
		if(!hand.publicToString().equals("2 of Spades")){
			fail("Did not get the players public hand string correctly.");
		}
	}
}