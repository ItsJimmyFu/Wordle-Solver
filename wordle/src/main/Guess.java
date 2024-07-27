import java.util.ArrayList;

public class Guess {
    public int wordLength;
    private int guessNumber;
    private String guess;
    private String solution;
    private ArrayList<Outcome> outcomes;

    //Constants for colour encoding in terminal output
    private static final String GREEN_BACKGROUND = "\u001B[42m";
    private static final String YELLOW_BACKGROUND = "\u001B[43m";
    private static final String RESET = "\u001B[0m";

    public Guess(String guess, String solution, int guessNumber){
        this.guess = guess;
        this.wordLength = guess.length();
        this.solution = solution;
        this.guessNumber = guessNumber;
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
        outcomes = new ArrayList<>();

        //Keep track of the remaining possible characters in the solution to account for duplicates
        ArrayList<Character> remainingChars = new ArrayList<>();

        //Add the Green matching characters
        for (int charIndex = 0; charIndex < wordLength; charIndex++) {
            if (Character.toLowerCase(guess.charAt(charIndex)) == Character.toLowerCase(solution.charAt(charIndex))) {
                outcomes.add(Outcome.GREEN);
            } else {
                //Add GRAY as a temporary placeholder in outcomes for potential YELLOW outcomes
                outcomes.add(Outcome.GRAY);
                //Add the character as green characters are now accounted for
                remainingChars.add(solution.charAt(charIndex));
            }
        }
        //Add the yellow matching characters
        for (int charIndex = 0; charIndex < wordLength; charIndex++){
            if(outcomes.get(charIndex) == Outcome.GRAY){
                if(remainingChars.contains(guess.charAt(charIndex))){
                    outcomes.set(charIndex,Outcome.YELLOW);
                }
                remainingChars.remove((Object) guess.charAt(charIndex));
            }
        }
    }

    //Display the guess into terminal in the specific color coding based on outcomes
    public void displayGuess(){
        String output = this.guessNumber + ": ";
        for (int charIndex = 0; charIndex < wordLength; charIndex++){
            switch (outcomes.get(charIndex)){
                case GREEN:
                    output += GREEN_BACKGROUND + " " + Character.toUpperCase(guess.charAt(charIndex)) + " " + RESET;
                    break;
                case YELLOW:
                    output += YELLOW_BACKGROUND + " " + Character.toUpperCase(guess.charAt(charIndex)) + " " + RESET;
                    break;
                case GRAY:
                    output += " " + Character.toUpperCase(guess.charAt(charIndex)) + " ";
                    break;
            }
        }
        System.out.println(output);
    }
}
