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

    public Solution(SearchingState forwardSearchingState, SearchingState backwardSearchingState, int time, String solver) {
        super(time,solver);
        this.cost = forwardSearchingState.getG()+backwardSearchingState.getG();
        solution = new LinkedList<>();
        buildSolutionBiDirectional(forwardSearchingState,backwardSearchingState);
    }

    public Solution(Solution forwardSolution, Solution backwardSolution, int time, String solver) {
        super(time,solver);
        this.cost = forwardSolution.cost+backwardSolution.cost;
        solution = listOutOf2(forwardSolution.solution,backwardSolution.solution);
    }

    private LinkedList<State> listOutOf2(LinkedList<State> listForward, LinkedList<State> listBackward) {
        LinkedList<State> list = new LinkedList<>(listForward);
        for (int i=listBackward.size()-2;i>=0;i--){
            list.add(listBackward.get(i));
        }
        return list;
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
                "\t" + "Solution= " + solToString() + ",\n" +
                "\t" + "Cost= " + cost + ",\n" +
                "\t" + "Time= " + time + " ms" + ",\n" +
                "\t" + "Solver= " + solver + "\n" +
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
