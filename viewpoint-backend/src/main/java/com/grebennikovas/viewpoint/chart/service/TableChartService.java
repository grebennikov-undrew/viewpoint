package com.grebennikovas.viewpoint.chart.service;

import com.grebennikovas.viewpoint.chart.*;
import com.grebennikovas.viewpoint.datasets.Dataset;
import com.grebennikovas.viewpoint.datasets.dto.DatasetDTO;
import com.grebennikovas.viewpoint.datasets.results.Result;
import com.grebennikovas.viewpoint.sources.SourceService;
import com.grebennikovas.viewpoint.sources.connections.ConnectionFactory;
import com.grebennikovas.viewpoint.sources.connections.DbConnection;
import com.grebennikovas.viewpoint.utils.SqlBuilder;
import com.grebennikovas.viewpoint.utils.SqlUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TableChartService implements ChartService {
    @Autowired
    ChartRepository chartRepository;
    @Autowired
    SourceService sourceService;

    @Override
    public String getType() {
        return ChartType.TABLE.getValue();
    }


    @Override
    public List<ChartDTO> findAll() {
        List<ChartDTO> chartsDTO = new ArrayList<>();
        List<Chart> charts = chartRepository.findAll();
        charts.forEach(c -> chartsDTO.add(new ChartDTO(c)));
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
