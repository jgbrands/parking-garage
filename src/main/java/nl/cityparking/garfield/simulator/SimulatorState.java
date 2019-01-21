package nl.cityparking.garfield.simulator;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;

public class SimulatorState {
	private LongProperty simulatorMinutes = new SimpleLongProperty();
	private LongProperty carsTotalIn = new SimpleLongProperty();
	private LongProperty carsTotalOut = new SimpleLongProperty();

	public long getSimulatorMinutes() {
		return simulatorMinutes.get();
	}

	public void setSimulatorMinutes(long m) {
		simulatorMinutes.set(m);
	}

	public LongProperty getSimulatorMinutesProperty() {
		return simulatorMinutes;
	}

	public long getCarsTotalIn() {
		return carsTotalIn.get();
	}

	public LongProperty carsTotalInProperty() {
		return carsTotalIn;
	}

	public void setCarsTotalIn(long carsTotalIn) {
		this.carsTotalIn.set(carsTotalIn);
	}

	public long getCarsTotalOut() {
		return carsTotalOut.get();
	}

	public LongProperty carsTotalOutProperty() {
		return carsTotalOut;
	}

	public void setCarsTotalOut(long carsTotalOut) {
		this.carsTotalOut.set(carsTotalOut);
	}
}
