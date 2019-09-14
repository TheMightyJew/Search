package Objects;

import Objects.Problems.Result;

import java.util.LinkedList;

public class Solution extends Result {
    private LinkedList<State> solution;
    private double cost;

    public Solution(LinkedList<State> solution, double cost, int time, String solver) {
        super(time,solver);
        this.solution = solution;
        this.cost = cost;
    }

    public Solution(SearchingState searchingState, int time, String solver){
        super(time,solver);
        this.cost = searchingState.getG();
        solution = new LinkedList<>();
        buildSolutionForward(searchingState);
    }

    private void buildSolutionForward(SearchingState searchingState){
        SearchingState tmp = searchingState;
        while (tmp != null){
            this.solution.addFirst(tmp.getState());
            tmp = tmp.getPrevieusState();
        }
    }

    private void buildSolutionBackward(SearchingState searchingState){
        SearchingState tmp = searchingState;
        while (tmp != null){
            this.solution.addLast(tmp.getState());
            tmp = tmp.getPrevieusState();
        }
    }

    public Solution(SearchingState forwardSearchingState, SearchingState backwardSearchingState, int time, String solver) {
        super(time,solver);
        this.cost = forwardSearchingState.getG()+backwardSearchingState.getG();
        solution = new LinkedList<>();
        buildSolutionBiDirectional(forwardSearchingState,backwardSearchingState);
    }

    private void buildSolutionBiDirectional(SearchingState forwardSearchingState, SearchingState backwardSearchingState) {
        buildSolutionForward(forwardSearchingState);
        SearchingState searchingState = backwardSearchingState.getPrevieusState();
        if(searchingState!=null){
            buildSolutionBackward(searchingState);
        }
    }

    public double getCost() {
        return cost;
    }

    public int getSize(){
        return solution.size();
    }

    @Override
    public String toString() {
        return "Solution{" + "\n" +
                "\t" + "solution= " + solToString() + ",\n" +
                "\t" + "cost= " + cost + ",\n" +
                "\t" + "time= " + time + " ms" + ",\n" +
                "\t" + "solver= " + solver + "\n" +
                '}';
    }

    private String solToString(){
        String next = " -> ";
        String str = "";
        for (State state:solution){
            str = str + state.toString() + next;
        }
        str = str.substring(0,str.length()-next.length());
        return str;
    }

}
