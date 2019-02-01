package nl.cityparking.garfield;

import javafx.application.Application;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import nl.cityparking.garfield.gui.AgentOverview;
import nl.cityparking.garfield.gui.PrimaryView;
import nl.cityparking.garfield.gui.StatisticsOverview;
import nl.cityparking.garfield.gui.simulator.GarageView;
import nl.cityparking.garfield.gui.EconomicViewController;
import nl.cityparking.garfield.gui.simulator.SimulatorControls;
import nl.cityparking.garfield.simulator.Simulator;
import nl.cityparking.garfield.simulator.SimulatorService;
import nl.cityparking.garfield.simulator.agent.Agent;
import nl.cityparking.garfield.simulator.config.Configuration;
import nl.cityparking.garfield.simulator.parking.ParkingFloor;
import nl.cityparking.garfield.simulator.parking.ParkingSpaceType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ParkingGarage extends Application {
	private Simulator simulator;
	private State state = new State();
	private GarageView garageView = null;
	
	private PrimaryView primaryViewController;
	private Pane primaryView;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		try {
			URL configResource = this.getClass().getResource("/config/SimulatorConfig.xml");

			JAXBContext context = JAXBContext.newInstance(Configuration.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Configuration configuration = (Configuration) unmarshaller.unmarshal(configResource);

			simulator = new Simulator(configuration);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Thread simulatorThread = new Thread(simulator, "Simulator Thread");
		simulatorThread.start();

		// Start our simulator service, too.
		SimulatorService service = new SimulatorService(simulator);
		service.setPeriod(Duration.millis(1000.0 / 30.0));
		service.setOnSucceeded(this::updateSimulatorState);
		service.start();

		stage.setTitle("Parking Garage");

		try {
			FXMLLoader loader = this.createLoader("/views/primaryView.fxml");
			this.primaryView = loader.load();
			this.primaryViewController = loader.getController();
		} catch(Exception e) {
			e.printStackTrace();
		}

		if (this.primaryView != null) {
			Scene scene = new Scene(this.primaryView, 1280, 720);
			stage.setScene(scene);
			scene.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());

			stage.show();
			try {
				FXMLLoader loader = this.createLoader("/views/garageView.fxml");
				Pane view = loader.load();
				garageView = loader.getController();
				garageView.setFloors((List<ParkingFloor>) simulator.getParkingManager().getFloors());
				garageView.firstQueue.setQueue(simulator.getCarQueue());
				this.primaryViewController.addMainViewTab(view, "Garage");
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				FXMLLoader loader = this.createLoader("/views/economicView.fxml");
				Pane view = loader.load();
				EconomicViewController economicViewController = loader.getController();
				this.primaryViewController.addMainViewTab(view, "Economy");
				economicViewController.setData(state.getReports());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				FXMLLoader loader = this.createLoader("/views/agentOverview.fxml");
				Pane view = loader.load();
				AgentOverview agentOverview = loader.getController();
				agentOverview.setAgents(state.getAgents());
				primaryViewController.addMainViewTab(view, "Agents");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				FXMLLoader loader = this.createLoader("/views/statisticsOverview.fxml");
				Pane view = loader.load();
				StatisticsOverview statisticsOverview = loader.getController();
				statisticsOverview.freeSpacesProperty().bind(state.openSpacesProperty());
				statisticsOverview.openParkersProperty().bind(state.openParkersProperty());
				statisticsOverview.passParkersProperty().bind(state.passParkersProperty());
				statisticsOverview.disabledParkersProperty().bind(state.disabledParkersProperty());
				primaryViewController.addMainViewTab(view, "Statistics");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				FXMLLoader loader = this.createLoader("/views/simulatorControls.fxml");
				SimulatorControls simulatorControlsController = new SimulatorControls(this.state);
				loader.setController(simulatorControlsController);
				Pane simulatorInfoView = loader.load();
				this.primaryViewController.setInfoBar(simulatorInfoView);

				simulatorControlsController.simulatorSpeedValueProperty().addListener((obs, ov, nv) -> {
					simulator.getSimulationTime().setTickSpeed(nv.longValue());
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void updateSimulatorState(WorkerStateEvent workerStateEvent) {
		state.getReports().addAll(simulator.getEconomyManager().getEconomy().getNewReports());
		state.setSimulatorMinutes(simulator.getSimulationTime().getMinutesPassed());
		state.setCarsTotalIn(simulator.getCarsIn());
		state.setCarsTotalOut(simulator.getCarsOut());
		state.setOpenSpaces(simulator.getParkingManager().getFreeSpaces());
		state.setOpenParkers(simulator.getParkingManager().getOccupation(ParkingSpaceType.OPEN));
		state.setPassParkers(simulator.getParkingManager().getOccupation(ParkingSpaceType.PASS_HOLDER_ONLY));
		state.setDisabledParkers(simulator.getParkingManager().getOccupation(ParkingSpaceType.DISABLED_ONLY));
		
		// Generate a list of differences:
		Collection<Agent> newAgents = simulator.getAgentManager().getAgents().stream()
				.filter(Predicate.not(new HashSet<>(state.getAgents())::contains))
				.collect(Collectors.toList());
		state.getAgents().addAll(newAgents);
		
		garageView.update();
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
			return new FXMLLoader(resource);
		}

		return null;
	}
}
