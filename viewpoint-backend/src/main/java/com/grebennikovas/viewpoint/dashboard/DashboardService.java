package com.grebennikovas.viewpoint.dashboard;

import com.grebennikovas.viewpoint.chart.Chart;
import com.grebennikovas.viewpoint.chart.ChartService;
import com.grebennikovas.viewpoint.chart.dto.ChartDataDto;
import com.grebennikovas.viewpoint.chart.dto.ChartDto;
import com.grebennikovas.viewpoint.datasets.Dataset;
import com.grebennikovas.viewpoint.datasets.DatasetRepository;
import com.grebennikovas.viewpoint.datasets.parameter.Parameter;
import com.grebennikovas.viewpoint.datasets.parameter.ParameterRepository;
import com.grebennikovas.viewpoint.datasets.results.Entry;
import com.grebennikovas.viewpoint.datasets.results.Result;
import com.grebennikovas.viewpoint.sources.Source;
import com.grebennikovas.viewpoint.sources.SourceRepository;
import com.grebennikovas.viewpoint.sources.SourceService;
import com.grebennikovas.viewpoint.sources.connections.ConnectionFactory;
import com.grebennikovas.viewpoint.sources.connections.DbConnection;
import com.grebennikovas.viewpoint.utils.SqlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DashboardService {

    @Autowired
    ParameterRepository parameterRepository;
    @Autowired
    SourceService sourceService;
    @Autowired
    DashboardRepository dashboardRepository;
    @Autowired
    ChartService chartService;

    public List<Dashboard> findAll() {
        return dashboardRepository.findAll();
    };

    public Dashboard save(Dashboard dashboard) {
        return dashboardRepository.save(dashboard);
    }

    public Dashboard findById(Long dashboardId) {
        Dashboard d = dashboardRepository.findById(dashboardId).get();
        return d;
    }

//    public List<String> getFilterValues(Long id) throws SQLException {
//        Parameter p = parameterRepository.findById(id).get();
////        Dataset ds = p.getDataset();
//        return getFilterValues(ds.getSource().getId(), p.getSqlQuery());
//    }

    public List<String> getFilterValues(Long sourceId, String sqlQuery) throws SQLException {
        Result column = sourceService.execute(sourceId,sqlQuery);
        List<String> filterOptions = SqlUtils.getFilterValues(column);
        return filterOptions;
    }

    public List<ChartDataDto> getData(Long id) throws SQLException {
        Dashboard dashboard = dashboardRepository.findById(id).get();
        List<ChartDataDto> chartData = new ArrayList<>();
        List<Chart> charts = dashboard.getCharts();
        for (Chart chart: charts) {
            chartData.add(chartService.getData(chart));
        }
        return chartData;
    }


}
