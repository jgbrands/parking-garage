package simulator.config;

import javax.xml.bind.annotation.XmlAttribute;

public class SpawnRatio {
	@XmlAttribute(name = "weekdayIndex")
	public int index = 0;

	@XmlAttribute(name = "base")
	public long base = 5;

	@XmlAttribute(name = "deviationLower")
	public long min = 2;

	@XmlAttribute(name = "deviationUpper")
	public long max = 1;
}
