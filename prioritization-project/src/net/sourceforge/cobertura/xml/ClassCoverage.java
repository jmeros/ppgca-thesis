package net.sourceforge.cobertura.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class ClassCoverage extends CoverageElement {
	private String name;
	private String filename;
	private List<MethodCoverage> methods;
	private List<LineCoverage> lines;
	
	public ClassCoverage() {}
	
	public ClassCoverage(ClassCoverage coverage) {
		super(coverage);
		
		// Deep clone of element
		name = coverage.name;
		filename = coverage.filename;
		methods = new ArrayList<MethodCoverage>();
		for (MethodCoverage method: coverage.methods)
			methods.add(new MethodCoverage(method));
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
	
	public String getFilename() {
		return filename;
	}
	
	@XmlAttribute(name="filename")
	public void setFilename(String filename) {
		this.filename = filename;
	}

	public List<MethodCoverage> getMethods() {
		return methods;
	}

	@XmlElementWrapper(name="methods")
	@XmlElement(name="method")
	public void setMethods(List<MethodCoverage> methods) {
		this.methods = methods;
	}

	public List<LineCoverage> getLines() {
		return lines;
	}

	@XmlElementWrapper(name="lines")
	@XmlElement(name="line")
	public void setLines(List<LineCoverage> lines) {
		this.lines = lines;
	}

	public LineCoverage retrieveLineByNumber(long lineNumber) {
		for (LineCoverage line: lines) {
			if (line.getNumber() == lineNumber) {
				return line;
			}
		}
		return null;
	}
	
}
