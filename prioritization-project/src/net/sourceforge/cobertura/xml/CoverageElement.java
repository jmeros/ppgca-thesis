package net.sourceforge.cobertura.xml;

import javax.xml.bind.annotation.XmlAttribute;

public abstract class CoverageElement {
	private double lineRate;
	private double branchRate;
	private double complexity;
	
	public CoverageElement() {}
	
	public CoverageElement(CoverageElement coverage) {
		// Deep clone of element
		lineRate = coverage.lineRate;
		branchRate = coverage.branchRate;
		complexity = coverage.complexity;
	}

	public double getLineRate() {
		return lineRate;
	}

	@XmlAttribute(name="line-rate")
	public void setLineRate(double lineRate) {
		this.lineRate = lineRate;
	}
	
	public double getBranchRate() {
		return branchRate;
	}
	
	@XmlAttribute(name="branch-rate")
	public void setBranchRate(double branchRate) {
		this.branchRate = branchRate;
	}
		
	public double getComplexity() {
		return complexity;
	}
	
	@XmlAttribute(name="complexity")
	public void setComplexity(double complexity) {
		this.complexity = complexity;
	}

}
