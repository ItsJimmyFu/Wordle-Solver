import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class GuessTest {

    @Test
    void TestYellowLetters() {
        //1 Yellow
        Guess guess = new Guess("mater","blimp",0);
        ArrayList<Outcome> expected = new ArrayList<>(Arrays.asList(Outcome.YELLOW,Outcome.GRAY,Outcome.GRAY,Outcome.GRAY,Outcome.GRAY));
        Assertions.assertEquals(expected,guess.getOutcomes());

        //2 Yellow
        guess = new Guess("stich","roast",0);
        expected = new ArrayList<>(Arrays.asList(Outcome.YELLOW,Outcome.YELLOW,Outcome.GRAY,Outcome.GRAY,Outcome.GRAY));
        Assertions.assertEquals(expected,guess.getOutcomes());

        //3 Yellow
        guess = new Guess("rants","crane",0);
        expected = new ArrayList<>(Arrays.asList(Outcome.YELLOW,Outcome.YELLOW,Outcome.YELLOW,Outcome.GRAY,Outcome.GRAY));
        Assertions.assertEquals(expected,guess.getOutcomes());

        //4 Yellow
        guess = new Guess("clean","uncle",0);
        expected = new ArrayList<>(Arrays.asList(Outcome.YELLOW,Outcome.YELLOW,Outcome.YELLOW,Outcome.GRAY,Outcome.YELLOW));
        Assertions.assertEquals(expected,guess.getOutcomes());

        //5 Yellow
        guess = new Guess("races", "scares", 0);
        expected = new ArrayList<>(Arrays.asList(Outcome.YELLOW,Outcome.YELLOW,Outcome.YELLOW,Outcome.YELLOW,Outcome.YELLOW));
        Assertions.assertEquals(expected,guess.getOutcomes());
    }

    @Test
    void TestGreenLetters() {
        //1 Green
        Guess guess = new Guess("batch","blimp",0);
        ArrayList<Outcome> expected = new ArrayList<>(Arrays.asList(Outcome.GREEN,Outcome.GRAY,Outcome.GRAY,Outcome.GRAY,Outcome.GRAY));
        Assertions.assertEquals(expected,guess.getOutcomes());

        //2 Green
        guess = new Guess("ruder","alter",0);
        expected = new ArrayList<>(Arrays.asList(Outcome.GRAY,Outcome.GRAY,Outcome.GRAY,Outcome.GREEN,Outcome.GREEN));
        Assertions.assertEquals(expected,guess.getOutcomes());

        //3 Green
        guess = new Guess("frame","crane",0);
        expected = new ArrayList<>(Arrays.asList(Outcome.GRAY,Outcome.GREEN,Outcome.GREEN,Outcome.GRAY,Outcome.GREEN));
        Assertions.assertEquals(expected,guess.getOutcomes());

        //4 Green
        guess = new Guess("boast","toast",0);
        expected = new ArrayList<>(Arrays.asList(Outcome.GRAY,Outcome.GREEN,Outcome.GREEN,Outcome.GREEN,Outcome.GREEN));
        Assertions.assertEquals(expected,guess.getOutcomes());

        //5 GREEN
        guess = new Guess("scare", "scare", 0);
        expected = new ArrayList<>(Arrays.asList(Outcome.GREEN,Outcome.GREEN,Outcome.GREEN,Outcome.GREEN,Outcome.GREEN));
        Assertions.assertEquals(expected,guess.getOutcomes());
    }

    @Test
    void testWordsWithDuplicateLetters(){
        Guess guess;
        ArrayList<Outcome> expected;

        //Duplicate letter solution with single letter guess - 1 green match
        guess = new Guess("abled","fever",0);
        expected = new ArrayList<>(Arrays.asList(Outcome.GRAY,Outcome.GRAY,Outcome.GRAY,Outcome.GREEN,Outcome.GRAY));
        Assertions.assertEquals(expected,guess.getOutcomes());

        //Duplicate letter solution with single letter guess - 1 yellow match
        guess = new Guess("easts","fever",0);
        expected = new ArrayList<>(Arrays.asList(Outcome.YELLOW,Outcome.GRAY,Outcome.GRAY,Outcome.GRAY,Outcome.GRAY));
        Assertions.assertEquals(expected,guess.getOutcomes());

        //Duplicate letter solution with single letter guess - 1 green & 1 yellow match
        guess = new Guess("peace","fever",0);
        expected = new ArrayList<>(Arrays.asList(Outcome.GRAY,Outcome.GREEN,Outcome.GRAY,Outcome.GRAY,Outcome.YELLOW));
        Assertions.assertEquals(expected,guess.getOutcomes());

        //Duplicate letter solution with duplicate letter guess - 1 yellow & 1 green match
        guess = new Guess("speed","fever",0);
        expected = new ArrayList<>(Arrays.asList(Outcome.GRAY,Outcome.GRAY,Outcome.YELLOW,Outcome.GREEN,Outcome.GRAY));
        Assertions.assertEquals(expected,guess.getOutcomes());

        //Duplicate letter solution with duplicate letter guess - 2 yellow matches
        guess = new Guess("these","fever",0);
        expected = new ArrayList<>(Arrays.asList(Outcome.GRAY,Outcome.GRAY,Outcome.YELLOW,Outcome.GRAY,Outcome.YELLOW));
        Assertions.assertEquals(expected,guess.getOutcomes());

        //Duplicate letter solution with duplicate letter guess - 2 green matches
        guess = new Guess("bedel","fever",0);
        expected = new ArrayList<>(Arrays.asList(Outcome.GRAY,Outcome.GREEN,Outcome.GRAY,Outcome.GREEN,Outcome.GRAY));
        Assertions.assertEquals(expected,guess.getOutcomes());

        //Single letter solution with duplicate letter guess - 1 yellow match
        guess = new Guess("fever","easts",0);
        expected = new ArrayList<>(Arrays.asList(Outcome.GRAY,Outcome.YELLOW,Outcome.GRAY,Outcome.GRAY,Outcome.GRAY));
        Assertions.assertEquals(expected,guess.getOutcomes());

        //Single letter solution with duplicate letter guess - 1 green match
        guess = new Guess("eager","epoch",0);
        expected = new ArrayList<>(Arrays.asList(Outcome.GREEN,Outcome.GRAY,Outcome.GRAY,Outcome.GRAY,Outcome.GRAY));
        Assertions.assertEquals(expected,guess.getOutcomes());
    }

    @Test
    void testCombinationOfGreenYellowLetters(){
        Guess guess;
        ArrayList<Outcome> expected;

        guess = new Guess("adieu","audio",0);
        expected = new ArrayList<>(Arrays.asList(Outcome.GREEN,Outcome.YELLOW,Outcome.YELLOW,Outcome.GRAY,Outcome.YELLOW));
        Assertions.assertEquals(expected,guess.getOutcomes());

        guess = new Guess("alien","shine",0);
        expected = new ArrayList<>(Arrays.asList(Outcome.GRAY,Outcome.GRAY,Outcome.GREEN,Outcome.YELLOW,Outcome.YELLOW));
        Assertions.assertEquals(expected,guess.getOutcomes());

        guess = new Guess("cheer","error",0);
        expected = new ArrayList<>(Arrays.asList(Outcome.GRAY,Outcome.GRAY,Outcome.YELLOW,Outcome.GRAY,Outcome.GREEN));
        Assertions.assertEquals(expected,guess.getOutcomes());

        guess = new Guess("sound","rouse",0);
        expected = new ArrayList<>(Arrays.asList(Outcome.YELLOW,Outcome.GREEN,Outcome.GREEN,Outcome.GRAY,Outcome.GRAY));
        Assertions.assertEquals(expected,guess.getOutcomes());
    }

    @Test
    void testNoMatches(){
        Guess guess;
        ArrayList<Outcome> expected;

        guess = new Guess("build","tacky",0);
        expected = new ArrayList<>(Arrays.asList(Outcome.GRAY,Outcome.GRAY,Outcome.GRAY,Outcome.GRAY,Outcome.GRAY));
        Assertions.assertEquals(expected,guess.getOutcomes());

        guess = new Guess("tacky","build",0);
        expected = new ArrayList<>(Arrays.asList(Outcome.GRAY,Outcome.GRAY,Outcome.GRAY,Outcome.GRAY,Outcome.GRAY));
        Assertions.assertEquals(expected,guess.getOutcomes());

        guess = new Guess("panic","study",0);
        expected = new ArrayList<>(Arrays.asList(Outcome.GRAY,Outcome.GRAY,Outcome.GRAY,Outcome.GRAY,Outcome.GRAY));
        Assertions.assertEquals(expected,guess.getOutcomes());

        guess = new Guess("study","panic",0);
        expected = new ArrayList<>(Arrays.asList(Outcome.GRAY,Outcome.GRAY,Outcome.GRAY,Outcome.GRAY,Outcome.GRAY));
        Assertions.assertEquals(expected,guess.getOutcomes());
    }
}
