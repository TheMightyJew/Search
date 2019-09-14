package Searchers.DepthFirstSearch;

import Objects.SearchingState;

public class IterativeDeepeningAstar extends IterativeDeepeningDepthFirstSearch{

    @Override
    public boolean keepSearching(SearchingState searchingState) {
        return searchingState.getF()<= maxDeep;
    }

    @Override
    public String toString() {
        return "IterativeDeepeningAstar";
    }
}
