package Heuristics;

import Game.Constraint;
import Game.Solver;

import java.util.ArrayList;
import java.util.HashSet;

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
