package Game;

import Heuristics.FirstFilteredGuess;
import Heuristics.Heuristic;

import javax.imageio.plugins.tiff.ExifParentTIFFTagSet;
import java.io.*;

public class Game {
    private BufferedWriter writer;
    public BufferedReader reader;
    public Wordle wordle;
    public Solver solver;
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
        if(game.setting == Setting.EXPERIMENT) {
            game.startExperiment();
        }
        else {
            while (game.playAgain) {
                //Generate the solution for the game
                game.wordle.generateSolution();

                if (game.setting == Setting.NORMAL) {
                    game.play();
                } else if (game.setting == Setting.INFO) {
                    game.playWithInfo();
                } else if (game.setting == Setting.SOLVER) {
                    game.playWithSolver();
                    game.wordle.printOutput();
                }
                game.end();
            }
        }
    }

    //Initialise the game
    public void initialise() {
        //Starting User Prompt for Word Length
        System.out.println("Welcome to Wordle. Please input the desired word length:");
        while (true) {
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
        System.out.println("Please input Game Mode: (Normal | Info | Solver | Experiment)");
        while (true) {
            String input = readLine();
            if (input.equalsIgnoreCase("Normal")) {
                this.setting = Setting.NORMAL;
                System.out.println("Normal Mode");
                break;
            } else if (input.equalsIgnoreCase("Info")) {
                this.setting = Setting.INFO;
                System.out.println("Info Mode");
                break;
            } else if (input.equalsIgnoreCase("Solver")) {
                this.setting = Setting.SOLVER;
                this.heuristic = new FirstFilteredGuess();
                System.out.println("Solver Mode");
                break;
            } else if (input.equalsIgnoreCase("Experiment")) {
                this.setting = Setting.EXPERIMENT;
                this.heuristic = new FirstFilteredGuess();

                String filePath = "src/resources/Results/" + heuristic.getName() + wordle.wordLength + ".txt";

                FileWriter file;
                try {
                    file = new FileWriter(filePath);
                } catch (IOException exception) {
                    System.out.println(exception.getMessage());
                    return;
                }
                writer = new BufferedWriter(file);
                System.out.println("Experiment Mode");
                break;
            } else {
                System.out.println("Not valid setting, must be (Normal | Info | Solver | Experiment)");
            }
        }
        if (setting == Setting.SOLVER || setting == Setting.EXPERIMENT) {
            solver = new Solver(wordle.loader.getWordList(), heuristic);
        }
        else {
            solver = new Solver(wordle.loader.getWordList());
        }
    }

    //Start the game
    public void play(){
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

    public void startExperiment(){
        int totalGuesses = 0;
        int wins = 0;
        float totalWords = wordle.loader.wordList.size();

        int curIdx = 0;
        for (String solution : wordle.loader.getWordList()){
            wordle.solution = solution;
            playWithSolver();

            try {
                writer.write(solution + " " + wordle.guesses.size() +"\n");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            totalGuesses+= wordle.guesses.size();
            if(wordle.win){
                wins++;
            }
            wordle.reset();
            solver.reset();
            curIdx++;

            //Display a progress bar for the experiment
            int increment = (int) totalWords/10;
            if(curIdx%increment==0){
                int percent = curIdx/increment;
                System.out.println("[" +"*".repeat(percent) +"-".repeat(10-percent)+"] " + (percent*10) +"%");
            }
        }

        try {
            writer.newLine();
            writer.write("Win Percentage is: " + (wins/totalWords*100) + "%. Wins: " + wins + " Losses: " + (int)(totalWords-wins) + "\n");
            writer.write("Average number of guesses is: " + totalGuesses/totalWords + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //Start the game but with info about next possible move
    public void playWithSolver(){

        while(!wordle.gameOver){
            try {
                //Add the solvers guess to the game
                wordle.addGuess(solver.makeGuess());
                //Filter out the possible solutions based on the guess
                solver.filterPossibleSolutions(wordle.guesses.get(wordle.guesses.size()-1));
            } catch (Exception e){
                System.out.println(e.getMessage());
                continue;
            }
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
        solver.reset();

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

