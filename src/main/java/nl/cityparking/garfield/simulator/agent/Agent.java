package nl.cityparking.garfield.simulator.agent;

import javafx.beans.property.*;

/**
 * Agent is the most granular object that is simulated. An agent in the
 * simulator is most easily represented as a person. For the sake of the
 * simulation engine however, this is irrelevant. Thus the agent can be thought
 * of as a more abstract thing.
 *
 * Agents have agency, in this sense represented as their behaviour. Their
 * behaviour decides for example how likely they are to have a parking pass
 * and for how long they will park their car, and when they are most likely to
 * spawn (arrive) at the garage. But also where they park their car, and how.
 *
 * Agents are the lifeblood of the simulator. Almost all logic depends on them
 * in some way, they are what makes everything move and seem 'alive.'
 *
 * @author Jesse
 * @since 1.0-RELEASE
 */
public class Agent {
	private StringProperty firstName;
	private StringProperty lastName;
	private ObjectProperty<Employment> employment = new SimpleObjectProperty<>(null);
	private LongProperty wealth = new SimpleLongProperty( 500);
	private ObjectProperty<ParkingPass> parkingPass = new SimpleObjectProperty<>( null);

	/**
	 * Initializes a new Agent
	 *
	 * @param firstName The first name of the Agent.
	 * @param lastName  The last name of the Agent.
	 */
	public Agent(String firstName, String lastName) {
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
	}

	/**
	 * Returns the full name of the Agent.
	 *
	 * @return The full name.
	 */
	public String getName() {
		return firstName.get() + " " + lastName.get();
	}
	
	public String getFirstName() {
		return firstName.get();
	}
	
	public StringProperty firstNameProperty() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}
	
	public String getLastName() {
		return lastName.get();
	}
	
	public StringProperty lastNameProperty() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}
	
	public long getWealth() {
		return wealth.get();
	}
	
	public LongProperty wealthProperty() {
		return wealth;
	}
	
	public void setWealth(long wealth) {
		this.wealth.set(wealth);
	}
	
	public ParkingPass getParkingPass() {
		return parkingPass.get();
	}
	
	public ObjectProperty<ParkingPass> parkingPassProperty() {
		return parkingPass;
	}
	
	public void setParkingPass(ParkingPass parkingPass) {
		this.parkingPass.set(parkingPass);
	}
	
	/**
	 * method to check whether the agent has a parkingpass
	 * @return true/false depending on whether the agent has a parkingpass
	 */
	public boolean hasParkingPass() {
		return parkingPass.get() != null;
	}
	
	public boolean setEmployment(Employment employment) {
		if (this.employment.get() == null) {
			this.employment.set(employment);
			return true;
		}
		
		return false;
	}
	
	public String getJobTitle() {
		if (isEmployed()) {
			return employment.get().position.name;
		}
		
		return null;
	}
	
	public String getEmployer() {
		if (isEmployed()) {
			return employment.get().employer.getName();
		}
		
		return null;
	}
	
	public boolean isEmployed() {
		return employment.get() != null;
	}
	
	public Schedule getSchedule() {
		return employment.get().position.getSchedule();
	}
}
