package com.grebennikovas.viewpoint.chart;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.grebennikovas.viewpoint.utils.AggFunction;
import com.grebennikovas.viewpoint.utils.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChartSettings implements Serializable {
//    @JsonProperty("xColumns")
//    List<String> xColumns;
//    @JsonProperty("yColumns")
//    List<String> yColumns;
    // Название столбца
    @JsonProperty("dimensions")
    List<Column> dimensions;

    @JsonProperty("metrics")
    List<Column> metrics;

    @JsonProperty("xAxis")
    Column xAxis;

    @JsonProperty("where")
    String where;
//    @JsonProperty("groupBy")
//    List<String> groupBy;
    @JsonProperty("having")
    String having;
    @JsonProperty("orderBy")
    List<Column> orderBy;
    @JsonProperty("desc")
    Boolean desc;
    @JsonProperty("limit")
    Integer limit;

}
