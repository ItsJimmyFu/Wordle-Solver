package Heuristics;

import Game.Solver;

import java.util.HashMap;

public class MostCommonLetters extends Heuristic {
    public String getSolution(Solver solver) {
        //Get the frequency count of all characters in wordList
        HashMap<Character,Double> frequencyCharList = new HashMap<>();
        for (String solution : solver.possibleSolutions){
            for (Character letter : solution.toCharArray()) {
                if (frequencyCharList.containsKey(letter)){
                    frequencyCharList.put(letter,frequencyCharList.get(letter)+1);
                }
                else{
                    frequencyCharList.put(letter,1.0);
                }
            }
        }
        //Convert the count into a percentage
        frequencyCharList.replaceAll((l, v) -> frequencyCharList.get(l) / solver.possibleSolutions.size());
        String optimalSolution = null;
        double optimalScore = 0;

        for (String solution : solver.possibleSolutions){
            double score = frequencyLettersValue(solution,frequencyCharList);
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

    public double frequencyLettersValue(String solution, HashMap<Character,Double> frequencyCharList){
        double score = 0;
        for (int charIdx = 0; charIdx<solution.length(); charIdx++){
            char letter = solution.charAt(charIdx);

            //Punish Duplicates
            if(solution.substring(0,charIdx).indexOf(letter) != -1) {
                score += frequencyCharList.get(solution.charAt(charIdx))/2;
            }
            else {
                score += frequencyCharList.get(solution.charAt(charIdx));
            }
        }
        return score;
    }

    @Override
    public String getName() {
        return "MostCommonLetters";
    }
}

