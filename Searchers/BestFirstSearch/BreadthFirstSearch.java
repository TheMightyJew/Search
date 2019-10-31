package Searchers.BestFirstSearch;

import Objects.*;

import java.util.concurrent.LinkedBlockingQueue;

public class BreadthFirstSearch extends BestFirstSearch {

    public BreadthFirstSearch() {
        openList = new LinkedBlockingQueue<>();
    }

    public Solution solveNow(Problem problem) {
        return super.solveNow(problem);
    }

    @Override
    public String toString() {
        return "BreadthFirstSearch";
    }
}
