// This class represents one blackjack player (or the dealer)
public class Player
{
	// define fields here
	Hand hand;
	String name;
	boolean dealer = false;

	// This constructor creates a player.
	// If isDealer is true, this Player object represents the dealer.
	public Player(String playerName, boolean isDealer)
	{
		if (isDealer)
			dealer = true;
		name = playerName;
		hand = new Hand();
	}

	// This method retrieves the player's name.
	public String getName()
	{
		return name;
	}

	// This method retrieves the player's hand of cards.
	public Hand getHand()
	{
		return hand;
	}
	
	// This method deals two cards to the player (one face down if this is the dealer).
	// The window input should be used to redraw the window whenever a card is dealt.
	public void startRound(Deck deck, BlackjackWindow window)
	{
		Card drawn = deck.drawCard();
		if (!dealer)
			drawn.turnFaceUp();
		hand.addCard(drawn);
		window.redraw();
		
		Card drawn2 = deck.drawCard();
		drawn2.turnFaceUp();
		hand.addCard(drawn2);
		window.redraw();		
	}

	// This method executes gameplay for one player.
	// If this player is the dealer:
	//	- hits until score is at least 17
	// If this is an ordinary player:
	//	- repeatedly asks the user if they want to hit (draw another card)
	//	  until either the player wants to stand (not take any more cards) or
	//	  his/her score exceeds 21 (busts).
	// The window input should be used to redraw the window whenever a card is dealt or turned over.
	public void playRound(Deck deck, BlackjackWindow window)
	{
		Card drawn;
		boolean hit = false;
		
		if (dealer) {
		
			drawn = hand.getCard(0);
			drawn.turnFaceUp();
			window.redraw();
			
			while (hand.getScore() < 17) {
				drawn = deck.drawCard();
				drawn.turnFaceUp();
				hand.addCard(drawn);
				window.redraw();
			}
		}
		else {
			do {
				System.out.println(name + ", do you want to hit? ");
				hit = IO.readBoolean();
				if (hit) {
					drawn = deck.drawCard();
					drawn.turnFaceUp();
					hand.addCard(drawn);
					window.redraw();
				}
				if (hand.getScore() > 21) {
					System.out.println("Bust");
					break;
				}
			} while (hit);
		}	
	}

	// This method informs the player about whether they won, lost, or pushed.
	// It also discards the player's cards to prepare for the next round.
	// The window input should be used to redraw the window after cards are discarded.
	public void finishRound(int dealerScore, BlackjackWindow window)
	{
		if ((dealerScore > 21 && hand.getScore() <=21) || 
			(hand.getScore() <= 21 && hand.getScore() > dealerScore)) 
			System.out.println(name + " won!");
		else if (dealerScore == hand.getScore() && hand.getScore() <= 21)
			System.out.println(name + " push!");
		else
			System.out.println(name + " lost!");
	}
}
