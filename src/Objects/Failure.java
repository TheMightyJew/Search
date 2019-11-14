package Objects;

import Objects.Problems.Result;

public class Failure extends Result {

    public Failure(int time, String solver) {
        super(time, solver);
    }

    @Override
    public String toString() {
        return "Failure{" + "\n" +
                "\t" + "time= " + time + " ms" + ",\n" +
                "\t" + "solver= " + solver + "\n" +
                '}';
    }
}
