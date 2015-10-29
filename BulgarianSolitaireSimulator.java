// Name: Carrina Lai
// USC loginid: carrinal
// USCID 6603835042
// CSCI455 PA2
// Fall 2015

/**
 * Simulates playing a game of Bulgarian Solitaire.
 * input -u as program argument to input a custom user configuration (initiates a random configuration if this is missing)
 * input -s as program argument to run each play by pressing enter
 */

import java.util.Scanner;

public class BulgarianSolitaireSimulator {

	public static void main(String[] args) {

		boolean singleStep = false;
		boolean userConfig = false;

		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-u")) {
				userConfig = true;
			} else if (args[i].equals("-s")) {
				singleStep = true;
			}
		}

		if (userConfig) {
			play(runUserConfig(), singleStep);
		} else {
			play(runRandomConfig(), singleStep);
		}
	}

	/**
	 * Returns a SolitaireBoard object based on configuration given by user input
	 */
	
	private static SolitaireBoard runUserConfig() {

		Scanner in = new Scanner(System.in);
		System.out.println("Number of total cards is " + SolitaireBoard.CARD_TOTAL);
		System.out.println("You will be entering the initial configuration of the cards (i.e., how many in each pile).");
		System.out.println("Please enter a space-separated list of positive integers followed by newline: ");
		String text = in.nextLine();
		while (!SolitaireBoard.isValidConfigString(text)) {
			System.out.println("ERROR: Each pile must have at least one card and the total number of cards must be 45");
			System.out.println("Please enter a space-separated list of positive integers followed by newline: ");
			text = in.nextLine();
		}
		SolitaireBoard board = new SolitaireBoard(text);
		return board;
	}
	
	/**
	 * Returns a SolitaireBoard object based on a random configuration
	 */
	
	private static SolitaireBoard runRandomConfig() {
		SolitaireBoard board = new SolitaireBoard();
		return board;
	}
	
	/**
	 * Simulates one game of Bulgarian Solitaire. 
	 * If singleStep is true, will only proceed with one play each time the user presses enter.
	 */
	
	private static void play(SolitaireBoard board, boolean single) {
		System.out.println("Initial configuration: " + board.configString());
		int i = 0;
		while (!board.isDone()){
			i++;
			board.playRound();
			System.out.println("[" + i + "] Current configuration: " + board.configString());
			if (single) {
				Scanner in = new Scanner(System.in);
				System.out.print("<Type return to continue>");
				in.nextLine();
			}
		}
		System.out.println("Done!");
	}
}
