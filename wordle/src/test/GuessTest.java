import Game.Guess;
import Game.Outcome;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class GuessTest {

    @Test
    void TestYellowLetters() {
        //1 Yellow
        Guess guess = new Guess("mater","blimp");
        ArrayList<Outcome> expected = new ArrayList<>(Arrays.asList(Outcome.YELLOW,Outcome.GRAY,Outcome.GRAY,Outcome.GRAY,Outcome.GRAY));
        Assertions.assertEquals(expected,guess.getOutcomes());

        //2 Yellow
        guess = new Guess("stich","roast");
        expected = new ArrayList<>(Arrays.asList(Outcome.YELLOW,Outcome.YELLOW,Outcome.GRAY,Outcome.GRAY,Outcome.GRAY));
        Assertions.assertEquals(expected,guess.getOutcomes());

        //3 Yellow
        guess = new Guess("rants","crane");
        expected = new ArrayList<>(Arrays.asList(Outcome.YELLOW,Outcome.YELLOW,Outcome.YELLOW,Outcome.GRAY,Outcome.GRAY));
        Assertions.assertEquals(expected,guess.getOutcomes());

        //4 Yellow
        guess = new Guess("clean","uncle");
        expected = new ArrayList<>(Arrays.asList(Outcome.YELLOW,Outcome.YELLOW,Outcome.YELLOW,Outcome.GRAY,Outcome.YELLOW));
        Assertions.assertEquals(expected,guess.getOutcomes());

        //5 Yellow
        guess = new Guess("races", "scares");
        expected = new ArrayList<>(Arrays.asList(Outcome.YELLOW,Outcome.YELLOW,Outcome.YELLOW,Outcome.YELLOW,Outcome.YELLOW));
        Assertions.assertEquals(expected,guess.getOutcomes());
    }

    @Test
    void TestGreenLetters() {
        //1 Green
        Guess guess = new Guess("batch","blimp");
        ArrayList<Outcome> expected = new ArrayList<>(Arrays.asList(Outcome.GREEN,Outcome.GRAY,Outcome.GRAY,Outcome.GRAY,Outcome.GRAY));
        Assertions.assertEquals(expected,guess.getOutcomes());

        //2 Green
        guess = new Guess("ruder","alter");
        expected = new ArrayList<>(Arrays.asList(Outcome.GRAY,Outcome.GRAY,Outcome.GRAY,Outcome.GREEN,Outcome.GREEN));
        Assertions.assertEquals(expected,guess.getOutcomes());

        //3 Green
        guess = new Guess("frame","crane");
        expected = new ArrayList<>(Arrays.asList(Outcome.GRAY,Outcome.GREEN,Outcome.GREEN,Outcome.GRAY,Outcome.GREEN));
        Assertions.assertEquals(expected,guess.getOutcomes());

        //4 Green
        guess = new Guess("boast","toast");
        expected = new ArrayList<>(Arrays.asList(Outcome.GRAY,Outcome.GREEN,Outcome.GREEN,Outcome.GREEN,Outcome.GREEN));
        Assertions.assertEquals(expected,guess.getOutcomes());

        //5 GREEN
        guess = new Guess("scare", "scare");
        expected = new ArrayList<>(Arrays.asList(Outcome.GREEN,Outcome.GREEN,Outcome.GREEN,Outcome.GREEN,Outcome.GREEN));
        Assertions.assertEquals(expected,guess.getOutcomes());
    }

    @Test
    void testWordsWithDuplicateLetters(){
        Guess guess;
        ArrayList<Outcome> expected;

        //Duplicate letter solution with single letter guess - 1 green match
        guess = new Guess("abled","fever");
        expected = new ArrayList<>(Arrays.asList(Outcome.GRAY,Outcome.GRAY,Outcome.GRAY,Outcome.GREEN,Outcome.GRAY));
        Assertions.assertEquals(expected,guess.getOutcomes());

        //Duplicate letter solution with single letter guess - 1 yellow match
        guess = new Guess("easts","fever");
        expected = new ArrayList<>(Arrays.asList(Outcome.YELLOW,Outcome.GRAY,Outcome.GRAY,Outcome.GRAY,Outcome.GRAY));
        Assertions.assertEquals(expected,guess.getOutcomes());

        //Duplicate letter solution with single letter guess - 1 green & 1 yellow match
        guess = new Guess("peace","fever");
        expected = new ArrayList<>(Arrays.asList(Outcome.GRAY,Outcome.GREEN,Outcome.GRAY,Outcome.GRAY,Outcome.YELLOW));
        Assertions.assertEquals(expected,guess.getOutcomes());

        //Duplicate letter solution with duplicate letter guess - 1 yellow & 1 green match
        guess = new Guess("speed","fever");
        expected = new ArrayList<>(Arrays.asList(Outcome.GRAY,Outcome.GRAY,Outcome.YELLOW,Outcome.GREEN,Outcome.GRAY));
        Assertions.assertEquals(expected,guess.getOutcomes());

        //Duplicate letter solution with duplicate letter guess - 2 yellow matches
        guess = new Guess("these","fever");
        expected = new ArrayList<>(Arrays.asList(Outcome.GRAY,Outcome.GRAY,Outcome.YELLOW,Outcome.GRAY,Outcome.YELLOW));
        Assertions.assertEquals(expected,guess.getOutcomes());

        //Duplicate letter solution with duplicate letter guess - 2 green matches
        guess = new Guess("bedel","fever");
        expected = new ArrayList<>(Arrays.asList(Outcome.GRAY,Outcome.GREEN,Outcome.GRAY,Outcome.GREEN,Outcome.GRAY));
        Assertions.assertEquals(expected,guess.getOutcomes());

        //Single letter solution with duplicate letter guess - 1 yellow match
        guess = new Guess("fever","easts");
        expected = new ArrayList<>(Arrays.asList(Outcome.GRAY,Outcome.YELLOW,Outcome.GRAY,Outcome.GRAY,Outcome.GRAY));
        Assertions.assertEquals(expected,guess.getOutcomes());

        //Single letter solution with duplicate letter guess - 1 green match
        guess = new Guess("eager","epoch");
        expected = new ArrayList<>(Arrays.asList(Outcome.GREEN,Outcome.GRAY,Outcome.GRAY,Outcome.GRAY,Outcome.GRAY));
        Assertions.assertEquals(expected,guess.getOutcomes());
    }

    @Test
    void testCombinationOfGreenYellowLetters(){
        Guess guess;
        ArrayList<Outcome> expected;

        guess = new Guess("adieu","audio");
        expected = new ArrayList<>(Arrays.asList(Outcome.GREEN,Outcome.YELLOW,Outcome.YELLOW,Outcome.GRAY,Outcome.YELLOW));
        Assertions.assertEquals(expected,guess.getOutcomes());

        guess = new Guess("alien","shine");
        expected = new ArrayList<>(Arrays.asList(Outcome.GRAY,Outcome.GRAY,Outcome.GREEN,Outcome.YELLOW,Outcome.YELLOW));
        Assertions.assertEquals(expected,guess.getOutcomes());

        guess = new Guess("cheer","error");
        expected = new ArrayList<>(Arrays.asList(Outcome.GRAY,Outcome.GRAY,Outcome.YELLOW,Outcome.GRAY,Outcome.GREEN));
        Assertions.assertEquals(expected,guess.getOutcomes());

        guess = new Guess("sound","rouse");
        expected = new ArrayList<>(Arrays.asList(Outcome.YELLOW,Outcome.GREEN,Outcome.GREEN,Outcome.GRAY,Outcome.GRAY));
        Assertions.assertEquals(expected,guess.getOutcomes());
    }

    @Test
    void testNoMatches(){
        Guess guess;
        ArrayList<Outcome> expected;

        guess = new Guess("build","tacky");
        expected = new ArrayList<>(Arrays.asList(Outcome.GRAY,Outcome.GRAY,Outcome.GRAY,Outcome.GRAY,Outcome.GRAY));
        Assertions.assertEquals(expected,guess.getOutcomes());

        guess = new Guess("tacky","build");
        expected = new ArrayList<>(Arrays.asList(Outcome.GRAY,Outcome.GRAY,Outcome.GRAY,Outcome.GRAY, Outcome.GRAY));
        Assertions.assertEquals(expected,guess.getOutcomes());

        guess = new Guess("panic","study");
        expected = new ArrayList<>(Arrays.asList(Outcome.GRAY,Outcome.GRAY,Outcome.GRAY,Outcome.GRAY,Outcome.GRAY));
        Assertions.assertEquals(expected,guess.getOutcomes());

        guess = new Guess("study","panic");
        expected = new ArrayList<>(Arrays.asList(Outcome.GRAY,Outcome.GRAY,Outcome.GRAY,Outcome.GRAY,Outcome.GRAY));
        Assertions.assertEquals(expected,guess.getOutcomes());
    }
}
