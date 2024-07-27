import java.sql.Array;
import java.util.*;

public class Solver {
    private ArrayList<String> solutions;
    public ArrayList<String> filteredSolutions;

    public Solver(ArrayList<String> wordList){
        this.filteredSolutions = wordList;
        this.solutions = wordList;
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

        for (Constraint constraint : constraints){
            Character letter = constraint.getLetter();

            //Keep track of the characters in the word that have not been mapped to by constraints
            HashSet<Integer> remainingCharIndex = new HashSet<>();
            for (int charIdx = 0; charIdx < word.length(); charIdx++){
                remainingCharIndex.add(charIdx);
            }

            for (int idx = 0; idx < constraint.size(); idx++){
                Outcome outcome = constraint.getOutcomeAtIndex(idx);
                Integer position = constraint.getPositionAtIndex(idx);

                switch(outcome){
                    case GREEN:
                        if((word.charAt(position) == letter) && (remainingCharIndex.contains(position))){
                            remainingCharIndex.remove(position);
                        }
                        else{
                            return false;
                        }
                        break;
                    case YELLOW:
                        if(word.charAt(position) == letter){
                            return false;
                        }
                        int indexOfLetter = -1;
                        for (int charIdx : remainingCharIndex){
                            if(word.charAt(charIdx) == letter){
                                indexOfLetter = charIdx;
                                break;
                            }
                        }
                        if(indexOfLetter == -1){
                            return false;
                        }
                        else{
                            remainingCharIndex.remove(indexOfLetter);
                        }
                        break;
                    case GRAY:
                        for (int charIdx : remainingCharIndex){
                            if(word.charAt(charIdx) == letter){
                                return false;
                            }
                        }
                        break;
                }
            }
        }
        return true;
    }



}
