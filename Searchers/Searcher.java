package Searchers;

import Objects.*;

public abstract class Searcher {
    protected double time;

    public Solution solve(Problem problem){
        time = System.currentTimeMillis();
        return solveNow(problem);
    }

    protected abstract Solution solveNow(Problem problem);

    public abstract String toString();

    protected int getTime(){
        int deltaTime = (int)(System.currentTimeMillis() - time);
        return deltaTime;
    }
}
