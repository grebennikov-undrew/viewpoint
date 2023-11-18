package com.grebennikovas.viewpoint.chart.processor;

import com.grebennikovas.viewpoint.chart.Chart;
import com.grebennikovas.viewpoint.chart.ChartSettings;
import com.grebennikovas.viewpoint.datasets.column.ColumnDto;
import com.grebennikovas.viewpoint.utils.SqlBuilder;
import com.grebennikovas.viewpoint.utils.SqlUtils;

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
    }

    public String buildQuery(Chart chart, List<ColumnDto> columnFilters) {
        // Получить запрос и настройки диаграммы
        String datasetQuery = chart.getDataset().getSqlQuery();
        ChartSettings settings = chart.getChartSettings();

        // Применить фильтры
        List<String> filters = SqlUtils.getConditionsFromFilters(columnFilters);
        String queryWithFilters = applyFilters(datasetQuery,filters);

        // Построить запрос
        String chartQuery = new SqlBuilder()
                .select(settings.getDimensions())
                .fromSubQuery(queryWithFilters)
                .where(settings.getWhere())
                .orderBy(settings.getOrderBy(),settings.getDesc())
                .limit(settings.getLimit())
                .build();
        return chartQuery;
    }

}
