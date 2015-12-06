package callgraphanalysis.coverage;

import callgraphanalysis.coverage.exception.InvalidTestCaseCoverageException;

public interface TestCaseCoverage {

	/**
	 * Retrieve the test case name represented by this element.
	 * 
	 * @return Test case name.
	 */
	public String getTestCaseName();
	
	/**
	 * Retrieve the coverage from this element.
	 * 
	 * @return The coverage value (for example, number of covered lines of code).
	 */
	public long getCoverage();
	
	/**
	 * Compare the number of covered elements from both TestCaseCoverage elements.
	 * 
	 * @param otherTestCase The TestCaseCoverage element to compare with.
	 * 
	 * @return 0, if both cover the same number of elements. A positive number if other element has 
	 * a bigger coverage and a negative number if this element has a bigger coverage.
	 * 
	 * @throws InvalidTestCaseCoverageException
	 */
	public int compareTo(TestCaseCoverage otherTestCase) throws InvalidTestCaseCoverageException;
	
	/**
	 * Create a copy of this element with a different test case name.
	 * 
	 * @param newTestCaseName New test case name to be used.
	 * 
	 * @return The copied TestCaseCoverage element.
	 */
	public TestCaseCoverage copy(String newTestCaseName);
	
	/**
	 * Merge coverage from other TestCaseCoverage element to this one.
	 * 
	 * @param otherTestCase The TestCaseCoverage element to be merged.
	 * 
	 * @throws InvalidTestCaseCoverageException 
	 */
	public void merge(TestCaseCoverage otherTestCase) throws InvalidTestCaseCoverageException;
	
	/**
	 * Calculate the additional coverage from other test case when compared with this test case.
	 * 
	 * @param otherTestCase TestCaseCoverage element used to calculate aditional coverage.
	 * 
	 * @return 0, if test case does not increase coverage. Or a positive number that is equal
	 * to the number of additional elements covered.
	 * 
	 * @throws InvalidTestCaseCoverageException 
	 */
	public int additionalCoverage(TestCaseCoverage otherTestCase) throws InvalidTestCaseCoverageException;
}
