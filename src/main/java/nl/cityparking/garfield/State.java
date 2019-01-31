package nl.cityparking.garfield;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import nl.cityparking.garfield.simulator.agent.Agent;
import nl.cityparking.garfield.simulator.economy.Report;

public class State {
	private LongProperty simulatorMinutes = new SimpleLongProperty();
	private LongProperty carsTotalIn = new SimpleLongProperty();
	private LongProperty carsTotalOut = new SimpleLongProperty();
	private LongProperty openSpaces = new SimpleLongProperty();
	private LongProperty openParkers = new SimpleLongProperty();
	private LongProperty passParkers = new SimpleLongProperty();
	private LongProperty disabledParkers = new SimpleLongProperty();
	
	private ObservableList<Report> reports = FXCollections.observableArrayList();
	private ObservableList<Agent> agents = FXCollections.observableArrayList();

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
	
	public long getOpenParkers() {
		return openParkers.get();
	}
	
	public LongProperty openParkersProperty() {
		return openParkers;
	}
	
	public void setOpenParkers(long openParkers) {
		this.openParkers.set(openParkers);
	}
	
	public long getPassParkers() {
		return passParkers.get();
	}
	
	public LongProperty passParkersProperty() {
		return passParkers;
	}
	
	public void setPassParkers(long passParkers) {
		this.passParkers.set(passParkers);
	}
	
	public long getDisabledParkers() {
		return disabledParkers.get();
	}
	
	public LongProperty disabledParkersProperty() {
		return disabledParkers;
	}
	
	public void setDisabledParkers(long disabledParkers) {
		this.disabledParkers.set(disabledParkers);
	}
	
	public ObservableList<Report> getReports() {
		return reports;
	}
	
	public ObservableList<Agent> getAgents() {
		return agents;
	}
	
	public void setAgents(ObservableList<Agent> agents) {
		this.agents = agents;
	}
	
	public long getOpenSpaces() {
		return openSpaces.get();
	}
	
	public LongProperty openSpacesProperty() {
		return openSpaces;
	}
	
	public void setOpenSpaces(long openSpaces) {
		this.openSpaces.set(openSpaces);
	}
}
