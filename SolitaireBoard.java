
/*
   class SolitaireBoard
   The board for Bulgarian Solitaire.  You can change what the total number of cards is for the game
   by changing NUM_FINAL_PILES, below.  Don't change CARD_TOTAL directly, because there are only some values
   for CARD_TOTAL that result in a game that terminates.
   (See comments below next to named constant declarations for more details on this.)
 */

import java.util.Random;
import java.util.Scanner;

public class SolitaireBoard {

	public static final int NUM_FINAL_PILES = 9;
	// number of piles in a final configuration
	// (note: if NUM_FINAL_PILES is 9, then CARD_TOTAL below will be 45)

	public static final int CARD_TOTAL = NUM_FINAL_PILES * (NUM_FINAL_PILES + 1) / 2;
	// bulgarian solitaire only terminates if CARD_TOTAL is a triangular number.
	// see: http://en.wikipedia.org/wiki/Bulgarian_solitaire for more details
	// the above formula is the closed form for 1 + 2 + 3 + . . . +
	// NUM_FINAL_PILES

	/**
	 * Representation invariant:
	 * 
	 * -Sum of all piles up to currentNumPiles is always CARD_TOTAL.
	 * -Data is in partially filled array, so data should only occupy indexes up to currentNumPiles
	 * -There should not be holes (ie zeros) in any elements before currentNumPiles.
	 * 
	 */

	// instance variables
	private int[] boardConfig = new int[CARD_TOTAL+1]; // current board configuration
	private int currentNumPiles; // current number of piles (ie last index in array used)

	/**
	 * Initialize the board with the given configuration. 
	 * PRE: SolitaireBoard.isValidConfigString(numberString)
	 */
	public SolitaireBoard(String numberString) {
		
		//check that the input is a valid string
		assert isValidConfigString(numberString);
		
		boardConfig = convertToArray(numberString);
		for (int i = 0 ; i < boardConfig.length; i++) {
			if (boardConfig[i] !=0) {
				//count nonzero piles to determine starting currentNumPiles since they
				//are 0 before any rounds are played.
				currentNumPiles ++;
			}
		}

		//check that boardConfig is in a valid state
		assert isValidSolitaireBoard();
	}

	/**
	 * Create a random initial configuration.
	 */
	public SolitaireBoard() {
		
		Random rand = new Random();
		int numToInsert = CARD_TOTAL;
		int i = 0;

		//insert random numbers until the sum is CARD_TOTAL
		while (numToInsert > 0) {
			int num = rand.nextInt(numToInsert) + 1;
			boardConfig[i] = num;
			numToInsert = numToInsert - num;
			i++;
			currentNumPiles = i;
		}
		//check that the boardConfig is in a valid state
		assert isValidSolitaireBoard();
	}

	/**
	 * Play one round of Bulgarian solitaire. Updates the configuration
	 * according to the rules of Bulgarian solitaire: Takes one card from each
	 * pile, and puts them all together in a new pile.
	 */
	public void playRound() {

		//take one card from each pile
		for (int i = 0; i < currentNumPiles; i++){
			boardConfig[i]--;
		}
		//add taken cards as a new pile at the end
		boardConfig[currentNumPiles] = currentNumPiles;
		currentNumPiles++;
		
		//pack the piles so that there are no holes
		int iter = 0;
		for (int j = 0; j < currentNumPiles; j++) {
			if (boardConfig[j] != 0) {
				boardConfig[iter] = boardConfig[j];
				iter++;
			}
		}
		currentNumPiles = iter;
		//check that the boardConfig is in a valid state
		assert isValidSolitaireBoard();
	}

	/**
	 * Return true iff the current board is at the end of the game. That is,
	 * there are NUM_FINAL_PILES piles that are of sizes 1, 2, 3, . . . ,
	 * NUM_FINAL_PILES, in any order.
	 */
	public boolean isDone() {
		
		if (currentNumPiles != NUM_FINAL_PILES) { //not done if not correct # piles
			return false;
		} else {
			int[] numCheck = new int[NUM_FINAL_PILES];
			for (int i = 0; i < currentNumPiles; i++) {
				if (boardConfig[i] > NUM_FINAL_PILES) {
					return false; //not done if #piles in boardConfig > than NUM_FINAL_PILES
				} else {
					numCheck[boardConfig[i]-1] = boardConfig[i];
				}
			}
			
			//if in the above iterations numCheck was written in more than once for any index, numCheck will have holes
			//isDone iff numCheck has no holes
			int numCheckSum = 0;
			for (int j = 0; j < currentNumPiles; j++) {
				numCheckSum += numCheck[j];
			}
			if (numCheckSum == CARD_TOTAL) {
				return true;
			}
		return false;
		}
	}

	/**
	 * Returns current board configuration as a string with the format of a
	 * space-separated list of numbers with no leading or trailing spaces. The
	 * numbers represent the number of cards in each non-empty pile.
	 */
	public String configString() {
		
		String stringConfig = "";
		for (int i = 0; i < currentNumPiles; i++) {
			stringConfig = stringConfig + " " + boardConfig[i];
		}
		return stringConfig.trim();
	}

	/**
	 * Returns true iff configString is a space-separated list of numbers that
	 * is a valid Bulgarian solitaire board assuming the card total
	 * SolitaireBoard.CARD_TOTAL
	 */
	public static boolean isValidConfigString(String configString) {

		//check that inputs are all int
		Scanner line = new Scanner(configString);
		int count = 0;
		while (line.hasNext()) {
			String next = line.next();
			if (!next.matches("\\d+$") || next.matches("^0$")){
				return false;
			}
			else {count++;}
		}
		return isValidConfiguration(convertToArray(configString), count);
	}

	/**
	 * Returns true iff the solitaire board data is in a valid state (See
	 * representation invariant comment for more details.)
	 */
	private boolean isValidSolitaireBoard() {
		
		return isValidConfiguration(boardConfig, currentNumPiles);
		
	}

	//additional private methods:
	
	/**
	 * Returns true iff the solitaire board data is in a valid state
	 */
	private static boolean isValidConfiguration(int[] piles, int numPiles) {
		int sumBoardConfig = 0;
		for (int i = 0; i < numPiles; i++) {
			sumBoardConfig += piles[i];
			if (piles[i] == 0) {
				//check that there are no holes in the piles of cards (ie piles of 0)
				return false;
			}
		}
		//check that the card total is equal between each play
		if (sumBoardConfig != CARD_TOTAL) {
			return false;
		}
		return true;
	}
	
	/**
	 * Converts a string of integers into an array
	 * 
	 */
	private static int [] convertToArray(String configString){
		
		Scanner in = new Scanner(configString);
		int [] arr = new int[CARD_TOTAL+1];
		
		//take in the next integer
		int i = 0;
		while (in.hasNext()) {
			arr[i] = in.nextInt();
			i++;
		}
		//currentNumPiles = i;
		return arr;	
	}
}
