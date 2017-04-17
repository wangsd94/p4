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
    /** Returns the start value (must be Comparable<T>) of the interval. */
    public T getStart() {
    	return start;
    }

    @Override
    /** Returns the end value (must be Comparable<T>) of the interval. */
    public T getEnd() {
    	return end;
    }

    @Override
    /** Returns the label for the interval. */
    public String getLabel() {
    	return label;
    }

    @Override
    /**
	 * Return true if this interval overlaps with the other interval. 
	 * 
	 * <p>Note: two intervals [a, b], [c, d] will NOT overlap if either b &lt; c or d
	 * &lt; a. </p>
	 * 
	 * <p>In all other cases, they will overlap.</p>
	 * 
	 * @param other target interval to compare for overlap
	 * @return true if it overlaps, false otherwise.
	 * @throws IllegalArgumentException
	 *             if the other interval is null.
	 */
    public boolean overlaps(IntervalADT<T> other) {
    	return other.getStart().compareTo(this.end) < 0 &&
    			other.getEnd().compareTo(this.start) >0  ;
    }

    @Override
    /**
	 * Returns true if given point lies inside the interval.
	 * 
	 * @param point
	 *            to search
	 * @return true if it contains the point
	 */
    public boolean contains(T point) {
    	return start.compareTo(point) < 0 &&
    			end.compareTo(point) > 0;
    }

    @Override
    /**
	 * Compares this interval with the other and return a negative value 
	 * if this interval comes before the "other" interval.  Intervals 
	 * are compared first on their start time.  The end time is only considered
	 * if the start time is the same.
	 * 
	 * <p>For example, if start times are different:</p>
	 * 
	 * <pre>
	 * [0,1] compared to [2,3]: returns -1 because 0 is before 2
	 * [2,3] compared to [0,1]: return 1 because 2 is after 0
	 * [0,4] compared to [2,3]: return -1 because 0 is before 2
	 * [2,3] compared to [0,4]: return 1 because 2 is after 0
	 * [0,3] compared to [2,4]: return -1 because 0 is before 2
	 * [2,4] compared to [0,3]: return 1 because 2 is after 0
	 * </pre>
	 * 
	 * <p>If start times are the same, compare based on end time:</p>
	 * <pre>
	 * [0,3] compared to [0,4]: return -1 because start is same and 3 is before 4
	 * [0,4] compared to [0,3]: return 1 because start is same and 4 is after 3</pre>
	 * 
	 * <p>If start times and end times are same, return 0</p>
	 * <pre>[0,4] compared to [0,4]: return 0</pre>
	 *
	 * @param other
	 *            the second interval to which compare this interval with
	 *            
	 * @return negative if this interval's comes before the other interval, 
	 * positive if this interval comes after the other interval,
	 * and 0 if the intervals are the same.  See above for details.
	 */
    public int compareTo(IntervalADT<T> other) {
    	if(start.compareTo(other.getStart()) == 0){
    		return end.compareTo(other.getEnd());
    	}else{
    		return start.compareTo(other.getStart());
    	}
    }
    /**
	 * Returns a specific string representation of the interval. It must return
	 * the interval in this form.
	 * 
	 *  <p>For example: If the interval's label is p1 and the start is 24 and the end is 45,
	 *  then the string returned is:</p>
	 *  
	 *  <pre>p1 [24, 45]</pre>
	 */
    public String toString(){
    	return label+" ["+ start.toString()+", "+ end.toString()+ "]";
    }

}
