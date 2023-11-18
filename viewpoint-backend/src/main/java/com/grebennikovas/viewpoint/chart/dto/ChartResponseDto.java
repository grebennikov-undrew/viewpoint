package com.grebennikovas.viewpoint.chart.dto;

import com.grebennikovas.viewpoint.chart.ChartSettings;
import com.grebennikovas.viewpoint.chart.ChartType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Dto с данными диаграммы
 * */
@Data
@NoArgsConstructor
public class ChartResponseDto {

    /** Id диаграммы */
    private Long id;

    /** Название диаграммы */
    private String name;

    /** Тип диаграммы */
    private ChartType chartType;

    /** Автор диаграммы */
    private String username;

    /** Id набора данных */
    private Long datasetId;

    /** Название набора данных */
    private String datasetName;

    /** Настройки диаграммы */
    private ChartSettings chartSettings;

    /** Данные для построения */
    private Map<String, Map<String, Object>> data;

    /** Список столбцов по оси X */
    private List<String> columns;

    /** Список строк по оси Y */
    private List<String> rows;

}
