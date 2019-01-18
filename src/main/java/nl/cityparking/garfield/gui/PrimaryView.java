package nl.cityparking.garfield.gui;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * It is the controller for the view called: primaryView
 * @author Karlien
 */
public class PrimaryView {
	@FXML
	private VBox mainAnchor;

	@FXML
	private VBox secondAnchor;

	@FXML
	private void initialize() {
	}

	/**
	 * Sets the main view of the screen (left side)
	 * @param pane the view you want to insert
	 */
	public void setMainView(Pane pane) {
		mainAnchor.getChildren().add(0, pane);
	}

	/**
	 * Sets the second view of the screen (right side)
	 * @param pane the view you want to insert
	 */
	public void setSecondView(Pane pane) {
		secondAnchor.getChildren().add(0, pane);	}
}
