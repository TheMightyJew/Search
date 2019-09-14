package Objects;

import java.util.LinkedList;

public abstract class State {
    protected boolean heuristics;

    public State(boolean heuristics) {
        this.heuristics = heuristics;
    }

    public void setHeuristics(boolean heuristics) {
        this.heuristics = heuristics;
    }

    public double getHeuristic(State state){
        if(heuristics==false){
            return 0;
        }
        else {
            return calculateHeuristic(state);
        }
    }

    public  abstract double calculateHeuristic(State state);
    public  abstract LinkedList<StatesMove> getMoves();
    public  abstract String toString();

}
