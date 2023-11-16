package com.grebennikovas.viewpoint.datasets.column;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.grebennikovas.viewpoint.datasets.column.Column;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ColumnDto {

    private Long datasetId;

    private String name;

    private String type;

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
