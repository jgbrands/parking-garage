package nl.cityparking.garfield.gui.simulator;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import nl.cityparking.garfield.simulator.parking.ParkingLot;
import nl.cityparking.garfield.simulator.parking.ParkingSpace;

/**
 * creats the class to draw parking spots
 */
public class ParkingLotController {
	private GraphicsContext gc = null;
	private Paint paintOpenSpot = Color.GREEN;;
	private Paint paintPassHolderSpot = Color.RED;
	private Paint paintDisabledSpot = Color.BLUE;;

	private ParkingLot parkingLot = null;

	private DoubleProperty spaceWidth = new SimpleDoubleProperty(30.0);
	private DoubleProperty spaceHeight = new SimpleDoubleProperty(10.0);
	private DoubleProperty spacePadding = new SimpleDoubleProperty(0);

	@FXML
	private AnchorPane canvasWrapper;

	@FXML
	private Canvas drawingCanvas;

	/**
	 * initialises the whole class.
	 */
	@FXML
	private void initialize() {
		this.gc = this.drawingCanvas.getGraphicsContext2D();
	}

	public void setParkingLot(ParkingLot parkingLot) {
		this.parkingLot = parkingLot;
	}

	/**
	 * draws the parking spots on the screen
	 */
	public void updateCanvas() {
		if (parkingLot == null) {
			return;
		}

		int i = 0;
		int x = 0;
		int y = 0;
		int rows = parkingLot.getRows();
		double width = spaceWidth.get();
		double height = spaceHeight.get();
		double padding = spacePadding.get();

		// We can easily pre-calculate our size!
		drawingCanvas.setHeight(Math.ceil(parkingLot.getSize() / (double) rows) * height + 1);
		drawingCanvas.setWidth(rows * width + 1);

		gc.setStroke(Color.BLACK);
		gc.setLineWidth(0.5);
		gc.clearRect(0, 0, this.drawingCanvas.getWidth(), this.drawingCanvas.getHeight());

		for (ParkingSpace space : parkingLot.getSpaces()) {
			switch (space.getSpaceType()) {
				case OPEN:
					gc.setFill(paintOpenSpot);
					break;

				case PASS_HOLDER_ONLY:
					gc.setFill(paintPassHolderSpot);
					break;

				case DISABLED_ONLY:
					gc.setFill(paintDisabledSpot);
					break;
			}

			if (space.isOccupied()) {
				gc.setFill(Color.WHITE);
			}

			gc.fillRect(width * x + 0.5, (height + padding) * y + 0.5, width, height);
			gc.strokeRect(width * x + 0.5, (height + padding) * y + 0.5, width, height);

			x++;

			if (i % rows == rows - 1) {
				x = 0;
				y++;
			}

			i++;
		}
	}

	public double getSpaceWidth() {
		return spaceWidth.get();
	}

	public DoubleProperty spaceWidthProperty() {
		return spaceWidth;
	}

	public void setSpaceWidth(double spaceWidth) {
		this.spaceWidth.set(spaceWidth);
	}

	public double getSpaceHeight() {
		return spaceHeight.get();
	}

	public DoubleProperty spaceHeightProperty() {
		return spaceHeight;
	}

	public void setSpaceHeight(double spaceHeight) {
		this.spaceHeight.set(spaceHeight);
	}

	public double getSpacePadding() {
		return spacePadding.get();
	}

	public DoubleProperty spacePaddingProperty() {
		return spacePadding;
	}

	public void setSpacePadding(double spacePadding) {
		this.spacePadding.set(spacePadding);
	}
}
