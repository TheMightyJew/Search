package Searchers.BestFirstSearch;

import Objects.Comparator.Gcomparator;
import Objects.Problem;
import Objects.SearchingState;
import Objects.Solution;

import java.util.PriorityQueue;

public class Dijkstra extends BestFirstSearch{

    public Dijkstra() {
        openList = new PriorityQueue<SearchingState>(new Gcomparator());
    }

    public Solution solveNow(Problem problem) {
        return super.solveNow(problem);
    }

    @Override
    public String toString() {
        return "Dijkstra";
    }
}
