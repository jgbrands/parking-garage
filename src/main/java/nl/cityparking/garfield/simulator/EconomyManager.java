package nl.cityparking.garfield.simulator;

import nl.cityparking.garfield.simulator.economy.Economy;
import java.util.Collection;

/**
 * Class that manages all things concerning economy
 *
 * @author Els Boerema
 */
public class EconomyManager {
	Economy economy = new Economy();

	long funds = 0;
	long wealth = 0;

	/**
	 * method that processes the payment for each agent
	 * decreasing the agent's wealth, increasing the company's funds
	 *
	 * @param departures : a collection containing agents that are departing the parking garage
	 */
	public void processPayments(Collection<Departure> departures) {
		long price;

		for (Departure departure : departures) {
			if(departure.excessTime > 0) {
				price = economy.calculateFine(departure.excessTime) + economy.calculateTicket(departure.parkedTime);
				funds += price;
				departure.agent.setWealth(departure.agent.getWealth() - price);
			} else {
				price = economy.calculateTicket(departure.excessTime);
				funds += price;
				departure.agent.setWealth(departure.agent.getWealth() - price);
			}
		}
	}

	/**
	 * method that returns the funds
	 * not yet in use, but put it here anyway
	 * @return long funds
	 */
	public long getFunds() {
		return funds;
	}

	/**
	 * method that returns the wealth (money an agent has)
	 * not yet in use, don't worry about this
	 * @return long wealth
	 */
	public long getWealth() { return wealth; }





}
