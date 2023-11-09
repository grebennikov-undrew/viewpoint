package com.grebennikovas.viewpoint.chart.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class ChartData {
    // Map - Серия: строки
    // List - Список строк
    // Map - атрибуты строки (атрибут: значение)
    Map<Object, List<Map<String, Object>>> data;

}
