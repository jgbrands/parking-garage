package nl.cityparking.garfield.simulator;

import nl.cityparking.garfield.simulator.agent.Agent;
import nl.cityparking.garfield.simulator.config.Configuration;

import java.util.ArrayList;
import java.util.List;

public class Simulator implements Runnable {
	private Configuration conf;
    private SimulatorTime simulationTime;
    private AgentManager agentManager = new AgentManager();
    private ArrivalManager arrivalManager = new ArrivalManager();
    private ParkingManager parkingManager = new ParkingManager();

    private boolean stopping = false;
    private long carsIn = 0;
    private long carsOut = 0;

    public Simulator(Configuration configuration) {
	    this.conf = configuration;

    	this.simulationTime = new SimulatorTime(1);
	    this.simulationTime.onTick(this::onTick);
	    this.simulationTime.onMinutePassed(this::onMinutePassed);
	    this.simulationTime.onHourPassed(this::onHourPassed);
	    this.simulationTime.onDayPassed(this::onDayPassed);
	    this.simulationTime.onWeekPassed(this::onWeekPassed);

	    this.parkingManager.addFloors(3, 5, 40);
    }

	public void run() {
         while (!this.stopping) {
        	this.simulationTime.tick();
        }
    }

    private void onTick() {
    }

    private void onMinutePassed() {
    	// Phase one, get leavers:
	    List<Agent> result = parkingManager.getLeavingAgents(simulationTime.getMinutesPassed());
	    carsOut += result.size();

	    // Phase two, get arrivals:
    	ArrayList<Arrival> arrivals = arrivalManager.getArrivals(simulationTime.getMinutesPassed());
    	for (Arrival arrival: arrivals) {
    		if (parkingManager.handleArrival(arrival)) {
			    carsIn++;
		    }
	    }
    }

	private void onHourPassed() {
	}

	private void onDayPassed() {
    }

    private void onWeekPassed() {
    	arrivalManager.generate(agentManager.getCommuters(), simulationTime.getMinutesPassed());
    }

	public void stop() {
    	this.stopping = true;
	}

	public SimulatorTime getSimulationTime() {
		return simulationTime;
	}

	public long getCarsIn() {
		return carsIn;
	}

	public long getCarsOut() {
		return carsOut;
	}

	public ParkingManager getParkingManager() {
    	return parkingManager;
	}
}
