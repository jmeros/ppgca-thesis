package callgraphanalysis.coverage.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import callgraphanalysis.coverage.TestCaseCoverage;
import callgraphanalysis.coverage.exception.InvalidTestCaseCoverageException;
import callgraphanalysis.staticcallgraph.Node;
import callgraphanalysis.staticcallgraph.StaticCallGraph;

public class TestCaseStaticCallGraphCoverage implements TestCaseCoverage {
	private String _testCaseName;
	private Set<Node> _testCaseNodes;
	
	public TestCaseStaticCallGraphCoverage(StaticCallGraph staticCallGraph) {
		_testCaseName = staticCallGraph.getRoot().getMethodName();
		_testCaseNodes = staticCallGraph.listAllNodes();
	}
	
	private TestCaseStaticCallGraphCoverage(String testCaseName, Set<Node> testCaseNodes) {
		_testCaseName = testCaseName;
		_testCaseNodes = new HashSet<Node>(testCaseNodes);
	}

	@Override
	public String getTestCaseName() {
		return _testCaseName;
	}

	@Override
	public long getCoverage() {
		return _testCaseNodes.size();
	}
	
	public Set<Node> getTestCaseNodes() {
		return _testCaseNodes;
	}

	@Override
	public int compareTo(TestCaseCoverage otherTestCase) throws InvalidTestCaseCoverageException {
		if (otherTestCase instanceof TestCaseStaticCallGraphCoverage) {
			TestCaseStaticCallGraphCoverage other = (TestCaseStaticCallGraphCoverage) otherTestCase;
			
			// Return number of different nodes between both elements
			return other._testCaseNodes.size() - this._testCaseNodes.size();
		}
		else
			throw new InvalidTestCaseCoverageException();
	}

	@Override
	public TestCaseCoverage copy(String newTestCaseName) {
		return new TestCaseStaticCallGraphCoverage(newTestCaseName, _testCaseNodes);
	}

	@Override
	public void merge(TestCaseCoverage otherTestCase) throws InvalidTestCaseCoverageException {
		if (otherTestCase instanceof TestCaseStaticCallGraphCoverage) {
			_testCaseNodes.addAll(((TestCaseStaticCallGraphCoverage) otherTestCase)._testCaseNodes); 
		}
		else
			throw new InvalidTestCaseCoverageException();
	}

	@Override
	public int additionalCoverage(TestCaseCoverage otherTestCase) throws InvalidTestCaseCoverageException {
		if (otherTestCase instanceof TestCaseStaticCallGraphCoverage) {
			int additionalCoverage = 0;
			for (Iterator<Node> it = ((TestCaseStaticCallGraphCoverage) otherTestCase)._testCaseNodes.iterator(); it.hasNext(); ) {
				if (!_testCaseNodes.contains(it.next())) {
					additionalCoverage++;
				}
			}
			return additionalCoverage;
		}
		else
			throw new InvalidTestCaseCoverageException();
	}

}
