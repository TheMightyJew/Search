package Objects.Comparator;

import Objects.SearchingState;
import Objects.State;

public class Hcomparator extends SearcingStateComparator {

    protected State goalState;

    public Hcomparator(State goalState) {
        this.goalState = goalState;
    }

    public void setGoalState(State goalState) {
        this.goalState = goalState;
    }

    @Override
    public int compare(SearchingState o1, SearchingState o2) {
        Double h1 = o1.getH();
        Double h2 = o2.getH();
        return h1.compareTo(h2);
    }
}
