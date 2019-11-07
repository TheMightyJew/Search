package Objects;

import java.util.Objects;

public class SearchingState {
    private State state;
    private State goalState;
    private SearchingState previeusState;
    private double g;

    public SearchingState(State state, State goalState) {
        this.state = state;
        this.goalState = goalState;
        previeusState = null;
        g = 0;
    }

    public SearchingState(State state, State goalState, double g) {
        this.state = state;
        this.goalState = goalState;
        this.g = g;
    }

    public SearchingState(State state, State goalState, SearchingState previeusState, double g) {
        this.state = state;
        this.goalState = goalState;
        this.previeusState = previeusState;
        this.g = g;
    }

    public State getState() {
        return state;
    }

    public SearchingState getPrevieusState() {
        return previeusState;
    }

    public double getG() {
        return g;
    }

    public double getH(){
        return state.getHeuristic(goalState);
    }

    public double getF(){
        return getG()+getH();
    }

    public State getGoalState() {
        return goalState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SearchingState that = (SearchingState) o;
        return Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state);
    }
}
