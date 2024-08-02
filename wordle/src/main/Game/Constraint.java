package Game;

public class Constraint implements Comparable<Constraint> {
    public char letter;
    public Integer position;
    public Outcome outcome;

    public Constraint(char letter, int position, Outcome outcome){
        this.letter = letter;
        this.position = position;
        this.outcome = outcome;
    }

    public char getLetter() {
        return letter;
    }

    public Outcome getOutcome(){ return outcome; }

    public Integer getPosition(){ return position; }

    //Creates a comparator to ensure that different Constraints are ordered from Green -> Yellow -> Gray
    @Override
    public int compareTo(Constraint o) {
        int outcomeComparison = this.outcome.compareTo(o.outcome);
        if(outcomeComparison == 0){
            return this.position-o.position;
        }
        else{
            return outcomeComparison;
        }
    }
}
