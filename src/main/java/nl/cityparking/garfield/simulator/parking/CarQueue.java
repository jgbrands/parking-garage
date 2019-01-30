package nl.cityparking.garfield.simulator.parking;

import nl.cityparking.garfield.simulator.Arrival;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class CarQueue {

	private static final int MAX_QUEUE_LENGTH = 50;
	private LinkedList<Arrival> carQueue = new LinkedList<>();

	public void addToQueue(Arrival arrival) {
		if (carQueue.size() < MAX_QUEUE_LENGTH) {
			carQueue.add(arrival);
		}
	}

	public Collection<Arrival> removeFromQueue(int amount) {
		if (carQueue.size() > 0) {
			Collection<Arrival> arrivals = carQueue.subList(0, (carQueue.size() >= amount) ? amount : carQueue.size());
			carQueue.removeAll(arrivals);
			return arrivals;
		}
		return new ArrayList<>();
	}



}
