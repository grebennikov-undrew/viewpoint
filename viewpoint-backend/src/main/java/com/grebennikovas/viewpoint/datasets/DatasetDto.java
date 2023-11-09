package com.grebennikovas.viewpoint.datasets;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.grebennikovas.viewpoint.datasets.column.ColumnDto;
import com.grebennikovas.viewpoint.datasets.parameter.ParameterDto;
import com.grebennikovas.viewpoint.sources.SourceDto;
import com.grebennikovas.viewpoint.users.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DatasetDto {

    private Long id;

    private String name;

    private String sqlQuery;

    @JsonProperty("user")
    private UserDto userDto;

    @JsonProperty("source")
    private SourceDto sourceDto;

    @JsonProperty("columns")
    private List<ColumnDto> columnsDto;

//    @JsonProperty("parameters")
//    private List<ParameterDto> parametersDto;

    private Date createdOn;

    private Date updatedOn;

}
