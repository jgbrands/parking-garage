package gui.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import simulator.math.NormalDistribution;

import javax.swing.*;
import java.awt.*;

public class DistributionView extends JFrame {
	private JFreeChart lineChart;
	private ChartPanel chartPanel;
	private NormalDistribution normalDistribution;

	public DistributionView(NormalDistribution normalDistribution, String title) {
		this.normalDistribution = normalDistribution;
		this.lineChart = ChartFactory.createLineChart(
				title,
				"x",
				"y",
				this.createDataset(),
				PlotOrientation.VERTICAL,
				false, true, false);


		this.chartPanel = new ChartPanel(this.lineChart);
		this.chartPanel.setPreferredSize(new Dimension(400, 300));
		this.setContentPane(this.chartPanel);

		// Hide the x-axis labels
		this.lineChart.getCategoryPlot().getDomainAxis().setVisible(false);
	}

	private DefaultCategoryDataset createDataset() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (double x = 0.0; x < 1.001; x += 0.002) {
			double value = this.normalDistribution.rescaledValue(x);
			dataset.addValue(value, "value", Double.toString(value));
		}

		return dataset;
	}
}
