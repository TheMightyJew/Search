package Objects.Problems;

public abstract class Result {
    protected int time;
    protected String solver;

    public Result(int time, String solver) {
        this.time = time;
        this.solver = solver;
    }

    public int getTime() {
        return time;
    }

    public String getSolver() {
        return solver;
    }

    public abstract String toString();
}
