package nl.cityparking.garfield.simulator.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;

public class EmployerConfiguration {
	@XmlAttribute(name = "name")
	public String name = new String();

	@XmlElement(name = "Position")
	public ArrayList<Position> positions = new ArrayList<>();


	public static class Position{
		@XmlAttribute(name = "name")
		public String name = new String();
		
		@XmlAttribute(name = "vacancies")
		public long vacancies = 0;

		@XmlElement(name = "WorkDay")
		public ArrayList<WorkDay> workdays = new ArrayList<WorkDay>();
	}


	public static class WorkDay {
		@XmlAttribute(name = "start")
		public long start = 0;

		@XmlAttribute(name = "end")
		public long end = 0;
	}




}
