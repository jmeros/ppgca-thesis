package callgraphanalysis.staticcallgraph;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CHATransformer;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;

public class StaticCallGraphFactory {
	
	private CallGraph _cg;
	private HashMap<SootMethod, Node> _mappedNodes;
	
	private StaticCallGraphFactory(CallGraph callGraph) {
		_cg = callGraph;
		_mappedNodes = new HashMap<SootMethod, Node>();
	}
	
	@SuppressWarnings("rawtypes")
	public static StaticCallGraph construct(Class clazz, Method method) {
		String className = clazz.getCanonicalName();
		String methodName = method.getName();
		
		// Calculate call graph from give method
		SootClass c = Scene.v().forceResolve(className, SootClass.BODIES);
        c.setApplicationClass();
        Scene.v().loadNecessaryClasses();
        SootMethod m = c.getMethodByName(methodName);
        List<SootMethod> entryPoints = new ArrayList<SootMethod>();
        entryPoints.add(m);
        Scene.v().setEntryPoints(entryPoints);
        CHATransformer.v().transform();
        CallGraph cg = Scene.v().getCallGraph();
        
        // Mount static call graph recursively
        return new StaticCallGraph(new StaticCallGraphFactory(cg).constructNode(m));
    }

	private Node constructNode(SootMethod m) {
		if (_mappedNodes.containsKey(m)) return _mappedNodes.get(m);
		
		String nodeMethodName = m.getDeclaringClass().getName() + "." + m.getName() + m.getParameterTypes().toString().replace('[', '(').replace(']', ')');
		Node newNode = new Node(nodeMethodName);
		
		for (Iterator<Edge> edges = _cg.edgesOutOf(m); edges.hasNext(); ) {
			Edge edge = edges.next();
			// Avoid loops and ignore Averroes dummy class
			if (edge.src() != edge.tgt() && !edge.tgt().getDeclaringClass().getName().equals("ca.uwaterloo.averroes.Library"))
				newNode.addChild(constructNode(edge.tgt().method()));
		}
		
		_mappedNodes.put(m, newNode);
		return newNode;
	}

}
