package nl.cityparking.garfield.simulator;

import nl.cityparking.garfield.simulator.agent.Agent;

public class Arrival {
	public final long arrivalMinute;
	public final long departureMinute;
	public final Agent agent;

	public Arrival(long arrivalMinute, long departureMinute, Agent agent) {
		this.arrivalMinute = arrivalMinute;
		this.departureMinute = departureMinute;
		this.agent = agent;
	}
}
