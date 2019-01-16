package simulator.config;

import javax.xml.bind.annotation.XmlAttribute;

public class WealthRatio {
	@XmlAttribute(name = "poor")
	public float ratioPoor;

	@XmlAttribute(name = "middleClass")
	public float ratioMiddleClass;

	@XmlAttribute(name = "wealthy")
	public float ratioWealthy;

	@XmlAttribute(name = "rich")
	public float ratioRich;
}
