package com.grebennikovas.viewpoint.datasets;

import com.grebennikovas.viewpoint.datasets.column.Column;
import com.grebennikovas.viewpoint.datasets.column.ColumnRepository;
import com.grebennikovas.viewpoint.datasets.dto.ColumnDTO;
import com.grebennikovas.viewpoint.datasets.dto.DatasetDTO;
import com.grebennikovas.viewpoint.datasets.dto.ParameterDTO;
import com.grebennikovas.viewpoint.datasets.parameter.Parameter;
import com.grebennikovas.viewpoint.datasets.parameter.ParameterRepository;
import com.grebennikovas.viewpoint.datasets.results.Result;
import com.grebennikovas.viewpoint.sources.Source;
import com.grebennikovas.viewpoint.sources.SourceRepository;
import com.grebennikovas.viewpoint.sources.connections.ConnectionFactory;
import com.grebennikovas.viewpoint.sources.connections.Executable;
import com.grebennikovas.viewpoint.users.User;
import com.grebennikovas.viewpoint.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class DatasetService {
    @Autowired
    DatasetRepository datasetRepository;
    @Autowired
    ColumnRepository columnRepository;
    @Autowired
    ParameterRepository parameterRepository;
    @Autowired
    SourceRepository sourceRepository;

    public List<DatasetDTO> findAll() {
        List<DatasetDTO> dsDTO = new ArrayList<>();
        List<Dataset> datasets = datasetRepository.findAll();
        datasets.forEach(d -> dsDTO.add(mapDataset(d)));
        return dsDTO;
    }

    public DatasetDTO getOne(Long id){
        List<Parameter> parameters = parameterRepository.findAllByDataset_id(id);
        List<Column> columns = columnRepository.findAllByDataset_id(id);
        Dataset ds = datasetRepository.findById(id).get();
        DatasetDTO dsDTO = mapDataset(ds,columns,parameters);
        return dsDTO;
    }

    public DatasetDTO save(DatasetDTO dsDTO) {
        // Сохранение объекта Dataset
        Dataset ds = mapDatasetDTO(dsDTO);
        Dataset savedDataset = save(ds);
        // Сохранение всех колонок датасета
        List<Column> columns = mapColumnsDTO(dsDTO.getColumns(), savedDataset);
        List<Column> savedColumns = upsertColumns(columns, savedDataset);
        // Сохранение всех параметров датасета
        List<Parameter> params = mapParameterDTO(dsDTO.getParameters(), savedDataset);
        List<Parameter> savedParams = upsertParameters(params, ds);
        // Преобразование в DTO
        DatasetDTO savedDatasetDTO = mapDataset(savedDataset, savedColumns, savedParams);
        return savedDatasetDTO;
    }

    // Выполнение еще не сохраненного запроса
    public Result execute(String query, Long sourceId, List<Parameter> parameters, Map<String,String> paramValues) {
        Source src = sourceRepository.findById(sourceId).get();
        Executable dbInstance = ConnectionFactory.connect(src);
        String preparedQuery = prepareQuery(query, parameters, paramValues);
        return dbInstance.execute(preparedQuery);
    }

    // Подстановка параметров в запрос
    public String prepareQuery(String sqlQuery, List<Parameter> parameters, Map<String,String> values) {
        String result = sqlQuery;
        Pattern pattern = Pattern.compile("(\\{:[\\w\\s]+?})", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(result);
        // Поочередная замена параметров запроса
        while (matcher.find()) {
            String parameter = matcher.group(1);
            String paramName = parameter.substring(2,parameter.length()-1).trim();
            // Есть ли такой параметр в params?
            if (values.containsKey(paramName)) {
                // Поиск прараметра в списке параметров датасета
                Parameter paramInfo = findParameter(parameters,paramName);
                String paramValue = values.get(paramName);
                String preparedParamValue = prepareParamValue(paramInfo, paramValue);
                result = matcher.replaceFirst(Matcher.quoteReplacement(preparedParamValue));
                matcher = pattern.matcher(result);
            } else {
                result = matcher.replaceFirst(Matcher.quoteReplacement(" NULL "));
                matcher = pattern.matcher(result);
            }
        }
        return result;
    }
    public Parameter findParameter(List<Parameter> parameters, String name){
        for (Parameter p : parameters) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    public String prepareParamValue(Parameter paramInfo, String paramValue){
        String preparedParamValue;
        if (paramInfo.getType().equals("String") || paramInfo.getType().equals("Timestamp")) {
            preparedParamValue = String.format(" '%s' ",paramValue.trim());
        } else {
            preparedParamValue = String.format(" %s ",paramValue.trim());
        }
        return preparedParamValue;
    }

    // Маппинг Dataset DTO->DAO
    public Dataset mapDatasetDTO(DatasetDTO dsDTO) {
        Dataset ds = new Dataset();
        ds.setId(dsDTO.getId());
        ds.setName(dsDTO.getName());
        ds.setSqlQuery(dsDTO.getSqlQuery());
        ds.setSource(new Source(dsDTO.getSource().getId()));
        ds.setUser(new User(dsDTO.getSource().getId()));
        return ds;
    }

    // Маппинг Dataset DAO->DTO
    public DatasetDTO mapDataset(Dataset ds, List<Column> columns, List<Parameter> params) {
        return new DatasetDTO(ds, columns, params);
    }
    public DatasetDTO mapDataset(Dataset ds) {
        return new DatasetDTO(ds);
    }

    // Сохранение датасета в таблицу
    public Dataset save(Dataset ds) {
        return datasetRepository.save(ds);
    }

    // Маппинг столбцов датасета DTO->DAO
    public List<Column> mapColumnsDTO(List<ColumnDTO> columnsDTO, Dataset dataset) {
        List<Column> columns = new ArrayList<>();
        columnsDTO.forEach(c -> columns.add(new Column(c.getId(),dataset,c.getName(),c.getType())));
        return columns;
    }

    // Маппинг столбцов датасета DAO->DTO
    public List<ColumnDTO> mapColumns(List<Column> columns) {
        List<ColumnDTO> columnsDTO = new ArrayList<>();
        columns.forEach(c -> columnsDTO.add(new ColumnDTO(c)));
        return columnsDTO;
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

    // Маппинг параметров датасета DTO->DAO
    public List<Parameter> mapParameterDTO(List<ParameterDTO> paramsDTO, Dataset dataset) {
        List<Parameter> params = new ArrayList<>();
        paramsDTO.forEach(p -> params.add(new Parameter(p.getId(),dataset,p.getName(),p.getType(),p.getSqlQuery())));
        return params;
    }

    // Маппинг параметров датасета DAO->DTO
    public List<ParameterDTO> mapParameters(List<Parameter> params) {
        List<ParameterDTO> columnsDTO = new ArrayList<>();
        params.forEach(p -> columnsDTO.add(new ParameterDTO(p)));
        return columnsDTO;
    }

    // Сохранение нового списка параметров в БД и удаление не используемых
    public List<Parameter> upsertParameters(List<Parameter> newParams, Dataset ds) {
        // Поиск старых параметров
        List<Parameter> oldParameters = parameterRepository.findAllByDataset_id(ds.getId());
        List<Parameter> addParameters = new ArrayList<>();

        for (Parameter newParam: newParams) {
            // Поиск имени в существующих параметрах
            for (Parameter oldParam : oldParameters) {
                if (oldParam.getName().equals(newParam.getName())) {
                    // Если параметр с таким именем существует
                    newParam.setId(oldParam.getId());
                    oldParameters.remove(oldParam);
                    break;
                }
            }
            addParameters.add(newParam);
        }
        // Удалить параметры, которых нет в новом списке
        parameterRepository.deleteAll(oldParameters);
        return parameterRepository.saveAll(addParameters);
    }
}
