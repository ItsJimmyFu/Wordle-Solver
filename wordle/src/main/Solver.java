import java.util.*;

public class Solver {
    private ArrayList<String> wordList;
    public ArrayList<String> possibleSolutions;

    public Solver(ArrayList<String> wordList){
        this.possibleSolutions = wordList;
        this.wordList = wordList;
    }

    //Filter the list of solutions into possibleSolutions based on the constraints of the guess
    public void filterPossibleSolutions(Guess guess){
        //Create the constraints for the guess which sorted them from Green -> Yellow -> Gray
        //This needs to be sorted so that the Green matches are checked first
        TreeSet<Constraint> constraints = new TreeSet<>();
        for (int charIdx=0; charIdx<guess.wordLength; charIdx++){
            char letter = guess.getGuess().charAt(charIdx);
            int position = charIdx;
            Outcome outcome = guess.getOutcomes().get(charIdx);
            constraints.add(new Constraint(letter, position, outcome));
        }

        //Creates the new filtered list of solutions and adds each word in the word list that satisfies the constraints
        ArrayList<String> newFilteredSolutions = new ArrayList<>();
        for (String solution : possibleSolutions){
            if(filterWord(solution,constraints)){
                newFilteredSolutions.add(solution);
            }
        }
        this.possibleSolutions = newFilteredSolutions;
    }

    //Check that the word satisfies all the constraints
    public boolean filterWord(String word, Collection<Constraint> constraints){
        //Keep track of the index of characters in the word that have not yet been mapped to by constraints
        HashSet<Integer> remainingCharIndex = new HashSet<>();
        for (int charIdx = 0; charIdx < word.length(); charIdx++){
            remainingCharIndex.add(charIdx);
        }

        //For each constraint check that the word satisfies it based on the outcome
        for (Constraint constraint : constraints){
            Character letter = constraint.getLetter();
            Outcome outcome = constraint.getOutcome();
            Integer position = constraint.getPosition();

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
        return true;
    }
}
