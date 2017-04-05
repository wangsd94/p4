import java.util.List;

public class IntervalTree<T extends Comparable<T>> implements IntervalTreeADT<T> {
	
	// TODO declare any data members needed for this class
	private IntervalNode<T> root;

	public IntervalTree(){
		root = null;
	}
	public IntervalTree(IntervalNode<T> root){
		this.root = root;
	}
	@Override
	public IntervalNode<T> getRoot() {
		// TODO Auto-generated method stub
		return root;
	}

	@Override
	public void insert(IntervalADT<T> interval)
					throws IllegalArgumentException {
		// TODO Auto-generated method stub
		root = inserthelper(root, interval);

	}
	private IntervalNode<T> inserthelper(IntervalNode<T> n, IntervalADT<T> key) 
			throws IllegalArgumentException {
		if(key == null) throw new IllegalArgumentException();
		if(n == null) {
			return new IntervalNode<T>(key);
		}
		if(n.getInterval().compareTo(key) == 0){
			throw new IllegalArgumentException();
		}
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
	public void delete(IntervalADT<T> interval)
					throws IntervalNotFoundException, IllegalArgumentException {
		// TODO Auto-generated method stub
		root = deleteHelper(root,interval);
	}

	@Override
	public IntervalNode<T> deleteHelper(IntervalNode<T> node,
					IntervalADT<T> interval)
					throws IntervalNotFoundException, IllegalArgumentException {

		if(node == null) {
			//PAY ATTENTION! I'm not sure what to put in () of this exception
			throw new IntervalNotFoundException(node.getInterval().getLabel());
		}
		if(node.getInterval().compareTo(interval) == 0){
			if(node.getRightNode() != null){
				IntervalADT<T> smallVal = node.getSuccessor().getInterval();
				node.setInterval(smallVal);
				deleteHelper(node.getRightNode(),smallVal);
				node.setMaxEnd(recalculateMaxEnd(node));
				return node;
			}
			else{
				return node.getLeftNode();
			}
		}
		if(node.getInterval().compareTo(interval) < 0){
			node.setRightNode(deleteHelper(node.getRightNode(),interval));
			node.setMaxEnd(recalculateMaxEnd(node));
			return node;
		}
		else{
			node.setLeftNode(deleteHelper(node.getLeftNode(), interval));
			node.setMaxEnd(recalculateMaxEnd(node));
			return node;
		}
	}

	@Override
	public List<IntervalADT<T>> findOverlapping(
					IntervalADT<T> interval) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<IntervalADT<T>> searchPoint(T point) {
		// TODO Auto-generated method stub
	}

	@Override
	public int getSize() {
		return getSizeHelper(root);
	}

	private int getSizeHelper(IntervalNode<T> t){
		if(t == null) return 0;
		return 1+getSizeHelper(t.getLeftNode()) 
				+ getSizeHelper(t.getRightNode());
	}
	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return getHeightHelper(root);
	}
	private int getHeightHelper(IntervalNode<T> t){
		if(t == null) return 0;
		int leftHeight = getHeightHelper(t.getLeftNode());
		int rightHeight = getHeightHelper(t.getRightNode());
		if(leftHeight > rightHeight) return 1 + leftHeight;
		else return 1+rightHeight;
	}

	@Override
	public boolean contains(IntervalADT<T> interval) 
			throws IllegalArgumentException{
		if(interval == null) throw new IllegalArgumentException();
		return containsHelper(root,interval);
	}

	private boolean containsHelper(IntervalNode<T> node, 
			IntervalADT<T> interval){
		if(node == null) return null;
		if(node.getInterval().compareTo(interval) == 0) return true;
		if(node.getInterval().compareTo(interval) > 0)
			return containsHelper(node.getLeftNode(),interval);
		else return containsHelper(node.getRightNode(),interval);
	}
	@Override
	public void printStats() {
		System.out.println("-----------------------------------------");
		System.out.println("Height: "+getHeight());
		System.out.println("Size: "+getSize());
		System.out.println("-----------------------------------------");
	}
	
	private T recalculateMaxEnd(IntervalNode<T> nodeToRecalculate){
		if(nodeToRecalculate.getLeftNode() == null&&
				nodeToRecalculate.getRightNode() == null){
			return nodeToRecalculate.getMaxEnd();
		}
		if(nodeToRecalculate.getLeftNode() == null){
			if(nodeToRecalculate.getMaxEnd().compareTo(
					nodeToRecalculate.getRightNode().getMaxEnd())<0){
				return nodeToRecalculate.getRightNode().getMaxEnd();
			}
			else return nodeToRecalculate.getMaxEnd();
		}
		if(nodeToRecalculate.getRightNode() == null){
			if(nodeToRecalculate.getMaxEnd().compareTo(
					nodeToRecalculate.getLeftNode().getMaxEnd())<0){
				return nodeToRecalculate.getLeftNode().getMaxEnd();
			}
			else return nodeToRecalculate.getMaxEnd();
		}
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
