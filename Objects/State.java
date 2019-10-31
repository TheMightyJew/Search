package Objects;

import java.util.LinkedList;

public abstract class State {
    protected boolean heuristics;
    /*protected int lastHash;
    private double randomHash = Math.random();

    public boolean isHeuristics() {
        return heuristics;
    }

    public int getLastHash() {
        return lastHash;
    }

    public void setLastHash(int lastHash) {
        this.lastHash = lastHash;
    }

    public double getRandomHash() {
        return randomHash;
    }

    public void setRandomHash(double randomHash) {
        this.randomHash = randomHash;
    }*/

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
