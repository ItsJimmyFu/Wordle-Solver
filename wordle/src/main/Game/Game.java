package Game;

import Heuristics.FirstFilteredGuess;
import Heuristics.Heuristic;

import java.io.*;

public class Game {
    private BufferedWriter writer;
    public BufferedReader reader;
    public Wordle wordle;
    private boolean playAgain=true;
    public Setting setting;
    public Heuristic heuristic;

    public Game(){
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    //Start the game in terminal
    public static void main(String[] args) {

        Game game = new Game();
        game.initialise();
        while(game.playAgain) {
            if(game.setting == Setting.NORMAL) {
                game.play();
            }
            else if (game.setting == Setting.INFO){
                game.playWithInfo();
            }
            else if(game.setting == Setting.SOLVER){
                game.playWithSolver();
            }
            game.end();
        }
    }

    //Initialise the game
    public void initialise(){
        //Starting User Prompt for Word Length
        System.out.println("Welcome to Wordle. Please input the desired word length:");
        while(true){
            String input = readLine();
            int wordLength;

            try {
                wordLength = Integer.parseInt(input);
                wordle = new Wordle(wordLength);

                break;
            } catch (Exception e) {
                System.out.println(input + " is not a valid word length");
            }
        }

        //Starting User Prompt for Game Setting
        System.out.println("Please input Game Mode: (Normal | Info | Solver)");
        while(true){
            String input = readLine();
            if(input.equalsIgnoreCase("Normal")){
                this.setting = Setting.NORMAL;
                System.out.println("Normal Mode");
                break;
            }
            else if(input.equalsIgnoreCase("Info")){
                this.setting = Setting.INFO;
                System.out.println("Info Mode");
                break;
            }
            else if(input.equalsIgnoreCase("Solver")){
                this.setting = Setting.SOLVER;
                this.heuristic = new FirstFilteredGuess();
                System.out.println("Solver Mode");
                break;
            }
            else {
                System.out.println("Not valid setting, must be (Normal | Info | Solver)");
            }
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

    //Start the game but with info about next possible move
    public void playWithInfo(){
        Solver solver = new Solver(wordle.loader.getWordList());

        wordle.generateSolution();
        while(!wordle.gameOver){
            String userGuess = readLine();
            //Check if the user guess is valid
            try {
                wordle.addGuess(userGuess);
                //Filter out the possible solutions based on the guess
                solver.filterPossibleSolutions(wordle.guesses.get(wordle.guesses.size()-1));
                System.out.println(solver.possibleSolutions);
            } catch (Exception e){
                System.out.println(e.getMessage());
                continue;
            }
            wordle.printOutput();
        }
    }

    //Start the game but with info about next possible move
    public void playWithSolver(){
        Solver solver = new Solver(wordle.loader.getWordList(), heuristic);

        wordle.generateSolution();
        while(!wordle.gameOver){
            try {
                //Add the solvers guess to the game
                wordle.addGuess(solver.makeGuess());
                //Filter out the possible solutions based on the guess
                solver.filterPossibleSolutions(wordle.guesses.get(wordle.guesses.size()-1));
                System.out.println(solver.possibleSolutions);
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

