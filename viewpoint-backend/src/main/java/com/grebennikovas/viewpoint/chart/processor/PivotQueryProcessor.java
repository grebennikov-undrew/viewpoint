package com.grebennikovas.viewpoint.chart.processor;

import com.grebennikovas.viewpoint.chart.Chart;
import com.grebennikovas.viewpoint.chart.ChartSettings;
import com.grebennikovas.viewpoint.datasets.results.Result;
import com.grebennikovas.viewpoint.utils.AggFunction;
import com.grebennikovas.viewpoint.utils.Column;
import com.grebennikovas.viewpoint.utils.SqlBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PivotQueryProcessor implements QueryProcessor {

    List<String> rows = new ArrayList<>();
    List<String> columns = new ArrayList<>();
    AggFunction aggFunction;

    @Override
    public String buildQuery(Chart chart) {
        String datasetQuery = chart.getDataset().getSqlQuery();
        ChartSettings settings = chart.getChartSettings();

        List<Column> selectColumns = new ArrayList<>(settings.getDimensions());
        selectColumns.addAll(settings.getMetrics());

        String chartQuery = new SqlBuilder()
                .select(selectColumns)
                .fromSubQuery(datasetQuery)
                .where(settings.getWhere())
                .groupBy(settings.getDimensions())
                .orderBy(settings.getOrderBy(),settings.getDesc())
                .limit(settings.getLimit())
                .build();
        return chartQuery;
    }

    @Override
    public Result pivotResult(Result result, ChartSettings settings) {
//        Result newResult = new Result();
//        Map<Object, Map<Object, Object>> myList = result.getRows().stream().collect(
//                Collectors.groupingBy(r -> r.getEntries().get)
//        );
        return null;
    }

    @Override
    public boolean needPivot() {
        return true;
    }
}
