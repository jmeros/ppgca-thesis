package net.sourceforge.cobertura.xml;

import javax.xml.bind.annotation.XmlAttribute;

public class LineCoverage {
	private long number;
	private long hits;
	private boolean branch;
	
	public LineCoverage() {}
	
	public LineCoverage(LineCoverage coverage) {
		// Deep clone of element
		number = coverage.number;
		hits = coverage.hits;
		branch = coverage.branch;
	}

	public long getNumber() {
		return number;
	}
	
	@XmlAttribute(name="number")
	public void setNumber(long number) {
		this.number = number;
	}
	
	public long getHits() {
		return hits;
	}
	
	@XmlAttribute(name="hits")
	public void setHits(long hits) {
		this.hits = hits;
	}
	
	public boolean isBranch() {
		return branch;
	}

	@XmlAttribute(name="branch")
	public void setBranch(boolean branch) {
		this.branch = branch;
	}

}
