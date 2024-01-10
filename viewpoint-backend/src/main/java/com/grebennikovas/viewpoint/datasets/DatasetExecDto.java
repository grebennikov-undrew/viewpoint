package com.grebennikovas.viewpoint.datasets;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto для выполнения SQL-запроса в редакторе
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatasetExecDto {

    /** SQL-Запрос */
    @NotBlank
    private String sqlQuery;

    /** Id источника (БД) */
    @NotNull
    private Long sourceId;

}
