package nl.cityparking.garfield.simulator.config;

import nl.cityparking.garfield.simulator.agent.Employer;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(name = "EconomyConfiguration")
public class EconomyConfiguration  {
	@XmlElementWrapper(name = "Employers")
	@XmlElement(name = "Employer")
	public ArrayList<EmployerConfiguration> employers = new ArrayList<>();
}
