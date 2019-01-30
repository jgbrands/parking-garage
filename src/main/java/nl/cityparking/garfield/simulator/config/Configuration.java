package nl.cityparking.garfield.simulator.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.ArrayList;

@XmlRootElement(name = "configuration")
public class Configuration implements Serializable {
	@XmlElement(name = "GarageLayout")
	public GarageLayout garageLayout = null;
	
	@XmlElement(name = "EconomyConfiguration")
	public EconomyConfiguration economyConfiguration = null;
}
