package nl.cityparking.garfield.simulator.economy;

import nl.cityparking.garfield.simulator.agent.Agent;

import java.util.Map;

/**
 * class that determines all things concerning money & prices
 *
 * contains static final ints that determine the prices and methods.
 *
 * methods: payTicket (determines how much money the Agent has to pay)
 *          calculateFine (if the Agent's overstayed his welcome, it has to pay a fine calculated by this method
 *          calculateTicket (calculates the ticket price)
 *
 * @author Els Boerema
 */
public class Economy {

	//prices
	private static final long HOURLYPRICE = 2;
	private static final long PASSHOURLYPRICE = 1;
	private static final long WEEKLYPASS = 10;

	//funds


	/**
	 * method to calculate the price to be paid by the agent
	 *
	 * @param minutes (which stands for the amount of minutes that an agent was parked
	 * @return ticket (which contains the price to be paid by the agent
	 */
	public long calculateTicket(long minutes) {
		long ticket = minutes/60 * HOURLYPRICE;
		return ticket;
	}

	/**
	 * Method to calculate the price to be paid by passholders
	 *
	 * @param minutes (the amount of minutes an agent was parked)
	 * @return ticket (price to be paid by passholder)
	 */
	public long calculatePassholderTicket(long minutes) {
		long ticket = minutes/60 * PASSHOURLYPRICE;
		return ticket;
	}

	/**
	 * method to calculate the fine that has to be payed if the agent's passed his time
	 *
	 * @param minutes, defined by the currenttime-leavetime, defined in other classes
	 * @return integer of amount of euros that have to be payed by the agent
	 */
	public long calculateFine(long minutes) {
		long fine = 10 + (minutes);
		return fine;
	}

	/**
	 * getter to return the price for a weekly pass
	 * @return int with the price for a weekly pass
	 */

	public long getWeeklyPass(){
		return WEEKLYPASS;
	}

}
