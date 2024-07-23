import java.io.*;

public class Game {
    private BufferedWriter writer;
    public BufferedReader reader;
    public Wordle wordle;

    public static void main(String[] args) {
        Game game = new Game();
        game.reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            game.wordle = new Wordle(5);
        }
        catch (Exception e) {
            return;
        }

        game.wordle.generateSolution();

        System.out.println("Please input a guess");
        while(!game.wordle.gameOver){
            String input = game.readLine();
            try {
                game.wordle.addGuess(input);
            } catch (Exception e){
                System.out.println(e.getMessage());
                continue;
            }
            game.wordle.printOutput();
        }
        if(game.wordle.win){
            int guesses = game.wordle.guesses.size();
            System.out.println("Correct, You got the answer in " + guesses + ((guesses==1) ? " try" : " tries"));
        }
        else{
            System.out.println("You are out of guesses. The correct answer is: " + game.wordle.solution);
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

