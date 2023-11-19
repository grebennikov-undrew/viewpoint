package com.grebennikovas.viewpoint.dashboard;

import com.grebennikovas.viewpoint.chart.Chart;
import com.grebennikovas.viewpoint.chart.ChartService;
import com.grebennikovas.viewpoint.chart.dto.ChartResponseDto;
import com.grebennikovas.viewpoint.dashboard.dto.*;
import com.grebennikovas.viewpoint.datasets.column.ColumnDto;
import com.grebennikovas.viewpoint.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    DashboardRepository dashboardRepository;
    @Autowired
    ChartService chartService;
    @Autowired
    DashboardMapper dashboardMapper;

    /**
     * Получить список дашбордов
     * @return список дашбордов в формте коротких DTO
     * */
    public List<DashboardShortDto> findAll() {
        List<DashboardShortDto> dashboardList = new ArrayList<>();
        List<Dashboard> dashboards = dashboardRepository.findAll();
        dashboards.forEach(c -> dashboardList.add(dashboardMapper.mapToDashboardDtoShort(c)));
        return dashboardList;
    };

    /**
     * Сохранить новый/измененный дашборд
     * @param dashboardRequestDto новый дашборд
     * @param userId пользователь, внесший изменения
     * @return данные для построения дашборда
     * */
    public DashboardResponseDto save(DashboardRequestDto dashboardRequestDto, Long userId) throws SQLException {
        // Сохранить дашборд
        Dashboard dashboard = dashboardMapper.mapToDashboard(dashboardRequestDto);
        dashboard.setUser(new User(userId));
        Dashboard savedDashboard = dashboardRepository.save(dashboard);

        // Получить данные для диаграмм
        return getData(savedDashboard);
    }

    /**
     * Удалить дашборд по id
     * @param dashboardId id дашборда
     * @return сообщение об ошибке
     * */
    public void deleteById(Long dashboardId) throws SQLException {
        dashboardRepository.deleteById(dashboardId);
    }

    /**
     * Построить дашборд по ID
     * @param dashboardId ID дашборда
     * @return данные для построения дашборда
     * */
    public DashboardResponseDto findById(Long dashboardId) throws SQLException {
        // Получить настройки дашборда
        Dashboard dashboard = dashboardRepository.findById(dashboardId).get();

        // Получить данные для диаграмм
        return getData(dashboard);
    }

    /**
     * Построить дашборд по ID с фильтрами
     * @param dashboardId ID дашборда
     * @param columnFilters список значений для фильтров
     * @return данные для построения дашборда
     * */
    public DashboardResponseDto findById(Long dashboardId, List<ColumnDto> columnFilters) throws SQLException {
        // Получить настройки дашборда
        Dashboard dashboard = dashboardRepository.findById(dashboardId).get();

        // Получить данные для диаграмм
        return getData(dashboard, columnFilters);
    }

    /**
     * Построить дашборд
     * @param dashboard дашборд
     * @return данные для построения дашборда
     * */
//    TODO: Отправлять только DTO в другие сервисы
    private DashboardResponseDto getData(Dashboard dashboard) throws SQLException {
        List<ChartResponseDto> chartData = new ArrayList<>();
        List<Chart> charts = dashboard.getCharts();
        for (Chart chart: charts) {
            chartData.add(chartService.getData(chart.getId()));
        }
        return dashboardMapper.mapToDashboardDto(dashboard, chartData);
    }

    /**
     * Построить дашборд с фильтрами
     * @param dashboard дашборд
     * @param columnFilters значения фильтров
     * @return данные для построения дашборда
     * */
    private DashboardResponseDto getData(Dashboard dashboard, List<ColumnDto> columnFilters) throws SQLException {
        List<ChartResponseDto> chartData = new ArrayList<>();
        List<Chart> charts = dashboard.getCharts();
        for (Chart chart: charts) {
            chartData.add(chartService.getData(chart.getId(), columnFilters));
        }
        return dashboardMapper.mapToDashboardDto(dashboard, chartData);
    }


}
