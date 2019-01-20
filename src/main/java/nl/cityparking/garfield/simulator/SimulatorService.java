package nl.cityparking.garfield.simulator;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

/**
 * SimulatorService is a scheduled service that can be run on the JavaFX
 * application thread. It polls the Simulator on regular intervals for its
 * current state.
 *
 * @author Jesse
 * @version 1.0
 */
public class SimulatorService extends ScheduledService<SimulatorState> {
	private Simulator simulator;

	public SimulatorService(Simulator simulator) {
		this.simulator = simulator;
	}

	@Override
	protected Task<SimulatorState> createTask() {
		return new Task<>() {
			@Override
			protected SimulatorState call() throws Exception {
				SimulatorState state = new SimulatorState();
				state.setSimulatorMinutes(simulator.getSimulationTime().getMinutesPassed());
				state.setCarsTotalIn(simulator.getCarsIn());
				return state;
			}
		};
	}
}
