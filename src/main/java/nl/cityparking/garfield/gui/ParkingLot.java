/**
 * creats the class to draw parking spots
 */
package nl.cityparking.garfield.gui;

import javafx.fxml.FXML;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;

public class ParkingLot {
	@FXML
	private AnchorPane canvasWrapper;

	@FXML
	private Canvas drawingCanvas;

	private GraphicsContext gc;
	private ArrayList<String> cars;
	private Paint paintGreen;
	private Paint paintRed;
	private Paint paintBlue;


	@FXML
	/**
	 * initialises the whole class.
	 */
	private void initialize(){
		this.paintBlue = Color.BLUE;
		this.paintGreen = Color.GREEN;
		this.paintRed = Color.RED;
		this.cars = new ArrayList<>();
		this.objects();
		this.gc  = this.drawingCanvas.getGraphicsContext2D();
		this.drawSpaces();

		this.canvasWrapper.widthProperty().addListener((observable, oldValue, newValue) -> {
			this.updateCanvasSize(newValue.doubleValue(), this.drawingCanvas.getHeight());
		});

		this.canvasWrapper.heightProperty().addListener((observable, oldValue, newValue) -> {
			this.updateCanvasSize(this.drawingCanvas.getWidth(), newValue.doubleValue());
		});
	}

	/**
	 * Set the size of the screen that must be displayed
	 * @param width
	 * @param height
	 */
	private void updateCanvasSize(double width, double height) {
		this.drawingCanvas.setHeight(height);
		this.drawingCanvas.setWidth(width);
		drawSpaces();
	}

	/**
	 * draws the parking spots on the screen
	 */
	private void drawSpaces(){
		int i = 0;
		int y = 0;
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(2);
		gc.clearRect(0, 0, this.drawingCanvas.getWidth(), this.drawingCanvas.getHeight());
		for(String auto: cars) {
			if (auto.equals("handicap")) {
				gc.setFill(paintBlue);
			}
			else if (auto.equals("standard")) {
				gc.setFill(paintGreen);
			}
			else{
				gc.setFill(paintRed);
			}
			if(i % 2 == 0){
				gc.fillRoundRect(0, 55 * y, 150, 50, 10, 10);
				gc.strokeRoundRect(0, 55 * y, 150, 50, 10, 10);
			}
			else {
				gc.fillRoundRect(155, 55 * y, 150, 50, 10, 10);
				gc.strokeRoundRect(155, 55 * y, 150, 50, 10, 10);
				y++;
			}
			i++;
		}
	}

	/**
	 * test method to visualise the parking spaces that can be drawn
	 * this method fills the arraylist
	 */
	private void objects(){
		for(int i = 0; i < 20; i++) {
			this.cars.add("handicap");
		}
		for(int i = 0; i < 30; i++){
			this.cars.add("standard");
		}
	}
}
