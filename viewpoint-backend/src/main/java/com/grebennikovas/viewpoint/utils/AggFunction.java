package com.grebennikovas.viewpoint.utils;

public enum AggFunction {
    SUM ("SUM"),
    AVG ("AVG"),
    MAX ("MAX"),
    COUNT ("COUNT");

    private String func;

    AggFunction(String func) {
        this.func = func;
    }

    public String getFunc() {
        return func;
    }
}
