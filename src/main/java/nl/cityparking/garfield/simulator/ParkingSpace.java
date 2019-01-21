package nl.cityparking.garfield.simulator;

import nl.cityparking.garfield.simulator.agent.Car;

public class ParkingSpace {
	private Car occupant = null;
	private ParkingSpaceType spaceType = ParkingSpaceType.OPEN;

	public boolean isOccupied() {
		return occupant != null;
	}

	public void setOccupant(Car occupant) {
		this.occupant = occupant;
	}

	public Car getOccupant() {
		return occupant;
	}

	public ParkingSpaceType getSpaceType() {
		return spaceType;
	}
}
