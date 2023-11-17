package com.grebennikovas.viewpoint.chart.processor;

import com.grebennikovas.viewpoint.chart.Chart;
import com.grebennikovas.viewpoint.chart.ChartSettings;
import com.grebennikovas.viewpoint.chart.dto.ChartResponseDto;
import com.grebennikovas.viewpoint.datasets.Dataset;
import com.grebennikovas.viewpoint.datasets.column.ColumnDto;
import com.grebennikovas.viewpoint.datasets.results.Result;
import com.grebennikovas.viewpoint.datasets.results.Row;
import com.grebennikovas.viewpoint.utils.SqlBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface QueryProcessor {

    String buildQuery(Chart chart);

    default ChartResponseDto postProcess(ChartResponseDto chartResponseDto, Result result) {
        List<Row> rawRows = result.getRows();
        Map<String, Map<String, Object>> newRows = new HashMap<>();

        long idx = 0L;
        for (Row rawRow: rawRows) {
            newRows.put(Long.toString(idx),rawRow.getEntries().entrySet().stream().collect(
                    Collectors.toMap(
                            k -> k.getKey(),
                            v -> v.getValue().getValue()
                    )
            ));
            idx += 1;
        }

        chartResponseDto.setColumns(new ArrayList<>(result.getColtypes().keySet()));
        chartResponseDto.setData(newRows);
        return chartResponseDto;
    }

    default String applyFilters(String sqlQuery, List<String> conditions) {
        String conditionsString = String.join(" AND ", conditions);

        String queryWithFilters = new SqlBuilder()
                .select()
                .fromSubQuery(sqlQuery)
                .where(conditionsString)
                .build();

        return queryWithFilters;
    }

    //temp
    default String buildQuery(Chart chart, List<ColumnDto> columnFilters) {
        return null;
    }

}
