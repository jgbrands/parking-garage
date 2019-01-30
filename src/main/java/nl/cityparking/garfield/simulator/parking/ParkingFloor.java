package nl.cityparking.garfield.simulator.parking;

import nl.cityparking.garfield.simulator.Arrival;

import java.util.ArrayList;

/**
 * Abstraction of a garage's floor, which is divided into parking lots with spaces.
 *
 * @author Jesse
 * @since 1.0
 */
public class ParkingFloor {
	private ArrayList<ParkingLot> parkingLots = new ArrayList<>();

	/**
	 * Retrieves the ParkingLots associated with this floor.
	 * @return The ParkingLots on this floor.
	 */
	public ArrayList<ParkingLot> getParkingLots() {
		return parkingLots;
	}

	/**
	 * Processes an Arrival and parks the contained Agent into a ParkingSpace.
	 * @param arrival The Arrival to process.
	 */
	public void parkArrival(Arrival arrival, ParkingSpaceType type) {
		for (ParkingLot lot: parkingLots) {
			if (lot.getAmountOfFreeSpaces(type) > 0) {
				lot.parkArrival(arrival, type);
				return;
			}
		}
	}

	/**
	 * Returns the amount of spaces that are unoccupied on this floor.
	 *
	 * @return The amount of empty spaces across all lots.
	 */
	public long getFreeSpots(ParkingSpaceType type) {
		return parkingLots.stream()
				.mapToLong(lot -> lot.getAmountOfFreeSpaces(type))
				.sum();
	}

	public void addLot(ParkingLot lot) {
		parkingLots.add(lot);
	}
}
