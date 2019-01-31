package nl.cityparking.garfield.gui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import nl.cityparking.garfield.simulator.agent.Agent;

import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;
import java.util.Locale;

public class AgentInspector {
	@FXML
	private Label agentNameLabel;
	
	@FXML
	private Label agentEmploymentLabel;
	
	@FXML
	private Label agentWealthLabel;
	
	private ObjectProperty<Agent> agent = new SimpleObjectProperty<>(null);
	
	@FXML
	private void initialize() {
		agent.addListener((observableValue, oldAgent, newAgent) -> {
			if (newAgent != null) {
				agentNameLabel.setText(newAgent.getName());
				agentEmploymentLabel.setText(newAgent.getJobTitle() + " at " + newAgent.getEmployer());
				agentWealthLabel.setText(NumberFormat.getCurrencyInstance().format(newAgent.getWealth()));
			}
		});
	}
	
	public Agent getAgent() {
		return agent.get();
	}
	
	public ObjectProperty<Agent> agentProperty() {
		return agent;
	}
	
	public void setAgent(Agent agent) {
		this.agent.set(agent);
	}
}
