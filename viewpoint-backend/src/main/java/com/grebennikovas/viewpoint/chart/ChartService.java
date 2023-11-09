package com.grebennikovas.viewpoint.chart;

import com.grebennikovas.viewpoint.chart.dto.ChartDataDto;
import com.grebennikovas.viewpoint.chart.dto.ChartDto;
import com.grebennikovas.viewpoint.chart.processor.QueryProcessor;
import com.grebennikovas.viewpoint.chart.processor.QueryProcessorFactory;
import com.grebennikovas.viewpoint.datasets.results.Result;
import com.grebennikovas.viewpoint.sources.SourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;

@Service
public class ChartService {

    @Autowired
    ChartRepository chartRepository;
    @Autowired
    SourceService sourceService;
    @Autowired
    ChartMapper chartMapper;

    public List<ChartDto> findAll() {
        List<ChartDto> chartsDTO = new ArrayList<>();
        List<Chart> charts = chartRepository.findAll();
        charts.forEach(c -> chartsDTO.add(chartMapper.mapToChartDto(c)));
        return chartsDTO;
    };

    public ChartDto save(ChartDto chartDto) {
        Chart chart = chartMapper.mapToChart(chartDto);
        return chartMapper.mapToChartDto(chartRepository.save(chart));
    }

    public ChartDto findById(Long chartId) {
        Chart chart = chartRepository.findById(chartId).get();
        ChartDto chartDto = chartMapper.mapToChartDto(chart);
        return chartDto;
    }

    // Получение данных для диаграммы по ID
    public ChartDataDto getData(Long chartId) throws SQLException {
        Chart chart = chartRepository.findById(chartId).get();
        return getData(chart);
    }

    // Получение данных для диаграммы по DTO - для редактора диаграммы
    public ChartDataDto getData(ChartDto chartDto) throws SQLException {
        Chart chart = chartMapper.mapToChart(chartDto);
        return getData(chart);
    }

    // // Получение данных для диаграммы по Entity - общий метод
    public ChartDataDto getData(Chart chart) throws SQLException {
        QueryProcessor queryProcessor = QueryProcessorFactory.getQueryProcessor(chart);
        String chartQuery = queryProcessor.buildQuery(chart);
        Result chartData = sourceService.execute(chart.getDataset().getSource().getId(),chartQuery);
        return queryProcessor.postProcess(chartData, chart.getChartSettings());
    }


}
