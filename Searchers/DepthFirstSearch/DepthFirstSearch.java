package Searchers.DepthFirstSearch;

import Objects.*;
import Searchers.Searcher;

import java.util.Stack;

public abstract class DepthFirstSearch extends Searcher {

    @Override
    public Solution solveNow(Problem problem) {
        Solution solution = null;
        SearchingState startState = new SearchingState(problem.getStartState(),problem.getGoalState());
        State goalState = problem.getGoalState();
        Stack<SearchingState> stack = new Stack<>();
        stack.push(startState);
        while (stack.empty()==false){
            SearchingState current = stack.pop();
            if(keepSearching(current)==false){
                continue;
            }
            if(current.getState().equals(goalState)){
                solution = new Solution(current,getTime(),toString());
                return solution;
            }
            for(StatesMove statesMove:current.getState().getMoves()){
                SearchingState newState = new SearchingState(statesMove.getToState(),current.getGoalState(),current,current.getG()+statesMove.getCost());
                stack.add(newState);
            }
        }
        return solution;
    }

    public boolean keepSearching(SearchingState searchingState){
        return true;
    }

    @Override
    public String toString() {
        return "DepthFirstSearch";
    }
}
