package Searchers.BiDirectionalSearch;

import Objects.Problem;
import Objects.Solution;
import Objects.State;
import Searchers.Searcher;

public class KnownMidSearch extends Searcher {
    private Searcher searcher;
    private State midState;

    public KnownMidSearch(Searcher searcher, State midState) {
        this.searcher = searcher;
        this.midState = midState;
    }

    @Override
    protected Solution solveNow(Problem problem) {
        Problem[] twoProblems = problem.splitProblemToBiDirectional(midState);
        Solution frontSolution = searcher.solve(twoProblems[0]);
        Solution backSolution = searcher.solve(twoProblems[1]);
        Solution solution = new Solution(frontSolution,backSolution,frontSolution.getTime(),toString());
        return solution;
    }

    @Override
    public String toString() {
        return "KnownMidSearch";
    }
}
