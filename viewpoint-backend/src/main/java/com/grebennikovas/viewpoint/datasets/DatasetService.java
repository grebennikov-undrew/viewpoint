package com.grebennikovas.viewpoint.datasets;

import com.grebennikovas.viewpoint.datasets.column.*;
import com.grebennikovas.viewpoint.datasets.results.*;
import com.grebennikovas.viewpoint.security.ViewPointUserDetails;
import com.grebennikovas.viewpoint.sources.SourceService;
import com.grebennikovas.viewpoint.users.User;
import com.grebennikovas.viewpoint.utils.AggFunction;
import com.grebennikovas.viewpoint.utils.Alias;
import com.grebennikovas.viewpoint.utils.SqlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;

@Service
public class DatasetService {

    @Autowired
    DatasetRepository datasetRepository;
    @Autowired
    ColumnRepository columnRepository;
    @Autowired
    SourceService sourceService;
    @Autowired
    DatasetMapper datasetMapper;
    @Autowired
    ColumnMapper columnMapper;

    /**
     * Получить список датасетов
     * @return список датасетов в формте коротких DTO
     * */
    public List<DatasetDto> findAll() {
        List<DatasetDto> dsDTO = new ArrayList<>();
        List<Dataset> datasets = datasetRepository.findAll();
        datasets.forEach(d -> dsDTO.add(datasetMapper.toShortDto(d)));
        return dsDTO;
    }

    /**
     * Получить данные по датасету по id
     * @param id id датасета
     * @return данные для датасета
     * */
    public DatasetDto findById(Long id){
        Dataset ds = datasetRepository.findById(id).get();
        return datasetMapper.toDto(ds);
    }

    /**
     * Сохранить новый/измененный датасет
     * @param newDataset новый датасет
     * @param userId ID пользователя, внесший изменения
     * @return данные для датасета
     * */
    public DatasetDto save(DatasetDto newDataset, Long userId) {
        Dataset ds = datasetMapper.toEntity(newDataset);
        ds.setUser(new User(userId));
        Dataset savedDataset = datasetRepository.save(ds);;
        return datasetMapper.toDto(savedDataset);
    }

    /**
     * Удалить диаграмму по id
     * @param datasetId id датасета
     * @return сообщение об ошибке
     * */
    public void deleteById(Long datasetId) throws SQLException {
        datasetRepository.deleteById(datasetId);
    }

    // Выполнение еще не сохраненного запроса
    /**
     * Выполнение еще не сохраненного запроса
     * @param query запрос
     * @param sourceId ID источника
     * @return данные для датасета
     * */
    public Result execute(String query, Long sourceId) throws SQLException {
        return sourceService.execute(sourceId, query);
    }

    /**
     * Получить список всех колонок заданных датасетов
     * @param datasetsId список ID датасетов
     * @return список всех колонок с их типом
     * */
    public Set<ColumnDto> getColumns(Set<Long> datasetsId) {
        Set<Column> columns = columnRepository.findAllByDatasetIdIn(datasetsId);
        return columnMapper.mapToColumnsDto(columns);
    }

    /**
     * Получить допустимые значения строковой колонки
     * @param datasetId ID датасета
     * @param columnName название колонки
     * @return данные для датасета
     * */
    public List<String> getColumnValues(Long datasetId, String columnName) throws SQLException {
        // Построение запроса для получения всех значений столбца
        Dataset dataset = datasetRepository.findById(datasetId).get();
        String sqlQuery = new SqlBuilder()
                .selectDistinct(List.of(new Alias(columnName)))
                .fromSubQuery(dataset.getSqlQuery())
                .build();

        // Выполнение запроса
        Result result = sourceService.execute(dataset.getSource().getId(),sqlQuery);

        // Маппинг массива строк таблицы на массив string
        List<String> columnValues = result.getRows().stream()
                .map(row -> row.getEntries().get(columnName)
                .toString())
                .toList();

        return columnValues;
    }

    /**
     * Получить минимальное и максимальное значение столбца (для фильтра)
     * @param datasetId ID датасета
     * @param columnName название колонки
     * @return данные для датасета
     * */
    public List<Object> getColumnBounds(Long datasetId, String columnName) throws SQLException {
        // Построение запроса для получения минимума и максимума по столбцу
        Dataset dataset = datasetRepository.findById(datasetId).get();
        String sqlQuery = new SqlBuilder()
                .select(List.of(new Alias(columnName, "min_value", AggFunction.MIN), new Alias(columnName,"max_value",AggFunction.MAX)))
                .fromSubQuery(dataset.getSqlQuery())
                .build();

        // Выполнение запроса
        Result result = sourceService.execute(dataset.getSource().getId(),sqlQuery);

        // Маппинг строки результата таблицы на мин и макс
        List<Object> columnBounds = result.getRows().get(0).getEntries().values().stream()
                .map(Entry::getValue).sorted().toList();
        return columnBounds;
    }

}
