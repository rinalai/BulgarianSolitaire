# BulgarianSolitaire
simulator for a game of Bulgarian Solitaire.

The game starts out by dividing a deck of s cards into p piles of cards, with a random number of cards in each pile.
In each subsequent round, a card is taken from each pile, and added to the end as a new pile.
The game terminates when there are piles of 1,2,3,...,m in any order, where s must be expressed as m*(m+1)/2 [ie. a triangular number].

There are two representations in this repository:
-The java files will simulate one game of Bulgarian Solitaire in the terminal
-The .r file simulates a user-inputed vector of number of games of Bulgarian Solitaire to trial, and outputs a corresponding bar plot of rounds it takes to terminate for each run.

###BulgarianSolitaireSimulator.java
simulates a game of Bulgarian Solitaire
* input -u as program argument to input a custom user configuration (initiates a random configuration if this is missing)
* input -s as program argument to run each play by pressing enter

###SolitaireBoard.java
class that represents a Bulgarian solitaire board

###BulgarianSolitaireSimulator.R
simulates a user inputed number of trials, and outputs a dataframe of the distribution of number of rounds it takes for the game to terminate in each trial. Also outputs a barplot for each set of trials.
