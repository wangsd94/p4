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
		if(n.getLeftNode() == null) {
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
		// TODO Auto-generated method stub
		if(node == null) {
			//PAY ATTENTION! I'm not sure what to put in () of this exception
			throw new IntervalNotFoundException(node.getInterval().getLabel());
		}
		if(node.getInterval().compareTo(interval) == 0){
			if(node.getRightNode() != null){
				IntervalNode<T> smallVal = node.getSuccessor();
				node.setInterval(smallVal.getInterval());
				deleteHelper(smallVal,smallVal.getInterval());//not sure
				// TODO update maxEnd
				return node;
			}
			else{
				return node.getLeftNode();
			}
		}
		if(node.getInterval().compareTo(interval) < 0){
			node.setRightNode(deleteHelper(node.getRightNode(),interval));
			if(node.getMaxEnd().compareTo(node.getRightNode().getMaxEnd()) < 0){
				//TODO update maxEnd
			}
			return node;
		}
		else{
			node.setLeftNode(deleteHelper(node.getLeftNode(), interval));
			if(node.getMaxEnd().compareTo(node.getLeftNode().getMaxEnd()) < 0){
				//TODO update maxEnd
			}
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
		// TODO Auto-generated method stub
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return getHight(root);
	}
	private int getHight(IntervalNode<T> t){
		if(t == null) return 0;
		if(t.getLeftNode())
	}

	@Override
	public boolean contains(IntervalADT<T> interval) {
		// TODO Auto-generated method stub
	}

	@Override
	public void printStats() {
		// TODO Auto-generated method stub

	}

}
