package com.grebennikovas.viewpoint.utils;

public enum AggFunction {
    SUM ("SUM"),
    AVG ("AVG"),
    MIN ("MIN"),
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
