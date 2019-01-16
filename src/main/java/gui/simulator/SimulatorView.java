package gui.simulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SimulatorView extends JFrame {
	private GridLayout layout;

	public SimulatorView(int cols){
		this.layout = new GridLayout(0, cols);

		this.setLayout(layout);
		this.setBounds(100, 100, 1000, 600);
	}
}
