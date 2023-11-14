package com.grebennikovas.viewpoint.dashboard;

import com.grebennikovas.viewpoint.chart.Chart;
import com.grebennikovas.viewpoint.chart.ChartService;
import com.grebennikovas.viewpoint.chart.dto.ChartResponseDto;
import com.grebennikovas.viewpoint.chart.dto.ChartShortDto;
import com.grebennikovas.viewpoint.dashboard.dto.DashboardRequestDto;
import com.grebennikovas.viewpoint.dashboard.dto.DashboardResponseDto;
import com.grebennikovas.viewpoint.dashboard.dto.DashboardShortDto;
import com.grebennikovas.viewpoint.datasets.parameter.ParameterRepository;
import com.grebennikovas.viewpoint.datasets.results.Result;
import com.grebennikovas.viewpoint.sources.SourceService;
import com.grebennikovas.viewpoint.users.User;
import com.grebennikovas.viewpoint.utils.SqlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    DashboardMapper dashboardMapper;

    public List<DashboardShortDto> findAll() {
        List<DashboardShortDto> dashboardList = new ArrayList<>();
        List<Dashboard> dashboards = dashboardRepository.findAll();
        dashboards.forEach(c -> dashboardList.add(dashboardMapper.mapToDashboardDtoShort(c)));
        return dashboardList;
    };

    public DashboardResponseDto save(DashboardRequestDto dashboardRequestDto, Long userId) throws SQLException {
        // Сохранить дашборд
        Dashboard dashboard = dashboardMapper.mapToDashboard(dashboardRequestDto);
        dashboard.setUser(new User(userId));
        Dashboard savedDashboard = dashboardRepository.save(dashboard);

        // Получить данные для диаграмм
        return getData(savedDashboard);
    }

    public DashboardResponseDto findById(Long dashboardId) throws SQLException {
        // Получить настройки дашборда
        Dashboard dashboard = dashboardRepository.findById(dashboardId).get();

        // Получить данные для диаграмм
        return getData(dashboard);
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

    public DashboardResponseDto getData(Long id) throws SQLException {
        Dashboard dashboard = dashboardRepository.findById(id).get();
        return getData(dashboard);
    }

    public DashboardResponseDto getData(DashboardRequestDto dashboardRequestDto) throws SQLException {
        Dashboard dashboard = dashboardMapper.mapToDashboard(dashboardRequestDto);
        return getData(dashboard);
    }

    private DashboardResponseDto getData(Dashboard dashboard) throws SQLException {
        List<ChartResponseDto> chartData = new ArrayList<>();
        List<Chart> charts = dashboard.getCharts();
        for (Chart chart: charts) {
            chartData.add(chartService.getData(chart.getId()));
        }
        return dashboardMapper.mapToDashboardDto(dashboard, chartData);
    }


}
