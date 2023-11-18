package com.grebennikovas.viewpoint.chart.dto;

import com.grebennikovas.viewpoint.chart.ChartSettings;
import com.grebennikovas.viewpoint.chart.ChartType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto для построения и сохранения диаграммы
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChartRequestDto {

    /** Id диаграммы */
    private Long id;

    /** Название диаграммы */
    private String name;

    /** Тип диаграммы */
    private ChartType chartType;

    /** Id набора данных */
    private Long datasetId;

    /** Настройки диаграммы */
    private ChartSettings chartSettings;

}
