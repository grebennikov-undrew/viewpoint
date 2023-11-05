package com.grebennikovas.viewpoint.chart.service;

import com.grebennikovas.viewpoint.chart.*;
import com.grebennikovas.viewpoint.datasets.results.Result;
import com.grebennikovas.viewpoint.sources.SourceService;
import com.grebennikovas.viewpoint.utils.SqlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TableChartService implements ChartService {

    @Autowired
    ChartRepository chartRepository;
    @Autowired
    SourceService sourceService;
    @Autowired
    ChartMapper chartMapper;


    @Override
    public String getType() {
        return ChartType.TABLE.getValue();
    }


    @Override
    public List<ChartDto> findAll() {
        List<ChartDto> chartsDTO = new ArrayList<>();
        List<Chart> charts = chartRepository.findAll();
        charts.forEach(c -> chartsDTO.add(chartMapper.mapToChartDto(c)));
        return chartsDTO;
    };

    @Override
    public Chart save(Chart chart) {
        return chartRepository.save(chart);
    }

    @Override
    public Optional<Chart> findById(Long id) {
        return chartRepository.findById(id);
    }

    // Выполнение запроса для построения диаграммы
    @Override
    public Result getData(Long id) throws SQLException {
        Chart chart = chartRepository.findById(id).get();
        return getData(chart);
    }
    @Override
    public Result getData(Chart chart) throws SQLException {
        // Настройки диаграммы
        ChartSettings settings = chart.getChartSettings();
        String datasetQuery = chart.getDataset().getSqlQuery();

        String chartQuery = new SqlBuilder()
                .select(settings.getxColumns())
                .fromSubQuery(datasetQuery)
                .where(settings.getWhere())
                .orderBy(settings.getOrderBy(),false)
                .limit(settings.getLimit())
                .build();

        Result chartData = sourceService.execute(chart.getDataset().getSource().getId(),chartQuery);

        return null;
    }


}
