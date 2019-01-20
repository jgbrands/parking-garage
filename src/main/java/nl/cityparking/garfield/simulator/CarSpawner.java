package nl.cityparking.garfield.simulator;

import nl.cityparking.garfield.simulator.config.SpawnRatio;
import nl.cityparking.garfield.simulator.family.Car;
import nl.cityparking.garfield.simulator.math.NormalDistribution;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class CarSpawner {
	private SpawnRatio ratio;
	private NormalDistribution peak;

	public CarSpawner() {
		setSpawnRatio(new SpawnRatio());
	}

	public ArrayList<Car> spawn(long minuteOfDay) {
		ArrayList<Car> cars = new ArrayList<>();

		double base = ThreadLocalRandom.current().nextDouble(ratio.base, ratio.base + ratio.variance + 1);
		double clampedTime = minuteOfDay / (double) SimulatorTime.MINUTES_PER_DAY;
		long carsToSpawn = Math.round(base * peak.clampedRescaledValue(clampedTime) * ratio.factor);

		// TODO: Spawn algorithm, right now we only spawn regular cars.
		for (int i = 0; i < carsToSpawn; i++) {
			cars.add(new Car());
		}

		return cars;
	}

	public void setSpawnRatio(SpawnRatio ratio) {
		this.ratio = (ratio != null) ? ratio : new SpawnRatio();
		this.peak = new NormalDistribution(this.ratio.peakMean, this.ratio.peakStandardDeviation);
	}

	public SpawnRatio getSpawnRatio() {
		return ratio;
	}
}
