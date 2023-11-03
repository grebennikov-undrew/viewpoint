package com.grebennikovas.viewpoint.chart.service;

import com.grebennikovas.viewpoint.chart.Chart;
import com.grebennikovas.viewpoint.datasets.results.Result;

import java.util.List;
import java.util.Optional;

public interface ChartService {
    String getType();
    List<Chart> findAll();
    Chart save(Chart chart);
    Optional<Chart> findById(Long id);
    Result getData(Chart chart);
}
