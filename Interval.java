
public class Interval<T extends Comparable<T>> implements IntervalADT<T> {

    // TODO declare any needed data members
	private T start;
	private T end;
	private String label;

    public Interval(T start, T end, String label) {
        // TODO Auto-generated constructor stub
    	this.start = start;
    	this.end = end;
    	this.label = label;
    }

    @Override
    public T getStart() {
        // TODO Auto-generated method stub
    	return start;
    }

    @Override
    public T getEnd() {
        // TODO Auto-generated method stub
    	return end;
    }

    @Override
    public String getLabel() {
        // TODO Auto-generated method stub
    	return label;
    }

    @Override
    public boolean overlaps(IntervalADT<T> other) {
        // TODO Auto-generated method stub
    	return other.getStart().compareTo(this.end) < 0 &&
    			other.getEnd().compareTo(this.start) >0  ;
    }

    @Override
    public boolean contains(T point) {
        // TODO Auto-generated method stub
    	return start.compareTo(point) < 0 &&
    			end.compareTo(point) > 0;
    }

    @Override
    public int compareTo(IntervalADT<T> other) {
        // TODO Auto-generated method stub
    	if(start.compareTo(other.getStart()) == 0){
    		return end.compareTo(other.getEnd());
    	}else{
    		return start.compareTo(other.getStart());
    	}
    }

}
