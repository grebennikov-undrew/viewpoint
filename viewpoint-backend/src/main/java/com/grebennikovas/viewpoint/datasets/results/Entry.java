package com.grebennikovas.viewpoint.datasets.results;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Entry<T> {
    private T value;

    @JsonValue
    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
