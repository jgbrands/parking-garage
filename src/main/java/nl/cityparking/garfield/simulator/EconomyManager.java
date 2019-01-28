package nl.cityparking.garfield.simulator;

import nl.cityparking.garfield.simulator.economy.Economy;
import java.util.Collection;

/**
 * Class that manages all things concerning economy
 *
 * @author Els Boerema
 */
public class EconomyManager {
	private Economy economy = new Economy();
	private long funds = 0;

	/**
	 * Method that processes the payment for each agent, decreasing the agent's wealth, increasing the company's funds.
	 * @param departures A collection containing agents that are departing the parking garage.
	 */
	public void processPayments(Collection<Departure> departures) {
		for (Departure departure : departures) {
			long price = economy.calculateTicket(departure.parkedTime);

			if(departure.excessTime > 0) {
				price += economy.calculateFine(departure.excessTime);
			}

			departure.agent.setWealth(departure.agent.getWealth() - price);
			funds += price;
		}
	}

	/**
	 * method that returns the funds
	 * not yet in use, but put it here anyway
	 * @return The company's funds.
	 */
	public long getFunds() {
		return funds;
	}
}
