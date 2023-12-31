package com.grebennikovas.viewpoint.datasets;

import com.grebennikovas.viewpoint.datasets.column.ColumnDto;
import com.grebennikovas.viewpoint.sources.SourceDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DatasetDto {

    private Long id;

    private String name;

    private String sqlQuery;

    private String user;

    @JsonProperty("source")
    private SourceDto sourceDto;

    @JsonProperty("columns")
    private Set<ColumnDto> columnsDto;

    private Date createdOn;

    private Date updatedOn;

}
