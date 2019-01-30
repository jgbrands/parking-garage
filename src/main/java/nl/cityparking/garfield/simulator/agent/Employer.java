package nl.cityparking.garfield.simulator.agent;

import java.util.ArrayList;

public class Employer {
	private String name;
	private ArrayList<Position> positions;
	
	public Employer(String name) {
		this.name = name;
	}
	
	public void employ(Agent agent, Position position) {
		assert (agent != null);
		assert (position != null);
		
		if (positions.contains(position) && position.popVacancy()) {
			Employment employment = new Employment(this, position);
			
			if (!agent.setEmployment(employment)) {
				position.freeVacancy();
			}
		}
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<Position> getPositions() {
		return positions;
	}
}
