package Objects.Problems.Puncake;

import Objects.Problems.RandomHashGenerator;
import Objects.State;
import Objects.StatesMove;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class PancakesState extends State {
    private int[] pancakes;

    public PancakesState(boolean heuristics) {
        super(heuristics);
        randomPuncakes(10);
    }

    public PancakesState(boolean heuristics,int[] pancakes) {
        super(heuristics);
        this.pancakes = pancakes;
    }

    public PancakesState(boolean heuristics,int numberOfPancakes) {
        super(heuristics);
        try{
            if(numberOfPancakes<1){
                throw new Exception("Number of pancakes has to be at list 1");
            }
            else{
                randomPuncakes(numberOfPancakes);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private int calculateRepresentative(){
        int ans = 0;
        for (int i=0;i<pancakes.length;i++){
            ans += Math.pow(pancakes.length+1,i)*pancakes[i];
        }
        return ans;
    }

    private void randomPuncakes(int numberOfPancakes){
        resetPancakes(numberOfPancakes);
        for(int i=numberOfPancakes;i>0;i--){
            int random = (int)(Math.random()*i);
            for(int j=0;j<pancakes.length;j++){
                if(pancakes[j]==0){
                    if(random==0){
                        pancakes[j]=i;
                        break;
                    }
                    else{
                        random--;
                    }
                }
            }
        }
    }

    private void resetPancakes(int numberOfPancakes) {
        pancakes = new int[numberOfPancakes];
        for(int i=0;i<pancakes.length;i++){
            pancakes[i]=0;
        }
    }

    @Override
    public double calculateHeuristic(State state) {
        double heuristic = 0;
        try{
            if(state instanceof PancakesState == false){
                throw new Exception("Cant compute heuristic to a different state.");
            }
            else {
                PancakesState otherPuncakes = ((PancakesState)state);
                if(getSize()!=otherPuncakes.getSize()){
                    throw new Exception("The Puncakes States are of a different problem.");
                }
                else{
                    // TODO: 01-Aug-19 generic heuristic that uses otherPuncakes
                    int i=0;
                    for(i=0;i<pancakes.length-1;i++){
                        if(Math.abs(pancakes[i+1]-pancakes[i])>1){
                            heuristic++;
                        }
                    }
                    if(pancakes[i]!=pancakes.length){
                        heuristic++;
                    }
                    return heuristic;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return Double.MAX_VALUE;
        }


    }

    @Override
    public LinkedList<StatesMove> getMoves() {
        LinkedList<StatesMove> states = new LinkedList<>();
        for(int i=pancakes.length-1;i>0;i--){
            int[] array = pancakes.clone();
            array = flip(array,i);
            PancakesState newState = new PancakesState(this.heuristics,array);
            states.add(new StatesMove(this,newState,1));
        }
        return states;
    }

    private int[] flip(int[] array, int toFlip) {
        int tmp;
        for (int index=0;index<=toFlip/2;index++){
            tmp = array[index];
            array[index] = array[toFlip-index];
            array[toFlip-index] = tmp;
        }
        return array;
    }

    @Override
    public String toString() {
        String str = "[";
        for (int i=0;i<pancakes.length;i++){
            str = str+pancakes[i]+',';
        }
        str = str.substring(0,str.length()-1)+']';
        return str;
    }

    public int getSize(){
        if(pancakes != null){
            return pancakes.length;
        }
        else {
            return -1;
        }
    }

    public PancakesState getPerfectState(){
        int[] array = new int[pancakes.length];
        for(int i=0;i<array.length;i++){
            array[i]=i+1;
        }
        return new PancakesState(this.heuristics,array);
    }

    public int[] getPancakes() {
        return pancakes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PancakesState that = (PancakesState) o;
        int i=0;
        for(int number:that.getPancakes()){
            if(number!=pancakes[i]){
                return false;
            }
            i++;
        }
        if(i!=pancakes.length){
            return false;
        }
        return true;
    }

    public RandomHashGenerator getRandomHashGenerator(){
        return new PancakeRandomHashGenerator(getSize());
    }

    private class PancakeRandomHashGenerator implements RandomHashGenerator {
        private Set<Integer> set = new HashSet<>();

        public PancakeRandomHashGenerator(int max) {
            /*int num;
            int constantMax = 17;
            if(max>constantMax){
                num=constantMax;
                while (num>0){
                    int random = (int)(Math.random()*max);
                    if(set.contains(random)==false){
                        set.add(random);
                        num--;
                    }
                }
            }
            else{
                for (int i=0;i<max;i++){
                    set.add(i);
                }
            }*/
            int counter = 0;
            while (counter < max * 0.6){
                int random = (int)(Math.random()*max);
                if(set.contains(random)==false){
                    set.add(random);
                    counter++;
                }
            }
        }

        @Override
        public long getHash(State state) {
            if(state instanceof PancakesState == false)
                return Integer.parseInt(null);
            int[] pancakes = ((PancakesState) state).getPancakes();
            /*long hash=0;
            for(int num:set){
                hash = hash*10 + pancakes[num];
            }
            return hash;*/
            String str = "";
            for(int num:set){
                str += pancakes[num];
            }
            int hash = str.hashCode();
            return Math.abs(hash);
        }

        @Override
        public String toString() {
            String ans="";
            for(Integer num:set){
                ans+=num+" ";
            }
            return ans;
        }
    }

}
