package nl.cityparking.garfield.simulator.parking;

import nl.cityparking.garfield.simulator.Arrival;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class CarQueue {
	private int maxSize = 50;
	private LinkedList<Arrival> carQueue = new LinkedList<>();

	public void addToQueue(Arrival arrival) {
		if (carQueue.size() < maxSize) {
			carQueue.add(arrival);
		}
	}

	public Collection<Arrival> removeFromQueue(int amount) {
		ArrayList<Arrival> arrivals = new ArrayList<>();
		
		for (int i = 0; i < amount; i++) {
			if (carQueue.size() == 0) {
				break;
			}
			
			arrivals.add(carQueue.pop());
		}
		
		return arrivals;
	}
	
	public Collection<Arrival> get() {
		return carQueue;
	}
	
	public int getMaxSize() {
		return maxSize;
	}
	
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}
}
