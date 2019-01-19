package nl.cityparking.garfield.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

/**
 * It is the controller for the view called: primaryView
 * @author Karlien
 */
public class PrimaryView {
	@FXML
	private BorderPane primaryLayout;

	@FXML
	private void initialize() {
	}

	/**
	 * Sets the main view of the screen (left side)
	 * @param node the view you want to insert
	 */
	public void setMainView(Node node) {
		primaryLayout.setCenter(node);
	}

	/**
	 * Sets the second view of the screen (right side)
	 * @param node the view you want to insert
	 */
	public void setSecondView(Node node) {
		primaryLayout.setRight(node);
	}

	public void setInfoBar(Node node) {
		primaryLayout.setBottom(node);
	}
}
