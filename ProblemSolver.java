import Objects.Problem;
import Objects.Solution;
import Searchers.Searcher;

import java.util.concurrent.Callable;

public class ProblemSolver implements Callable<Solution> {
    private Problem problem;
    private Searcher searcher;

    public ProblemSolver(Problem problem, Searcher searcher) {
        this.problem = problem;
        this.searcher = searcher;
    }

    @Override
    public Solution call() {
        return searcher.solve(problem);
    }
}
