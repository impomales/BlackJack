/*
 * Isaias Pomales
 * BlackJack GUI version
*/

// This is the main program for the blackjack game.
public class Blackjack
{
	// This method should:
	//	- Ask the user how many people want to play (up to 3, not including the dealer).
	//	- Create an array of players.
	//	- Create a Blackjack window.
	// 	- Play rounds until the players want to quit the game.
	//	- Close the window.
	public static void main(String[] args)
	{
		int numPlay;
		do {
			System.out.println("How many players? (maximum of three)");
			 numPlay = IO.readInt();
		} while (numPlay < 0 || numPlay > 3);
		
		Player[] play = new Player[numPlay + 1];
		play[0] = new Player("The Dealer", true);
		for (int i = 1; i < play.length; i++) {
			System.out.print("Enter name of player" + i + ": ");
			String name = IO.readString();
			play[i] = new Player(name, false);
		}
		BlackjackWindow window = new BlackjackWindow(play);
		boolean playAgain = true;
		while (playAgain) { 
			playRound(play, window);
			System.out.print("Do you want to play another round? ");
			playAgain = IO.readBoolean();
			for (int i = 0; i < play.length; i++) {
				play[i].hand.discardAll();
			}
		}	
		window.close();
	}

	// This method executes an single round of play (for all players).  It should:
	//	- Create and shuffle a deck of cards.
	//	- Start the round (deal cards) for each player, then the dealer.
	//	- Allow each player to play, then the dealer.
	//	- Finish the round (announce results) for each player.
	public static void playRound(Player[] players, BlackjackWindow window)
	{
		Deck deck = new Deck();
		deck.shuffle();
		
		for (int i = 1; i < players.length; i++) {
			players[i].startRound(deck, window);
		}
		players[0].startRound(deck, window);
		
		for (int i = 1; i < players.length; i++) {
			players[i].playRound(deck, window);
		}
		players[0].playRound(deck, window);
		
		for (int i = 1; i < players.length; i++) {
			players[i].finishRound(players[0].hand.getScore(), window);
		}
	}
}
