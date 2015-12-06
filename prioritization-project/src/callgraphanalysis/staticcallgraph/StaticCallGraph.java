package callgraphanalysis.staticcallgraph;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class StaticCallGraph {
	private Node _root;

	StaticCallGraph(Node rootNode) {
		_root = rootNode;
	}
	
	/**
	 * Retrieve the root node from this static call graph.
	 * 
	 * @return Node representing the root element of the static call graph.
	 */
	public Node getRoot() {
		return _root;
	}
	
	/**
	 * Retrieve a set of all nodes present on this static call graph.
	 */
	public Set<Node> listAllNodes() {
		return listAllNodes(_root);
	}

	/**
	 * Retrieve a set of all nodes starting from the given node.
	 * 
	 * @param node The initial node to list all children nodes.
	 * 
	 * @return Set of all nodes starting from the given node (except it).
	 */
	private Set<Node> listAllNodes(Node node) {
		HashSet<Node> list = new HashSet<Node>();
		
		for (Iterator<Node> it = node.getChildren().iterator(); it.hasNext(); ) {
			Node child = it.next();
			list.add(child);
			list.addAll(listAllNodes(child));
		}
		
		return list;
	}
}
