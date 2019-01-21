package nl.cityparking.garfield.simulator;

import nl.cityparking.garfield.simulator.agent.Agent;
import nl.cityparking.garfield.simulator.agent.AgentGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AgentManager {
	private ArrayList<Agent> agents = new ArrayList<>();
	private ArrayList<Agent> commuters = new ArrayList<>();

	/**
	 * TODO: Generates roughly 100 agents at the moment, this will not do.
	 */
	public AgentManager() {
		AgentGenerator generator = new AgentGenerator();

		for (int i = 0; i < 600; i++) {
			Agent a = generator.generate();
			agents.add(a);

			if (a.isEmployed()) {
				commuters.add(a);
			}
		}

		commuters.sort(Comparator.comparingLong(a -> a.getEmployment().getNextWorkHour(0).getStartHour()));
	}

	public ArrayList<Agent> getAgents() {
		return agents;
	}

	public ArrayList<Agent> getCommuters() {
		return commuters;
	}
}
