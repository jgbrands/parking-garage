package nl.cityparking.garfield;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import nl.cityparking.garfield.gui.PrimaryView;
import nl.cityparking.garfield.gui.simulator.SimulatorControls;
import nl.cityparking.garfield.simulator.Simulator;
import nl.cityparking.garfield.simulator.config.Configuration;
import nl.cityparking.garfield.simulator.config.SpawnRatio;

import java.net.URL;

public class ParkingGarage extends Application {
	private Thread simulatorThread;
	private Simulator simulator;

	private PrimaryView primaryViewController;
	private SimulatorControls simulatorControlsController;
	private Pane primaryView;
	private Pane parkingLotView;
	private Pane distributionChartView;
	private Pane simulatorInfoView;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		Configuration configuration = new Configuration();

		for (int i = 0; i < 7; i++) {
			SpawnRatio spawnRatio = new SpawnRatio();
			spawnRatio.index = i;
			spawnRatio.base = 1 + 2 * i;

			configuration.spawnRatios.add(spawnRatio);
		}

		simulator = new Simulator(configuration);
		simulatorThread = new Thread(simulator, "Simulator Thread");
		simulatorThread.start();

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
				this.primaryViewController.setSecondView(this.parkingLotView);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				FXMLLoader loader = this.createLoader("/views/distributionChart.fxml");
				this.distributionChartView = loader.load();
				this.primaryViewController.setMainView(this.distributionChartView);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				FXMLLoader loader = this.createLoader("/views/simulatorControls.fxml");
				this.simulatorControlsController = new SimulatorControls(this.simulator);
				loader.setController(this.simulatorControlsController);
				this.simulatorInfoView = loader.load();
				this.primaryViewController.setInfoBar(this.simulatorInfoView);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void stop() {
		if (simulator != null) {
			simulator.stop();
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
