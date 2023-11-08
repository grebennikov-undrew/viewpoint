package com.grebennikovas.viewpoint.chart;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.grebennikovas.viewpoint.utils.AggFunction;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChartSettings implements Serializable {
    @JsonProperty("xColumns")
    List<String> xColumns;
    @JsonProperty("yColumns")
    List<String> yColumns;
    @JsonProperty("where")
    String where;
    @JsonProperty("groupBy")
    List<String> groupBy;
    @JsonProperty("having")
    String having;
    @JsonProperty("orderBy")
    List<String> orderBy;
    @JsonProperty("desc")
    Boolean desc;
    @JsonProperty("limit")
    Integer limit;
    @JsonProperty("aggFunction")
    AggFunction aggFunction;

}
