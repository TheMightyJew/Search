package Searchers.DepthFirstSearch;
import Objects.Problem;
import Objects.SearchingState;
import Objects.Solution;

public class IterativeDeepeningDepthFirstSearch extends DepthFirstSearch {
    protected double maxDeep;

    public IterativeDeepeningDepthFirstSearch() {
    }

    @Override
    public Solution solveNow(Problem problem) {
        maxDeep = problem.getCheapestMove();
        Solution solution = null;
        while ((solution=super.solveNow(problem)) == null){
            maxDeep++;
        }
        return solution;
    }

    @Override
    public boolean keepSearching(SearchingState searchingState) {
        return searchingState.getG()<= maxDeep;
    }

    @Override
    public String toString() {
        return "IterativeDeepeningDepthFirstSearch";
    }
}
