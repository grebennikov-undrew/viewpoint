package com.grebennikovas.viewpoint.chart.service;

import com.grebennikovas.viewpoint.chart.Chart;
import com.grebennikovas.viewpoint.chart.ChartDto;
import com.grebennikovas.viewpoint.datasets.results.Result;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ChartService {

    String getType();
    List<ChartDto> findAll();
    Chart save(Chart chart);
    Optional<Chart> findById(Long id);
    Result getData(Long id) throws SQLException;
    Result getData(Chart chart) throws SQLException;

}
