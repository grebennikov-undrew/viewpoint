package com.grebennikovas.viewpoint.chart.processor;

import com.grebennikovas.viewpoint.chart.Chart;
import com.grebennikovas.viewpoint.chart.ChartSettings;
import com.grebennikovas.viewpoint.datasets.results.Result;
import com.grebennikovas.viewpoint.utils.Column;
import com.grebennikovas.viewpoint.utils.SqlBuilder;

import java.util.List;

public class SimpleQueryProcessor implements QueryProcessor{

    @Override
    public String buildQuery(Chart chart) {
        String datasetQuery = chart.getDataset().getSqlQuery();
        ChartSettings settings = chart.getChartSettings();

        String chartQuery = new SqlBuilder()
                .select(settings.getDimensions())
                .fromSubQuery(datasetQuery)
                .where(settings.getWhere())
                .orderBy(settings.getOrderBy(),settings.getDesc())
                .limit(settings.getLimit())
                .build();
        return chartQuery;
    };

    @Override
    public final Result pivotResult(Result result, ChartSettings settings) {
        return result;
    }

    @Override
    public boolean needPivot() {
        return false;
    }

}