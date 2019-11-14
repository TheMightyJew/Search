package Objects.Problems;

import Objects.State;

public interface RandomHashGenerator {
    public long getHash(State state);
    public String toString();
}
