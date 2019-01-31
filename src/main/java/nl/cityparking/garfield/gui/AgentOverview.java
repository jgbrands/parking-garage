package nl.cityparking.garfield.gui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

import nl.cityparking.garfield.simulator.agent.Agent;

import java.io.IOException;

public class AgentOverview {
	@FXML
	private SplitPane layout;

	@FXML
	private TableView<Agent> agentsTableView;
	
	@FXML
	private TableColumn nameColumn;
	
	@FXML
	private TableColumn wealthColumn;
	
	@FXML
	private TableColumn actionColumn;
	
	private AgentInspector agentInspector;
	private Node agentInspectorView;
	
	private ObjectProperty<Agent> selectedAgent = new SimpleObjectProperty<>(null);
	
	@FXML
	private void initialize() {
		try {
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/views/agentInspector.fxml"));
			agentInspectorView = loader.load();
			agentInspector = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		agentsTableView.getSelectionModel().selectedItemProperty().addListener((observableValue, prev, selected) -> {
			selectedAgent.set(selected);
		});
		
		agentInspector.agentProperty().bind(selectedAgent);
		
		selectedAgent.addListener((observableValue, old, agent) -> {
			if (agent == null) {
				layout.getItems().removeAll(agentInspectorView);
			} else {
				if (old == null) {
					layout.getItems().add(agentInspectorView);
				}
			}
		});
	}
	
	public void setAgents(ObservableList<Agent> agents) {
		agentsTableView.setItems(agents);
}
	
	public AgentInspector getAgentInspector() {
		return agentInspector;
	}
}
