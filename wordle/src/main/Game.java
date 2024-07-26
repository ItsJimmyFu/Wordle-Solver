import java.io.*;
import java.util.Locale;

public class Game {
    private BufferedWriter writer;
    public BufferedReader reader;
    public Wordle wordle;
    private boolean playAgain=true;

    public Game(){
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    //Start the game in terminal
    public static void main(String[] args) {
        Setting setting = Setting.INFO;

        Game game = new Game();
        game.initialise();
        while(game.playAgain) {
            if(setting == Setting.NORMAL) {
                game.play();
            }
            else if (setting == Setting.INFO){
                game.playInfo();
            }
            game.end();
        }
    }

    //Initialise the game
    public void initialise(){
        try {
            wordle = new Wordle(5);
        }
        catch (Exception e) {
            return;
        }
    }

    //Start the game
    public void play(){
        wordle.generateSolution();
        System.out.println("Please input a guess");
        while(!wordle.gameOver){
            String userGuess = readLine();
            //Check if the user guess is valid
            try {
                wordle.addGuess(userGuess);
            } catch (Exception e){
                System.out.println(e.getMessage());
                continue;
            }
            wordle.printOutput();
        }
    }

    public void playInfo(){
        Solver solver = new Solver(wordle.loader);

        wordle.generateSolution();
        while(!wordle.gameOver){
            String userGuess = readLine();
            //Check if the user guess is valid
            try {
                wordle.addGuess(userGuess);
                solver.filterSolutions(wordle.guesses.get(wordle.guesses.size()-1));
                System.out.println(solver.filteredSolutions);
            } catch (Exception e){
                System.out.println(e.getMessage());
                continue;
            }
            wordle.printOutput();
        }
    }

    //End the game and allow the user to reset
    public void end(){
        if(wordle.win){
            System.out.println("Correct, You got the answer in " + wordle.guesses.size() + ((wordle.guesses.size()==1) ? " try" : " tries"));
        }
        else{
            System.out.println("You are out of guesses. The correct answer is: " + wordle.solution);
        }

        wordle.reset();
        System.out.println("Play Again? YES (Y) : NO (N)");
        while(true) {
            String input = readLine();
            if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("YES")) {
                break;
            }
            else if (input.equalsIgnoreCase("N") || input.equalsIgnoreCase("NO")) {
                playAgain = false;
                try {
                    this.reader.close();
                } catch (IOException e){
                    System.out.println(e.getMessage());
                }
                break;
            }
            else {
                System.out.println("Play Again? YES (Y) : NO (N)");
            }
        }
    }

    //Custom buffered reader readline method to avoid redundancy and catch IOException
    private String readLine(){
        String input;
        try {
            input = reader.readLine();
        } catch (IOException e){
            System.out.println(e.getMessage());
            return "";
        }
        return input;
    }
}

