package com.grebennikovas.viewpoint.datasets.parameter;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParameterDto {

    private Long id;

    private String name;

    private String type;

    private String sqlQuery;

    private Long sourceId;

}
