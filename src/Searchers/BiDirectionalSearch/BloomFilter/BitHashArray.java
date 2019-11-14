package Searchers.BiDirectionalSearch.BloomFilter;

import Objects.Problems.RandomHashGenerator;
import Objects.State;

import java.util.BitSet;

public class BitHashArray {

    private RandomHashGenerator randomHashGenerator;
    private BitSet array;
    private int insertions = 0;

    public BitHashArray(RandomHashGenerator randomHashGenerator, int size) {
        this.randomHashGenerator = randomHashGenerator;
        this.array = new BitSet(size);
    }

    public boolean contains(State state){
        return array.get(getID(state));
    }

    public void add(State state){
        array.set(getID(state));
    }

    public boolean isFull(){
        return array.cardinality() == array.size();
    }

    public int getID(State state){
        return (int)(randomHashGenerator.getHash(state)%array.size());
    }
}
