package Searchers.BiDirectionalSearch.BloomFilter;

import Objects.Problems.RandomHashGenerator;
import Objects.State;

import java.util.BitSet;

public class BitHashArray {

    private RandomHashGenerator randomHashGenerator;
    private BitSet array;

    public BitHashArray(RandomHashGenerator randomHashGenerator, int size) {
        this.randomHashGenerator = randomHashGenerator;
        this.array = new BitSet(size);
    }

    public BitHashArray(BitHashArray tempBloomFilter) {
        this.randomHashGenerator = tempBloomFilter.randomHashGenerator;
        this.array = tempBloomFilter.array;
    }

    public boolean contains(State state){
        Object object =  array.get(getID(state));
        boolean contains = array.get(getID(state));
        return contains;
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

    public double spaceTaken() {
        return ((double)array.cardinality()/(double)array.size());
    }

    public BitSet getArray() {
        return array;
    }
}
