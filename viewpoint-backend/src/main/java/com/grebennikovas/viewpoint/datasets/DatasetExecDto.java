package com.grebennikovas.viewpoint.datasets;

import com.grebennikovas.viewpoint.datasets.parameter.Parameter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatasetExecDto {

    private String sqlQuery;

    private Long sourceId;

}
