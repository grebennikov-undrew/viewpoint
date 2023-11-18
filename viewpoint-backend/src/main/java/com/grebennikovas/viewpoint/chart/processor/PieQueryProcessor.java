package com.grebennikovas.viewpoint.chart.processor;

import com.grebennikovas.viewpoint.chart.Chart;
import com.grebennikovas.viewpoint.chart.ChartSettings;
import com.grebennikovas.viewpoint.datasets.column.ColumnDto;
import com.grebennikovas.viewpoint.utils.Alias;
import com.grebennikovas.viewpoint.utils.SqlBuilder;
import com.grebennikovas.viewpoint.utils.SqlUtils;

import java.util.ArrayList;
import java.util.List;

public class PieQueryProcessor implements QueryProcessor{

    @Override
    public String buildQuery(Chart chart) {
        String datasetQuery = chart.getDataset().getSqlQuery();
        ChartSettings settings = chart.getChartSettings();
        List<Alias> selectAliases = new ArrayList<>(settings.getDimensions());
        selectAliases.addAll(settings.getMetrics());

        String chartQuery = new SqlBuilder()
                .select(selectAliases)
                .fromSubQuery(datasetQuery)
                .where(settings.getWhere())
                .groupBy(settings.getDimensions())
                .orderBy(settings.getOrderBy(),settings.getDesc())
                .limit(settings.getLimit())
                .build();
        return chartQuery;
    };

    @Override
    public String buildQuery(Chart chart, List<ColumnDto> columnFilters) {
        String datasetQuery = chart.getDataset().getSqlQuery();
        ChartSettings settings = chart.getChartSettings();
        List<Alias> selectAliases = new ArrayList<>(settings.getDimensions());
        selectAliases.addAll(settings.getMetrics());

        // Применить фильтры
        List<String> filters = SqlUtils.getConditionsFromFilters(columnFilters);
        String queryWithFilters = applyFilters(datasetQuery,filters);

        String chartQuery = new SqlBuilder()
                .select(selectAliases)
                .fromSubQuery(queryWithFilters)
                .where(settings.getWhere())
                .groupBy(settings.getDimensions())
                .orderBy(settings.getOrderBy(),settings.getDesc())
                .limit(settings.getLimit())
                .build();
        return chartQuery;
    };

}
