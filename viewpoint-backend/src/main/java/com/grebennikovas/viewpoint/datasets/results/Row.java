package com.grebennikovas.viewpoint.datasets.results;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Map;

public class Row {
    private Map<String, Entry> entries;

    @JsonValue
    public Map<String, Entry> getEntries() {
        return entries;
    }

    public void setEntries(Map<String, Entry> entries) {
        this.entries = entries;
    }
}
