import java.sql.Array;
import java.util.*;

public class Solver {
    private Loader loader;
    private ArrayList<String> solutions;
    public ArrayList<String> filteredSolutions;

    public Solver(Loader loader){
        this.loader = loader;
        this.filteredSolutions =this.loader.getWordList();
        this.solutions = this.loader.getWordList();
    }

    public void filterSolutions(Guess guess){
        //Create the constraints for the guess
        HashMap<Character,Constraint> constraints = new HashMap<>();

        for (int charIdx=0; charIdx<guess.wordLength; charIdx++){
            char letter = guess.getGuess().charAt(charIdx);
            int index = charIdx;
            Outcome outcome = guess.getOutcomes().get(charIdx);

            if(constraints.containsKey(letter)){
                constraints.get(letter).addConstraint(index,outcome);
            }
            else {
                Constraint constraint = new Constraint(letter,index,outcome);
                constraints.put(letter,constraint);
            }
        }

        ArrayList<String> newFilteredSolutions = new ArrayList<>();
        for (String solution : filteredSolutions){
            if(filterWord(solution,constraints.values())){
                newFilteredSolutions.add(solution);
            }
        }
        this.filteredSolutions = newFilteredSolutions;
    }

    public boolean filterWord(String word, Collection<Constraint> constraints){
        ArrayList<Integer> searchedCharIndex = new ArrayList<>();

        for (Constraint constraint : constraints){
            Character letter = constraint.getLetter();

            for (int idx = 0; idx < constraint.size(); idx++){
                Outcome outcome = constraint.getOutcomeAtIndex(idx);
                Integer position = constraint.getPositionAtIndex(idx);

                switch(outcome){
                    case GREEN:
                        if((word.charAt(position) == letter) && !searchedCharIndex.contains(position)){
                            searchedCharIndex.add(position);
                        }
                        else{
                            return false;
                        }
                        break;
                    case YELLOW:
                        if(word.charAt(position) == letter){
                            return false;
                        }
                        boolean containsLetter = false;
                        for (int charIdx = 0; charIdx < word.length(); charIdx++){
                            if(searchedCharIndex.contains(charIdx)){
                                continue;
                            }
                            if(word.charAt(charIdx)==letter){
                                searchedCharIndex.add(charIdx);
                                containsLetter = true;
                                break;
                            }
                        }
                        if(!containsLetter){
                            return false;
                        }
                        break;
                    case GRAY:
                        if(word.indexOf(letter) != -1){
                            return false;
                        }
                        break;
                }
            }
        }
        return true;
    }



}
