package nl.cityparking.garfield.simulator.config;

import javax.xml.bind.annotation.XmlAttribute;

public class SpawnRatio {
	@XmlAttribute(name = "weekdayIndex")
	public int index = 0;

	@XmlAttribute(name = "base")
	public long base = 5;

	@XmlAttribute(name = "earlyPeakMean")
	public double earlyPeakMean = 0.35;

	@XmlAttribute(name = "earlyPeakStandardDeviation")
	public double earlyPeakStandardDeviation = 0.9;

	@XmlAttribute(name = "latePeakMean")
	public double latePeakMean = 0.7;

	@XmlAttribute(name = "latePeakStandardDeviation")
	public double latePeakStandardDeviation = 0.85;
}
