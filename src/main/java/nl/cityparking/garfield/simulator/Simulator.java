package nl.cityparking.garfield.simulator;

import nl.cityparking.garfield.simulator.agent.Agent;
import nl.cityparking.garfield.simulator.agent.AgentGenerator;
import nl.cityparking.garfield.simulator.config.Configuration;

import java.util.ArrayList;

public class Simulator implements Runnable {
	private Configuration conf;
    private SimulatorTime simulationTime;
    private AgentManager agentManager = new AgentManager();
    private ArrivalManager arrivalManager = new ArrivalManager();
    private CarSpawner carSpawner = new CarSpawner();

    private boolean stopping = false;
    private long carsIn = 0;

    public Simulator(Configuration configuration) {
	    this.conf = configuration;

    	this.simulationTime = new SimulatorTime(1);
	    this.simulationTime.onTick(this::onTick);
	    this.simulationTime.onMinutePassed(this::onMinutePassed);
	    this.simulationTime.onHourPassed(this::onHourPassed);
	    this.simulationTime.onDayPassed(this::onDayPassed);
	    this.simulationTime.onWeekPassed(this::onWeekPassed);

	    // Get the spawn ratio, or the default if it doesn't exist.
	    carSpawner.setSpawnRatio(conf.getSpawnRatio(0));
    }

	public void run() {
         while (!this.stopping) {
        	this.simulationTime.tick();
        }
    }

    private void onTick() {
    }

    private void onMinutePassed() {
    	ArrayList<ArrivalManager.Arrival> arrivals = arrivalManager.getArrivals(simulationTime.getMinutesPassed());
    	carsIn += arrivals.size();
    }

	private void onHourPassed() {
	}

	private void onDayPassed() {
    	carSpawner.setSpawnRatio(this.conf.getSpawnRatio(this.simulationTime.getDayOfWeek()));
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
}
