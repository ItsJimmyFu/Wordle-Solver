package Heuristics;

import Game.Constraint;

import java.util.ArrayList;
import java.util.HashSet;

public class FirstFilteredGuess extends Heuristic {

    public String getSolution(HashSet<String> solutions, HashSet<String> filteredSolutions, ArrayList<Constraint> constraints){
        //Get the first word from filteredSolutions
        for (String word: filteredSolutions){
            return word;
        }
        return "";
    }

    @Override
    public String getName() {
        return "FirstPossibleSolution";
    }
}
