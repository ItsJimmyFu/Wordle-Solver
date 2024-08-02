package Heuristics;

import Game.Constraint;
import Game.Solver;

import java.lang.constant.Constable;
import java.util.ArrayList;
import java.util.HashSet;

public abstract class Heuristic {

    public abstract String getSolution(Solver solver);
    public abstract String getName();
}
