package com.grebennikovas.viewpoint.chart.dto;

import com.grebennikovas.viewpoint.chart.ChartType;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto для списков (короткая версия)
 * */
@Data
@NoArgsConstructor
public class ChartShortDto {

    /** Id диаграммы */
    private Long id;

    /** Название диаграммы */
    private String name;

    /** Тип диаграммы */
    private ChartType chartType;

    /** Автор диаграммы */
    private String username;

    /** Название набора данных */
    private String datasetName;

    /** Название источника данных */
    private String sourceName;
}
