package Heuristics;

import Game.Constraint;

import java.lang.constant.Constable;
import java.util.ArrayList;
import java.util.HashSet;

public abstract class Heuristic {

    public abstract String getSolution(HashSet<String> solutions, HashSet<String> filteredSolutions, ArrayList<Constraint> constraints);
    public abstract String getName();
}
