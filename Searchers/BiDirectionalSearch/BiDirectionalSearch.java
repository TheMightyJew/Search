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
        boolean add2Front = true;
        while(true){
            Solution solution = null;
            try {
                solution = solveBiDirectional(problem,frontThreshold,backThreshold);
            }
            catch (Exception e){
                e.printStackTrace();
                return null;
            }
            if(solution != null){
                return solution;
            }
            else{
                if(add2Front){
                    frontThreshold += cheapestMove;
                }
                else{
                    backThreshold += cheapestMove;
                }
                add2Front = !add2Front;            }
        }
    }

    public abstract Solution solveBiDirectional(Problem problem, int frontThreshold, int backThreshold) throws Exception;

}

