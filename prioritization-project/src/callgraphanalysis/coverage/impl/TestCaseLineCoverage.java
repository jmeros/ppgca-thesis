package callgraphanalysis.coverage.impl;

import callgraphanalysis.coverage.TestCaseCoverage;
import callgraphanalysis.coverage.exception.InvalidTestCaseCoverageException;
import net.sourceforge.cobertura.xml.ClassCoverage;
import net.sourceforge.cobertura.xml.LineCoverage;
import net.sourceforge.cobertura.xml.PackageCoverage;
import net.sourceforge.cobertura.xml.RootCoverage;

public class TestCaseLineCoverage implements TestCaseCoverage {
	private String testcaseName;
	private RootCoverage coverage;
	
	public TestCaseLineCoverage(String testcaseName, RootCoverage coverage) {
		this.testcaseName = testcaseName;
		this.coverage = coverage;
	}

	@Override
	public String getTestCaseName() {
		return testcaseName;
	}

	@Override
	public long getCoverage() {
		return coverage.getLinesCovered();
	}

	@Override
	public int compareTo(TestCaseCoverage otherTestCase) throws InvalidTestCaseCoverageException {
		if (otherTestCase instanceof TestCaseLineCoverage) {
			TestCaseLineCoverage other = (TestCaseLineCoverage) otherTestCase;
			
			// Return number of different lines between both elements
			return (int) (other.coverage.getLinesCovered() - this.coverage.getLinesCovered());
		}
		else
			throw new InvalidTestCaseCoverageException();
	}

	@Override
	public TestCaseCoverage copy(String newTestCaseName) {
		return new TestCaseLineCoverage(newTestCaseName, new RootCoverage(coverage));
	}

	@Override
	public void merge(TestCaseCoverage otherTestCase) throws InvalidTestCaseCoverageException {
		if (otherTestCase instanceof TestCaseLineCoverage) {
			TestCaseLineCoverage other = (TestCaseLineCoverage) otherTestCase;
			
			// Merge package by package, class by class, line by line
			int totalLineCoverage = 0;
			for (PackageCoverage currentPackage: coverage.getPackages()) {
				// Retrieve same package from other test case
				PackageCoverage otherPackage = other.coverage.retrievePackageByName(currentPackage.getName());
				if (otherPackage == null) throw new InvalidTestCaseCoverageException();
				
				// Merge each class inside the current package
				for (ClassCoverage currentClass: currentPackage.getClasses()) {
					// Retrieve same class from other test case
					ClassCoverage otherClass = otherPackage.retrieveClassByName(currentClass.getName());
					if (otherClass == null) throw new InvalidTestCaseCoverageException();
					
					// Merge each line inside the current class
					for (LineCoverage currentLine: currentClass.getLines()) {
						LineCoverage otherLine = otherClass.retrieveLineByNumber(currentLine.getNumber());
						if (otherLine == null) throw new InvalidTestCaseCoverageException();
						
						if (otherLine.getHits() > currentLine.getHits()) {
							currentLine.setHits(otherLine.getHits());
							totalLineCoverage++;
						}
						else if (currentLine.getHits() > 0) {
							totalLineCoverage++;
						}
					}
				}
			}
			
			// Set total line coverage to root
			coverage.setLinesCovered(totalLineCoverage);
		}
		else
			throw new InvalidTestCaseCoverageException();
	}

	@Override
	public int additionalCoverage(TestCaseCoverage otherTestCase) throws InvalidTestCaseCoverageException {
		if (otherTestCase instanceof TestCaseLineCoverage) {
			TestCaseLineCoverage other = (TestCaseLineCoverage) otherTestCase;
			int additionalCoverage = 0;
			
			// Calculate additional coverage package by package, class by class, line by line
			for (PackageCoverage currentPackage: coverage.getPackages()) {
				PackageCoverage otherPackage = other.coverage.retrievePackageByName(currentPackage.getName());
				if (otherPackage == null) throw new InvalidTestCaseCoverageException();
				
				for (ClassCoverage currentClass: currentPackage.getClasses()) {
					ClassCoverage otherClass = otherPackage.retrieveClassByName(currentClass.getName());
					if (otherClass == null) throw new InvalidTestCaseCoverageException();
					
					for (LineCoverage currentLine: currentClass.getLines()) {
						LineCoverage otherLine = otherClass.retrieveLineByNumber(currentLine.getNumber());
						if (otherLine == null) throw new InvalidTestCaseCoverageException();
						
						// Increment additional coverage if line is covered by otherTestCase
						// and is not covered by this testCase
						if (otherLine.getHits() > 0 && currentLine.getHits() == 0) {
							additionalCoverage++;
						}
					}
				}
			}
			
			return additionalCoverage;
		}
		else
			throw new InvalidTestCaseCoverageException();
	}

}
