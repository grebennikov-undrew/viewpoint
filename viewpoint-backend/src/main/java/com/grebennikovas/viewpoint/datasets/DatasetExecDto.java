package com.grebennikovas.viewpoint.datasets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatasetExecDto {

    private String sqlQuery;

    private Long sourceId;

}
