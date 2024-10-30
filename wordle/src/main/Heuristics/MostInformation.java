package Heuristics;

import Game.*;

import java.util.*;

public class MostInformation extends Heuristic{
    @Override
    public String getSolution(Solver solver) {
        //For First Guess
        if(solver.solutionWordList == solver.possibleSolutions) {
            return "tarse";
        }
        if(solver.possibleSolutions.size()==1){
            FirstFilteredGuess ffg = new FirstFilteredGuess();
            return ffg.getSolution(solver);
        }

        float maxEntropy = 0;
        String bestGuess = "";

        for (String potentialWord: solver.possibleGuesses) {
            float val = 0;
            HashMap<ArrayList<Outcome>,Integer> searched = new HashMap();
            for (String solution : solver.possibleSolutions) {
                Guess guess = new Guess(potentialWord, solution);
                if (searched.keySet().contains(guess.getOutcomes())) {
                    searched.put(guess.getOutcomes(),searched.get(guess.getOutcomes())+1);
                }
                else{
                    searched.put(guess.getOutcomes(),1);
                }
            }

            for (ArrayList<Outcome> pattern : searched.keySet()){
                float occur = searched.get(pattern);
                float prob = occur/solver.possibleGuesses.size();
                val += prob * (int)(Math.log(1/prob) / Math.log(2));
            }
            if( val > maxEntropy){
                maxEntropy = val;
                bestGuess = potentialWord;
            }
        }
        return bestGuess;
    }

    @Override
    public String getName() {
        return "MostInformation";
    }
}
