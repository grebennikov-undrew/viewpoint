package com.grebennikovas.viewpoint.chart;

import com.grebennikovas.viewpoint.chart.dto.ChartResponseDto;
import com.grebennikovas.viewpoint.chart.dto.ChartRequestDto;
import com.grebennikovas.viewpoint.chart.dto.ChartShortDto;
import com.grebennikovas.viewpoint.chart.processor.QueryProcessor;
import com.grebennikovas.viewpoint.chart.processor.QueryProcessorFactory;
import com.grebennikovas.viewpoint.datasets.DatasetDto;
import com.grebennikovas.viewpoint.datasets.DatasetService;
import com.grebennikovas.viewpoint.datasets.column.ColumnDto;
import com.grebennikovas.viewpoint.datasets.results.Result;
import com.grebennikovas.viewpoint.sources.Source;
import com.grebennikovas.viewpoint.sources.SourceService;
import com.grebennikovas.viewpoint.users.User;
import com.grebennikovas.viewpoint.utils.CsvFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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
    DatasetService datasetService;
    @Autowired
    ChartMapper chartMapper;

    /**
     * Получить список всех диаграмм в формате короткиго DTO
     * @return массив диаграмм
     * */
    public List<ChartShortDto> findAll() {
        List<ChartShortDto> chartsDTO = new ArrayList<>();
        List<Chart> charts = chartRepository.findAll();
        charts.forEach(c -> chartsDTO.add(chartMapper.mapToChartDtoShort(c)));
        return chartsDTO;
    };

    /**
     * Сохранить/изменить диаграмму
     * @param chartRequestDto настройки дмаграммы
     * @param userId id пользователя, изменивший диаграмму
     * @return сохраненная диаграмма с данными
     * */
    public ChartResponseDto save(ChartRequestDto chartRequestDto, Long userId) throws SQLException {
        Chart chart = chartMapper.mapToChart(chartRequestDto);
        chart.setUser(new User(userId));
        chartRepository.save(chart);
        return getData(chartRequestDto);
    }

    /**
     * Поулчить информацию о диаграмме по id
     * @param chartId id диаграммы
     * @return диаграмма с данными
     * */
    public ChartResponseDto findById(Long chartId) throws SQLException {
        Chart chart = chartRepository.findById(chartId).get();
        return getData(chart);
    }

    /**
     * Удалить диаграмму по id
     * @param chartId id диаграммы
     * @return сообщение об ошибке
     * */
    public void deleteById(Long chartId) throws SQLException {
        chartRepository.deleteById(chartId);
    }

    /**
     * Получение данных для диаграммы по id
     * @param chartId id диаграммы
     * @return диаграмма с данными
     * */
    public ChartResponseDto getData(Long chartId) throws SQLException {
        Chart chart = chartRepository.findById(chartId).get();
        return getData(chart);
    }

    /**
     * Получение данных для диаграммы по id с фильтрами
     * @param chartId id диаграммы
     * @param columnFilters список фильтров
     * @return диаграмма с данными
     * */
    public ChartResponseDto getData(Long chartId, List<ColumnDto> columnFilters) throws SQLException {
        Chart chart = chartRepository.findById(chartId).get();
        return getData(chart, columnFilters);
    }

    /**
     * Получение данных для диаграммы по DTO - для редактора диаграммы
     * @param chartRequestDto настройки диаграммы
     * @return диаграмма с данными
     * */
    public ChartResponseDto getData(ChartRequestDto chartRequestDto) throws SQLException {
        Chart chart = chartMapper.mapToChart(chartRequestDto);
        DatasetDto datasetInfo = datasetService.findById(chart.getDataset().getId());
        chart.getDataset().setSqlQuery(datasetInfo.getSqlQuery());
        chart.getDataset().setSource(new Source(datasetInfo.getSourceDto().getId()));
        return getData(chart);
    }

    /**
     * Получение данных для диаграммы по Entity
     * @param chart настройки диаграммы
     * @return диаграмма с данными
     * */
    private ChartResponseDto getData(Chart chart) throws SQLException {
        // Получить данные о диаграмме и процессор
        ChartResponseDto chartResponseDto = chartMapper.mapToChartDto(chart);
        QueryProcessor queryProcessor = QueryProcessorFactory.getQueryProcessor(chart);

        // Построить SQL запрос по заданной стратегии и выполнить
        String chartQuery = queryProcessor.buildQuery(chart);
        Result chartData = sourceService.execute(chart.getDataset().getSource().getId(),chartQuery);
        return queryProcessor.postProcess(chartResponseDto, chartData);
    }

    /**
     * Получение данных для диаграммы по Entity с фильтрами
     * @param chart настройки диаграммы
     * @param dashboardFilters все фильтры
     * @return диаграмма с данными
     * */
    private ChartResponseDto getData(Chart chart, List<ColumnDto> dashboardFilters) throws SQLException {
        // Получить данные о диаграмме и процессор
        ChartResponseDto chartResponseDto = chartMapper.mapToChartDto(chart);
        QueryProcessor queryProcessor = QueryProcessorFactory.getQueryProcessor(chart);

        // Оставить фильтры только тех столбцов, которые есть в датасете
        Set<ColumnDto> chartColumns = datasetService.getColumns(Set.of(chart.getDataset().getId()));
        List<ColumnDto> availFilters = dashboardFilters.stream()
                .filter(chartColumns::contains).toList();

        // Построить SQL запрос по заданной стратегии и выполнить
        String chartQuery = queryProcessor.buildQuery(chart, availFilters);
        Result chartData = sourceService.execute(chart.getDataset().getSource().getId(),chartQuery);
        return queryProcessor.postProcess(chartResponseDto, chartData);
    }


}
