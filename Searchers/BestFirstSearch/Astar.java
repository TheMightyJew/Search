package Searchers.BestFirstSearch;

import Objects.Comparator.Fcomparator;
import Objects.Problem;
import Objects.SearchingState;
import Objects.Solution;

import java.util.PriorityQueue;

public class Astar extends BestFirstSearch{

    public Astar() {
    }

    public Solution solveNow(Problem problem) {
        openList = new PriorityQueue<SearchingState>(new Fcomparator(problem.getGoalState()));
        return super.solveNow(problem);
    }

    @Override
    public String toString() {
        return "Astar";
    }
}
