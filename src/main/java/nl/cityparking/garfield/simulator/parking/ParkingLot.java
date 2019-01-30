package nl.cityparking.garfield.simulator.parking;

import nl.cityparking.garfield.simulator.Arrival;
import nl.cityparking.garfield.simulator.Departure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

/**
 * An abstraction of a physical parking lot. A ParkingLot consists of multiple ParkingSpaces.
 *
 * @author Jesse
 * @since 1.0
 */
public class ParkingLot {
	private ArrayList<ParkingSpace> spaces = new ArrayList<>();
	private TreeSet<Integer> freeOpenSpaces = new TreeSet<>();
	private TreeSet<Integer> freePassSpaces = new TreeSet<>();
	private TreeSet<Integer> freeDisabledSpaces = new TreeSet<>();

	private int rows = 2;

	/**
	 * Gets the index of the last parking spot with an occupant in it.
	 * @return The index of the occupied space, -1 if no spaces are occupied.
	 */
	public int getLastOccupiedSpace() {
		for (int i = spaces.size() - 1; i >= 0; i--) {
			ParkingSpace space = spaces.get(i);

			if (space.isOccupied()) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Returns the first free parking space.
	 * @return index of the free space, or -1 if all spaces are occupied.
	 */
	private synchronized int popFirstFreeIndex(TreeSet<Integer> spacesSet) {
		if (spacesSet.size() > 0) {
			int freeIndex = spacesSet.first();
			spacesSet.remove(freeIndex);
			return freeIndex;
		}

		return -1;
	}

	/**
	 * Processes an arrival and parks it into this spot.
	 * @param arrival The Arrival to park.
	 */
	public synchronized void parkArrival(Arrival arrival, ParkingSpaceType type) {
		int index = -1;
		
		switch (type) {
			case PASS_HOLDER_ONLY:
				index = popFirstFreeIndex(freePassSpaces);
				break;
				
			case DISABLED_ONLY:
				index = popFirstFreeIndex(freeDisabledSpaces);
				break;
				
			default:
				index = popFirstFreeIndex(freeOpenSpaces);
				break;
		}
		
		if (index != -1) {
			spaces.get(index).setOccupant(arrival.agent, arrival.arrivalMinute, arrival.departureMinute);
		}
	}

	/**
	 * Frees the given space, registering it as available for parking.
	 * @param space The ParkingSpace to free up.
	 * @return The Agent as a Departure.
	 */
	public synchronized Departure freeSpace(ParkingSpace space) {
		int index = spaces.indexOf(space);
		
		switch (space.getSpaceType()) {
			case PASS_HOLDER_ONLY:
				freePassSpaces.add(index);
				break;
				
			case DISABLED_ONLY:
				freeDisabledSpaces.add(index);
				break;
				
			default:
				freeOpenSpaces.add(index);
				break;
		}
		
		Departure departure = new Departure(space.getOccupant(), space.getOccupiedUntil() - space.getOccupiedOn(), 0);
		space.free();

		return departure;
	}

	/**
	 * Gets the amount of ParkingSpaces contained within this ParkingLot
	 * @return The amount of parking spaces.
	 */
	public int getSize() {
		return spaces.size();
	}

	/**
	 * Gets the amount of ParkingSpaces that are occupied within this ParkingLot
	 * @return The amount of occupied spaces.
	 */
	public int getAmountOfOccupants() {
		return spaces.size() - freeOpenSpaces.size();
	}

	/**
	 * Gets the amount of empty ParkingSpaces in this ParkingLot.
	 * @return The amount of free spaces.
	 */
	public int getAmountOfFreeSpaces(ParkingSpaceType type) {
		switch (type) {
			case PASS_HOLDER_ONLY:
				return freePassSpaces.size();
				
			case DISABLED_ONLY:
				return freeDisabledSpaces.size();
		}
		
		return freeOpenSpaces.size();
	}

	/**
	 * The amount of rows in this ParkingSpace.
	 * @return The amount of rows.
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * Sets the amount of rows. The amount of spaces must be divisible by rows. If this is not the case, the value
	 * will not change.
	 * @param rows The amount of rows to divide the parking spaces by.
	 * @return true if successful, false if the amount of spaces is not divisible by rows.
	 */
	public boolean setRows(int rows) {
		if (getSize() % rows == 0) {
			this.rows = rows;
			return true;
		}

		return false;
	}

	/**
	 * Returns all parking spaces in this lot.
	 * @return A Collection of ParkingSpaces.
	 */
	public Collection<ParkingSpace> getSpaces() {
		return spaces;
	}

	public void addSpaces(int count, ParkingSpaceType type) {
		int from = spaces.size();
		for (int i = 0; i < count; i++) {
			spaces.add(new ParkingSpace(type));

			switch (type) {
				case OPEN:
					freeOpenSpaces.add(from++);
					break;

				case PASS_HOLDER_ONLY:
					freePassSpaces.add(from++);
					break;

				case DISABLED_ONLY:
					freeDisabledSpaces.add(from++);
					break;
			}
		}
	}
}
