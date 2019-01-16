package simulator.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.ArrayList;

@XmlRootElement(name = "configuration")
public class SimulatorConfiguration implements Serializable {
	public WealthRatio wealthRatio;

	@XmlElementWrapper(name = "spawnRatios")
	@XmlElement(name = "ratio")
	public ArrayList<SpawnRatio> spawnRatios;

	public SimulatorConfiguration() {
		super();

		this.wealthRatio = new WealthRatio();
		this.spawnRatios = new ArrayList<>();
	}
}
