package com.grebennikovas.viewpoint.chart.dto;

import com.grebennikovas.viewpoint.chart.ChartSettings;
import com.grebennikovas.viewpoint.chart.ChartType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @Min(1)
    private Long id;

    /** Название диаграммы */
    @NotBlank
    private String name;

    /** Тип диаграммы */
    @NotNull
    private ChartType chartType;

    /** Id набора данных */
    @NotNull
    private Long datasetId;

    /** Настройки диаграммы */
    private ChartSettings chartSettings;

}
