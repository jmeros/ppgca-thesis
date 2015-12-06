package net.sourceforge.cobertura.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class MethodCoverage extends CoverageElement {
	private String name;
	private String signature;
	private List<LineCoverage> lines;
	
	public MethodCoverage() {}
	
	public MethodCoverage(MethodCoverage coverage) {
		super(coverage);
		
		// Deep clone of element
		name = coverage.name;
		signature = coverage.signature;
		lines = new ArrayList<LineCoverage>();
		for (LineCoverage line: coverage.lines)
			lines.add(new LineCoverage(line));
	}

	public String getName() {
		return name;
	}
	
	@XmlAttribute(name="name")
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSignature() {
		return signature;
	}
	
	@XmlAttribute(name="signature")
	public void setSignature(String signature) {
		this.signature = signature;
	}

	public List<LineCoverage> getLines() {
		return lines;
	}

	@XmlElementWrapper(name="lines")
	@XmlElement(name="line")
	public void setLines(List<LineCoverage> lines) {
		this.lines = lines;
	}
	
}
