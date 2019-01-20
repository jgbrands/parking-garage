package nl.cityparking.garfield.simulator.config;

import javax.xml.bind.annotation.XmlAttribute;

public class SpawnRatio {
	@XmlAttribute(name = "weekdayIndex")
	public int index = 0;

	@XmlAttribute(name = "base")
	public double base = 0;

	@XmlAttribute(name = "factor")
	public double factor = 1;

	@XmlAttribute(name = "variance")
	public double variance = 2;

	@XmlAttribute(name = "peakMean")
	public double peakMean = -0.8;

	@XmlAttribute(name = "peakStandardDeviation")
	public double peakStandardDeviation = 1.35;
}
