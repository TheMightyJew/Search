package Searchers.BiDirectionalSearch.BloomFilter;

import Objects.*;
import Searchers.BiDirectionalSearch.BiDirectionalSearch;
import Searchers.BiDirectionalSearch.KnownMidSearch;
import Searchers.DepthFirstSearch.IterativeDeepeningAstar;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BloomFilterSearch extends BiDirectionalSearch {
    private int numberOfBits;
    private int stateSize;
    private int numberOfIterations = 0;

    public BloomFilterSearch(int numberOfBits, int stateSize) {
        super(0.5);
        this.numberOfBits = numberOfBits;
        this.stateSize = stateSize;
    }

    public Solution solveBiDirectional(Problem problem, int frontThreshold, int backThreshold) throws Exception {
        Solution solution = null;
        State midState;
        midState = getMidState(problem.getStartState(),problem.getGoalState(),frontThreshold,backThreshold);
        if(midState==null){
            return null;
        }
        else {
            IterativeDeepeningAstar iterativeDeepeningAstar = new IterativeDeepeningAstar();
            KnownMidSearch knownMidSearch = new KnownMidSearch(iterativeDeepeningAstar,midState);
            solution = knownMidSearch.solve(problem);
            solution.setSolver(toString());
            solution.setTime(getTime());
            return solution;
        }
    }

    private State getMidState(State startState, State goalState, int frontThreshold,int backThreshold) throws Exception {
        // TODO: 15/09/2019 only for test
        //int expectedElements = (int)(numberOfBits * 0.9);//
        int expectedElements = 5;//
        BloomFilter bloomFilter = new BloomFilter(expectedElements,numberOfBits);
        List<State> knownMidStates = new ArrayList<>();
        boolean outOfSpace = false;
        boolean listReady = false;
        boolean frontSearch = true;
        State currentStartState;
        State currentGoalState;
        int threshold;
        do {
            //initiate for search
            if(frontSearch){
                currentStartState = startState;
                currentGoalState = goalState;
                threshold = frontThreshold;
            }
            else{
                currentStartState = goalState;
                currentGoalState = startState;
                threshold = backThreshold;
            }
            SearchingState startSearchingState = new SearchingState(currentStartState,currentGoalState);
            Stack<SearchingState> stack = new Stack<>();
            //search
            stack.push(startSearchingState);
            while (stack.empty()==false){
                SearchingState current = stack.pop();
                if(current.getG() == threshold){
                    if(listReady){
                        if(knownMidStates.contains(current.getState())){
                            return current.getState();
                        }
                    }
                    else if(outOfSpace){// already use bloomfilter
                        if(bloomFilter.isFull()){
                            throw new Exception("Number of bits is not enough for the bloomfilter");
                        }
                        else{
                            bloomFilter.add(current.getState());
                        }
                    }
                    else { // not yet using bloomfilter
                        if((numberOfBits/stateSize)-1 > knownMidStates.size()){
                            if(knownMidStates.contains(current.getState())==false){
                                knownMidStates.add(current.getState());
                            }
                        }
                        else {
                            outOfSpace = true;
                            for(State state:knownMidStates){
                                bloomFilter.add(state);
                            }
                            knownMidStates.clear();
                        }
                    }
                }
                else if(current.getG() < threshold){
                    for(StatesMove statesMove:current.getState().getMoves()){
                        SearchingState newState = new SearchingState(statesMove.getToState(),current.getGoalState(),current,current.getG()+statesMove.getCost());
                        stack.add(newState);
                    }
                }
            }
            numberOfIterations++;
            frontSearch = !frontSearch;
            if(outOfSpace==false){
                if(listReady==true){
                    return null;
                }
                else {
                    listReady = true;
                }
            }
        }
        while (true);
    }

    @Override
    public String toString() {
        return "BloomFilterSearch";
    }
}
