package Searchers.BiDirectionalSearch;

import Objects.Comparator.Gcomparator;
import Objects.Problem;
import Objects.SearchingState;
import Objects.Solution;
import Objects.State;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class MeetInTheMiddle extends BiDirectionalSearch {

    public MeetInTheMiddle() {
        super(0.5);
    }

    public Solution solveBiDirectional(Problem problem, int frontThreshold, int backThreshold) {
        Solution solution = null;
        ArrayList<SearchingState> frontSet = new ArrayList<>();
        ArrayList<SearchingState> backSet = new ArrayList<>();
        //not concurrent
        frontSet.addAll(getThresholdList(problem.getStartState(),problem.getGoalState(),frontThreshold));
        backSet.addAll(getThresholdList(problem.getGoalState(),problem.getStartState(),backThreshold));
        /*Semaphore semaphore = new Semaphore(0);
        try {
            searchCallable frontSearch = new searchCallable(frontSet,problem.getStartState(),problem.getGoalState(),frontThreshold,semaphore);
            frontSearch.start();
            searchCallable backSearch = new searchCallable(backSet,problem.getGoalState(),problem.getStartState(),backThreshold,semaphore);
            backSearch.start();
            semaphore.acquire(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        for (SearchingState frontState:frontSet){
            if(backSet.contains(frontState)){
                for (SearchingState backState:backSet){
                    if(backState.equals(frontState)){
                        solution = buildSolution(frontState,backState);
                        return solution;
                    }
                }
            }
        }
        return solution;
    }

    private HashSet<SearchingState> getThresholdList(State startState, State goalState, int Threshold) {
        Queue<SearchingState> openListQ = new PriorityQueue<>(new Gcomparator());
        HashSet<SearchingState> thresholdList = new HashSet<>();
        HashSet<State> closeList = new HashSet<>();
        SearchingState startSearchingState = new SearchingState(startState,goalState);
        openListQ.add(startSearchingState);
        while (openListQ.isEmpty()==false){
            SearchingState current = openListQ.poll();
            if(closeList.contains(current.getState())){
                continue;
            }
            else {
                closeList.add(current.getState());
            }
            if(current.getG()>Threshold){
                return thresholdList;
            }
            else if(current.getG() == Threshold){
                thresholdList.add(current);
            }
            addNeighbors(current,closeList,openListQ);
        }
        return thresholdList;
    }


    private Solution buildSolution(SearchingState forwardMatch, SearchingState backwardMatch) {
        Solution solution = new Solution(forwardMatch,backwardMatch,getTime(),toString());
        return solution;
    }

    @Override
    public String toString() {
        return "MeetInTheMiddle";
    }

    public double getFraction() {
        return fraction;
    }

    private class searchCallable extends Thread {

        private ArrayList<SearchingState> set;
        private Objects.State startState;
        private Objects.State goalState;
        private int threshold;
        private Semaphore semaphore;

        public searchCallable(ArrayList<SearchingState> set, Objects.State startState, Objects.State goalState, int threshold, Semaphore semaphore) {
            this.set = set;
            this.startState = startState;
            this.goalState = goalState;
            this.threshold = threshold;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            set.addAll(getThresholdList(startState,goalState,threshold));
            semaphore.release();
        }
    }
}
