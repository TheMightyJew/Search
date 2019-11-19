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
    private final int stateSize;
    private int numberOfIterations = 0;

    public BloomFilterSearch(int numberOfBits,int stateSize) {
        super(0.5);
        this.numberOfBits = numberOfBits;
        this.stateSize = stateSize;
    }

    public Solution solveBiDirectional(Problem problem, int frontThreshold, int backThreshold) throws Exception {
        Solution solution = null;
        State midState;
        midState = getMidState(problem.getStartState(),problem.getGoalState(),frontThreshold,backThreshold);
        if(midState == null){
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
        BitHashArray bloomFilter = null;
        List<State> knownMidStates = new ArrayList<>();
        boolean firstRun = true;
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
            BitHashArray tempBloomFilter = null;
            Stack<SearchingState> stack = new Stack<>();
            SearchingState startSearchingState = new SearchingState(currentStartState,currentGoalState);
            //search
            stack.push(startSearchingState);
            while (stack.empty()==false){
                SearchingState current = stack.pop();
                if(current.getG() == threshold){
                    if(!listReady) {
                        if(firstRun || bloomFilter.contains(current.getState())){
                            if(!outOfSpace){// not using bloomfilter yet
                                if(statesCapacity()-1 > knownMidStates.size()){
                                    if(knownMidStates.contains(current.getState())==false){
                                        knownMidStates.add(current.getState());
                                    }
                                }
                                else {
                                    outOfSpace = true;
                                    tempBloomFilter = new BitHashArray(currentStartState.getRandomHashGenerator(),numberOfBits);
                                    tempBloomFilter.add(current.getState());
                                    for(State state:knownMidStates){
                                        tempBloomFilter.add(state);
                                    }
                                    knownMidStates.clear();
                                }
                            }
                            else { // already using bloomfilter
                                tempBloomFilter.add(current.getState());
                                if(tempBloomFilter.isFull()){
                                    throw new Exception("Number of bits is not enough for the bloomfilter");
                                }
                            }
                        }
                    }
                    else{
                        if (knownMidStates.contains(current.getState())) {
                            return current.getState();
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
            if(listReady == true){
                return null;
            }
            else{
                if(outOfSpace == false){
                    listReady = true;
                }
                else{
                    listReady = false;
                }
            }
            if(tempBloomFilter != null){
                bloomFilter = new BitHashArray(tempBloomFilter);
            }
            firstRun = false;
            outOfSpace = false;
        }
        while (true);
    }


    @Override
    public String toString() {
        return "BloomFilterSearch";
    }

    private int statesCapacity(){
        return numberOfBits/stateSize;
    }
}
