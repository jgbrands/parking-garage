package nl.cityparking.garfield.gui.chart;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import nl.cityparking.garfield.simulator.math.NormalDistribution;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.fx.ChartViewer;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class DistributionChart {
	private NormalDistribution normalDistribution;
	private ChartViewer chartViewer;
	private boolean useRescale = false;

	public Slider meanSlider;
	public TextField meanValue;
	public VBox vbox;
	public Slider sdSlider;
	public TextField sdValue;
	public ToggleGroup scaleFuncGroup;
	public RadioButton rbScaleFuncNone;
	public RadioButton rbScaleFuncRescaleLog;

	@FXML
	private void initialize() {
		this.normalDistribution = new NormalDistribution(0.5, 0.9);

		// Generate the chart.
		JFreeChart chart = this.createChart(this.createDataset());
		this.chartViewer = new ChartViewer(chart);
		this.chartViewer.setVisible(true);
		this.chartViewer.setMinHeight(300);
		this.chartViewer.setBackground(null);

		this.vbox.getChildren().add(0, this.chartViewer);
		VBox.setVgrow(this.chartViewer, Priority.ALWAYS);

		this.meanValue.setText(Double.toString(this.normalDistribution.getMean()));
		this.meanSlider.setValue(this.normalDistribution.getMean());
		this.sdValue.setText(Double.toString(this.normalDistribution.getStandardDeviation()));
		this.sdSlider.setValue(this.normalDistribution.getStandardDeviation());

		// Set listeners
		this.meanSlider.valueProperty().addListener((ov, oldValue, newValue) -> this.onMeanSliderChange(newValue.doubleValue()));
		this.meanValue.textProperty().addListener(((ov, oldValue, newValue) -> {
			try {
				double value = Double.parseDouble(newValue);
				this.onMeanSliderChange(value);
			} catch (Exception e) {
				// Do nothing
			}
		}));

		this.sdSlider.valueProperty().addListener((ov, oldValue, newValue) -> this.onStandardDeviationChange(newValue.doubleValue()));
		this.sdValue.textProperty().addListener((ov, oldValue, newValue) -> {
			try {
				double value = Double.parseDouble(newValue);
				this.onStandardDeviationChange(value);
			} catch (Exception e) {
				// Do nothing
			}
		});

		this.scaleFuncGroup.selectedToggleProperty().addListener((ov, oldToggle, newToggle) -> {
			if (newToggle == rbScaleFuncNone) {
				this.setUseRescale(false);
			} else {
				this.setUseRescale(true);
			}
		});
	}

	private void onMeanSliderChange(Double value) {
		this.meanValue.setText(Double.toString(value));
		this.normalDistribution = new NormalDistribution(value, this.normalDistribution.getStandardDeviation());
		this.updateChart();
	}

	private void onStandardDeviationChange(Double value) {
		this.sdValue.setText(Double.toString(value));
		this.normalDistribution = new NormalDistribution(this.normalDistribution.getMean(), value);
		this.updateChart();
	}

	public void setUseRescale(boolean useRescale) {
		this.useRescale = useRescale;
		this.updateChart();
	}

	private void updateChart() {
		this.chartViewer.setChart(this.createChart(this.createDataset()));
	}

	private CategoryDataset createDataset() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (double x = 0.0; x < 1.001; x += 0.01) {
			if (this.useRescale) {
				dataset.addValue(this.normalDistribution.rescaledValue(x), "Value", Double.toString(x));
			} else {
				dataset.addValue(this.normalDistribution.value(x), "value", Double.toString(x));
			}
		}

		return dataset;
	}

	private JFreeChart createChart(CategoryDataset dataset) {
		JFreeChart chart = ChartFactory.createLineChart("Distribution",
				"x", "Value",
				dataset,
				PlotOrientation.VERTICAL, false, true, false);

		chart.getCategoryPlot().getDomainAxis().setVisible(false);
		chart.setBackgroundPaint(null);

		if (this.useRescale) {
			chart.addSubtitle(new TextTitle("Rescaled (log)"));
		}

		return chart;
	}
}
