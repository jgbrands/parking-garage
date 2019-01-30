package nl.cityparking.garfield.gui.simulator;

import javafx.fxml.FXML;

import java.awt.*;

/**
 * @author Allard
 */
public class SetSettingsViewController {
	public Label passHolderAmount;
	public Label reservationAmount;
	public Label avgMonthlyIncome;

	private String amountPassHolders;
	private String amountReservations;
	private String amoutnAvgMonthlyIncome;

	@FXML
	private void initialize() {
		passHolderAmount.setText(amountPassHolders);
		reservationAmount.setText(amountReservations);
		avgMonthlyIncome.setText(amoutnAvgMonthlyIncome);
	}

	public String getAmoutnAvgMonthlyIncome() {
		return amoutnAvgMonthlyIncome;
	}

	public void setAmoutnAvgMonthlyIncome(String amoutnAvgMonthlyIncome) {
		this.amoutnAvgMonthlyIncome = amoutnAvgMonthlyIncome;
	}

	public String getAmountPassHolders() {
		return amountPassHolders;
	}

	public void setAmountPassHolders(String amountPassHolders) {
		this.amountPassHolders = amountPassHolders;
	}

	public String getAmountReservations() {
		return amountReservations;
	}

	public void setAmountReservations(String amountReservations) {
		this.amountReservations = amountReservations;
	}
}
