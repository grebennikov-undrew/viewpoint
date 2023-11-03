package com.grebennikovas.viewpoint.chart;

public enum ChartType {
    TABLE ("table"),
    BAR ("bar"),
    LINE ("line"),
    PIE ("pie");

    private String value;

    ChartType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}