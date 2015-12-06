package net.sourceforge.cobertura.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class PackageCoverage extends CoverageElement {
	private String name;
	private List<ClassCoverage> classes;

	public PackageCoverage() {}
	
	public PackageCoverage(PackageCoverage coverage) {
		super(coverage);
		
		// Deep clone of element
		name = coverage.name;
		classes = new ArrayList<ClassCoverage>();
		for (ClassCoverage clazz: coverage.classes)
			classes.add(new ClassCoverage(clazz));
	}

	public String getName() {
		return name;
	}

	@XmlAttribute(name="name")
	public void setName(String name) {
		this.name = name;
	}

	public List<ClassCoverage> getClasses() {
		return classes;
	}

	@XmlElementWrapper(name="classes")
	@XmlElement(name="class")
	public void setClasses(List<ClassCoverage> classes) {
		this.classes = classes;
	}

	public ClassCoverage retrieveClassByName(String className) {
		for (ClassCoverage clazz: classes) {
			if (clazz.getName().equals(className)) {
				return clazz;
			}
		}
		return null;
	}
	
}
