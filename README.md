# wordle-solver

This project is a comprehensive Wordle-solving tool that provides different settings, heuristics, and modes for solving Wordle puzzles. The solver can operate autonomously through a web browser to interact with the Wordle site, using the best heuristic-based strategies to predict optimal guesses.

Features

Settings:
	•	Normal: A standard game mode that solves the Wordle puzzle.
	•	Info: Similar to Normal mode, but provides additional information about the guess selection process based on Heuristic selected.
	•	Solver: An enhanced mode that utilised selected heuristic and algorithms to deduce the solution.
	•	Experiment: Runs a tests on selected heuristic with the solver on the word list with results saved to a results file.
Heuristics:
	•	FirstFilteredGuess: Chooses the first word in a list of possible guesses.
	•	LeastConstraints: Prioritizes words that have the least constraints to maximize viable guesses.
 	•	MostCommonLetters: Focuses on words with the most frequently used letters.
	•	MostCommonPositionalLetters: Builds on MostCommonLetters but focuses on words with the most frequently used letters in each position.
	•	LeastConstraintsAndMostCommonPositionalLetters: Combines Least Constraints and MostCommonPositionalLetters on a specific weighting.
	•	MostInformation: Selects guesses that yield the most information based on Entropy.
Automated Browser Play:
  •	The WebBrowserSolver class utilizes a Safari WebDriver and Selenium to interact directly with the Wordle website.
	•	Injects guesses from the best Wordle-solving heuristic, as determined by Experiment mode, and records the results.

Installation

	1.	Clone the Repository:

git clone https://github.com/ItsJimmyFu/wordle-solver.git
cd wordle-solver

	2.	Set Up Dependencies:

Make sure you have Selenium WebDriver installed, as well as the Safari WebDriver.
Use Maven to manage Java dependencies if your project setup includes a pom.xml. Alternatively, include the Selenium .jar files directly in your project.

Usage

Running the Wordle Solver in Normal Mode

The Game class offers a text-based interface where you can set parameters before solving the puzzle.

To start the game:
# Navigate to main Java directory
cd wordle

# Compile the classes
javac *.java

# Run the game class
java Game

Upon running, you will be prompted to:

	1.	Set the word length (Currently the available option is 5 letters).
	2.	Choose a setting (Normal, Info, Solver, Experiment).
	3.	Choose a heuristic.

The game will then proceed with the chosen configuration and display the output directly in the terminal.

Automated Browser Mode with WebBrowserSolver

To use the WebBrowserSolver and let the program interact directly with the Wordle website:

# Compile the classes
javac *.java

# Run the WebBrowserSolver class
java WebBrowserSolver

This mode will:

	•	Automatically connect to the Wordle website.
	•	Generate guesses using the solver with MostInformation heuristic.
	•	Print the result.

License

This project is open source and available under the MIT License.
