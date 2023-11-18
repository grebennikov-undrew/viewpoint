package com.grebennikovas.viewpoint.chart;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.grebennikovas.viewpoint.utils.Alias;
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
    List<Alias> dimensions;

    @JsonProperty("metrics")
    List<Alias> metrics;

    @JsonProperty("xAxis")
    String xAxis;

    @JsonProperty("xAxisType")
    String xAxisType;

    @JsonProperty("where")
    String where;
//    @JsonProperty("groupBy")
//    List<String> groupBy;
    @JsonProperty("having")
    String having;
    @JsonProperty("orderBy")
    List<Alias> orderBy;
    @JsonProperty("desc")
    Boolean desc;
    @JsonProperty("limit")
    Integer limit;

}
