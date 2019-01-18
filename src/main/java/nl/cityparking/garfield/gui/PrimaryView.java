package nl.cityparking.garfield.gui;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PrimaryView {
	@FXML
	private VBox mainAnchor;

	@FXML
	private VBox secondAnchor;

	@FXML
	private void initialize() {
	}

	public void setMainView(Pane pane) {
		mainAnchor.getChildren().add(0, pane);
	}

	public void setSecondView(Pane pane) {
		secondAnchor.getChildren().add(0, pane);	}
}
