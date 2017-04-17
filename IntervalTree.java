// Semester:         CS367 Spring 2016 
// PROJECT:          p4 Schedule Planner
// FILE:             IntervalTree.java
//
// TEAM:    Team 18a, Eighteen
// Authors: 
// Author1: Eric Schirtzinger, schirtzinger@wisc.edu, schirtzinger, Lec 001
// Author2: Erika Stroik, emstroik@wisc.edu, emstroik, Lec 001
// Author3: Shaodong Wang ,swang647@wisc.edu,swang647,Lec 001
//
import java.util.ArrayList;
import java.util.List;
/**
 * This class is responsible for tracking nodes and 
 * implementing the methods required by the 
 * IntervalTreeADT. 
 * @param <T>
 */
public class IntervalTree<T extends Comparable<T>> implements IntervalTreeADT<T> {
	
	private IntervalNode<T> root;
	// Upon creation, set root to null
	public IntervalTree(){
		root = null;
	}
	// Upon creation, set starting node
	public IntervalTree(IntervalNode<T> root){
		this.root = root;
	}
	@Override
	/** Returns the root node of this IntervalTree. */
	public IntervalNode<T> getRoot() {
		return root;
	}

	@Override
	/**
	 * Inserts an Interval in the tree.
	 * 
	 * @param interval the interval (item) to insert in the tree.
	 * @throws IllegalArgumentException if interval is null or is found 
	 * to be a duplicate of an existing interval in this tree. 
	 */
	public void insert(IntervalADT<T> interval)
					throws IllegalArgumentException {
		root = inserthelper(root, interval);

	}
	/**
	 * helper method of insert.
	 * @param n
	 * @param key
	 * @return IntervalNode<T>
	 * @throws IllegalArgumentException
	 */
	private IntervalNode<T> inserthelper(IntervalNode<T> n, IntervalADT<T> key) 
			throws IllegalArgumentException {
		// Can't add a null interval
		if(key == null) throw new IllegalArgumentException();
		// If you reach the end, return the interval to be added 
		if(n == null) {
			return new IntervalNode<T>(key);
		}
		// Can't add an interval that already exists
		if(n.getInterval().compareTo(key) == 0){
			throw new IllegalArgumentException();
		}
		// If the current node is greater than the interval, move left
		if(n.getInterval().compareTo(key) > 0){
			//add key to the left subtree
			n.setLeftNode(inserthelper(n.getLeftNode(),key));
			//set maxEnd
			if(n.getLeftNode().getMaxEnd().compareTo(n.getMaxEnd()) > 0){
				n.setMaxEnd(n.getLeftNode().getMaxEnd());
			}
			return n;
		}
		else {
			//add key to the right subtree
			n.setRightNode(inserthelper(n.getRightNode(),key));
			//set maxEnd
			if(n.getRightNode().getMaxEnd().compareTo(n.getMaxEnd()) > 0){
				n.setMaxEnd(n.getRightNode().getMaxEnd());
			}
			return n;
		}
	}

	@Override
	/**
	 * Delete the node containing the specified interval in the tree.
	 * Delete operations must also update the maxEnd of interval nodes
	 * that change as a result of deletion.  
	 *  
	 * <p>Tip: call <code>deleteHelper(root)</code> with the root node.</p>
	 * 
	 * @throws IllegalArgumentException if interval is null
	 * @throws IntervalNotFoundException if the interval does not exist.
	 */
	public void delete(IntervalADT<T> interval)
					throws IntervalNotFoundException, IllegalArgumentException {
		root = deleteHelper(root,interval);
	}

	@Override
	/**
	 * helper method of delete
	 */
	public IntervalNode<T> deleteHelper(IntervalNode<T> node,
					IntervalADT<T> interval)
					throws IntervalNotFoundException, IllegalArgumentException {
		// Can't delete null interval
		if(interval == null){
			throw new IllegalArgumentException(); 
		}
		// Went through whole tree, didn't find interval
		if(node == null) {
			throw new IntervalNotFoundException(interval.toString());
		}
		// Found a match
		if(node.getInterval().compareTo(interval) == 0){
			// This is not a leafnode 
			if(node.getRightNode() != null){
				// Set current node to be the in order successor
				IntervalADT<T> smallVal = node.getSuccessor().getInterval();
				node.setInterval(smallVal);
				// delete the value we just used to replace deleted interval
				deleteHelper(node.getRightNode(),smallVal);
				node.setMaxEnd(recalculateMaxEnd(node));
				return node;
			}
			else{
				return node.getLeftNode();
			}
		}
		// If current node is less than interval to be deleted, move right
		if(node.getInterval().compareTo(interval) < 0){
			node.setRightNode(deleteHelper(node.getRightNode(),interval));
			node.setMaxEnd(recalculateMaxEnd(node));
			return node;
		}
		// If current node is greater than interval to be deleted, move left
		else{
			node.setLeftNode(deleteHelper(node.getLeftNode(), interval));
			node.setMaxEnd(recalculateMaxEnd(node));
			return node;
		}
	}

	/**
	 * This method returns a list of all 
	 * intervals found in the tree that overlap 
	 * with the interval being passed in. 
	 */
	@Override
	public List<IntervalADT<T>> findOverlapping(
					IntervalADT<T> interval) {
		// Can't overlap with a null interval
		if(interval == null) throw new IllegalArgumentException();
		List<IntervalADT<T>> result = new ArrayList<IntervalADT<T>>();
		findOverlappingHelper(root, interval, result);
		return result;
	}
	
	/**
	 * helper method of findOverlapping
	 * @param node
	 * @param interval
	 * @param result
	 */
	private void findOverlappingHelper(IntervalNode<T> node, 
			IntervalADT<T> interval, List<IntervalADT<T>> result){
		// We've reached the end if our node is null
		if(node == null) return;
		// Check if current node overlaps
		if(node.getInterval().overlaps(interval)){
			result.add(node.getInterval());
		}
		// If the left subtree has an interval who's end is greater 
		// than intervals start. There could be an overlap in left tree. 
		if(node.getLeftNode() != null &&
				node.getLeftNode().getMaxEnd().compareTo(interval.getStart()) > 0){
			findOverlappingHelper(node.getLeftNode(), interval, result);
		}
		// If the right subtree has an interval who's end is greater 
		// than intervals start. There could be an overlap in right tree. 
		if(node.getRightNode() != null &&
				node.getRightNode().getMaxEnd().compareTo(interval.getStart()) > 0){
			findOverlappingHelper(node.getRightNode(), interval, result);
		}
	}

	// Finds all intervals that contain a point of type T
	/**
	 * Finds all intervals that contain a point of type T
	 * @param point the point that should be searched
	 * @return result a list containing intervals that have point
	 */
	public List<IntervalADT<T>> searchPoint(T point) {
		// Can't have null point to find
		if(point == null) throw new IllegalArgumentException();
		// List to store intervals
		List<IntervalADT<T>> result = new ArrayList<IntervalADT<T>>();
		searchPointHelper(root, point, result);
		return result;
	}

	/**
	 * helper method of searchPoint
	 * @param node
	 * @param point
	 * @param result
	 */
	private void searchPointHelper(IntervalNode<T> node, 
			T point, List<IntervalADT<T>> result){
		if(node == null) return;
		// If the node contains the point, add to list
		if(node.getInterval().contains(point)){
			result.add(node.getInterval());
		}
		// If there's a left node and it can contain the point, check left
		if(node.getLeftNode() != null &&
				node.getLeftNode().getMaxEnd().compareTo(point) > 0){
			searchPointHelper(node.getLeftNode(), point, result);
		}
		// If there's a right node and it can contain the point, check right
		if(node.getRightNode() != null &&
				node.getRightNode().getMaxEnd().compareTo(point) > 0){
			searchPointHelper(node.getRightNode(), point, result);
		}
	}
	@Override
	/**
	 * Get the size of the interval tree. The size is the total number of
	 * nodes present in the tree. 
	 * 
	 * <p>Tip: Define and call a recursive helper function to calculate this.</p>
	 * 
	 * @return int number of nodes in the tree.
	 */
	public int getSize() {
		return getSizeHelper(root);
	}

	/**
	 * helper method of getSizeHelper
	 * @param t
	 * @return
	 */
	private int getSizeHelper(IntervalNode<T> t){
		if(t == null) return 0;
		// Count recursively 
		return 1+getSizeHelper(t.getLeftNode()) 
				+ getSizeHelper(t.getRightNode());
	}
	@Override
	/**
	 * Return the height of the interval tree at the root of the tree. 
	 * 
	 * <p>Tip: Define and call a recursive helper function to calculate this for a given node.</p>
	 * 
	 * @return the height of the interval tree
	 */
	public int getHeight() {
		return getHeightHelper(root);
	}
	/**
	 * helper method of getHeight
	 * @param t
	 * @return
	 */
	private int getHeightHelper(IntervalNode<T> t){
		if(t == null) return 0;
		int leftHeight = getHeightHelper(t.getLeftNode());
		int rightHeight = getHeightHelper(t.getRightNode());
		// Recursively return the greater height between left and right
		if(leftHeight > rightHeight) return 1 + leftHeight;
		else return 1+rightHeight;
	}

	@Override
	/**
	 * Returns true if the tree contains an exact match for the start and end of the given interval.
	 * The label is not considered for this operation.
	 *  
	 * <p>Tip: Define and call a recursive helper function to call with root node 
	 * and the target interval.</p>
	 * 
	 * @param interval
	 * 				target interval for which to search the tree for. 
	 * @return boolean 
	 * 			   	representing if the tree contains the interval.
	 *
	 * @throws IllegalArgumentException
	 *             	if interval is null.
	 * 
	 */
	public boolean contains(IntervalADT<T> interval) {
		if(interval == null) throw new IllegalArgumentException();
		return containsHelper(root,interval);
	}

	/**
	 * helper method of contains
	 * @param node
	 * @param interval
	 * @return
	 */
	private boolean containsHelper(IntervalNode<T> node, 
			IntervalADT<T> interval){
		// Reached end, no match
		if(node == null) return false;
		// Found match
		if(node.getInterval().compareTo(interval) == 0) return true;
		// Compare and go down correct tree
		if(node.getInterval().compareTo(interval) > 0)
			return containsHelper(node.getLeftNode(),interval);
		else return containsHelper(node.getRightNode(),interval);
	}
	@Override
	/**
	 * Print the statistics of the tree in the below format
	 * <pre>
	 *	-----------------------------------------
	 *	Height: 2
	 *	Size: 3
	 *	-----------------------------------------
	 *	</pre> 
	 */
	public void printStats() {
		System.out.println("-----------------------------------------");
		System.out.println("Height: "+getHeight());
		System.out.println("Size: "+getSize());
		System.out.println("-----------------------------------------");
	}
	
	/**
	 * recalculate MaxEnd
	 * @param nodeToRecalculate
	 * @return
	 */
	private T recalculateMaxEnd(IntervalNode<T> nodeToRecalculate){
		T nodeEnd = nodeToRecalculate.getInterval().getEnd();
		nodeToRecalculate.setMaxEnd(nodeEnd);
		// If this is the last node, it's max end is the max end
		if(nodeToRecalculate.getLeftNode() == null&&
				nodeToRecalculate.getRightNode() == null){
			return nodeToRecalculate.getMaxEnd();
		}
		// Go right
		if(nodeToRecalculate.getLeftNode() == null){
			if(nodeToRecalculate.getMaxEnd().compareTo(
					nodeToRecalculate.getRightNode().getMaxEnd())<0){
				// If the right node's end is bigger, that's node's maxEnd
				return nodeToRecalculate.getRightNode().getMaxEnd();
			}
			else return nodeToRecalculate.getMaxEnd();
		}
		// Go left
		if(nodeToRecalculate.getRightNode() == null){
			if(nodeToRecalculate.getMaxEnd().compareTo(
					nodeToRecalculate.getLeftNode().getMaxEnd())<0){
				// If left's maxEnd is greater than node's, that's new maxEnd
				return nodeToRecalculate.getLeftNode().getMaxEnd();
			}
			else return nodeToRecalculate.getMaxEnd();
		}
		// Go direction of greatest maxEnd
		T maxVal = nodeToRecalculate.getLeftNode().getMaxEnd();
		if(maxVal.compareTo(nodeToRecalculate.getRightNode().getMaxEnd()) < 0){
			maxVal = nodeToRecalculate.getRightNode().getMaxEnd();
		}
		if(maxVal.compareTo(nodeToRecalculate.getMaxEnd()) < 0){
			maxVal = nodeToRecalculate.getMaxEnd();
		}
		return maxVal;
	}

}
