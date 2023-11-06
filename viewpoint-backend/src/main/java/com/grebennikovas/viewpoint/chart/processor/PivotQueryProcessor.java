package com.grebennikovas.viewpoint.chart.processor;

import com.grebennikovas.viewpoint.chart.Chart;
import com.grebennikovas.viewpoint.datasets.results.Result;

import java.util.ArrayList;
import java.util.List;

public class PivotQueryProcessor implements QueryProcessor {

    List<String> rows = new ArrayList<>();
    List<String> columns = new ArrayList<>();
    AggFunction aggFunction;

    @Override
    public String buildQuery(Chart chart) {
        return null;
    }

    @Override
    public Result pivotResult(Result result) {
        return null;
    }

    @Override
    public boolean needPivot() {
        return true;
    }
}
