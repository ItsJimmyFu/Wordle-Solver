package Heuristics;

import Game.Outcome;
import Game.Solver;

import java.util.ArrayList;
import java.util.HashMap;

public class LeastConstraintsAndMostCommonPositionalLetters extends Heuristic{
    //Combining MCPL and LC
    @Override
    public String getSolution(Solver solver) {
        LeastConstraints lc = new LeastConstraints();
        MostCommonPositionalLetters mcpl = new MostCommonPositionalLetters();

        //For First Guess
        if(solver.wordList == solver.possibleGuesses) {
            return mcpl.getSolution(solver);
        }

        if(solver.possibleSolutions.size() == 1){
            FirstFilteredGuess ffg = new FirstFilteredGuess();
            return ffg.getSolution(solver);
        }

        HashMap<Outcome, HashMap<Character, ArrayList<Integer>>> conMap = lc.getConstraintMapping(solver);
        HashMap<String,Double> charFreq = mcpl.getCharFrequency(solver);

        String optimalSolution = null;
        float optimalScore = -100000000;

        for (String solution : solver.possibleGuesses){
            float b = lc.informationValue(solution,conMap)*10;
            float a = (float) mcpl.frequencyLettersValue(solution,charFreq);
            float score = a+b;
            if(score > optimalScore){
                optimalScore = score;
                optimalSolution = solution;
            }
        }
        if(optimalSolution == null){
            FirstFilteredGuess ffg = new FirstFilteredGuess();
            return ffg.getSolution(solver);
        }
        return optimalSolution;
    }

    @Override
    public String getName() {
        return "LeastConstraintsAndMostCommonPositionalLetters";
    }

}
