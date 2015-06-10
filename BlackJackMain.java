/*
 * Isaias Pomales
 * Main method for BlackJack
 */

public class BlackJackMain {

	public static void main(String[] args) {
	
		System.out.println("\n\n\nLet's play BlackJack!\nBlackJack Pays 3:2\n\n");
		
		System.out.println("How many players? (maximum of 5)");
		int numPlayers = IO.readInt();
		
		double[] money = new double[numPlayers];
		double[] bet = new double[numPlayers];
		
		if (numPlayers <= 0 || numPlayers > 5) {
			IO.reportBadInput();
			return;
		}
		
		// all players begin with $100. 
		for(int i = 0; i < money.length; i++) {
			money[i] = 100;
		}		
		
		// true if player wants to play the game again. 
		boolean playAgain = true;
		
		// runningCount[0] counts the running count.
		// the others counts the cards that have been dealt.
		int[] count = new int[11];
		
		// creates array of 52 cards. 
		Card[] cardArray = BlackJack.createCardArray();
		// shuffles the cards. 
		BlackJack.shuffle(cardArray);
		
		// this loops prints all cards, used to test order before and after shuffle. 
		/*
 		for(int i = 0; i < cardArray.length; i++) {
 			System.out.println(cardArray[i].getFace() + "		" + cardArray[i].getSuit());
		}
		*/
			
		// creates the deck of cards. 
		Deck deck = new Deck(cardArray);
		
		// entire game loops until player decides not to play again. 
		game: while (playAgain) {
		
			// if player runs out of money. 
			for (int i = 0; i < money.length; i++) {
			
				if (money[i] == 0) {
				
					System.out.println("\n\nPlayer" + (i+1) + " has run out of money.");
					break game;
					
				}
				
			}
			
			// an array of scores containing the score of each player.
			int[] score = new int[numPlayers];
			// array of scores if players split.
			int[] scoreSplit = new int[numPlayers];
			// true if player splits.
			boolean split = false;
			// score of the dealer. 
			int scoreDealer = 0;
			// if player wants insurance. 
			double[] sideBet = new double[numPlayers];
			// if player has a blackjack hand.
			boolean[] blackJack = new boolean[numPlayers];
			// initial cards dealt. 
			Card card;
			Card card2;
			Card dealerCard;
			Card dealerCard2;
			
			// dealt to dealer.
			BlackJack.newDeck(deck, cardArray, count);
			dealerCard = deck.deal();
			BlackJack.newDeck(deck, cardArray, count);
			dealerCard2 = deck.deal();
			BlackJack.newDeck(deck, cardArray, count);
			boolean blackJackDealer = false;
			
			BlackJack.cardCounter(count, dealerCard2);
			
			// stores initial score of dealer, prints only one of the dealer's card. 
			System.out.println("\nDealer: ");
			BlackJack.printCards(dealerCard2);
			
			scoreDealer = BlackJack.addScore(scoreDealer, dealerCard) + 
							BlackJack.addScore(scoreDealer, dealerCard2);
							
			if (scoreDealer == 21) 
				blackJackDealer = true;
				
			// asks each player how much they want to bet, must be <= money. 
			for(int i = 0; i < bet.length; i++) {
			
				System.out.print("\n\nPlayer" + (i+1) + ", you have $" + money[i] +
								"\nHow much would you like to bet? ");
				BlackJack.betHint(count);
									
				do {
					
					bet[i] = IO.readDouble();
						
					if (bet[i] > money[i]) 
						System.out.print("You do not have enough money, try again. ");
					else if (bet[i] <= 0) 
						System.out.print("You can't do negative or zero bets, try again. ");
				
				} while (bet[i] > money[i] || bet[i] <= 0);
				
				// amount of wager is removed from each player's bankroll.
				money[i] -= bet[i];
				
			}	// end of bet. 			
			
			// deals cards to each player and stores initial score. 
			for (int i = 0; i < score.length; i++) {
			
				
				card = deck.deal();
				BlackJack.newDeck(deck, cardArray, count);
				card2 = deck.deal();
				BlackJack.newDeck(deck, cardArray, count);
				
				BlackJack.cardCounter(count, card);
				BlackJack.cardCounter(count, card2);
				
				// if initial cards are of same value, player has option to split. 
				if (card.getFace() == card2.getFace()) {
				
					System.out.println("Player " + (i+1) + ": ");
					BlackJack.printCards(card, card2);
					System.out.println("Would you like to split? ");
					BlackJack.splitHint(card, dealerCard2);
					split = IO.readBoolean();
					
					if (split) {
					
						// amount of bet is doubled.
						money[i] -= bet[i];
						bet[i] += bet[i];
						// player now holds two separate scores. 
						score[i] = BlackJack.addScore(score[i], card);
						scoreSplit[i] = BlackJack.addScore(scoreSplit[i], card2);
						
						// blackjack[i] is true if either hand is blackjack.
						if (score[i] == 21 || scoreSplit[i] == 21) 
							blackJack[i] = true;
						
						split = false;
						
					}
					else {
					
						// calculates score of each player that does not split. 
						score[i] = BlackJack.dealCards(score[i], i, card, card2);
						BlackJack.basicHint(card, card2, dealerCard2);
					
						if (score[i] == 21) 
							blackJack[i] = true;
					
					}
					
				}	// end of initial split.
				else {
				
					// calculates score of each player that does not split. 
					score[i] = BlackJack.dealCards(score[i], i, card, card2);
					BlackJack.basicHint(card, card2, dealerCard2);
					
					if (score[i] == 21) 
						blackJack[i] = true;
					
				}
				
			}	// end of dealing.
			
			// if dealer's upcard is an ace, player has option to take insurance 
			// up to half of their main wager. 
			if (dealerCard2.getFace() == 1) {
			
				boolean[] insurance = new boolean[sideBet.length];
				
				for(int i = 0; i < sideBet.length; i++) {
				
					System.out.println("\n\nPlayer" + (i+1) + ", would you like to have insurance?");
					double probB = 0;
					int cardsInDeck = cardArray.length;
					for (int j = 1; j < count.length; j++) {
						cardsInDeck -= count[j];
					}
					probB = Math.round(((double)(16-count[10])/cardsInDeck) * 1000);
					System.out.print("HINT: There is a " + probB/10 + "% chance dealer " 
															+ "will have blackjack. ");
					insurance[i] = IO.readBoolean();
					
					if (insurance[i]) {
					
						System.out.print("How much would you like to bet?"
										+ " (up to half of main wager) ");
										
						do {
						
							sideBet[i] = IO.readDouble();
							// side must be within interval (0, bet[i]/2).
							if (sideBet[i] <= 0 || sideBet[i] > (bet[i]/2)) 
								System.out.print("Invalid bet, try again... ");
								
						} while ((sideBet[i] <= 0 || sideBet[i] > (bet[i]/2)) 
													&& (sideBet[i] < money[i]));
													
						money[i] -= sideBet[i];
						
					}
					
				}
				
			}	// end of insurance. 
				
			// used to calculate bets when player doubles down after a split.
			int[] x = new int[score.length];
			for (int i = 0; i < x.length; i++) {
				x[i] = 2;
			}
			
			// each player's turn, can hit, double down, or stand. 
			// final score at end of turn is stored. 
			for (int i = 0; i < score.length; i++) {
			
				
				// if split player has two scores that they can hit, double down, or stand. 
				if (scoreSplit[i] != 0) {
					
					scoreSplit[i] = BlackJack.playerTurn(scoreSplit[i], score[i], dealerCard2, 
														cardArray, count, money, bet, deck, i, x);
					System.out.println("Split score: " + scoreSplit[i] + "\n");
					
				}
				
				// regular turn. 
				score[i] = BlackJack.playerTurn(score[i], scoreSplit[i], dealerCard2, 
														cardArray, count, money, bet, deck, i, x);
				System.out.println("Score: " + score[i] + "\n");
				
			} // end of players' turns.
			
			// dealer's turn after all of each player's turn is over. 
			System.out.println("\n\nDealer's turn...\nUpcard:");
			BlackJack.printCards(dealerCard2);
			scoreDealer = BlackJack.dealerTurn(scoreDealer, deck,cardArray, dealerCard, count);
			System.out.println(scoreDealer);
			
			// method that determines who the winners are, and how winnings are paid. 
			BlackJack.winner(score, scoreSplit, scoreDealer, money,
						 bet, sideBet, blackJack, blackJackDealer);
			
			// goes to beginning of game loop if player wants to play again. 
			// money carries over. 
			System.out.print("Would you like to play another round? ");
			playAgain = IO.readBoolean();
			
		}	// end of game.
		
	}	// end of main.

}	// end of class BlackJackMain.
