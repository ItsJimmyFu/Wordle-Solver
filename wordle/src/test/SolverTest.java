import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class SolverTest {

    private ArrayList<String> wordPoolA = new ArrayList<String>(Arrays.asList("barke","backe","black"));
    private ArrayList<String> wordPoolB = new ArrayList<String>(Arrays.asList("fever","sever","lever","fleece","telds", "selee", "seeee"));

    @Test
    void testFilterCorrectGuess() {
        for (String guess: wordPoolA){
            Solver solver = new Solver(wordPoolA);
            solver.filterPossibleSolutions(new Guess(guess,guess,0));
            Assertions.assertEquals(1,solver.possibleSolutions.size());
            Assertions.assertEquals(guess, solver.possibleSolutions.get(0));
        }
    }

    @Test
    void testFilterNormalWords(){

    }

    @Test
    void testFilterDuplicateLetterWord(){
        Solver solver = new Solver(wordPoolB);

        //Testing filter for words with unlimited letter occurences
        solver.filterPossibleSolutions(new Guess("eexxx", "zezez",0));
        ArrayList<String> expected = new ArrayList<>(Arrays.asList("fever","sever","lever","selee","seeee"));
        Assertions.assertEquals(expected,solver.possibleSolutions);

        //Testing filter for words with max 2 letter occurences
        solver.filterPossibleSolutions(new Guess("eexex", "zezez",0));
        expected = new ArrayList<>(Arrays.asList("fever","sever","lever"));
        Assertions.assertEquals(expected,solver.possibleSolutions);
    }
}
