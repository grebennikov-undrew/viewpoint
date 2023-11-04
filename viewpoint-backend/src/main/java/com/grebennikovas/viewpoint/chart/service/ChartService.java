package com.grebennikovas.viewpoint.chart.service;

import com.grebennikovas.viewpoint.chart.Chart;
import com.grebennikovas.viewpoint.chart.ChartDTO;
import com.grebennikovas.viewpoint.datasets.Dataset;
import com.grebennikovas.viewpoint.datasets.results.Result;
import com.grebennikovas.viewpoint.users.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ChartService {
    String getType();
    List<ChartDTO> findAll();
    Chart save(Chart chart);
    Optional<Chart> findById(Long id);
    Result getData(Long id) throws SQLException;
    Result getData(Chart chart) throws SQLException;

    static Chart mapChartDto(ChartDTO chartDTO) {
        Chart chart = new Chart();
        chart.setId(chartDTO.getId());
        chart.setName(chartDTO.getName());
        chart.setChartType(chartDTO.getChartType());
        chart.setUser(new User(chartDTO.getUser().getId()));
        chart.setDataset(new Dataset(chartDTO.getDataset().getId()));
        return chart;
    }
}
