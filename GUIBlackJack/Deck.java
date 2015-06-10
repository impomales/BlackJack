import java.util.Random;

// This class represents the deck of cards from which cards are dealt to players.
public class Deck
{
	// define fields here
	Card[] d;
	int index;
	
	// This constructor builds a deck of 52 cards.
	public Deck()
	{
		d = new Card[52];
		index = 0;
		
		for (int i = 0; i < d.length;) {
			// from SPADES-DIAMONDS
			for (int j = Card.SPADES; j <= Card.DIAMONDS; j++) {
				// from A-K.
				for (int k = 1; k <=13; k++) {
				
					d[i] = new Card(j, k);
					i++;
			
				}
		
			}
			
		}
	}

	// This method shuffles the deck (randomizes the array of cards).
	// Hint: loop over the cards and swap each one with another card in a random position.
	public void shuffle()
	{
		int random = 0;		// random number.
		Card temp;			// variable used for swapping.	
	
		// creates a random number between 0 and i. swaps values at index i with random index. 
		// shuffles three times. 
	
		for (int i = 3; i > 0; i --) {
			for (int j = 0; j < d.length; j++) {
			
				random = (int) ((Math.random() * (d.length-1)) + 1);
				temp = d[j];
				d[j] = d[random];
				d[random] = temp;
			}
		}
	}
	
	// This method takes the top card off the deck and returns it.
	public Card drawCard()
	{
		if (index == d.length) 
			return null;
		else {
			index++;
			return d[index];
		}
	}
	
	// This method returns the number of cards left in the deck.
	public int getSize()
	{
		return (52 - index);
	}
}

