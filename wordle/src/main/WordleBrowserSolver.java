import Game.Guess;
import Game.Outcome;
import Game.Solver;
import Game.Wordle;
import Heuristics.MostInformation;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class WordleBrowserSolver {
    WebDriver driver;
    Wordle wordle;

    public void initialize() throws InterruptedException {
        //Initialize wordle
        try {
            wordle = new Wordle(5);
            wordle.solver = new Solver(wordle.loader.getWordList(), wordle.loader.getSolutionWordList(), new MostInformation());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Initialize SafariDriver
        driver = new SafariDriver();

        // Navigate to wordle website
        driver.get("https://www.nytimes.com/games/wordle/index.html");

        Thread.sleep(2000);

        //Starting by pressing the Play Button
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement playButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='Play']")));
        playButton.click();

        //Close the automatic information tab
        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement closeButton = wait2.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button.Modal-module_closeIcon__TcEKb")));
        closeButton.click();
    }

    //Send the word guess to the wordle application
    public void sendInput() throws InterruptedException {
        //Add the solvers guess to the game
        String guess = wordle.solver.makeGuess();

        //Wait 1 second
        Thread.sleep(1000);

        // Press the key buttons for each character in the guess
        for (Character key : guess.toCharArray()) {
            WebElement keyButton = driver.findElement(By.cssSelector("[data-key='" + key + "']"));
            keyButton.click();
        }

        // Press the enter key
        WebElement keyButton = driver.findElement(By.cssSelector("[aria-label='enter']"));
        keyButton.click();
    }

    //Read the outcomes of the guess at certain row
    public void readOutput(int rowIndex){
        // Get the row element at rowIndex
        WebElement rowElement = driver.findElement(By.cssSelector("[aria-label='Row " + rowIndex + "']"));

        // Check that the output of the row has updated and shows the result
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until((ExpectedCondition<Boolean>) driver -> {
            // Get the last column element
            List<WebElement> columnElements = rowElement.findElements(By.className("Tile-module_tile__UWEHN"));
            String outcomeValue = columnElements.get(columnElements.size()-1).getAttribute("data-state");
            //Check if a guess has been made yet
            return "absent".equals(outcomeValue) || "present".equals(outcomeValue) || "correct".equals(outcomeValue);
        });

        StringBuilder guessString = new StringBuilder();
        ArrayList<Outcome> outcomes = new ArrayList<>();

        //Read the final result
        List<WebElement> children = rowElement.findElements(By.className("Tile-module_tile__UWEHN"));
        for (WebElement child : children) {
            // Split the string by to get the text and outcome component
            String[] label = child.getAttribute("aria-label").split(", ");
            guessString.append(label[1].toLowerCase());
            switch (label[2]) {
                case "absent" -> outcomes.add(Outcome.GRAY);
                case "present in another position" -> outcomes.add(Outcome.YELLOW);
                case "correct" -> outcomes.add(Outcome.GREEN);
                default -> System.out.println("Invalid: " + label[2]);
            }
        }

        //Add the guess to the wordle class
        Guess guess = new Guess(guessString.toString(),outcomes);
        wordle.guesses.add(guess);

        //Filter out the possible solutions based on the recent guess
        wordle.solver.filterPossibleSolutions(wordle.getRecentGuess());
    }

    public static void main(String[] args) throws Exception {
        WordleBrowserSolver wordleBrowserSolver = new WordleBrowserSolver();
        wordleBrowserSolver.initialize();

        boolean correct = false;

        for (int i = 1; i <= 6; i++) {
            //Send the guess string to wordle browser
            wordleBrowserSolver.sendInput();

            //Read the output of the guess from the wordle browser
            wordleBrowserSolver.readOutput(i);

            //Check if the last guess made is correct
            if(wordleBrowserSolver.wordle.getRecentGuess().isCorrect()){
                correct = true;
                break;
            }
        }

        //print the result
        if(correct){
            System.out.println("Correct: " + wordleBrowserSolver.wordle.getRecentGuess().getGuess() + " : in" +
                    " " + wordleBrowserSolver.wordle.guesses.size() + " guesses");
        }
        else{

            WebDriverWait wait = new WebDriverWait(wordleBrowserSolver.driver, Duration.ofSeconds(10));
            WebElement solution = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("Toast-module_toast__iiVsN")));
            System.out.println("Incorrect, solution was: " + solution.getText());
        }

        //Wait 5 seconds
        Thread.sleep(5000);

        // Close the browser
        wordleBrowserSolver.driver.quit();
    }
}
