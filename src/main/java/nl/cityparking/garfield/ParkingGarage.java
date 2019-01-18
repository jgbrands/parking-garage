package nl.cityparking.garfield;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import nl.cityparking.garfield.gui.PrimaryView;
import nl.cityparking.garfield.gui.simulator.ParkingLot;
import nl.cityparking.garfield.simulator.ParkingSpace;
import nl.cityparking.garfield.simulator.Simulator;
import nl.cityparking.garfield.simulator.config.Configuration;
import nl.cityparking.garfield.simulator.config.SpawnRatio;

import java.io.IOException;
import java.net.URL;

public class ParkingGarage extends Application {
	private PrimaryView primaryViewController;
	private Pane primaryView;
	private Pane parkingLotView;
	private Pane distributionChartView;

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

		try {
			FXMLLoader loader = this.createLoader("/views/primaryView.fxml");
			this.primaryView = loader.load();
			this.primaryViewController = loader.getController();
		} catch(Exception e) {
			e.printStackTrace();
		}

		if (this.primaryView != null) {
			stage.setScene(new Scene(this.primaryView, 0, 0));
			stage.show();

			try {
				FXMLLoader loader = this.createLoader("/views/parkingLot.fxml");
				this.parkingLotView = loader.load();
				this.primaryViewController.setMainView(this.parkingLotView);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				FXMLLoader loader = this.createLoader("/views/distributionChart.fxml");
				this.distributionChartView = loader.load();
				this.primaryViewController.setSecondView(this.distributionChartView);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public FXMLLoader createLoader(String filename) {
		URL resource = this.getClass().getResource(filename);

		if (resource != null) {
			FXMLLoader loader = new FXMLLoader(resource);
			return loader;
		}

		return null;
	}
}
