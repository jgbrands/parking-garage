package nl.cityparking.garfield.simulator.economy;

/**
 * class that determines all things concerning money & prices
 *
 * @author Els Boerema
 */
public class Economy {
	private static final long HOURLY_PRICE = 2;
	private static final long PASS_HOURLY_PRICE = 1;
	private static final long WEEKLY_PASS = 10;

	/**
	 * method to calculate the price to be paid by the agent
	 *
	 * @param minutes (which stands for the amount of minutes that an agent was parked
	 * @return ticket (which contains the price to be paid by the agent
	 */
	public long calculateTicket(long minutes) {
		return minutes/60 * HOURLY_PRICE;
	}

	/**
	 * Method to calculate the price to be paid by passholders
	 *
	 * @param minutes (the amount of minutes an agent was parked)
	 * @return ticket (price to be paid by passholder)
	 */
	public long calculatePassholderTicket(long minutes) {
		return minutes/60 * PASS_HOURLY_PRICE;
	}

	/**
	 * method to calculate the fine that has to be payed if the agent's passed his time
	 *
	 * @param minutes, defined by the currenttime-leavetime, defined in other classes
	 * @return integer of amount of euros that have to be payed by the agent
	 */
	public long calculateFine(long minutes) {
		return 10 + (minutes);
	}

	/**
	 * getter to return the price for a weekly pass
	 * @return int with the price for a weekly pass
	 */
	public long getWeeklyPass(){
		return WEEKLY_PASS;
	}
}
