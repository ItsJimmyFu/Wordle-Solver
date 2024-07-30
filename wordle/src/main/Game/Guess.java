package Game;

import java.util.ArrayList;
import java.util.Arrays;

public class Guess {
    public int wordLength;
    private String guess;
    private String solution;
    private ArrayList<Outcome> outcomes;

    //Constants for colour encoding in terminal output
    private static final String GREEN_BACKGROUND = "\u001B[42m";
    private static final String YELLOW_BACKGROUND = "\u001B[43m";
    private static final String RESET = "\u001B[0m";

    public Guess(String guess, String solution){
        this.guess = guess;
        this.wordLength = guess.length();
        this.solution = solution;
        createOutcomes();
    }

    public ArrayList<Outcome> getOutcomes() {
        return outcomes;
    }

    public String getGuess() {
        return guess;
    }

    //Get the outcomes of the word compared to the solution of the game
    public void createOutcomes(){
        Outcome[] outcomes = new Outcome[wordLength];

        //Keep track of the remaining characters in the solution
        ArrayList<Character> remainingChars = new ArrayList<>();

        //Add the outcomes for all Green matching characters
        for (int charIndex = 0; charIndex < wordLength; charIndex++) {
            //Check if the character at charIndex of guess is equal to the character at charIndex of the solution
            if (Character.toLowerCase(guess.charAt(charIndex)) == Character.toLowerCase(solution.charAt(charIndex))) {
                outcomes[charIndex] = Outcome.GREEN;
            } else {
                //Add the remaining characters in the solution that are not Green outcomes
                remainingChars.add(solution.charAt(charIndex));
            }
        }

        // Add the Yellow and Gray Matches
        for(int charIndex = 0; charIndex < wordLength; charIndex++) {
            //Skip if the guess at charIndex is already matched to a solution char
            if(outcomes[charIndex] == Outcome.GREEN){
                continue;
            }
            else{
                //Check if guess at charIndex can be mapped to the remaining solution characters
                if(remainingChars.contains(guess.charAt(charIndex))){
                    outcomes[charIndex] = Outcome.YELLOW;
                    remainingChars.remove((Object) guess.charAt(charIndex));
                }
                else{
                    outcomes[charIndex] = Outcome.GRAY;
                }
            }
        }
        this.outcomes = new ArrayList<>(Arrays.asList(outcomes));
    }

    //Get the string encoding of the guess
    @Override
    public String toString(){
        String string = " ";
        for (int charIndex = 0; charIndex < wordLength; charIndex++){
            switch (outcomes.get(charIndex)){
                case GREEN:
                    string += GREEN_BACKGROUND + " " + Character.toUpperCase(guess.charAt(charIndex)) + " " + RESET;
                    break;
                case YELLOW:
                    string += YELLOW_BACKGROUND + " " + Character.toUpperCase(guess.charAt(charIndex)) + " " + RESET;
                    break;
                case GRAY:
                    string += " " + Character.toUpperCase(guess.charAt(charIndex)) + " ";
                    break;
            }
        }
        return string;
    }
}
