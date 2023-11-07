package com.grebennikovas.viewpoint.datasets;

import com.grebennikovas.viewpoint.datasets.column.Column;
import com.grebennikovas.viewpoint.datasets.column.ColumnMapper;
import com.grebennikovas.viewpoint.datasets.column.ColumnRepository;
import com.grebennikovas.viewpoint.datasets.column.ColumnDto;
import com.grebennikovas.viewpoint.datasets.parameter.ParameterDto;
import com.grebennikovas.viewpoint.datasets.parameter.Parameter;
import com.grebennikovas.viewpoint.datasets.parameter.ParameterMapper;
import com.grebennikovas.viewpoint.datasets.parameter.ParameterRepository;
import com.grebennikovas.viewpoint.datasets.results.Result;
import com.grebennikovas.viewpoint.sources.Source;
import com.grebennikovas.viewpoint.sources.SourceService;
import com.grebennikovas.viewpoint.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DatasetService {
    @Autowired
    DatasetRepository datasetRepository;
    @Autowired
    ColumnRepository columnRepository;
    @Autowired
    ParameterRepository parameterRepository;
    @Autowired
    SourceService sourceService;
    @Autowired
    DatasetMapper datasetMapper;
    @Autowired
    ColumnMapper columnMapper;
    @Autowired
    ParameterMapper parameterMapper;

    public List<DatasetDto> findAll() {
        List<DatasetDto> dsDTO = new ArrayList<>();
        List<Dataset> datasets = datasetRepository.findAll();
        datasets.forEach(d -> dsDTO.add(datasetMapper.toShortDto(d)));
        return dsDTO;
    }

    public DatasetDto findById(Long id){
        Dataset ds = datasetRepository.findById(id).get();
        return datasetMapper.toDto(ds);
    }

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

    // Сохранение списка колонок в БД и удаление убранных
    public List<Column> upsertColumns(List<Column> newColumns, Dataset ds) {

        // Поиск старых параметров
        List<Column> oldColumns = columnRepository.findAllByDataset_id(ds.getId());
        List<Column> addColumns = new ArrayList<>();

        for (Column newColumn: newColumns) {
            // Поиск имени в существующих параметрах
            for (Column oldColumn : oldColumns) {
                if (oldColumn.getName().equals(newColumn.getName())) {
                    // Если параметр с таким именем существует
                    newColumn.setId(oldColumn.getId());
                    oldColumns.remove(oldColumn);
                    break;
                }
            }
            addColumns.add(newColumn);
        }
        // Удалить параметры, которых нет в новом списке
        columnRepository.deleteAll(oldColumns);
        return columnRepository.saveAll(addColumns);
    }

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
