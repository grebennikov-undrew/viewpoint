package com.grebennikovas.viewpoint.datasets;

import com.grebennikovas.viewpoint.datasets.column.Column;
import com.grebennikovas.viewpoint.datasets.column.ColumnMapper;
import com.grebennikovas.viewpoint.datasets.column.ColumnRepository;
import com.grebennikovas.viewpoint.datasets.column.ColumnDto;
import com.grebennikovas.viewpoint.datasets.parameter.ParameterMapper;
import com.grebennikovas.viewpoint.datasets.parameter.ParameterRepository;
import com.grebennikovas.viewpoint.datasets.results.Entry;
import com.grebennikovas.viewpoint.datasets.results.Result;
import com.grebennikovas.viewpoint.sources.SourceService;
import com.grebennikovas.viewpoint.utils.AggFunction;
import com.grebennikovas.viewpoint.utils.Alias;
import com.grebennikovas.viewpoint.utils.SqlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

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

    // Вернуть все датасеты в виде коротких DTO
    public List<DatasetDto> findAll() {
        List<DatasetDto> dsDTO = new ArrayList<>();
        List<Dataset> datasets = datasetRepository.findAll();
        datasets.forEach(d -> dsDTO.add(datasetMapper.toShortDto(d)));
        return dsDTO;
    }

    // Вернуть информацию по датасету в расширенном DTO
    public DatasetDto findById(Long id){
        Dataset ds = datasetRepository.findById(id).get();
        return datasetMapper.toDto(ds);
    }

    // Сохранить датасет
    public DatasetDto save(DatasetDto dsDTO) {
        // Сохранение объекта Dataset
        Dataset ds = datasetMapper.toEntity(dsDTO);
        Dataset savedDataset = save(ds);

        // Сохранение всех колонок датасета
//        List<Column> columns = mapColumnsDTO(dsDTO.getColumns(), savedDataset);
//        List<Column> savedColumns = upsertColumns(columns, savedDataset);
//
//        // Сохранение всех параметров датасета
//        List<Parameter> params = mapParameterDTO(dsDTO.getParameters(), savedDataset);
//        List<Parameter> savedParams = upsertParameters(params, ds);
//
//        // Преобразование в DTO
//        DatasetDto savedDatasetDto = mapDataset(savedDataset, savedColumns, savedParams);
        return datasetMapper.toDto(savedDataset);
    }

    // Выполнение еще не сохраненного запроса
    public Result execute(String query, Long sourceId) throws SQLException {
        return sourceService.execute(sourceId, query);
    }

    // Сохранение датасета в таблицу
    public Dataset save(Dataset ds) {
        return datasetRepository.save(ds);
    }

    // Получить набор столбцов заданных датасетов
    public Set<ColumnDto> getColumns(Set<Long> datasetsId) {
        Set<Column> columns = columnRepository.findAllByDatasetIdIn(datasetsId);
        return columnMapper.mapToColumnsDto(columns);
    }

    // Получить список всех значений колонки
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

    // Получить минимальное и максимальное значение столбца
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

    // Сохранение списка колонок в БД и удаление убранных
//    public List<Column> upsertColumns(List<Column> newColumns, Dataset ds) {
//
//        // Поиск старых параметров
//        List<Column> oldColumns = columnRepository.findAllByDataset_id(ds.getId());
//        List<Column> addColumns = new ArrayList<>();
//
//        for (Column newColumn: newColumns) {
//            // Поиск имени в существующих параметрах
//            for (Column oldColumn : oldColumns) {
//                if (oldColumn.getName().equals(newColumn.getName())) {
//                    // Если параметр с таким именем существует
//                    newColumn.setId(oldColumn.getId());
//                    oldColumns.remove(oldColumn);
//                    break;
//                }
//            }
//            addColumns.add(newColumn);
//        }
//        // Удалить параметры, которых нет в новом списке
//        columnRepository.deleteAll(oldColumns);
//        return columnRepository.saveAll(addColumns);
//    }



    // Сохранение нового списка параметров в БД и удаление не используемых
//    public List<Parameter> upsertParameters(List<Parameter> newParams, Dataset ds) {
//        // Поиск старых параметров
//        List<Parameter> oldParameters = parameterRepository.findAllByDataset_id(ds.getId());
//        List<Parameter> addParameters = new ArrayList<>();
//
//        for (Parameter newParam: newParams) {
//            // Поиск имени в существующих параметрах
//            for (Parameter oldParam : oldParameters) {
//                if (oldParam.getName().equals(newParam.getName())) {
//                    // Если параметр с таким именем существует
//                    newParam.setId(oldParam.getId());
//                    oldParameters.remove(oldParam);
//                    break;
//                }
//            }
//            addParameters.add(newParam);
//        }
//        // Удалить параметры, которых нет в новом списке
//        parameterRepository.deleteAll(oldParameters);
//        return parameterRepository.saveAll(addParameters);
//    }
}
