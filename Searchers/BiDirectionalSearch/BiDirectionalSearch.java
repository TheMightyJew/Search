package Searchers.BiDirectionalSearch;

import Objects.Problem;
import Objects.Solution;
import Searchers.BestFirstSearch.BestFirstSearch;

public abstract class BiDirectionalSearch extends BestFirstSearch {

    double fraction;

    public BiDirectionalSearch(double fraction) {
        this.fraction = fraction;
    }

    @Override
    public Solution solveNow(Problem problem) {
        double heuristic = problem.getStartState().getHeuristic(problem.getGoalState());
        int frontThreshold = ((int)(heuristic* fraction));
        int backThreshold = ((int)(Math.floor(heuristic-frontThreshold)));
        int cheapestMove = ((int)(problem.getCheapestMove()));
        while(true){
            Solution solution = solveBiDirectional(problem,frontThreshold,backThreshold);
            if(solution == null){
                frontThreshold += cheapestMove;
                solution = solveBiDirectional(problem,frontThreshold,backThreshold);
                if(solution == null){
                    backThreshold += cheapestMove;
                    solution = solveBiDirectional(problem,frontThreshold,backThreshold);
                }
            }
            if(solution != null){
                return solution;
            }
        }
    }

    public abstract Solution solveBiDirectional(Problem problem, int frontThreshold, int backThreshold);

}

