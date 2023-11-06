package com.grebennikovas.viewpoint.chart.processor;

import com.grebennikovas.viewpoint.chart.Chart;
import com.grebennikovas.viewpoint.chart.ChartSettings;
import com.grebennikovas.viewpoint.chart.ChartType;
import org.springframework.stereotype.Component;

@Component
public class QueryProcessorFactory {

    public static QueryProcessor getQueryProcessor(Chart chart) {
        if (chart.getChartType() == ChartType.TABLE)
            return new SimpleQueryProcessor();
        if (chart.getChartType() == ChartType.PIE)
            return new SimpleQueryProcessor();
        if (chart.getChartType() == ChartType.LINE)
            return new PivotQueryProcessor();
        if (chart.getChartType() == ChartType.BAR)
            return new PivotQueryProcessor();
        else throw new IllegalArgumentException("Unsupported chart type");
    }
}
