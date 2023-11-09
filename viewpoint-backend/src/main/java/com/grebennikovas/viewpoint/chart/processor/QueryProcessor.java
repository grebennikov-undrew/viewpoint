package com.grebennikovas.viewpoint.chart.processor;

import com.grebennikovas.viewpoint.chart.Chart;
import com.grebennikovas.viewpoint.chart.ChartSettings;
import com.grebennikovas.viewpoint.datasets.results.Result;

public interface QueryProcessor {

    String buildQuery(Chart chart);

    Result pivotResult(Result result, ChartSettings settings);

    boolean needPivot();

}
