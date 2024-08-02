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
            MostCommonPositionalLetters mcpl = new MostCommonPositionalLetters();
            return mcpl.getSolution(solver);
        }

        if(solver.possibleSolutions.size() == 1){
            FirstFilteredGuess ffg = new FirstFilteredGuess();
            return ffg.getSolution(solver);
        }

        HashMap<Outcome,HashMap<Character, ArrayList<Integer>>> constraintMapping = getConstraintMapping(solver);

        String optimalSolution = null;
        int optimalScore = -10000;

        for (String solution : solver.wordList){
            int score = informationValue(solution,constraintMapping);
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

    public HashMap<Outcome,HashMap<Character, ArrayList<Integer>>> getConstraintMapping(Solver solver){
        HashMap<Outcome,HashMap<Character, ArrayList<Integer>>> constraintMapping = new HashMap<>();
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

        constraintMapping.put(Outcome.GREEN,greenMaps);
        constraintMapping.put(Outcome.YELLOW,yellowMaps);
        constraintMapping.put(Outcome.GRAY,grayMaps);
        return constraintMapping;
    }

    public int informationValue(String solution, HashMap<Outcome,HashMap<Character, ArrayList<Integer>>> constraintMapping){
        int score = 0;
        for (int idx = 0; idx < solution.toCharArray().length; idx++) {
            char letter = solution.charAt(idx);
            if(solution.substring(0,idx).contains(letter +"")){
                continue;
            }

            if(constraintMapping.get(Outcome.GREEN).containsKey(letter)){
                score -= 5;
            }
            else if (constraintMapping.get(Outcome.YELLOW).containsKey(letter)){
                if(constraintMapping.get(Outcome.YELLOW).get(letter).contains(idx)){
                    score -=1;
                }
                else {
                    score += 1;
                }
            }
            else if (constraintMapping.get(Outcome.GRAY).containsKey(letter)){
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

