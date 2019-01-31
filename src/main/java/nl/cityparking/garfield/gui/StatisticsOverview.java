package nl.cityparking.garfield.gui;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

public class StatisticsOverview {
	@FXML
	private Label freeSpotsLabel;
	
	@FXML
	private Label openParkingLabel;
	
	@FXML
	private Label passParkingLabel;
	
	@FXML
	private Label disabledParkingLabel;
	
	@FXML
	private PieChart statisticsChart;
	
	private LongProperty freeSpaces = new SimpleLongProperty();
	private LongProperty openParkers = new SimpleLongProperty();
	private LongProperty passParkers = new SimpleLongProperty();
	private LongProperty disabledParkers = new SimpleLongProperty();
	
	private PieChart.Data vacantSpacesData = new PieChart.Data("Open Spaces", 0);
	private PieChart.Data openParkersData = new PieChart.Data("Parkers", 0);
	private PieChart.Data passParkersData = new PieChart.Data("Pass Parkers", 0);
	private PieChart.Data disabledParkersData = new PieChart.Data("Disabled Parkers", 0);
	
	private ObservableList<PieChart.Data> statisticsData = FXCollections.observableArrayList(
			vacantSpacesData,
			openParkersData,
			passParkersData,
			disabledParkersData
	);
	
	@FXML
	private void initialize() {
		vacantSpacesData.pieValueProperty().bind(freeSpaces);
		openParkersData.pieValueProperty().bind(openParkers);
		passParkersData.pieValueProperty().bind(passParkers);
		disabledParkersData.pieValueProperty().bind(disabledParkers);
		statisticsChart.setData(statisticsData);
		
		freeSpotsLabel.textProperty().bind(freeSpaces.asString());
		openParkingLabel.textProperty().bind(openParkers.asString());
		passParkingLabel.textProperty().bind(passParkers.asString());
		disabledParkingLabel.textProperty().bind(disabledParkers.asString());
	}
	
	public long getOpenParkers() {
		return openParkers.get();
	}
	
	public LongProperty openParkersProperty() {
		return openParkers;
	}
	
	public void setOpenParkers(long openParkers) {
		this.openParkers.set(openParkers);
	}
	
	public long getPassParkers() {
		return passParkers.get();
	}
	
	public LongProperty passParkersProperty() {
		return passParkers;
	}
	
	public void setPassParkers(long passParkers) {
		this.passParkers.set(passParkers);
	}
	
	public long getDisabledParkers() {
		return disabledParkers.get();
	}
	
	public LongProperty disabledParkersProperty() {
		return disabledParkers;
	}
	
	public void setDisabledParkers(long disabledParkers) {
		this.disabledParkers.set(disabledParkers);
	}
	
	public long getFreeSpaces() {
		return freeSpaces.get();
	}
	
	public LongProperty freeSpacesProperty() {
		return freeSpaces;
	}
	
	public void setFreeSpaces(long freeSpaces) {
		this.freeSpaces.set(freeSpaces);
	}
}
