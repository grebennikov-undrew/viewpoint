package com.grebennikovas.viewpoint.chart.processor;

import com.grebennikovas.viewpoint.chart.Chart;
import com.grebennikovas.viewpoint.chart.ChartSettings;
import com.grebennikovas.viewpoint.chart.dto.ChartResponseDto;
import com.grebennikovas.viewpoint.datasets.results.Entry;
import com.grebennikovas.viewpoint.datasets.results.Result;
import com.grebennikovas.viewpoint.datasets.results.Row;
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
        selectColumns.add(new Column(settings.getXAxis()));

        List<Column> groupByColumns = new ArrayList<>(settings.getDimensions());
        groupByColumns.add(new Column(settings.getXAxis()));

        String chartQuery = new SqlBuilder()
                .select(selectColumns)
                .fromSubQuery(datasetQuery)
                .where(settings.getWhere())
                .groupBy(groupByColumns)
                .orderBy(settings.getOrderBy(),settings.getDesc())
                .limit(settings.getLimit())
                .build();
        return chartQuery;
    }

    @Override
    public ChartResponseDto postProcess(ChartResponseDto chartResponseDto, Result result) {
        // Данные в виде листа
        List<Row> rawData = result.getRows();

        // Настройки сводной таблицы
        ChartSettings settings = chartResponseDto.getChartSettings();
        String xAxis = settings.getXAxis();
        String metric = settings.getMetrics().get(0).getLabel();
        String dimension = settings.getDimensions().size()>0 ? settings.getDimensions().get(0).getLabel() : null;

        // Расчет допустимых значений измерения
        Map<String, Object> emptyValues = rawData.stream()
                .collect(Collectors.toMap(
                        k -> k.getEntries().get(xAxis).toString(),
                        v -> 0,
                        (exist, replace) -> exist));

        // Расчет сводной таблицы
        Map<String, Map<String, Object>> actualValues = rawData.stream().
                collect(Collectors.groupingBy(
                        map -> map.getEntries().getOrDefault(dimension,new Entry("dimension")).toString(),
                        Collectors.toMap(
                                k -> k.getEntries().get(xAxis).toString(),
                                v -> v.getEntries().get(metric),
                                (existing, replacement) -> existing
                        )));

        // Заполнение пустых значений в сводной таблице
        actualValues.forEach((serie, values) -> {
            emptyValues.forEach(values::putIfAbsent);
        });

        // Создание DTO
        chartResponseDto.setColumns(new ArrayList<>(emptyValues.keySet()));
        chartResponseDto.setRows(new ArrayList<>(actualValues.keySet()));
        chartResponseDto.setData(actualValues);
        return chartResponseDto;
    }
}
