package nl.cityparking.garfield.simulator.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.ArrayList;

@XmlRootElement(name = "configuration")
public class Configuration implements Serializable {
	@XmlElementWrapper(name = "spawnRatios")
	@XmlElement(name = "ratio")
	public ArrayList<SpawnRatio> spawnRatios = null;

	@XmlElement(name = "GarageLayout")
	public GarageLayout garageLayout = null;

	@XmlElement(name = "EconomyConfiguration")
	public EconomyConfiguration economyConfiguration = null;

	public Configuration() {
		super();

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
