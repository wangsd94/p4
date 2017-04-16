// Semester:         CS367 Spring 2016 
// PROJECT:          p4 Schedule Planner
// FILE:             Interval.java
//
// TEAM:    Team 18a, Eighteen
// Authors: 
// Author1: Eric Schirtzinger, schirtzinger@wisc.edu, schirtzinger, Lec 001
// Author2: Erika Stroik, emstroik@wisc.edu, emstroik, Lec 001
// Author3: Shaodong Wang ,swang647@wisc.edu,swang647,Lec 001
//
public class Interval<T extends Comparable<T>> implements IntervalADT<T> {

	private T start;
	private T end;
	private String label;

    public Interval(T start, T end, String label) {
    	this.start = start;
    	this.end = end;
    	this.label = label;
    }

    @Override
    public T getStart() {
    	return start;
    }

    @Override
    public T getEnd() {
    	return end;
    }

    @Override
    public String getLabel() {
    	return label;
    }

    @Override
    public boolean overlaps(IntervalADT<T> other) {
    	return other.getStart().compareTo(this.end) < 0 &&
    			other.getEnd().compareTo(this.start) >0  ;
    }

    @Override
    public boolean contains(T point) {
    	return start.compareTo(point) < 0 &&
    			end.compareTo(point) > 0;
    }

    @Override
    public int compareTo(IntervalADT<T> other) {
    	if(start.compareTo(other.getStart()) == 0){
    		return end.compareTo(other.getEnd());
    	}else{
    		return start.compareTo(other.getStart());
    	}
    }
    public String toString(){
    	return label+" ["+ start.toString()+", "+ end.toString()+ "]";
    }

}
