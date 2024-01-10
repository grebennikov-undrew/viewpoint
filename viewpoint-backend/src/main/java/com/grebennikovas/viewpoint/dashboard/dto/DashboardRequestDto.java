package com.grebennikovas.viewpoint.dashboard.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Dto для сохранения дашборда
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardRequestDto {

    /** Id дашборда */
    @Min(1)
    private Long id;

    /** Название дашборда */
    @NotBlank
    private String name;

    /** Описание дашборда */
    @NotBlank
    private String description;

    /** Список диаграм дашборда */
    private List<Long> chartsId;

    /** Разметка дашборда (blob) */
    private String layout;

    /** Список значений фильтров на дашборде */
    private Map<String, List<Object>> filterValues;

}
