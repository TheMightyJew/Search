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
    private int counter;

    public BloomFilterSearch(int numberOfBits, int stateSize) {
        super(0.5);
        this.numberOfBits = numberOfBits;
        this.stateSize = stateSize;
        this.counter = 0;
    }

    public Solution solveBiDirectional(Problem problem, int frontThreshold, int backThreshold) {
        Solution solution = null;
        List<State> knownMidStates;
        knownMidStates = getThresholdList(problem.getStartState(),problem.getGoalState(),frontThreshold,backThreshold);
        // TODO: 9/16/2019  
        State midState= null;
        //midState = findMidState(problem.getGoalState(),problem.getStartState(),backThreshold,frontBloom);
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

    private List<State> getThresholdList(State startState, State goalState, int frontThreshold,int backThreshold) {
        // TODO: 15/09/2019 only for test
        //int expectedElements = (int)(numberOfBits * 0.9);//
        int expectedElements = 5;//
        BloomFilter bloomFilter = new BloomFilter(expectedElements,numberOfBits);
        List<State> knownMidStates = new ArrayList<>();
        boolean outOfSpace = false;
        boolean frontSearch = true;
        do {
            State currentStartState;
            State currentGoalState;
            int threshold;
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
            stack.push(startSearchingState);
            while (stack.empty()==false){
                SearchingState current = stack.pop();
                if(current.getG() == threshold){
                    if(outOfSpace){// already use bloomfilter
                        if(bloomFilter.isFull()){
                            return null;
                        }
                        else{
                            bloomFilter.add(current.getState());
                        }
                    }
                    else { // not yet using bloomfilter
                        if((numberOfBits/stateSize)-1>knownMidStates.size()){
                            knownMidStates.add(current.getState());
                        }
                        else {
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
        }
        while (outOfSpace = true);
        return knownMidStates;
    }

    private State findMidState(State startState, State goalState, int Threshold, BloomFilter bloomFilter) {
        SearchingState startSearchingState = new SearchingState(startState,goalState);
        Stack<SearchingState> stack = new Stack<>();
        stack.push(startSearchingState);
        while (stack.empty()==false){
            SearchingState current = stack.pop();
            if(current.getG() == Threshold){
                if(bloomFilter.contains(current.getState())){
                    return current.getState();
                }
            }
            else if(current.getG() < Threshold){
                for(StatesMove statesMove:current.getState().getMoves()){
                    SearchingState newState = new SearchingState(statesMove.getToState(),current.getGoalState(),current,current.getG()+statesMove.getCost());
                    stack.add(newState);
                }
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return "BloomFilterSearch";
    }
}
