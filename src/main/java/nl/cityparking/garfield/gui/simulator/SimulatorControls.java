package nl.cityparking.garfield.gui.simulator;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import nl.cityparking.garfield.simulator.SimulatorState;
import nl.cityparking.garfield.simulator.SimulatorTime;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class SimulatorControls {
	private SimulatorState state;
	private LongProperty simulatorSpeedValue = new SimpleLongProperty();

	@FXML
	private Label simulatorDateLabel;

	@FXML
	private Slider simulatorSpeedSlider;

	@FXML
	private Label simulatorSpeedLabel;

	@FXML
	private Label carsInLabel;

	@FXML
	private Label carsOutLabel;

	public SimulatorControls(SimulatorState state) {
		this.state = state;
	}

	@FXML
	private void initialize() {
		state.getSimulatorMinutesProperty().addListener(((obs, ov, nv) -> {
			simulatorDateLabel.setText(startDate.plusMinutes(nv.longValue()).format(dateTimeFormatter));
		}));

		simulatorSpeedSlider.valueProperty().addListener((obs, ov, nv) -> {
			if (nv.longValue() == 10) {
				simulatorSpeedLabel.setText("MAX");
				simulatorSpeedValue.set(0);
			} else {
				long simulatorSpeed = SimulatorTime.DEFAULT_TICK_WAIT / (long) Math.pow(2, nv.longValue());
				simulatorSpeedLabel.setText((long) Math.pow(2, nv.longValue()) + "x");
				simulatorSpeedValue.set(simulatorSpeed);
			}
		});

		state.carsTotalInProperty().addListener((obs, ov, nv) -> carsInLabel.setText(nv.toString()));
		state.carsTotalOutProperty().addListener((obs, ov, nv) -> carsOutLabel.setText(nv.toString()));
	}

	private final static LocalDateTime startDate = LocalDateTime.of(1990, Month.JANUARY, 1, 0, 0);
	private final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, d MMMM, yyyy hh:mm a");

	public long getSimulatorSpeedValue() {
		return simulatorSpeedValue.get();
	}

	public LongProperty simulatorSpeedValueProperty() {
		return simulatorSpeedValue;
	}

	public void setSimulatorSpeedValue(long simulatorSpeedValue) {
		this.simulatorSpeedValue.set(simulatorSpeedValue);
	}
}
