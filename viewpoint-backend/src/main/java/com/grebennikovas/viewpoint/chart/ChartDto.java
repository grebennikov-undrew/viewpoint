package com.grebennikovas.viewpoint.chart;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.grebennikovas.viewpoint.datasets.DatasetDto;
import com.grebennikovas.viewpoint.users.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChartDto {

    private Long id;

    private String name;

    private ChartType chartType;

    @JsonProperty("user")
    private UserDto userDto;

    @JsonProperty("dataset")
    private DatasetDto datasetDto;

    private ChartSettings chartSettings;

}
