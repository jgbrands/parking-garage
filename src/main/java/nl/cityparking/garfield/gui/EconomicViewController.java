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
import nl.cityparking.garfield.TestModelEconomic;

import java.text.NumberFormat;
import java.time.LocalDate;

public class EconomicViewController {
	public LineChart<String, Number> incomeChart;
	public TextField inputField;
	public Button confirmButton;
	public TableView<TestModelEconomic> overView;

	private LineChart.Series<String, Number> series = new LineChart.Series<>();
	private LongProperty minutes = new SimpleLongProperty();
	private LongProperty carsIn = new SimpleLongProperty();
	private LongProperty carsOut = new SimpleLongProperty();
	private ObservableList<TestModelEconomic> data;

	private final static Callback<TableColumn<TestModelEconomic, Number>, TableCell<TestModelEconomic, Number>> currencyFormatFactory = tc -> new TableCell<>() {
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
		/**
		 *
		 * @author Allard
		 */
		TableColumn<TestModelEconomic, Number> total = new TableColumn<>("Total");
		TableColumn<TestModelEconomic, LocalDate> date = new TableColumn<>("Date");
		TableColumn<TestModelEconomic, Number> expense = new TableColumn<>("Expense");
		TableColumn<TestModelEconomic, Number> income = new TableColumn<>("Income");



		overView.getColumns().add(date);
		overView.getColumns().add(expense);
		overView.getColumns().add(income);
		overView.getColumns().add(total);

		data = FXCollections.observableArrayList(
				new TestModelEconomic(LocalDate.now()),
				new TestModelEconomic(LocalDate.now().plusDays(1)),
				new TestModelEconomic(LocalDate.now().plusDays(2)),
				new TestModelEconomic(LocalDate.now().plusDays(3))

		);

		total.setCellFactory(currencyFormatFactory);
		income.setCellFactory(currencyFormatFactory);
		expense.setCellFactory(currencyFormatFactory);
		total.setCellValueFactory(new PropertyValueFactory<>("total"));
		income.setCellValueFactory(new PropertyValueFactory<>("income"));
		expense.setCellValueFactory(new PropertyValueFactory<>("expense"));
		date.setCellValueFactory(new PropertyValueFactory<>("date"));

		data.get(0).setIncome(100);
		data.get(1).setIncome(50);
		data.get(2).setIncome(475);
		data.get(3).setIncome(178);
		data.get(0).setExpense(433);
		data.get(1).setExpense(987);
		data.get(2).setExpense(45);
		data.get(3).setExpense(17);

		overView.setItems(data);
		incomeChart.getData().add(series);
		setData(data);
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

	public ObservableList<TestModelEconomic> getData() {
		return data;
	}

	private void populateSeries(ObservableList<TestModelEconomic> data) {
		LineChart.Series<String, Number> series = new LineChart.Series<>();

		for (TestModelEconomic d : data) {
			series.getData().add(new XYChart.Data<>(d.getDate().toString(), d.getTotal()));
		}

		incomeChart.getData().add(series);
		this.series = series;
	}

	public void setData(ObservableList<TestModelEconomic> data) {
		this.data = data;

		this.data.addListener((ListChangeListener<? super TestModelEconomic>) observable -> {
			populateSeries(data);
		});

		populateSeries(data);
	}
}
