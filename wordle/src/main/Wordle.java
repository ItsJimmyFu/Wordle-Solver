
import java.util.ArrayList;
import java.util.Random;

public class Wordle {
    public Loader loader;
    public Solver solver;
    private int wordLength;
    public String solution;
    public ArrayList<Guess> guesses;
    public boolean gameOver = false;
    public boolean win = false;

    public Wordle(int wordLength) throws Exception{
        this.wordLength = wordLength;
        this.guesses = new ArrayList<>();
        try {
            loader = new Loader(wordLength);
        } catch (Exception e) {
            throw e;
        }
        solver = new Solver(loader.getWordList());
    }

    //Reset the wordle game
    public void reset(){
        gameOver = false;
        win = false;
        guesses = new ArrayList<>();
    }

    //Add a guess to the wordle game
    public void addGuess(String word) throws Exception{
        //Ensure that the word is valid
        if(word.length()!= wordLength || !loader.getWordList().contains(word.toLowerCase())){
            throw new Exception("Invalid Guess");
        }

        //Create the guess
        Guess guess = new Guess(word,this.solution,guesses.size()+1);
        guesses.add(guess);

        //Check if the guess is correct
        if(word.toLowerCase().equals(solution)){
            gameOver=true;
            win=true;
        }
        if(guesses.size()==6){
            gameOver = true;
        }
    }

    //Generate a random solution from the list of words
    public void generateSolution(){
        //Choose a random index in wordList
        Random random = new Random();
        this.solution = loader.getWordList().get(random.nextInt(loader.getWordList().size()));

        //Display the solution - delete this later
        System.out.println("Word to guess is: " + solution);
    }

    //Print the guesses and the result
    public void printOutput(){
        //Clear the display
        System.out.print("\033[H\033[2J");
        System.out.flush();

        //Printing each guess and its result
        for(Guess guess: guesses){
            guess.displayGuess();
        }
    }
}
