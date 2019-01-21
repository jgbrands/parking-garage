package nl.cityparking.garfield.simulator.parking;

import nl.cityparking.garfield.simulator.Arrival;

import java.util.ArrayList;

public class ParkingFloor {
	private ArrayList<ParkingLot> parkingLots = new ArrayList<>();

	public ParkingFloor(int count, int lotSize) {
		addLots(count, lotSize);
	}

	public void addLots(int count, int lotSize) {
		for (int i = 0; i < count; i++) {
			parkingLots.add(new ParkingLot(lotSize));
		}
	}

	public ArrayList<ParkingLot> getParkingLots() {
		return parkingLots;
	}

	public void parkArrival(Arrival arrival) {
		for (ParkingLot lot: parkingLots) {
			if (lot.getAmountOfFreeSpaces() > 0) {
				lot.parkArrival(arrival);
				return;
			}
		}
	}

	public long getFreeSpots() {
		return parkingLots.stream()
				.mapToLong(ParkingLot::getAmountOfFreeSpaces)
				.sum();
	}
}
