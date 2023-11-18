package com.grebennikovas.viewpoint.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto для списков (короткий)
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardShortDto {

    /** Id дашборда */
    private Long id;

    /** Название дашборда */
    private String name;

    /** Описание дашборда */
    private String description;

}
