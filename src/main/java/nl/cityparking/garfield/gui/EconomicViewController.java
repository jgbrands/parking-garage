package nl.cityparking.garfield.gui;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EconomicViewController {
	public LineChart<Number, Number> incomeChart;
	public TextField inputField;
	public Button confirmButton;

	private XYChart.Series<Number, Number> series = new XYChart.Series<>();
	private int counter = 0;

	private LongProperty minutes = new SimpleLongProperty();
	private LongProperty carsIn = new SimpleLongProperty();
	private LongProperty carsOut = new SimpleLongProperty();

	@FXML
	private void initialize() {
		incomeChart.getData().add(series);

		minutes.addListener((observableValue, oldValue, newValue) -> {
			if (newValue.longValue() % 60 == 0) {
				series.getData().add(new XYChart.Data<>(newValue.longValue(), carsIn.get() - carsOut.get()));
			}
		});
	}

	public long getMinutes() {
		return minutes.get();
	}

	public LongProperty minutesProperty() {
		return minutes;
	}

	public void setMinutes(long minutes) {
		this.minutes.set(minutes);
	}

	public long getCarsIn() {
		return carsIn.get();
	}

	public LongProperty carsInProperty() {
		return carsIn;
	}

	public void setCarsIn(long carsIn) {
		this.carsIn.set(carsIn);
	}

	public long getCarsOut() {
		return carsOut.get();
	}

	public LongProperty carsOutProperty() {
		return carsOut;
	}

	public void setCarsOut(long carsOut) {
		this.carsOut.set(carsOut);
	}
}
