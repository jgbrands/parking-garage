package simulator.config;

import javax.xml.bind.annotation.XmlAttribute;

public class SpawnRatio {
	@XmlAttribute(name = "weekdayIndex")
	public int index;

	@XmlAttribute(name = "base")
	public long base;

	@XmlAttribute(name = "minModifier")
	public float min;

	@XmlAttribute(name = "maxModifier")
	public float max;
}
