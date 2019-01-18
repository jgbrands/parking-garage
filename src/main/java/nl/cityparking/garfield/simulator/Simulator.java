package nl.cityparking.garfield.simulator;

import nl.cityparking.garfield.simulator.config.Configuration;
import nl.cityparking.garfield.simulator.config.SpawnRatio;

import java.util.concurrent.ThreadLocalRandom;

public class Simulator implements Runnable {
	private Configuration conf;
    private Time simulationTime;
    private SpawnRatio spawnRatio;

    private boolean stopping = false;

    public Simulator(Configuration configuration) {
	    this.conf = configuration;

    	this.simulationTime = new Time(1);
	    this.simulationTime.onTick(this::onTick);
	    this.simulationTime.onMinutePassed(this::onMinutePassed);
	    this.simulationTime.onDayPassed(this::onDayPassed);
	    this.simulationTime.onWeekPassed(this::onWeekPassed);

	    // Get the spawn ratio, or the default if it doesn't exist.
	    this.spawnRatio = this.conf.getSpawnRatio(0);
	    if (this.spawnRatio == null) {
	    	this.spawnRatio = new SpawnRatio();
	    }
    }

    public void run() {
         while (!this.stopping) {
        	this.simulationTime.tick();
        }
    }

    private void onTick() {
    	System.out.println("Tick!!");
    }

    private void onMinutePassed() {
    	long carsArriving = ThreadLocalRandom.current().nextLong(
    			this.spawnRatio.base - this.spawnRatio.min,
			    this.spawnRatio.base + this.spawnRatio.max + 1);
    }

    private void onDayPassed() {
    	this.spawnRatio = this.conf.getSpawnRatio(this.simulationTime.getDayOfWeek());
	    if (this.spawnRatio == null) {
		    this.spawnRatio = new SpawnRatio();
	    }
    }

    private void onWeekPassed() {
    }

	public void stop() {
    	this.stopping = true;
	}
}
