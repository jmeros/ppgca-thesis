package callgraphanalysis.coverage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import callgraphanalysis.coverage.exception.InvalidTestCaseCoverageException;

public class TestSuiteCoverageFactory {
	
	/**
	 * Create a test suite with the given list of test cases using a greedy algorithm.
	 * 
	 * @param testCases List of test cases to be used to create the test suite.
	 * @param additionalGreedy True, if greedy algorithm may use additional logic. False, otherwise.
	 * 
	 * @return The TestSuiteCoverage created.
	 */
	public static <T extends TestCaseCoverage> TestSuiteCoverage<T> createTestSuiteUsingGreedyAlgorithm(Set<T> testCases, boolean additionalGreedy) {
		TestSuiteCoverage<T> testSuite = new TestSuiteCoverage<T>();
		
		while (!testCases.isEmpty()) {
			T maxTestCase = null;
			long maxCoverage = -1; // Used by additional greedy
			
			// Find test cases with maximum coverage
			for (Iterator<T> it = testCases.iterator(); it.hasNext(); ) {
				T testCase = it.next();
				if (additionalGreedy) {
					long coverage = testSuite.additionalCoverage(testCase);
					if (coverage > maxCoverage) {
						maxTestCase = testCase;
						maxCoverage = coverage;
					}
				}
				else {
					if (maxTestCase == null)
						maxTestCase = testCase;
					else {
						try {
							if (maxTestCase.compareTo(testCase) > 0)
								maxTestCase = testCase;
						} catch (InvalidTestCaseCoverageException e) {}
					}
				}
			}
			
			// Check if additional coverage is not complete (full coverage)
			if (maxCoverage == 0 && !testSuite.isAggregateCoverageEmpty()) {
				// Reset test suite aggregate coverage and continue
				testSuite.resetAdditionalCoverage();
			}
			else {
				// Add maximum coverage test case to test suite and remove from list
				try {
					testSuite.add(maxTestCase);
				} catch (InvalidTestCaseCoverageException e) {}
				testCases.remove(maxTestCase);
			}
		}
		
		return testSuite;
	}

	public static <T extends TestCaseCoverage> TestSuiteCoverage<T> createTestSuiteUsingRandom(Set<T> testCases) throws InvalidTestCaseCoverageException {
		List<T> testCasesList = new ArrayList<T>(testCases);
		Collections.shuffle(testCasesList);
		
		TestSuiteCoverage<T> testSuite = new TestSuiteCoverage<T>();
		for (T testCase: testCasesList) {
			testSuite.add(testCase);
		}
		
		return testSuite;
	}

}
