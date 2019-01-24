package nl.cityparking.garfield.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

/**
 * It is the controller for the view called: primaryView
 * @author Karlien
 */
public class PrimaryView {
	@FXML
	public TabPane primaryTabView;

	@FXML
	private BorderPane primaryLayout;

	/**
	 * Sets the main view of the screen (left side)
	 * @param node the view you want to insert
	 * @param tabLabel
	 */
	public void addMainViewTab(Node node, String tabLabel) {
		primaryTabView.getTabs().add(new Tab(tabLabel, node));
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
