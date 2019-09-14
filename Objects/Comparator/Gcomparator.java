package Objects.Comparator;

import Objects.SearchingState;

public class Gcomparator extends SearcingStateComparator{

    @Override
    public int compare(SearchingState o1, SearchingState o2) {
        Double g1 = o1.getG();
        Double g2 = o2.getG();
        // TODO: 03-Aug-19 check if need to multiply by -1 
        return g1.compareTo(g2);
    }
}
