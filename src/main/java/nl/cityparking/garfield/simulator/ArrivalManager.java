package nl.cityparking.garfield.simulator;

import nl.cityparking.garfield.simulator.agent.Agent;
import nl.cityparking.garfield.simulator.agent.Employment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ArrivalManager {
	private ArrayList<Arrival> arrivals;
	private int index = 0;
	private long nextArrival = 0;


	public void generate(ArrayList<Agent> commuters, long startOfWeek) {
		arrivals = new ArrayList<>();
		index = 0;
		nextArrival = 0;
		int[] randomarray = {1, 2, 3, 4, 5, 6};

		for (Agent agent: commuters) {
			Employment employment = agent.getEmployment();
			agent.updateWealth(); //I put this here to give the agents weekly earnings!

			// For each work day, we want to add an arrival.
			for (Employment.WorkingHours workingHours: employment.getWorkingHours()) {
				long adjustedArrivalMinute = workingHours.getStartHour() * 60 - ThreadLocalRandom.current().nextLong(60);
				long adjustedDepartureMinute = workingHours.getEndHour() * 60 - ThreadLocalRandom.current().nextLong(30);
				arrivals.add(new Arrival(adjustedArrivalMinute + startOfWeek, adjustedDepartureMinute + startOfWeek, agent));
			}
		}

		arrivals.sort(Comparator.comparingLong(a -> a.arrivalMinute));
		nextArrival = arrivals.get(0).arrivalMinute;
	}

	public ArrayList<Arrival> getArrivals(long minuteOfWeek) {
		ArrayList<Arrival> arrivals = new ArrayList<>();

		while (nextArrival == minuteOfWeek && index < this.arrivals.size()) {
			Arrival arrival = this.arrivals.get(index);

			if (arrival.arrivalMinute > minuteOfWeek) {
				nextArrival = arrival.arrivalMinute;
				break;
			} else if (arrival.arrivalMinute == minuteOfWeek) {
				arrivals.add(arrival);
			}

			index++;
		}

		return arrivals;
	}
}
