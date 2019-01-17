package nl.cityparking.garfield.simulator.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.ArrayList;

@XmlRootElement(name = "configuration")
public class Configuration implements Serializable {
	public WealthRatio wealthRatio;

	@XmlElementWrapper(name = "spawnRatios")
	@XmlElement(name = "ratio")
	public ArrayList<SpawnRatio> spawnRatios;

	public Configuration() {
		super();

		this.wealthRatio = new WealthRatio();
		this.spawnRatios = new ArrayList<>();
	}

	public SpawnRatio getSpawnRatio(long dayOfWeek) {
		for (SpawnRatio ratio : this.spawnRatios) {
			if (ratio.index == dayOfWeek) {
				return ratio;
			}
		}

		return null;
	}
}
