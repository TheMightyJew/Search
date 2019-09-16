package Searchers.BiDirectionalSearch;

import Objects.Problem;
import Objects.Solution;
import Searchers.BestFirstSearch.BestFirstSearch;

public abstract class BiDirectionalSearch extends BestFirstSearch {

    double fraction;

    public BiDirectionalSearch(double fraction) {
        if(fraction<0 || fraction>1){
            this.fraction=0.5;
            System.out.println("fraction must be a number between 0 to 1");
        }
        else {
            this.fraction = Math.max(fraction,1-fraction);
        }
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

