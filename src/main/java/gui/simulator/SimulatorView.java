package gui.simulator;

import javax.swing.*;
import java.awt.*;

public class SimulatorView extends JFrame {
	private int numberOfFloors;
	private int numberOfRows;
	private int numberOfPlaces;
	private int numberOfOpenSpots;

	public SimulatorView(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
		this.numberOfFloors = numberOfFloors;
		this.numberOfRows = numberOfRows;
		this.numberOfPlaces = numberOfPlaces;
		this.numberOfOpenSpots =numberOfFloors*numberOfRows*numberOfPlaces;

		Container contentPane = getContentPane();

		pack();
		setVisible(true);
  }
  
	public void updateView() {
	}

	public int getNumberOfFloors() {
		return numberOfFloors;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public int getNumberOfPlaces() {
		return numberOfPlaces;
	}

	public int getNumberOfOpenSpots(){
		return numberOfOpenSpots;
	}
}
