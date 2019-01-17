import gui.chart.DistributionView;
import simulator.Simulator;
import simulator.config.Configuration;
import simulator.config.SpawnRatio;
import simulator.math.NormalDistribution;

public class ParkingGarage {
	public static void main(String[] args) {
		Configuration configuration = new Configuration();

		for (int i = 0; i < 7; i++) {
			SpawnRatio spawnRatio = new SpawnRatio();
			spawnRatio.index = i;
			spawnRatio.base = 1 + 2 * i;
			spawnRatio.min = (long) Math.floor(Math.abs(Math.sin(i) / 2));
			spawnRatio.max = (long) Math.ceil(Math.abs(Math.cos(i) / 2));

			configuration.spawnRatios.add(spawnRatio);
		}

		// Create the simulator and its thread.
		Simulator simulator = new Simulator(configuration);
		Thread simulatorThread = new Thread(simulator, "Simulator Thread");
		simulatorThread.start();

		NormalDistribution nd = new NormalDistribution(0.7, 0.9);
		DistributionView chart = new DistributionView(nd, "Rescaled Normal Distribution");
		chart.pack();
		chart.setVisible(true);
	}
}
