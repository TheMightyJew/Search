package Searchers.BiDirectionalSearch.BloomFilter;

import Objects.*;
import Searchers.BiDirectionalSearch.BiDirectionalSearch;
import Searchers.BiDirectionalSearch.KnownMidSearch;
import Searchers.DepthFirstSearch.IterativeDeepeningAstar;

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
        BloomFilter frontBloom = null;
        frontBloom = getThresholdList(problem.getStartState(),problem.getGoalState(),frontThreshold);
        State midState = findMidState(problem.getGoalState(),problem.getStartState(),backThreshold,frontBloom);
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

        /*backSet = getThresholdList(problem.getGoalState(),problem.getStartState(),backThreshold);
        for (SearchingState frontState:frontSet){
            if(backSet.contains(frontState)){
                for (SearchingState backState:backSet){
                    if(backState.hashCode()==frontState.hashCode()){
                        solution = buildSolution(frontState,backState);
                    }
                }
            }
        }
        return solution;*/
    }

    private BloomFilter getThresholdList(State startState, State goalState, int Threshold) {
        //int expectedElements = (int)(numberOfBits * 0.9);//
        int expectedElements = 5;//
        // TODO: 15/09/2019 only for test
        BloomFilter bloomFilter = new BloomFilter(expectedElements,numberOfBits);
        SearchingState startSearchingState = new SearchingState(startState,goalState);

        Stack<SearchingState> stack = new Stack<>();
        stack.push(startSearchingState);
        while (stack.empty()==false){
            SearchingState current = stack.pop();
            if(current.getG() == Threshold){
                if(bloomFilter.isFull()){
                    return null;
                }
                else{
                    bloomFilter.add(current.getState());
                }
            }
            else if(current.getG() < Threshold){
                for(StatesMove statesMove:current.getState().getMoves()){
                    SearchingState newState = new SearchingState(statesMove.getToState(),current.getGoalState(),current,current.getG()+statesMove.getCost());
                    stack.add(newState);
                }
            }

        }
        //with memory ריצה לרוחב
        /*Queue<SearchingState> openList = new PriorityQueue<>(new Gcomparator());
        HashSet<SearchingState> thresholdList = new HashSet<>();
        HashSet<State> closeList = new HashSet<>();
        openList.add(startSearchingState);
        while (openList.isEmpty()==false){
            SearchingState current = openList.poll();
            if(closeList.contains(current.getState())){
                continue;
            }
            else {
                closeList.add(current.getState());
            }
            if(current.getG()>Threshold){
                return bloomFilter;
            }
            for (StatesMove move:current.getState().getMoves()){
                if(closeList.contains(move.getToState())==false){
                    double newG = current.getG()+move.getCost();
                    if(newG == Threshold){
                        bloomFilter.add(current);
                        counter++;
                    }
                    else{
                        if(numberOfBits/stateSize > stateSize*(openList.size()+1)){
                            SearchingState newState = new SearchingState(move.getToState(),current.getGoalState(),current,newG);
                            openList.add(newState);
                        }
                        else{
                            return null;
                        }
                    }
                }
            }
        }*/
        return bloomFilter;
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
