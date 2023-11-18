package com.grebennikovas.viewpoint.dashboard.dto;

import com.grebennikovas.viewpoint.chart.dto.ChartResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Dto с данными дашборда
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponseDto {

    /** Id дашборда */
    private Long id;

    /** Название дашборда */
    private String name;

    /** Описание дашборда */
    private String description;

    /** Сетка дашборда (json) */
    private String layout;

    /** Данные для диаграмм */
    private List<ChartResponseDto> charts;

}
