package nl.cityparking.garfield.simulator.agent;

/**
 * class that defines a parkingpass with a validity of 7 days
 *
 * @author Els
 */

public class ParkingPass {
	private int daysValid = 7;

	/**
	 * get the amount of days the instance pass is valid
	 * @return the amount of days that the pass is valid
	 */
	public int getDaysValid() {
		return daysValid;
	}

	/**
	 * set the amount of days that a pass is valid to the parameter
	 * @param days : an int containing the amount of days to set the validity to
	 */
	public void setDaysValid(int days) {
		daysValid = days;
	}

	/**
	 * method to check whether a pass is valid or not
	 * @return true/false depending on the validity
	 */
	public boolean isValid() {
		return daysValid > 0;
	}

}
