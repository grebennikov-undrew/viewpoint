package com.grebennikovas.viewpoint.datasets;

import com.grebennikovas.viewpoint.datasets.column.ColumnDto;
import com.grebennikovas.viewpoint.sources.SourceDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Dto для сохранения датасета
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DatasetDto {

    /** Id датасета */
    @Min(1)
    private Long id;

    /** Наименование датасета */
    @NotBlank
    private String name;

    /** SQL-запрос для построения датасета */
    @NotBlank
    private String sqlQuery;

    /** Создатель датасета */
    @NotBlank
    private String user;

    /** Источних данных датасета (БД) */
    @JsonProperty("source")
    @NotBlank
    private SourceDto sourceDto;

    /** Список колонок датасета (не должны повторяться) */
    @JsonProperty("columns")
    private Set<ColumnDto> columnsDto;

    /** Дата создания датасета */
    private Date createdOn;

    /** Дата обновления датасета */
    private Date updatedOn;

}
