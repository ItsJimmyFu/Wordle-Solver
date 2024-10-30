package Heuristics;

import Game.Solver;

public abstract class Heuristic {

    public abstract String getSolution(Solver solver);
    public abstract String getName();
}
