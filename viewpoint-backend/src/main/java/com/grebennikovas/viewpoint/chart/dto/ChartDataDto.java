package com.grebennikovas.viewpoint.chart.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class ChartDataDto {
    // Map - идентификатор строки: строка
    // Map - атрибут строки : значение атрибута
    private Map<String, Map<String, Object>> data;
    private List<String> columns;
    private List<String> rows;
    private ChartDto chart;

}
