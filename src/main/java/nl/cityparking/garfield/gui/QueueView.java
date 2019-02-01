package nl.cityparking.garfield.gui;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import nl.cityparking.garfield.simulator.Arrival;
import nl.cityparking.garfield.simulator.agent.Agent;
import nl.cityparking.garfield.simulator.parking.CarQueue;

import java.util.Collection;
import java.util.Objects;

public class QueueView extends Canvas {
	private CarQueue queue = null;
	private DoubleProperty elementHeight = new SimpleDoubleProperty(5);
	
	public void update() {
		setHeight(queue.getMaxSize() * elementHeight.get() + 2);
		GraphicsContext gc = getGraphicsContext2D();
		
		double x = 0.5, y = 0.5;
		int count = 0;
		
		gc.clearRect(0, 0, getWidth(), getHeight());
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1.0);
		
		Collection<Arrival> arrivals = queue.get();
		
		for (Arrival arrival: arrivals) {
			gc.setFill(getAgentPaint(arrival.agent));
			
			y = drawElement(gc, x, y);
			count++;
		}
		
		gc.setFill(Color.WHITE);
		
		for (; count < queue.getMaxSize(); count++) {
			y = drawElement(gc, x, y);
		}
	}
	
	private double drawElement(GraphicsContext gc, double x, double y) {
		gc.fillRect(x, y, getWidth() - 2, elementHeight.get());
		gc.strokeRect(x, y, getWidth() - 2, elementHeight.get());
		y += elementHeight.get();
		return y;
	}
	
	private Paint getAgentPaint(Agent agent) {
		Objects.requireNonNull(agent);
		
		if (agent.hasParkingPass()) {
			return Color.RED;
		}
		
		return Color.GREEN;
	}
	
	public double getElementHeight() {
		return elementHeight.get();
	}
	
	public DoubleProperty elementHeightProperty() {
		return elementHeight;
	}
	
	public void setElementHeight(double elementHeight) {
		this.elementHeight.set(elementHeight);
	}
	
	public CarQueue getQueue() {
		return queue;
	}
	
	public void setQueue(CarQueue queue) {
		this.queue = queue;
	}
}
