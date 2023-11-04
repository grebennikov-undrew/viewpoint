package com.grebennikovas.viewpoint.chart.service;

import com.grebennikovas.viewpoint.chart.Chart;
import com.grebennikovas.viewpoint.chart.ChartRepository;
import com.grebennikovas.viewpoint.chart.ChartSettings;
import com.grebennikovas.viewpoint.chart.ChartType;
import com.grebennikovas.viewpoint.datasets.results.Result;
import com.grebennikovas.viewpoint.sources.connections.ConnectionFactory;
import com.grebennikovas.viewpoint.sources.connections.DbConnection;
import com.grebennikovas.viewpoint.utils.SqlUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class TableChartService implements ChartService {
    @Autowired
    ChartRepository chartRepository;

    @Override
    public String getType() {
        return ChartType.TABLE.getValue();
    }


    @Override
    public List<Chart> findAll() {
        return chartRepository.findAll();
    };

    @Override
    public Chart save(Chart chart) {
        return chartRepository.save(chart);
    }

    @Override
    public Optional<Chart> findById(Long id) {
        return chartRepository.findById(id);
    }

    @Override
    public Result getData(Chart chart) {
//        DbConnection dbInstance = ConnectionFactory.connect(chart.getDataset().getSource());
//        String preparedQuery = prepareQuery(query, parameters, paramValues);
//        return dbInstance.execute(preparedQuery);
        return null;
    }

    // Выполнение всех необходимых операций с данным запросом
    public String prepareChartQuery(Chart chart) {
        // Настройки диаграммы
        ChartSettings settings = chart.getChartSettings();

        String sqlQuery = chart.getDataset().getSqlQuery();

        // Преобразования к строке
        String xColumns = SqlUtils.convertToString(settings.getxColumns());
        String where = settings.getWhere();
        String groupBy = SqlUtils.convertToString(settings.getGroupBy());
        String having = settings.getHaving();
        String orderBy = SqlUtils.convertToString(settings.getOrderBy());
        Integer limit = settings.getLimit();


        return null;


    }
}
