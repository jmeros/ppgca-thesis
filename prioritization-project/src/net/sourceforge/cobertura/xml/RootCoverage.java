package net.sourceforge.cobertura.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="coverage")
public class RootCoverage extends CoverageElement {
	private long linesCovered;
	private long branchesCovered;
	private long branchesValid;
	private String coberturaVersion;
	private long timestamp;
	private List<PackageCoverage> packages;

	public RootCoverage() {}
	
	public RootCoverage(RootCoverage coverage) {
		super(coverage);
		
		// Deep clone of element
		linesCovered = coverage.linesCovered;
		branchesCovered = coverage.branchesCovered;
		branchesValid = coverage.branchesValid;
		coberturaVersion = coverage.coberturaVersion;
		timestamp = coverage.timestamp;
		
		packages = new ArrayList<PackageCoverage>();
		for (PackageCoverage cPackage: coverage.packages)
			packages.add(new PackageCoverage(cPackage));
	}

	public long getLinesCovered() {
		return linesCovered;
	}
	
	@XmlAttribute(name="lines-covered")
	public void setLinesCovered(long linesCovered) {
		this.linesCovered = linesCovered;
	}
	
	public long getBranchesCovered() {
		return branchesCovered;
	}
	
	@XmlAttribute(name="branches-covered")
	public void setBranchesCovered(long branchesCovered) {
		this.branchesCovered = branchesCovered;
	}

	public long getBranchesValid() {
		return branchesValid;
	}
	
	@XmlAttribute(name="branches-valid")
	public void setBranchesValid(long branchesValid) {
		this.branchesValid = branchesValid;
	}

	public String getCoberturaVersion() {
		return coberturaVersion;
	}
	
	@XmlAttribute(name="version")
	public void setCoberturaVersion(String coberturaVersion) {
		this.coberturaVersion = coberturaVersion;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
	@XmlAttribute(name="timestamp")
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public List<PackageCoverage> getPackages() {
		return packages;
	}

	@XmlElementWrapper(name="packages")
	@XmlElement(name="package")
	public void setPackages(List<PackageCoverage> packages) {
		this.packages = packages;
	}

	public PackageCoverage retrievePackageByName(String packageName) {
		for (PackageCoverage cPackage: packages) {
			if (cPackage.getName().equals(packageName)) {
				return cPackage;
			}
		}
		return null;
	}

}
