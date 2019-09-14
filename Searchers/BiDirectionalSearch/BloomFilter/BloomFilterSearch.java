/*package Searchers.BiDirectionalSearch.BloomFilter;

import Objects.*;
import Objects.Comparator.Gcomparator;
import Searchers.BiDirectionalSearch.BiDirectionalSearch;
import javafx.scene.effect.Bloom;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;

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
        backSet = getThresholdList(problem.getGoalState(),problem.getStartState(),backThreshold);
        for (SearchingState frontState:frontSet){
            if(backSet.contains(frontState)){
                for (SearchingState backState:backSet){
                    if(backState.hashCode()==frontState.hashCode()){
                        solution = buildSolution(frontState,backState);
                    }
                }
            }
        }
        return solution;
    }

    private BloomFilter getThresholdList(State startState, State goalState, int Threshold) {
        int expectedElements = (int)(numberOfBits * 0.9);
        BloomFilter bloomFilter = new BloomFilter(expectedElements,numberOfBits);
        Queue<SearchingState> openList = new PriorityQueue<>(new Gcomparator());
        HashSet<SearchingState> thresholdList = new HashSet<>();
        HashSet<State> closeList = new HashSet<>();
        SearchingState startSearchingState = new SearchingState(startState,goalState);
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
        }
        return bloomFilter;
    }

    @Override
    public String toString() {
        return "BloomFilterSearch";
    }
}*/
