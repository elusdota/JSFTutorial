package org.shawn.tutorials.jsf;

import org.primefaces.model.chart.PieChartModel;
import org.springframework.stereotype.Component;

@Component("chart")
public class ChartController {
	
	private PieChartModel model;

	public ChartController() {
		model = new PieChartModel();
		model.set("Brand 1", 540);
		model.set("Brand 2", 325);
		model.set("Brand 3", 702);
		model.set("Brand 4", 421);
		model.setTitle("饼图");
		model.setLegendPosition("w");
	}

	public PieChartModel getModel() {
		return model;
	}

}
