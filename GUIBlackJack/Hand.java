// This class represents the set of cards held by one player (or the dealer).
public class Hand
{
	// define fields here
	Card[] hand;

	// This constructor builds a hand (with no cards, initially).
	public Hand()
	{
		hand = new Card[26];
	}
	
	// This method retrieves the size of this hand.
	public int getNumberOfCards()
	{
		int count = 0;
		for (int i = 0; i < hand.length; i++) {
			if (hand[i] == null) {
				break;
			}
			count++;
		}
		return count;
	}

	// This method retrieves a particular card in this hand.  The card number is zero-based.
	public Card getCard(int index)
	{
		return hand[index];
	}

	// This method takes a card and places it into this hand.
	public void addCard(Card newcard)
	{
		for (int i = 0; i < hand.length; i++) {
			if (hand[i] == null) {
				hand[i] = newcard;
				break;
			}
		}
	}
	
	// This method computes the score of this hand.
	public int getScore()
	{
		int score = 0;
		for (int i = 0; i < hand.length; i++) {
			if (hand[i] == null)
				break;
			if (hand[i].getFace() == 1 && score < 11)
				score += 11;
			else 
				score += hand[i].getValue();
		}
		return score;
	}

	// This methods discards all cards in this hand.
	public void discardAll()
	{
		for (int i = 0; i < hand.length; i++) {
			hand[i] = null;
		}
	}
}
