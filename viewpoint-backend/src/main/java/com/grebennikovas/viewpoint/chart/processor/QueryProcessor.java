package com.grebennikovas.viewpoint.chart.processor;

import com.grebennikovas.viewpoint.chart.Chart;
import com.grebennikovas.viewpoint.chart.ChartSettings;
import com.grebennikovas.viewpoint.chart.dto.ChartDataDto;
import com.grebennikovas.viewpoint.datasets.results.Result;
import com.grebennikovas.viewpoint.datasets.results.Row;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface QueryProcessor {

    String buildQuery(Chart chart);

    default ChartDataDto postProcess(Result result, ChartSettings settings) {
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

        ChartDataDto chartDataDto = new ChartDataDto();
        chartDataDto.setColumns(new ArrayList<>(result.getColtypes().keySet()));
        chartDataDto.setData(newRows);
        return chartDataDto;
    }

}
