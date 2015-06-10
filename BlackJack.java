/*
 * Isaias Pomales
 * Methods used in BlackJackMain.java
 */

public class BlackJack {

	public static Card[] createCardArray() {
		// creates array of 52 cards.
		Card[] cardArray = new Card[52];
			
		for (int i = 0; i < cardArray.length;) {
			// from SPADES-DIAMONDS
			for (int j = Card.SPADES; j <= Card.DIAMONDS; j++) {
				// from A-K.
				for (int k = 1; k <=13; k++) {
				
					cardArray[i] = new Card(j, k);
					i++;
			
				}
		
			}
			
		}
		
		return cardArray;
	
	}
	
	public static void shuffle(Card[] cardArray) {
		
		int random = 0;		// random number.
		Card temp;			// variable used for swapping.	
	
		// creates a random number between 0 and i. swaps values at index i with random index. 
		// shuffles three times. 
	
		for (int i = 3; i > 0; i --) {
			for (int j = 0; j < cardArray.length; j++) {
			
				random = (int) ((Math.random() * (cardArray.length-1)) + 1);
				temp = cardArray[j];
				cardArray[j] = cardArray[random];
				cardArray[random] = temp;
			
			}
		}
	}
	
	public static int dealCards(int score, int n, Card card, Card card2) {
	
		// deals two cards a player, counts the initial score, and prints the cards.
		System.out.println("\nPlayer" + (n+1) + ": ");
		score = BlackJack.addScore(score, card) + BlackJack.addScore(score, card2);
		BlackJack.printCards(card, card2);
		return score;
		
	}
	
	public static int playerTurn(int score, 
							int scoreSplit, 
							Card dealerCard,
							Card[] cardArray,
							int[] count, 
							double[] money, 
							double[] bet,
							Deck d, int n, int[] x) {
	
		// player's turn until "Stand" or score is above 21.
		Card dealt;
		
		int option = -1;
		
		while (true) {
		
			System.out.println("\n\nPlayer " + (n+1) +"\nEnter number of option" +
							"\n\t1-hit\n\t2-double down\n\t3-stand ");
			basicHint(score, dealerCard);
			probability(score, count, d);
			option = IO.readInt();
			
			// if player chooses to hit of double down.
			if (option == 1 || option == 2) {
				// turn ends if deck runs out of cards. 
				if (d.isEmpty()) {
					System.out.println("Deck ran out of cards...round ends");
					break;
				}
				
				dealt = d.deal();
				BlackJack.newDeck(d, cardArray, count);
				cardCounter(count, dealt);
				
				System.out.println();
				score = addScore(score, dealt);
				printCards(dealt);
				
				// if player doubles down, bet is doubled.
				if (option == 2) {
				
					if (scoreSplit != 0) {
						
						money[n] -= (bet[n]/x[n]);
						bet[n] += (bet[n]/x[n]);
						x[n]++;
						
					}
					else {
					
						money[n] -= bet[n];
						bet[n] *= 2;
						
					}	
					System.out.println("\nPlayer" + (n+1) +
										 "chose to double down, wager now is $" + bet[n]);
										 
					// turn ends if player busts. 
					if (score > 21) 
						System.out.print("Bust! ");
										
					break;
					
				}
				
				if (score > 21) {
				
					System.out.print("Bust! ");
					break;
					
				}
					
			}
			// player stands		
			else if (option == 3) {
			
				System.out.println("Player Stands...");
				break;
				
			}
			// invalid input
			else 			
				System.out.println("Invalid option, try again....");
			
		}
			
		System.out.println("Turn is over.");
		return score;
	
	}
	
	public static void basicHint(int score, Card dealerCard) {
		
		int dealerScore = addScore(0, dealerCard);
	
		if (score >= 17 && score <=21) 
			System.out.println("HINT: You should stand...");
		else if (score >= 13 && score <= 16) {
		
			if (dealerScore < 7) 
				System.out.println("HINT: You should stand...");
			else
				System.out.println("HINT: You should hit...");
			
		}
		else if (score == 12) {
			
			if (dealerScore >= 4 && dealerScore <= 6) 
				System.out.println("HINT: You should stand...");
			else 
				System.out.println("HINT: You should hit...");
		
		}
		else if (score == 11) {
		
			if (dealerScore != 11)
				System.out.println("HINT: You should double down...");
			else
				System.out.println("HINT: You should hit...");
			
		}
		else if (score == 10) {
		
			if (dealerScore != 10 || dealerScore != 11)
				System.out.println("HINT: You should double down...");
			else
				System.out.println("HINT: You should hit...");
				
		}
		else if (score == 9) {
		
			if (dealerScore >= 3 && dealerScore <= 6) 
				System.out.println("HINT: You should double down...");
			else 
				System.out.println("HINT: You should hit...");
		
		}
		else if (score >= 5 && score <= 8) {
		
			System.out.println("HINT: You should hit...");
		
		}
	
	}
	
	public static void basicHint(Card card, Card card2, Card dealerCard) {
	
		int score = addScore(0, card) + addScore(0, card2);
		int scoreDealer = addScore(0, dealerCard);
		
		if (card.getFace() == 1 || card2.getFace() == 1) {
		
			if (score >= 19)
				System.out.println("HINT: Soft total, you should stand...");
			else if (score == 17) {
			
				if (scoreDealer >= 3 && scoreDealer <= 6) 
					System.out.println("HINT: Soft total, you should double down...");
				else if (scoreDealer > 8)
					System.out.println("HINT: Soft total, you should hit...");
				else
					System.out.println("HINT: Soft total, you should stand...");
					
			}
			else if (score == 16) {
			
				if (scoreDealer >= 3 && scoreDealer <= 6) 
					System.out.println("HINT: Soft total, you should double down...");
				else
					System.out.println("HINT: Soft total, you should hit...");
			
			}
			else if (score == 14 || score == 15) {
			
				if (scoreDealer >= 4 && scoreDealer <= 6)
					System.out.println("HINT: Soft total, you should double down...");
				else 
					System.out.println("HINT: Soft total, you should hit...");
			
			}
			else if (score == 12 || score == 13) {
			
				if (scoreDealer == 5 || scoreDealer == 6)
					System.out.println("HINT: Soft total, you should double down...");
				else
					System.out.println("HINT: Soft total, you should hit...");
			
			}
			
		}
	
	}
	
	public static void splitHint(Card card, Card dealerCard) {
	
		if (card.getFace() == 1 || card.getFace() == 8) 
			System.out.println("HINT: You should split...");
		else if (card.getValue() == 10) 
			System.out.println("HINT: Don't split, stand...");
		else if (card.getValue() == 9) {
			
			if (dealerCard.getValue() == 7 ||
				dealerCard.getValue() == 10 || 
				dealerCard.getValue() == 11) {
				
				System.out.println("HINT: Don't split, stand...");
				
			}
			else 
				System.out.println("HINT: You should split...");
			
		}
		else if (card.getValue() == 2 ||
				card.getValue() == 3 ||
				card.getValue() == 7) {
				
			if (dealerCard.getValue() >= 2 && dealerCard.getValue() <= 7)
				System.out.println("HINT: You should split...");
			else
				System.out.println("HINT: Don't split, hit...");
					
		}
		else if (card.getValue() == 6) {
		
			if (dealerCard.getValue() >=2 && dealerCard.getValue() < 7)
				System.out.println("HINT: You should split...");
			else
				System.out.println("HINT: Don't split, hit...");
					
		}
		else if (card.getValue() == 5) {
		
			if (dealerCard.getValue() >= 2 && dealerCard.getValue() <= 9)
				System.out.println("HINT: Don't split, double down...");
			else
				System.out.println("HINT: Don't split, hit...");
		
		}
		else if (card.getValue() == 4) {
		
			if (dealerCard.getValue() == 5 || dealerCard.getValue() == 6)
				System.out.println("HINT: You should split...");
			else
				System.out.println("HINT: Don't split, hit..."); 
		
		}			
	
	}
	
	public static void betHint(int[] count) {
	
		// based on card counting strategy. 
		// player more likely to win bet if card count is positive. 
		if (count[0] > 0) 
			System.out.println("\nRunning count is " + count[0] + ", you should bet high");
		else if (count[0] < 0)
			System.out.println("\nRunning count is " + count[0] + ", you should bet low");
		else if (count[0] == 0)
			System.out.println("\nRunning count is " + count[0] + ", you can bet high or low");
	
	}
	
	public static void cardCounter(int[] count, Card card) {
		
		// Zen Count strategy. more positive, means more high cards in deck which benefits player. 
		if (card.getValue() == 2 || card.getValue() == 3 || card.getValue() == 7)
			count[0] += 1;
		else if (card.getValue() >= 4 && card.getValue() <= 6)
			count[0] += 2;
		else if (card.getValue() == 10) 
			count[0] -= 2;
		else if (card.getFace() == 1)
			count[0] -= 1;
			
		// keeps track of what cards are out. 
		// increments different counter for each different card. 
		for (int i = 1; i < count.length; i++) {
			if (card.getValue() == i)
				count[i]++;
		}	
		
	}
	
	public static void probability(int score, int[] count, Deck d) {
		
		int condition = 21 - score;		// value above condition results in player bust. 
		double prob = 0;
		int cardsInDeck = 52;
		
		// finds how many of each type of card is left in deck that would result in a bust.
		for (int i = (condition + 1); i < count.length; i++) {
		
			if (i == 10)
				prob += (16 - count[i]);
			else
				prob += (4 - count[i]);
		
		}
		
		for (int i = 1; i < count.length; i++) {
			// counts number of cards left in deck so far. 
			cardsInDeck -= count[i];
		
		}
		// calculates the probability rounding to one decimal place. 
		prob = Math.round((prob/cardsInDeck) * 1000);
		System.out.println("There is a " + prob/10 + "% chance you will bust, if you hit...");
	
	}
	
	public static int dealerTurn(int score, Deck d, Card[] cardArray, Card flipped, int[] count) {
	
		// dealer's turn until "bust" or score is above 17.
		printCards(flipped);
		cardCounter(count, flipped);
	
		Card dealt;
		
		// dealer hits until his score >= 17 or busts. 
		while (score < 17) {
		
			if (d.isEmpty()) {
				break;
			}
			
			dealt = d.deal();
			BlackJack.newDeck(d, cardArray, count);
			
			cardCounter(count, dealt);
			printCards(dealt);
			
			if (dealt.getValue() != 1) 
				score = addScore(score, dealt);
			else {
			
				// if card is an ace, add 11 if score will not bust, otherwise add 1.
				if (score < 11 ) 
					score += 11;
				else 
					score += 1;
				
			}
			
			if (score > 21) {
			
				System.out.println("Dealer Bust!");
				break;
				
			}
			
		}
		
		return score;
		
	}
	
	public static int addScore(int score, Card dealt) {
	
		// adds value of dealt card to player's score. 
		// if an ace, add 11 if score will not bust, otherwise add 1.
		if (dealt.getValue() == 1) {
			if (score < 11 ) {
					score += 11;
				}
				else {
					score += 1;
				}
		}
		else {
			score += dealt.getValue();
		}
		
		return score;
	
	}
	
	public static void winner(int[] score,
							int[] scoreSplit, 
							int scoreDealer, 
							double[] money, 
							double[] bet,
							double[] sideBet,
							boolean[] blackJack,
							boolean blackJackDealer) {
		
		if (scoreDealer > 21) {
			
			for (int i = 0; i < score.length; i++) {
			
				// if dealer busts, players that didn't bust wins
				if (scoreSplit[i] != 0 && scoreSplit[i] <= 21) {
				
					System.out.println("Player" + (i+1) + "wins split hand!");
					
					if (blackJack[i] && (score[i] == 21)) 
						money[i] += (bet[i]/2) * (1 + 1.5);	
					else 
						money[i] += bet[i]/2;
					
				}
				if (score[i] <= 21) {
				
					System.out.println("Player" + (i+1) + "wins!");
					
					// if hand is blackjack, players wins 3:2, else 1:1.
					if (blackJack[i] && (score[i] == 21)) {
					
						if (scoreSplit[i] != 0) 
							money[i] += (bet[i]/2) * (1 + 1.5);
						else 
							money[i] += bet[i] * (1 + 1.5);
						
					}
					else {
					
						if (scoreSplit[i] != 0) 
							money[i] += bet[i]/2;
						else 
							money[i] += (2 * bet[i]);
						
					}
					
					System.out.println("Player" + (i+1) + " has $" + money[i]);
					
				}
				else {
				
					System.out.println("Player" + (i+1) + "loses!");
					System.out.println("Player" + (i+1) + " has $" + money[i]);
					
				}
			
			}
			
		}
		else {
		
			// players that didn't bust with higher score than dealer wins
			for (int i = 0; i < score.length; i++) {
			
				// if dealer has blackjack, player wins the sidebet. 
				if (blackJackDealer && sideBet[i] != 0) {
				
					System.out.println("Dealer has BlackJack! Player" + (i+1) + 
												"wins insurance bet $" + sideBet[i]);
					money[i] += (sideBet[i] * 2);
					
				}
				if (scoreSplit[i] != 0) {
				
					if (scoreSplit[i] > scoreDealer && scoreSplit[i] <= 21) {
					
						System.out.println("Player" + (i+1) + "wins a split hand!");
					
						// if blackjack hand wins 3:2, else 1:1.
						if (blackJack[i] && (scoreSplit[i] == 21)) 
							money[i] += (bet[i]/2) * (1 + 1.5);
						else 
							money[i] += bet[i];
						
					}
					else if (scoreSplit[i] == scoreDealer) {
					
						System.out.println("Player" + (i+1) + "pushes a split hand!");
						money[i] += (bet[i]/2);
						
					}
					else
						System.out.println("Player" + (i+1) + "loses a split hand!");
					
				}
				// if player has not bust and has higher score than dealer.
				if (score[i] > scoreDealer && score[i] <= 21) {
				
					System.out.println("Player" + (i+1) + "wins!");
					
					// if blackjack hand wins 3:2, else 1:1.
					if (blackJack[i] && (score[i] == 21)) {
					
						if (scoreSplit[i] != 0) 
							money[i] += (bet[i]/2) * (1 + 1.5);
						else 
							money[i] += bet[i] * (1 + 1.5);
						
					}
					else {
					
						if (scoreSplit[i] != 0) 
							money[i] += bet[i];
						else 
							money[i] += (2 * bet[i]);
						
					}
					
					System.out.println("Player" + (i+1) + " has $" + money[i]);
					
				}
				
				else if (score[i] == scoreDealer) {
				
					System.out.println("Player" + (i+1) + "pushes!");
					
					if (scoreSplit[i] != 0) 
						money[i] += (bet[i]/2);
					else 
						money[i] += bet[i];
					
					System.out.println("Player" + (i+1) + " has $" + money[i]);
					
				}
				else{
				
					System.out.println("Player" + (i+1) + "loses!");
					System.out.println("Player" + (i+1) + " has $" + money[i]);
					
				}
			
			}
			
		}
	
	}
	
	public static void newDeck(Deck deck, Card[] cardArray, int[] count) {
	
		// if deck runs out of cards a new deck of shuffled cards is created.
		// all the counts are reset to zero. 
		if (deck.index == (52)) {
			shuffle(deck.d);
			deck.index = 0;
			for (int i = 0; i < count.length; i++) {
				count[i] = 0;
			}
			System.out.println("\n\nDeck ran out of cards, cards are now reshuffled...");
		}
	
	}

	public static String printSuit(int suit) {
	
		// print String name of suit.
		if (suit == 0) {
			return "Spades";
		}
		else if (suit == 1) {
			return "Hearts";
		}
		else if (suit == 2) {
			return "Clubs";
		}
		else if (suit == 3) {
			return "Diamonds";
		}
		else {
			return "";
		}
			
	}
	
	public static String printFace(int face) {
	
		// print String name of Face. 
		if (face == 1) {
			return "Ace";
		}
		else if (face > 1 && face <= 10) {
			return String.valueOf(face);
		}
		else if (face == 11) {
			return "Jack";
		}
		else if (face == 12) {
			return "Queen";
		}
		else if (face == 13) {
			return "King";
		}
		else {
			return "";
		}
		
	}
	
	// prints the cards received by the players in form of text. 
	public static void printCards(Card card1, Card card2) {
	
		System.out.println(printFace(card1.getFace()) + " of " + printSuit(card1.getSuit()) + 
					" and a " + printFace(card2.getFace()) + " of " + printSuit(card2.getSuit()));
					
	}
	
	public static void printCards(Card card1) {
	
		System.out.println(printFace(card1.getFace()) + " of " + printSuit(card1.getSuit()) + "\n");
					
	}

}
