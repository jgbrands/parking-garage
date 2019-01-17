package nl.cityparking.garfield.simulator.family;

import java.util.ArrayList;

public class Family {
	private String name;
	private ArrayList<Person> members;
	private FamilyWealth wealth;

	public Family(String name, FamilyWealth wealth) {
		this.name = name;
		this.members = new ArrayList<>();
		this.wealth = wealth;
	}

	public void addMember(Person person) {
		this.members.add(person);
	}

	public String getName() {
		return this.name;
	}

	public ArrayList<Person> getMembers() {
		return this.members;
	}

	public FamilyWealth getWealth() {
		return wealth;
	}
}
