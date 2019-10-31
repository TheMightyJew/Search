package Objects.Comparator;

import Objects.SearchingState;
import Objects.State;

public class Fcomparator extends Hcomparator {

    public Fcomparator(State goalState) {
        super(goalState);
    }


    @Override
    public int compare(SearchingState o1, SearchingState o2) {
        int ans = 0;
        Double f1 = o1.getF();
        Double f2 = o2.getF();
        if(f1 > f2){
            ans = -1;
        }
        else if(f1 < f2 ){
            ans = 1;
        }
        else{
            Hcomparator hcomparator = new Hcomparator(goalState);
            return hcomparator.compare(o1,o2);
        }
        return (-1)*ans;
    }
}
