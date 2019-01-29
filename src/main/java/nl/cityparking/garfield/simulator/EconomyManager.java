package nl.cityparking.garfield.simulator;

import nl.cityparking.garfield.simulator.agent.ParkingPass;
import nl.cityparking.garfield.simulator.economy.Economy;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class that manages all things concerning economy
 *
 * @author Els Boerema
 */
public class EconomyManager {
	private Economy economy = new Economy();

	/**
	 * Method that processes the payment for each agent, decreasing the agent's wealth, increasing the company's funds.
	 *
	 * @param departures A collection containing agents that are departing the parking garage.
	 */
	public void processPayments(Collection<Departure> departures) {
		for (Departure departure : departures) {
			if (departure.agent.hasParkingPass() && departure.agent.getParkingPass().isValid()) {
				long price = economy.calculatePassholderTicket(departure.parkedTime);
				departure.agent.setWealth(departure.agent.getWealth() - price);
			} else {
				long price = economy.calculateTicket(departure.parkedTime);
				departure.agent.setWealth(departure.agent.getWealth() - price);
			}
		}
	}

	public void processArrival(Arrival arrival) {
		if (arrival.agent.hasParkingPass()) {
			if (arrival.agent.getParkingPass().isValid()) {
				//lowers the validity by one day
				arrival.agent.getParkingPass().setDaysValid(arrival.agent.getParkingPass().getDaysValid() - 1);
			} else if (!arrival.agent.getParkingPass().isValid()) {
				arrival.agent.setParkingPass(null);
			}
		}

		if (!arrival.agent.hasParkingPass()) {
			if(ThreadLocalRandom.current().nextInt(100) <= 10){
				economy.buyNewPass(arrival.agent);
				arrival.agent.setParkingPass(new ParkingPass());
			}
		}
	}

	public Economy getEconomy() {
		return economy;
	}
}
