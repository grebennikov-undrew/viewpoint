package com.grebennikovas.viewpoint.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Alias implements Serializable {

    private String label;
    private String value;
    private AggFunction aggFunction;

    public Alias(String column, String label, AggFunction aggFunction) {
        this.label = label;
        this.aggFunction = aggFunction;
        this.value = column;
    }

    public Alias(String column, String label) {
        this.label = label;
        this.value = column;
    }

    public Alias(String column) {
        this.label = column;
        this.value = column;
    }

    public String getValue() {
        return "\"" + value + "\"";
    }
}
