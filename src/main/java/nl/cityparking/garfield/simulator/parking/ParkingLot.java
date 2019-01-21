package nl.cityparking.garfield.simulator.parking;

import nl.cityparking.garfield.simulator.Arrival;
import nl.cityparking.garfield.simulator.agent.Agent;

import java.util.ArrayList;
import java.util.TreeSet;

public class ParkingLot {
	private ArrayList<ParkingSpace> spaces = new ArrayList<>();
	private TreeSet<Integer> freeSpaces = new TreeSet<>();

	private int rows = 2;

	public ParkingLot(int spaces) {
		assert (spaces % 2 == 0);
		resize(spaces);
	}

	/**
	 * Resizes the parking lot, adding or removing new parking spaces. This
	 * method will fail if the size of the lot is reduced, while there are
	 * still occupied spots within the range that is being resized.
	 * @param size The desired, new size of the parking lot.
	 */
	public void resize(int size) {
		if (size > spaces.size()) {
			for (int i = spaces.size(); i < size; i++) {
				this.spaces.add(new ParkingSpace());
				this.freeSpaces.add(i);
			}
		} else if (getLastOccupiedSpace() < size) {
			// These spaces are guaranteed to be free.
			for (int i = size; i < spaces.size(); i++) {
				this.freeSpaces.remove(i);
			}

			spaces.subList(size, spaces.size()).clear();
		}
	}

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
	 * @return A ParkingSpace instance that is unoccupied, or null if all spaces are occupied.
	 */
	private synchronized int popFirstFreeSpaceIndex() {
		while (freeSpaces.size() > 0) {
			int freeIndex = freeSpaces.first();
			ParkingSpace space = spaces.get(freeIndex);

			if (space.isOccupied()) {
				// This is odd, but we can correct our mistake here.
				// TODO: Maybe throw an exception instead?
				freeSpaces.remove(freeIndex);
			} else {
				freeSpaces.remove(freeIndex);
				return freeIndex;
			}
		}

		return -1;
	}

	public void parkArrival(Arrival arrival) {
		int index = popFirstFreeSpaceIndex();
		spaces.get(index).setOccupant(arrival.agent, arrival.departureMinute);
	}

	public synchronized Agent freeSpace(ParkingSpace space) {
		int index = spaces.indexOf(space);
		freeSpaces.add(index);
		return space.free();
	}

	public int getAmountOfOccupants() {
		return spaces.size() - freeSpaces.size();
	}

	public int getAmountOfFreeSpaces() {
		return freeSpaces.size();
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public ArrayList<ParkingSpace> getSpaces() {
		return spaces;
	}
}
