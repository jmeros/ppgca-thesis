package callgraphanalysis.staticcallgraph;

import java.util.HashSet;
import java.util.Set;

public class Node {
	private String _methodName;
	private Set<Node> _children;
	
	Node(String methodName) {
		_methodName = methodName;
		_children = new HashSet<Node>();
	}
	
	public String getMethodName() {
		return _methodName;
	}
	
	public Set<Node> getChildren() {
		return new HashSet<Node>(_children);
	}
	
	public void addChild(Node child) {
		_children.add(child);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_methodName == null) ? 0 : _methodName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (_methodName == null) {
			if (other._methodName != null)
				return false;
		} else if (!_methodName.equals(other._methodName))
			return false;
		return true;
	}
	
	
}
