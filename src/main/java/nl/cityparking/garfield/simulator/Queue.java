package nl.cityparking.garfield.simulator;

import nl.cityparking.garfield.simulator.family.Car;

import java.util.LinkedList;

public class Queue {
	private java.util.Deque<Car> deque = new LinkedList<>();

	public void addCar(Car car) {
		deque.add(car);
	}

	public Car removeCar() {
		return deque.poll();
	}

	public int carsInQueue(){
		return deque.size();
	}
}
