import java.util.ArrayList;
import java.util.Arrays;

public class Constraint {
    private char letter;
    private ArrayList<Integer> positions;
    public ArrayList<Outcome> outcomes;

    public Constraint(char letter, int position, Outcome outcome){
        this.letter = letter;
        positions = new ArrayList<>(Arrays.asList(position));
        outcomes = new ArrayList<>(Arrays.asList(outcome));
    }

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

    // Add the new constraint to positions and outcomes
    public void addConstraint(int position, Outcome outcome){
        //Add the constraint to constraints ArrayList so that it is sorted
        // in order of Green, Yellow and then Gray Outcomes

        //Default index to insert is 0 for a Green Outcome or empty lists
        int insertIndex = 0;

        //If Outcome is Gray insert at the end of the list
        if(outcome == Outcome.GRAY){
            insertIndex = outcomes.size();
        }
        //If outcome is yellow, find the last green outcome and insert after that
        else if (outcome == Outcome.YELLOW){
            for(int idx = 0; idx < outcomes.size(); idx++){
                insertIndex = idx;
                if(outcomes.get(idx) != Outcome.GREEN){
                    break;
                }
            }
        }

        //By default Green will insert at the head of the list
        positions.add(insertIndex,position);
        outcomes.add(insertIndex,outcome);
    }
}
