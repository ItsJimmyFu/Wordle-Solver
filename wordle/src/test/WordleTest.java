import Game.Loader;
import Game.Wordle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

public class WordleTest {

    @Test
    void testInitialiseInvalidWordLength() {
        Assertions.assertThrows(Exception.class,() -> new Wordle(0));
    }

    @Test
    void testInvalidWords() {
        Wordle wordle;
        try {
            wordle = new Wordle(5);
        } catch (Exception e){
            Assertions.fail();
            return;
        }

        //Invalid word of length 1
        Assertions.assertThrows(Exception.class, () -> wordle.addGuess("a"));
        //Invalid word of length 4
        Assertions.assertThrows(Exception.class, () -> wordle.addGuess("clap"));
        //Invalid word not in word list
        Assertions.assertThrows(Exception.class, () -> wordle.addGuess("bavar"));

    }

    @Test
    void testCorrectGuess() throws Exception {
        Wordle wordle;
        try {
            wordle = new Wordle(5);
        } catch (Exception e) {
            Assertions.fail();
            return;
        }

        wordle.solution = "place";

        wordle.addGuess("guess");
        Assertions.assertFalse(wordle.gameOver);
        Assertions.assertFalse(wordle.win);

        wordle.addGuess("crane");
        Assertions.assertFalse(wordle.gameOver);
        Assertions.assertFalse(wordle.win);

        wordle.addGuess("maker");
        Assertions.assertFalse(wordle.gameOver);
        Assertions.assertFalse(wordle.win);

        wordle.addGuess("train");
        Assertions.assertFalse(wordle.gameOver);
        Assertions.assertFalse(wordle.win);

        wordle.addGuess("swamp");
        Assertions.assertFalse(wordle.gameOver);
        Assertions.assertFalse(wordle.win);

        wordle.addGuess("place");
        Assertions.assertTrue(wordle.gameOver);
        Assertions.assertTrue(wordle.win);
    }

    @Test
    void testIncorrectGuess() throws Exception {
        Wordle wordle;
        try {
            wordle = new Wordle(5);
        } catch (Exception e) {
            Assertions.fail();
            return;
        }

        wordle.solution = "place";

        wordle.addGuess("guess");
        Assertions.assertFalse(wordle.gameOver);
        Assertions.assertFalse(wordle.win);

        wordle.addGuess("crane");
        Assertions.assertFalse(wordle.gameOver);
        Assertions.assertFalse(wordle.win);

        wordle.addGuess("maker");
        Assertions.assertFalse(wordle.gameOver);
        Assertions.assertFalse(wordle.win);

        wordle.addGuess("train");
        Assertions.assertFalse(wordle.gameOver);
        Assertions.assertFalse(wordle.win);

        wordle.addGuess("swamp");
        Assertions.assertFalse(wordle.gameOver);
        Assertions.assertFalse(wordle.win);

        wordle.addGuess("liver");
        Assertions.assertTrue(wordle.gameOver);
        Assertions.assertFalse(wordle.win);

    }

    @Test
    void testSolutionGenerate() throws Exception{
        Loader loader = new Loader(5);
        loader.wordList = new HashSet<>();
        loader.wordList.add("SOL.1");

        Wordle wordle = new Wordle(5);
        wordle.loader = loader;
        wordle.generateSolution();
        Assertions.assertEquals("SOL.1",wordle.solution);

    }
}
