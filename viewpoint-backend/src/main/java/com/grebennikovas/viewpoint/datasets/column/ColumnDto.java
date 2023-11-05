package com.grebennikovas.viewpoint.datasets.column;

import com.grebennikovas.viewpoint.datasets.column.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColumnDto {

    private Long id;

    private String name;

    private String type;

}
