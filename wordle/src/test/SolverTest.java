import Game.Guess;
import Game.Solver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

public class SolverTest {

    //TODO
    //MAke a test that possibleGuesses is always less than or equal to possibleSolutions

    private HashSet<String> wordPoolA = new HashSet<>(Arrays.asList("fever","sever","lever","fleece","telds", "selee", "seeee"));
    private HashSet<String> wordPoolB = new HashSet<>(Arrays.asList(
            "media", "valid", "space", "arena", "funny",
            "drive", "asset", "usual", "peter", "upper",
            "alike", "wound", "joint", "plain", "house",
            "amigo"));
    private HashSet<String> wordPoolC = new HashSet<>(Arrays.asList(
            "other", "brief", "breed", "wound", "skill",
            "album", "seven", "fight", "major", "right",
            "equal", "sight", "anger", "grand", "store",
            "speed", "space", "daily", "solid", "about",
            "place", "worth", "ratio", "write", "meant",
            "these", "mayor", "sixty", "strip", "trial"
    ));

    @Test
    void testFilterGrayLetterWords(){
        Solver solver = new Solver(wordPoolB, wordPoolB);
        solver.filterPossibleSolutions(new Guess("shown", "alike"));
        HashSet<String> expected = new HashSet<>(Arrays.asList(
                "media", "valid", "drive", "peter", "upper", "alike"));
        Assertions.assertEquals(expected, solver.possibleSolutions);

        solver.filterPossibleSolutions(new Guess("crowd", "alike"));
        expected = new HashSet<>(Arrays.asList(
                "alike"));
        Assertions.assertEquals(expected, solver.possibleSolutions);
    }

    @Test
    void testFilterYellowLetterWords(){
        Solver solver = new Solver(wordPoolB, wordPoolB);
        solver.filterPossibleSolutions(new Guess("bravo", "joint"));
        HashSet<String> expected = new HashSet<>(Arrays.asList("wound", "joint", "house"));

        Assertions.assertEquals(expected, solver.possibleSolutions);

        solver.filterPossibleSolutions(new Guess("field", "joint"));
        expected = new HashSet<>(Arrays.asList(
                "joint"));
        Assertions.assertEquals(expected, solver.possibleSolutions);

        solver.filterPossibleSolutions(new Guess("ointj", "joint"));
        expected = new HashSet<>(Arrays.asList(
                "joint"));
        Assertions.assertEquals(expected, solver.possibleSolutions);
    }

    @Test
    void testFilterGreenLetterWords(){
        Solver solver = new Solver(wordPoolB, wordPoolB);
        solver.filterPossibleSolutions(new Guess("abuds", "arena"));
        HashSet<String> expected = new HashSet<>(Arrays.asList(
               "arena", "alike", "amigo"));
        Assertions.assertEquals(expected, solver.possibleSolutions);

        solver.filterPossibleSolutions(new Guess("arena", "arena"));
        expected = new HashSet<>(Arrays.asList(
                "arena"));
        Assertions.assertEquals(expected, solver.possibleSolutions);
    }

    @Test
    void testFilterRandomWords(){
        //Solution = daily
        Solver solver = new Solver(wordPoolC, wordPoolC);
        solver.filterPossibleSolutions(new Guess("phase", "daily"));
        HashSet<String> expected = new HashSet<>(Arrays.asList(
                "album", "major", "daily", "about", "ratio", "mayor", "trial"
        ));
        Assertions.assertEquals(expected, solver.possibleSolutions);
        solver.filterPossibleSolutions(new Guess("stuck", "daily"));
        expected = new HashSet<>(Arrays.asList(
                "major", "daily", "mayor"
        ));
        Assertions.assertEquals(expected, solver.possibleSolutions);
        solver.filterPossibleSolutions(new Guess("print", "daily"));
        expected = new HashSet<>(Arrays.asList(
                "daily"
        ));
        Assertions.assertEquals(expected, solver.possibleSolutions);

        //Solution = speed
        solver = new Solver(wordPoolC, wordPoolC);
        solver.filterPossibleSolutions(new Guess("under", "speed"));
        expected = new HashSet<>(Arrays.asList(
                "speed"
        ));

        //Solution = sixty
        solver = new Solver(wordPoolC, wordPoolC);
        solver.filterPossibleSolutions(new Guess("check", "sixty"));
        expected = new HashSet<>(Arrays.asList(
                "wound", "album", "major", "grand", "daily", "solid", "about", "ratio", "mayor", "sixty", "strip", "trial"
        ));
        Assertions.assertEquals(expected, solver.possibleSolutions);
        solver.filterPossibleSolutions(new Guess("quilt", "sixty"));
        expected = new HashSet<>(Arrays.asList(
                "ratio", "sixty", "strip"
        ));
        Assertions.assertEquals(expected, solver.possibleSolutions);
        solver.filterPossibleSolutions(new Guess("score", "sixty"));
        expected = new HashSet<>(Arrays.asList(
                "sixty"
        ));
        Assertions.assertEquals(expected, solver.possibleSolutions);

        //Solution = ratio
        solver = new Solver(wordPoolC, wordPoolC);
        solver.filterPossibleSolutions(new Guess("horse", "ratio"));
        expected = new HashSet<>(Arrays.asList("major", "ratio", "mayor"));
        Assertions.assertEquals(expected, solver.possibleSolutions);

        solver.filterPossibleSolutions(new Guess("arose", "ratio"));
        expected = new HashSet<>(Arrays.asList("major","ratio", "mayor"));
        Assertions.assertEquals(expected, solver.possibleSolutions);

        solver.filterPossibleSolutions(new Guess("click", "ratio"));
        expected = new HashSet<>(Arrays.asList("ratio"));
        Assertions.assertEquals(expected, solver.possibleSolutions);
    }

    @Test
    void testFilterDuplicateLetterWords(){
        Solver solver = new Solver(wordPoolA, wordPoolA);

        //Testing filter for words with unlimited letter occurences
        solver.filterPossibleSolutions(new Guess("eexxx", "zezez"));
        HashSet<String> expected = new HashSet<>(Arrays.asList("fever","sever","lever","selee","seeee"));
        Assertions.assertEquals(expected,solver.possibleSolutions);

        //Testing filter for words with max 2 letter occurences
        solver.filterPossibleSolutions(new Guess("eexex", "zezez"));
        expected = new HashSet<>(Arrays.asList("fever","sever","lever"));
        Assertions.assertEquals(expected,solver.possibleSolutions);
    }
}
