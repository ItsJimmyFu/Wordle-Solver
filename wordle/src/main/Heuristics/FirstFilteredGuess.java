package Heuristics;

import Game.Solver;

public class FirstFilteredGuess extends Heuristic {

    public String getSolution(Solver solver){
        //Get the first word from filteredSolutions
        for (String word: solver.possibleSolutions){
            return word;
        }
        return "";
    }

    @Override
    public String getName() {
        return "FirstPossibleSolution";
    }
}
