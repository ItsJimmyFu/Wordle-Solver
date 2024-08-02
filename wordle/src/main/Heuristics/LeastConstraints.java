package Heuristics;

import Game.Constraint;
import Game.Outcome;
import Game.Solver;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class LeastConstraints extends Heuristic {
    public String getSolution(Solver solver){
        //For First Guess

        if(solver.wordList == solver.possibleGuesses) {
            return "alter";
        }

        if(solver.possibleSolutions.size() == 1){
            FirstFilteredGuess ffg = new FirstFilteredGuess();
            return ffg.getSolution(solver);
        }

        String optimalSolution = null;
        int optimalScore = 0;

        HashMap<Character, ArrayList<Integer>> greenMaps = new HashMap<>();
        HashMap<Character, ArrayList<Integer>> yellowMaps = new HashMap<>();
        HashMap<Character, ArrayList<Integer>> grayMaps = new HashMap<>();

        for (Constraint constraint : solver.constraints){
            char letter = constraint.letter;
            int position = constraint.position;
            if(constraint.outcome==Outcome.GREEN){
                if(!greenMaps.containsKey(letter)){
                    greenMaps.put(letter,new ArrayList<>());
                }
                greenMaps.get(letter).add(position);
            }
            else if(constraint.outcome==Outcome.YELLOW){
                if(!yellowMaps.containsKey(letter)){
                    yellowMaps.put(letter,new ArrayList<>());
                }
                yellowMaps.get(letter).add(position);
            }
            else if(constraint.outcome==Outcome.GRAY){
                if(!grayMaps.containsKey(letter)){
                    grayMaps.put(letter,new ArrayList<>());
                }
                grayMaps.get(letter).add(position);
            }
        }

        for (String solution : solver.wordList){
            int score = informationValue(solution,greenMaps,yellowMaps,grayMaps);
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

    public int informationValue(String solution, HashMap<Character, ArrayList<Integer>> greenMaps,HashMap<Character, ArrayList<Integer>> yellowMaps,HashMap<Character, ArrayList<Integer>> grayMaps){
        int score = 0;
        for (int idx = 0; idx < solution.toCharArray().length; idx++) {
            char letter = solution.charAt(idx);
            if(greenMaps.containsKey(letter)){
                score -= 2;
            }
            else if (yellowMaps.containsKey(letter)){
                if(yellowMaps.get(letter).contains(idx)){
                    score -=1;
                }
                else {
                    score += 1;
                }
            }
            else if (grayMaps.containsKey(letter)){
                score -= 3;
            }
            else {
                score += 2;
            }
        }
        return score;
    }

    @Override
    public String getName() {
        return "LeastConstraints";
    }
}

