
#simulate n trials for termination of a game of Bulgarian solitaire with p final piles
# invariants:
#   -sum of all piles up to currentNumPiles will always be cardTotal
#   -piles of zero cards are removed, so the current configurations should have no holes
#   -game only terminates iff cardTotal is a triangular number, and if so, will terminate within 
#     p^2-p trials or fewer.

######## change inputs here ###########
#vector of the number of trials requested
n = numTrials = c(10,100,1000,10000) 
#number of final piles of cards
p = numFinalPiles = 9 
#######################################

cardTotal = numFinalPiles * (numFinalPiles + 1) /2
maxTrialsPossible = p^2-p
roundsTaken = rep(0,maxTrialsPossible)


#==================================================
#function to output a vector of roundsTakens for different variations of numTrials and numFinalPiles
roundsTakenVector = function(numTrials, numFinalPiles) {
  
  #----begin trials----
  for (k in 1:numTrials) {
    
    #----initial board set up----
    #Start with a random distribution of piles of cards
    cardsRemainingToInsert = cardTotal
    i = 1
    solitaireBoard = rep(0,cardTotal+1)
    while (cardsRemainingToInsert > 0 ) {
      num = sample(1:cardsRemainingToInsert, 1)
      solitaireBoard[i] = num
      cardsRemainingToInsert = cardsRemainingToInsert - num
      i = i+1
      currentNumPiles = i-1
    }
    
    #function to check if the game is done
    isDone = function(solitaireBoard) {
      
      if (currentNumPiles != numFinalPiles) {
        return(FALSE) #board has incorrect number of piles at this iteration
      } else {
        numCheck = rep(0,numFinalPiles)
        for (i in 1:currentNumPiles) {
          if (solitaireBoard[i] > numFinalPiles) {
            return(FALSE) #not done if the number of piles in the board has more cards than the number of final piles
          } else {
            numCheck[solitaireBoard[i]] = solitaireBoard[i];
          }
        }
        numCheckSum = 0
        for (j in 1:currentNumPiles) {
          numCheckSum = numCheckSum + numCheck[j]
        }
        if (numCheckSum == cardTotal) {
          return(TRUE)
        }
        return(FALSE)
      }
      
    }
    
    
    #----play the game----
    numRounds = 0 #record how many rounds it takes for the game to terminate
    
    while (!isDone(solitaireBoard)) {
      
      #----play one round of the game----
      
      for (i in 1:currentNumPiles) {
        #take one card from each pile
        solitaireBoard[i] = solitaireBoard[i]-1
      }
      solitaireBoard[currentNumPiles+1] = currentNumPiles
      currentNumPiles = currentNumPiles + 1
      
      #----pack the piles so that there are no holes----
      iter = 1
      for (j in 1:currentNumPiles) {
        if (solitaireBoard[j] != 0) {
          solitaireBoard[iter] = solitaireBoard[j]
          iter = iter + 1
        }
        j = j+1
      }
      currentNumPiles = iter-1
      #print(solitaireBoard[1:currentNumPiles])
      numRounds = numRounds + 1
    }
    roundsTaken[numRounds] = roundsTaken[numRounds] + 1
  }
  return(roundsTaken)
}
#==================================================

results = data.frame(matrix(NA, nrow = maxTrialsPossible, ncol = length(numTrials)))
par(mfrow = c(2,length(numTrials)/2))
for (test in 1:length(numTrials)) {
  results[,test] = roundsTakenVector(numTrials[test], numFinalPiles)
  colnames(results)[test] = paste(numTrials[test], "trials")
  barplot(results[,test], main=paste(numTrials[test], "trials"))
}


