package Searchers.BestFirstSearch;

import Objects.Comparator.Hcomparator;
import Objects.Problem;
import Objects.SearchingState;
import Objects.Solution;

import java.util.PriorityQueue;

public class PureHeuristicSearch extends BestFirstSearch {

    public PureHeuristicSearch() {
    }

    public Solution solveNow(Problem problem) {
        openList = new PriorityQueue<SearchingState>(new Hcomparator(problem.getGoalState()));
        return super.solveNow(problem);
    }

    @Override
    public String toString() {
        return "PureHeuristicSearch";
    }
}
