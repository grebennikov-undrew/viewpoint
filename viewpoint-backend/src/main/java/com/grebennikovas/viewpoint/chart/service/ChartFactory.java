package com.grebennikovas.viewpoint.chart.service;

import com.grebennikovas.viewpoint.chart.ChartType;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Component
public class ChartFactory {
    @Autowired
    private List<ChartService> chartServices;

    public static Map<String, ChartService> chartServicesCache = new HashMap<>();

    @PostConstruct
    void initCache() {
        chartServices.forEach(s -> chartServicesCache.put(s.getType(),s));
    }

    public ChartService getChartService(ChartType chartType) {
        if (chartServicesCache.containsKey(chartType.getValue())) {
            return chartServicesCache.get(chartType.getValue());
        }
        throw new NoSuchElementException("No implementation for chart type");
    }
}
