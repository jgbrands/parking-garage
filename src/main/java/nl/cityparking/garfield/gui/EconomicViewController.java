package nl.cityparking.garfield.gui;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import nl.cityparking.garfield.simulator.economy.Report;

import java.text.NumberFormat;
import java.time.LocalDate;

public class EconomicViewController {
	public LineChart<String, Number> incomeChart;
	public TextField inputField;
	public Button confirmButton;
	public TableView<Report> overView;

	private LineChart.Series<String, Number> incomeSeries = new LineChart.Series<>();
	private LineChart.Series<String, Number> expensesSeries = new LineChart.Series<>();
	private LineChart.Series<String, Number> totalSeries = new LineChart.Series<>();

	private LongProperty minutes = new SimpleLongProperty();
	private LongProperty carsIn = new SimpleLongProperty();
	private LongProperty carsOut = new SimpleLongProperty();
	private ObservableList<Report> data;

	private final static Callback<TableColumn<Report, Number>, TableCell<Report, Number>> currencyFormatFactory = tc -> new TableCell<>() {
		@Override
		protected void updateItem(Number value, boolean empty) {
			super.updateItem(value, empty);

			if (value == null || empty) {
				setText("");
			} else {
				setText(NumberFormat.getCurrencyInstance().format(value));
			}
		}
	};


	@FXML
	private void initialize() {
		TableColumn<Report, Number> total = new TableColumn<>("Total");
		TableColumn<Report, LocalDate> date = new TableColumn<>("Date");
		TableColumn<Report, Number> expense = new TableColumn<>("Expenses");
		TableColumn<Report, Number> income = new TableColumn<>("Income");

		overView.getColumns().add(date);
		overView.getColumns().add(expense);
		overView.getColumns().add(income);
		overView.getColumns().add(total);

		total.setCellFactory(currencyFormatFactory);
		income.setCellFactory(currencyFormatFactory);
		expense.setCellFactory(currencyFormatFactory);
		total.setCellValueFactory(new PropertyValueFactory<>("total"));
		income.setCellValueFactory(new PropertyValueFactory<>("income"));
		expense.setCellValueFactory(new PropertyValueFactory<>("expenses"));
		date.setCellValueFactory(new PropertyValueFactory<>("date"));

		incomeSeries.setName("Income");
		expensesSeries.setName("Expenses");
		totalSeries.setName("Total");
		incomeChart.getData().add(incomeSeries);
		incomeChart.getData().add(expensesSeries);
		incomeChart.getData().add(totalSeries);
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

	public ObservableList<Report> getData() {
		return data;
	}

	public void setData(ObservableList<Report> data) {
		this.data = data;
		overView.setItems(this.data);

		// Empty out the series so we can populate it with new data.
		incomeSeries.getData().removeAll(incomeSeries.getData());
		expensesSeries.getData().removeAll(expensesSeries.getData());
		totalSeries.getData().removeAll(totalSeries.getData());

		this.data.addListener((ListChangeListener<? super Report>) observable -> {
			while (observable.next()) {
				Report report = data.get(observable.getFrom());

				incomeSeries.getData().add(new XYChart.Data<>(report.getDate().toString(), report.getIncome()));
				expensesSeries.getData().add(new XYChart.Data<>(report.getDate().toString(), report.getExpenses()));
				totalSeries.getData().add(new XYChart.Data<>(report.getDate().toString(), report.getTotal()));
			}
		});
	}
}
