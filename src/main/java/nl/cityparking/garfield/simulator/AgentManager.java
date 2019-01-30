package nl.cityparking.garfield.simulator;

import nl.cityparking.garfield.simulator.agent.*;
import nl.cityparking.garfield.simulator.config.EmployerConfiguration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * AgentManager handles the generation, modification and querying of Agents in the Simulation. All their logic and
 * behaviour generally speaking gets resolved and parsed here for use in the rest of the simulation. The consequence
 * of that is that while the simulation appears to be "live" and updates happening in real time, a lot of it is
 * actually precomputed and destined to happen, usually a week in advance.
 *
 * @author Jesse
 * @since 1.0
 */
public class AgentManager {
	private AgentGenerator generator = new AgentGenerator();
	private ArrayList<Employer> employers = new ArrayList<>();
	private ArrayList<Agent> agents = new ArrayList<>();
	
	public void generateAgents(int amount) {
		for (int i = 0; i < amount; i++) {
			agents.add(generator.generate());
		}
	}

	public void generateEmployers(Collection<EmployerConfiguration> configurations) {
		for (EmployerConfiguration configuration: configurations) {
			Employer employer = new Employer(configuration.name);
			
			for (EmployerConfiguration.Position positionConfig: configuration.positions) {
				Position position = new Position(positionConfig.name);
				position.setTotalVacancies(positionConfig.vacancies);
				
				int dayIndex = 0;
				for (EmployerConfiguration.WorkDay workDay: positionConfig.workdays) {
					if (dayIndex >= 7) {
						break;
					}
					
					long dayFactor = (workDay.day != 0) ? workDay.day - 1 : dayIndex;
					position.getSchedule().addWorkingHours(new Schedule.Entry(
							workDay.start + 24 * dayFactor,
							workDay.end + 24 * dayFactor
					));
					
					dayIndex++;
				}
				
				employer.getPositions().add(position);
			}
			
			employers.add(employer);
		}
	}
	
	public void allocateJobsToAgents() {
		Collection<Agent> unemployedAgents = agents.parallelStream()
				.filter(a -> !a.isEmployed())
				.collect(Collectors.toList());
		
		for (Agent agent: unemployedAgents) {
			for (Employer employer: employers) {
				for (Position position: employer.getPositions()) {
					if (position.getOpenVacancies() > 0) {
						employer.employ(agent, position);
					}
				}
			}
		}
	}
	
	/**
	 * Retrieves the list of all Agents present in the simulation, including inactive ones
	 * @return Collection of all Agents
	 */
	public Collection<Agent> getAgents() {
		return agents;
	}
}
