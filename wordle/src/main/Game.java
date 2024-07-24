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
        Game game = new Game();
        game.initialise();
        while(game.playAgain) {
            game.play();
            game.end();
        }
    }

    public void initialise(){
        try {
            wordle = new Wordle(5);
        }
        catch (Exception e) {
            return;
        }
    }

    public void play(){
        wordle.generateSolution();
        System.out.println("Please input a guess");
        while(!wordle.gameOver){
            String input = readLine();
            try {
                wordle.addGuess(input);
            } catch (Exception e){
                System.out.println(e.getMessage());
                continue;
            }
            wordle.printOutput();
        }
    }

    public void end(){
        if(wordle.win){
            System.out.println("Correct, You got the answer in " + wordle.guesses.size() + ((wordle.guesses.size()==1) ? " try" : " tries"));
        }
        else{
            System.out.println("You are out of guesses. The correct answer is: " + wordle.solution);
        }

        wordle.win = false;
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

