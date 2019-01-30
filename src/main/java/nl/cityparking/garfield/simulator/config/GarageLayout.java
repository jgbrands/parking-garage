package nl.cityparking.garfield.simulator.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(name = "GarageLayout")
public class GarageLayout {
	@XmlElement(name = "GarageFloor")
	public ArrayList<ParkingFloorLayout> floorLayouts = null;

	public static class ParkingFloorLayout {
		@XmlElement(name = "ParkingLot")
		public ArrayList<ParkingLotLayout> lotLayouts = null;
	}

	public static class ParkingLotLayout {
		@XmlAttribute(name = "openSpaces")
		public int openSpaces = 0;

		@XmlAttribute(name = "passholderSpaces")
		public int passholderSpaces = 0;

		@XmlAttribute(name = "disabledParkingSpaces")
		public int disabledParkingSpaces = 0;
	}
}
