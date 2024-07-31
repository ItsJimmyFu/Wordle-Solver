package Heuristics;

import Game.Constraint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class MostCommonPositionalLetters extends Heuristic {
    @Override
    public String getSolution(HashSet<String> solutions, HashSet<String> filteredSolutions, ArrayList<Constraint> constraints) {
        //Making a hashmap of the character its index and its count
        HashMap<String,Double> frequencyCharList = new HashMap<>();
        for (String solution : filteredSolutions){
            for (int charIdx = 0 ; charIdx < solution.length(); charIdx++){
                char letter = solution.charAt(charIdx);
                String key = letter + "" + charIdx;
                if (frequencyCharList.containsKey(key)){
                    frequencyCharList.put(key,frequencyCharList.get(key)+1);
                }
                else{
                    frequencyCharList.put(key,1.0);
                }
            }
        }

        String optimalSolution = null;
        double optimalScore = 0;

        for (String solution : filteredSolutions){
            double score = frequencyLettersValue(solution,frequencyCharList);
            if(score > optimalScore){
                optimalScore = score;
                optimalSolution = solution;
            }
        }
        if(optimalSolution == null){
            FirstFilteredGuess ffg = new FirstFilteredGuess();
            return ffg.getSolution(solutions,filteredSolutions,constraints);
        }
        return optimalSolution;
    }

    public double frequencyLettersValue(String solution, HashMap<String,Double> frequencyCharList){
        double score = 0;
        for (int charIdx = 0; charIdx<solution.length(); charIdx++){
            char letter = solution.charAt(charIdx);
            String newKey = letter + "" + charIdx;

            score += frequencyCharList.get(newKey);
        }
        return score;
    }

    @Override
    public String getName() {
        return "MostCommonPositionalLetters";
    }
}
