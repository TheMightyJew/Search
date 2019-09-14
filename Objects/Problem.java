package Objects;

public class Problem {
    private State startState;
    private State goalState;
    private double cheapestMove;

    public Problem(State startState, State goalState) {
        this.startState = startState;
        this.goalState = goalState;
        this.cheapestMove = 1;
    }

    public Problem(State startState, State goalState, double cheapestMove) {
        this.startState = startState;
        this.goalState = goalState;
        this.cheapestMove = cheapestMove;
    }

    public State getStartState() {
        return startState;
    }

    public State getGoalState() {
        return goalState;
    }

    public double getCheapestMove() {
        return cheapestMove;
    }

}
