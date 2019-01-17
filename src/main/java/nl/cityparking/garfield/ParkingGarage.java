package nl.cityparking.garfield;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import nl.cityparking.garfield.simulator.Simulator;
import nl.cityparking.garfield.simulator.config.Configuration;
import nl.cityparking.garfield.simulator.config.SpawnRatio;

import java.net.URL;

public class ParkingGarage extends Application {
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

		// Create the nl.cityparking.garfield.simulator and its thread.
		Simulator simulator = new Simulator(configuration);
		Thread simulatorThread = new Thread(simulator, "Simulator Thread");
		simulatorThread.start();

		// Start the GUI
		launch(args);

		// Clean up, stop the simulator thread
		simulator.stop();
	}

	@Override
	public void start(Stage stage) {
		stage.setTitle("Parking Garage");

		// Load our views viewer.
		try {
			URL resource = this.getClass().getResource("/views/distributionChart.fxml");
			if (resource != null) {
				FXMLLoader loader = new FXMLLoader();

				Pane root = loader.load(resource.openStream());
				stage.setScene(new Scene(root, 0, 0));
				stage.show();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
