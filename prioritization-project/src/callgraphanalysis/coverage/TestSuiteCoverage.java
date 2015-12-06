package callgraphanalysis.coverage;

import java.util.LinkedList;
import java.util.List;

import callgraphanalysis.coverage.exception.InvalidTestCaseCoverageException;

public class TestSuiteCoverage<T extends TestCaseCoverage> {
	private List<T> testCases = new LinkedList<T>();
	private T agregateTestCase = null;
	
	public List<T> getTestCases() {
		return testCases;
	}

	/**
	 * Add given test case to the order list of test cases and
	 * recalculate total suite coverage.
	 *  
	 * @param testCase TestCaseCoverage to be added.
	 * 
	 * @throws InvalidTestCaseCoverageException 
	 */
	@SuppressWarnings("unchecked")
	public void add(T testCase) throws InvalidTestCaseCoverageException {
		testCases.add(testCase);
		
		if (agregateTestCase == null)
			agregateTestCase = (T) testCase.copy("Test Suite Coverage");
		else
			agregateTestCase.merge(testCase);
	}
	
	/**
	 * Calculate the additional coverage from the given test case based on
	 * the current test suite.
	 * 
	 * @param testCase The TestCaseCoverage to have its additional coverage
	 * calculated.
	 * 
	 * @return 0, if no additional coverage exists or a positive number that
	 * represent the number of new elements covered.
	 */
	public long additionalCoverage(T testCase) {
		if (agregateTestCase == null) return testCase.getCoverage();
		
		try {
			return agregateTestCase.additionalCoverage(testCase);
		} catch (InvalidTestCaseCoverageException e) {
			return 0;
		}
	}

	/**
	 * Reset aggregate test coverage to permit new test cases to be added
	 * when using additional coverage.
	 */
	public void resetAdditionalCoverage() {
		agregateTestCase = null;
	}

	/**
	 * Retrieve aggregate coverage from the test suite.
	 * 
	 * @return Aggregate coverage from this test suite.
	 */
	public long getAggregateCoverage() {
		return agregateTestCase.getCoverage();
	}

	/**
	 * Check if current test suite aggregate coverage is empty.
	 * 
	 * @return
	 */
	public boolean isAggregateCoverageEmpty() {
		return agregateTestCase == null;
	}
}
