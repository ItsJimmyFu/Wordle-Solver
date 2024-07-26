import java.util.ArrayList;
import java.util.Arrays;

public class Constraint {
    private char letter;
    private ArrayList<Integer> positions;
    private ArrayList<Outcome> outcomes;
    //private int minOccurrences;
    //private int maxOccurrences;
    //private int grayCount;

    public Constraint(char letter, int position, Outcome outcome){
        this.letter = letter;
        positions = new ArrayList<>(Arrays.asList(position));
        outcomes = new ArrayList<>(Arrays.asList(outcome));
        //minOccurrences = 1;
        //Update this to the word length
        //maxOccurrences = 5;

        //grayCount = 0;
    }

    /*
    public boolean withinOccurrenceRange(int occurrences){
        if(occurrences < minOccurrences || occurrences > maxOccurrences){
            return false;
        }
        else {
            return true;
        }
    }
     */

    public char getLetter() {
        return letter;
    }

    public Outcome getOutcomeAtIndex(int index){
        return outcomes.get(index);
    }

    public Integer getPositionAtIndex(int index){
        return positions.get(index);
    }

    public int size(){
        return outcomes.size();
    }

    public void addConstraint(int position, Outcome outcome){
        //Add the constraint to constraints ArrayList so that it is sorted
        // in order of Green -> Yellow -> Gray

        //Default is 0 for Green or empty lists
        int insertIndex = 0;

        if(outcome == Outcome.GRAY){
            insertIndex = outcomes.size();
            //grayCount++;
        }
        else if (outcome == Outcome.YELLOW){
            for(int idx = 0; idx < outcomes.size(); idx++){
                insertIndex = idx;
                if(outcomes.get(idx) != Outcome.GREEN){
                    break;
                }
            }
        }

        /*
        //Update the minOccurences and maxOccurences based on the number of gray outcomes for the letter in the guess
        this.minOccurrences = outcomes.size()-grayCount;

        if(grayCount == 0){
            //Change to word length
            this.maxOccurrences = 5;
        }
        else{
            this.maxOccurrences = minOccurrences;
        }
        */


        //By default Green will insert at the head of the list
        positions.add(insertIndex,position);
        outcomes.add(insertIndex,outcome);
    }
}
