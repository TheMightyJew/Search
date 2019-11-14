package Searchers.BiDirectionalSearch;

public class FractionalMeetInTheMiddle extends MeetInTheMiddle{

    public FractionalMeetInTheMiddle(double fraction) {
        if(fraction>0 && fraction<1){
            this.fraction = fraction;
        }
        else {
            this.fraction = 0.5;
        }
    }

    public void setFraction(double fraction) {
        this.fraction = fraction;
    }

    @Override
    public String toString() {
        return "FractionalMeetInTheMiddle: " + getFraction();
    }
}
