package Objects;

import Objects.Problems.Result;

public class Failure extends Result {
    private String  exception;

    public Failure(int time, String solver) {
        super(time, solver);
    }

    public Failure(int time, String solver,String exception) {
        super(time, solver);
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "Failure{" + "\n" +
                "\t" + "Type= " + exception + ",\n" +
                "\t" + "Time= " + time + " ms" + ",\n" +
                "\t" + "Solver= " + solver + "\n" +
                '}';
    }
}
