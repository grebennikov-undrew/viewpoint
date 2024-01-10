package com.grebennikovas.viewpoint.datasets.column;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.grebennikovas.viewpoint.datasets.column.Column;
import lombok.*;

import java.util.List;
import java.util.Objects;

/**
 * Dto метаданных столбца
 * */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ColumnDto {

    /** Id колонки */
    private Long datasetId;

    /** Наименование колонки */
    private String name;

    /** Тип данных колонки */
    private String type;

    /** Значения фильтрации датасета по колонке */
    private List<Object> filterValues;

    // Сравнение только по name и type
    // В DTO столбцы из различных датасетов не должны повторяться
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ColumnDto columnDto = (ColumnDto) o;

        if (!Objects.equals(name, columnDto.name)) return false;
        return Objects.equals(type, columnDto.type);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
