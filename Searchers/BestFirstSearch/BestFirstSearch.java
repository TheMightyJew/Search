package Searchers.BestFirstSearch;

import Objects.*;
import Searchers.Searcher;

import java.util.HashSet;
import java.util.Queue;

public abstract class BestFirstSearch extends Searcher {

    protected Queue<SearchingState> openList;


    public Solution solveNow(Problem problem){
        Solution solution = null;
        HashSet<State> closeList = new HashSet<>();
        SearchingState startSearchingState = new SearchingState(problem.getStartState(),problem.getGoalState());
        openList.add(startSearchingState);
        while (openList.isEmpty()==false){
            SearchingState current = openList.poll();
            if(closeList.contains(current.getState())){
                continue;
            }
            else {
                closeList.add(current.getState());
            }
            if(current.getState().equals(problem.getGoalState())){
                solution = new Solution(current,getTime(),toString());
                return solution;
            }
            addNeighbors(current,closeList,openList);
        }
        return solution;
    }

    protected void addNeighbors(SearchingState current, HashSet<State> closeList, Queue<SearchingState> openList){
        for (StatesMove move:current.getState().getMoves()){
            if(closeList.contains(move.getToState())==false){
                SearchingState newState = new SearchingState(move.getToState(),current.getGoalState(),current,current.getG()+move.getCost());
                openList.add(newState);
            }
        }
    }


}
