package nl.cityparking.garfield.gui.simulator;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.util.Duration;

import nl.cityparking.garfield.simulator.Simulator;
import nl.cityparking.garfield.simulator.Time;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class SimulatorControls {
	private final TimeService timeService;
	private Simulator simulator;

	@FXML
	private Label simulatorDateLabel;

	@FXML
	private Slider simulatorSpeedSlider;

	@FXML
	private Label simulatorSpeedLabel;

	public SimulatorControls(Simulator simulator) {
		this.simulator = simulator;
		timeService = new TimeService(this.simulator);
		timeService.setPeriod(Duration.seconds(1));
		timeService.setOnSucceeded(ev -> setSimulationTime((long) ev.getSource().getValue()));
	}

	@FXML
	private void initialize() {
		timeService.start();

		// Create a listener for the slider, so that we can set the speed of
		// the simulator.
		simulatorSpeedSlider.valueProperty().addListener(((ob, oldValue, newValue) -> {
			long value = newValue.longValue();

			if (value == 10) {
				simulator.getSimulationTime().setTickSpeed(0);
				simulatorSpeedLabel.setText("MAX");
			} else {
				long newSpeed = (long) Math.pow(2, value);
				simulator.getSimulationTime().setTickSpeed(Time.DEFAULT_TICK_WAIT / newSpeed);
				simulatorSpeedLabel.setText(newSpeed + "x");
			}
		}));
	}

	public void setSimulationTime(long timePassed) {
		simulatorDateLabel.setText(startDate.plusMinutes(timePassed).format(dateTimeFormatter));
	}

	private final static LocalDateTime startDate = LocalDateTime.of(1990, Month.JANUARY, 1, 0, 0);
	private final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, d MMMM, yyyy hh:mm a");

	private static class TimeService extends ScheduledService<Long> {
		private Time time;

		public TimeService(Simulator simulator) {
			time = simulator.getSimulationTime();
		}

		@Override
		protected Task<Long> createTask() {
			return new Task<>() {
				protected Long call() {
					return time.getMinutesPassed();
				}
			};
		}
	}
}
