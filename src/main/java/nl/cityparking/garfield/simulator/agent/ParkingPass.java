package nl.cityparking.garfield.simulator.agent;

public class ParkingPass {

	//private boolean isValid = true;
	private int daysValid = 7;

	public int getDaysValid() {
		return daysValid;
	}

	public void setDaysValid(int days) {
		daysValid = days;
	}

	public boolean isValid(){
		return daysValid > 0;
	}



}
