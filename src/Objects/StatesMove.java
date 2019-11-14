package Objects;

public class StatesMove {
    private State fromState;
    private State toState;
    private double cost;

    public StatesMove(State fromState, State toState, double cost) {
        this.fromState = fromState;
        this.toState = toState;
        this.cost = cost;
    }

    public State getFromState() {
        return fromState;
    }

    public State getToState() {
        return toState;
    }

    public double getCost() {
        return cost;
    }
}
