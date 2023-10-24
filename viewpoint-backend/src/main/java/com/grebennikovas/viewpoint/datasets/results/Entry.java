package com.grebennikovas.viewpoint.datasets.results;

import com.fasterxml.jackson.annotation.JsonValue;

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
