package Game;

import main.WordList.Loader;

import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class Wordle {
    private final BufferedReader reader;
    private BufferedWriter writer;

    private Setting setting;
    public Loader loader;

    public int wordLength;
    private String solution;
    private final int guessLimit = 6;
    private ArrayList<Guess> guesses;
    private Boolean win = false;
    private Boolean playAgain = true;

    public static void main(String[] args) {
        Wordle wordle = new Wordle();
        wordle.initialiseGame();
        while (wordle.playAgain) {
            wordle.generateSolution();
            wordle.startNormal();
            wordle.end();
        }
        /*if(wordle.setting == Setting.EXPERIMENT){
            wordle.startExperiment();
        }
        else {
            while (wordle.playAgain) {
                wordle.generateSolution();
                if (wordle.setting == Setting.SOLVER) {
                    wordle.startSolver();
                } else {
                    wordle.startNormal();
                }
                wordle.end();
            }
        }
         */
    }

    public Wordle(){
        //Declare a reader for user input
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    /*
    public void startExperiment(){
        int totalGuesses = 0;
        float totalWords = loader.getWordList().size();
        int wins = 0;

        for (String sol : loader.getWordList()){
            solution = sol;
            startSolver();

            try {
                writer.write(sol + " " + guesses.size() +"\n");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            totalGuesses+= guesses.size();
            if(win){
                wins++;
            }
            win=false;
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
    */

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

    //Print the guesses and the result
    public static void printOutput(ArrayList<Guess> guessOutput){
        //Clear the display
        System.out.print("\033[H\033[2J");
        System.out.flush();

        //Printing each guess and its result
        for(Guess guess: guessOutput){
            guess.displayGuess();
        }
    }

    public void generateSolution(){
        //Choose a random word from the pool of words as the solution
        Random random = new Random();
        this.solution = loader.getWordList().get(random.nextInt(loader.getWordList().size()));

        //Display the solution - delete this later
        System.out.println("Word to guess is: " + solution);
    }

    public void setSolution(String solution){
        this.solution = solution;
    }

    public void initialiseGame(){
        //Starting User Prompt for Word Length
        System.out.println("Welcome to Wordle. Please input the desired word length:");
        while(true){
            String input = readLine();
            try {
                this.wordLength = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println(input + " is not a valid word length");
            }
        }

        //Starting User Prompt for main.Game Setting
        System.out.println("Now please input the gamemode: (Normal | Info | Solver | Experiment)");
        while(true){
            String input = readLine();
            if(input.equalsIgnoreCase("Normal")){
                this.setting = Setting.NORMAL;
                System.out.println("Normal Mode");
                break;
            }
            /*
            else if(input.equalsIgnoreCase("Solver")){
                this.setting = Setting.SOLVER;
                //Starting User Prompt for Solver Heuristic
                System.out.println("Please Input Heuristic");
                while(true) {
                    input = readLine();
                    if (input.equalsIgnoreCase("FPS")) {
                        this.heuristic = new FirstPossibleSolution();
                        break;
                    }
                    if (input.equalsIgnoreCase("MI")) {
                        this.heuristic = new MostInformation();
                        break;
                    }
                    if (input.equalsIgnoreCase("MCL")) {
                        this.heuristic = new MostCommonLetters();
                        break;
                    }
                    if (input.equalsIgnoreCase("MCPL")) {
                        this.heuristic = new MostCommonPositionalLetters();
                        break;
                    }
                    if (input.equalsIgnoreCase("C")) {
                        this.heuristic = new Combined();
                        break;
                    }
                    System.out.println("Not valid heuristic");
                }
                System.out.println("Solver Mode");
                break;
            }
            else if(input.equalsIgnoreCase("Experiment") ){
                this.setting = Setting.EXPERIMENT;
                //Starting User Prompt for Experiment Heuristic
                System.out.println("Please Input Heuristic");
                input = readLine();
                while(true) {
                    if (input.equalsIgnoreCase("FPS")) {
                        this.heuristic = new FirstPossibleSolution();
                        break;
                    }
                    if (input.equalsIgnoreCase("MI")) {
                        this.heuristic = new MostInformation();
                        break;
                    }
                    if (input.equalsIgnoreCase("MCL")) {
                        this.heuristic = new MostCommonLetters();
                        break;
                    }
                    if (input.equalsIgnoreCase("MCPL")) {
                        this.heuristic = new MostCommonPositionalLetters();
                        break;
                    }
                    if (input.equalsIgnoreCase("C")) {
                        this.heuristic = new Combined();
                        break;
                    }
                    System.out.println("Not valid heuristic");
                }
                String filePath = "src/Results/" + heuristic.getName() + wordLength + ".txt";

                FileWriter file;
                try {
                    file = new FileWriter(filePath);
                } catch (IOException exception) {
                    System.out.println(exception.getMessage());
                    return;
                }
                writer = new BufferedWriter(file);

                System.out.println("Solver Test Mode");
                break;
            }
            else if(input.equalsIgnoreCase("Info")){
                this.setting = Setting.INFO;
                System.out.println("Info Mode");
                break;
            }
            */
            else {
                System.out.println("Not valid setting, must be (Normal | Info | Solver)");
            }
        }

        //Initializing the word loader
        while(true) {
            try {
                this.loader = new Loader(wordLength);
                break;
            } catch (Exception e) {
                System.out.println("Try again, please input a valid length");
            }
        }
    }
    /*
    private void startSolver() {
        solver = new Solver(loader, heuristic);

        this.guesses = new ArrayList<>();
        while (guesses.size() < guessLimit) {

            String input = solver.solve();

            //Add the valid guess to the list of guesses
            Guess guess = new Guess(input,solution,guesses.size()+1);
            guesses.add(guess);
            solver.filterSolution(guess);
            if(this.setting != Setting.EXPERIMENT) {
                guess.displayGuess();
            }

            //Check if the guess is the solution
            if (input.equalsIgnoreCase(solution)){
                win = true;
                break;
            }
        }
    }
    */

    private void startNormal() {
        //solver = new Solver(loader);

        this.guesses = new ArrayList<>();
        while (guesses.size() < guessLimit) {
            String input;
            input = readLine();

            //Checking that the input is valid
            if(input.length() != wordLength){
                System.out.println("Guess must be " + wordLength + " letters");
                continue;
            }
            else if (!loader.getWordList().contains(input)){
                System.out.println("Not a valid guess");
                continue;
            }

            //Add the valid guess to the list of guesses
            Guess guess = new Guess(input,solution,guesses.size()+1);
            guesses.add(guess);

            //Print the guesses to solution
            printOutput(guesses);

            /*
            //Filter out possible solutions
            solver.filterSolution(guess);

            if(setting == Setting.INFO){
                System.out.println(solver.getFilteredSolutions().size() + " possible solutions: " + solver.getFilteredSolutions());
            }
            */

            //Check if the guess is the solution
            if (input.equalsIgnoreCase(solution)){
                win = true;
                break;
            }
        }
    }

    private void end(){
        if(win){
            System.out.println("Correct, You got the answer in " + guesses.size() + ((guesses.size()==1) ? " try" : " tries"));
        }
        else{
            System.out.println("You are out of guesses. The correct answer is: " + solution);
        }
        win = false;
        System.out.println("Play Again? YES (Y) : NO (N)");
        while(true) {
            String input = readLine();
            if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("YES")) {
                while (true) {
                    System.out.println("Same Settings? YES (Y) : NO (N)");
                    input = readLine().toUpperCase(Locale.ROOT);
                    if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("YES")) {
                        break;
                    }
                    if (input.equalsIgnoreCase("N") || input.equalsIgnoreCase("NO")) {
                        initialiseGame();
                        break;
                    }
                }
                break;
            }
            if (input.equalsIgnoreCase("N") || input.equalsIgnoreCase("NO")) {
                playAgain = false;
                try {
                    this.reader.close();
                } catch (IOException e){
                    System.out.println(e.getMessage());
                }
                break;
            }
        }
    }
}

