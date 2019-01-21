package nl.cityparking.garfield.simulator;

import nl.cityparking.garfield.simulator.agent.Car;

import java.util.ArrayList;
import java.util.Objects;
import java.util.TreeSet;

public class ParkingLot {
	private ArrayList<ParkingSpace> spaces = new ArrayList<>();
	private TreeSet<Integer> freeSpaces = new TreeSet<>();

	private int rows = 2;

	public ParkingLot(int spaces) {
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
	 *
	 * @param car
	 */
	public boolean parkCar(Car car) {
		Objects.requireNonNull(car, "car cannot be null");

		int freeIndex = getFirstFreeSpaceIndex();
		ParkingSpace freeSpace = spaces.get(freeIndex);

		if (freeSpace != null) {
			freeSpace.setOccupant(car);
			freeSpaces.remove(freeIndex);
		}

		return false;
	}

	/**
	 * Returns the first free parking space.
	 * @return A ParkingSpace instance that is unoccupied, or null if all spaces are occupied.
	 */
	private int getFirstFreeSpaceIndex() {
		while (freeSpaces.size() > 0) {
			int freeIndex = freeSpaces.first();
			ParkingSpace space = spaces.get(freeIndex);

			if (space.isOccupied()) {
				// This is odd, but we can correct our mistake here.
				// TODO: Maybe throw an exception instead?
				freeSpaces.remove(freeIndex);
			} else {
				return freeIndex;
			}
		}

		return -1;
	}

	public int getAmountOfOccupants() {
		return spaces.size() - freeSpaces.size();
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
}
