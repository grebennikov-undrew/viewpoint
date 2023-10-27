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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @Autowired
    UserRepository userRepository;

    public List<DatasetDTO> findAll() {
        List<DatasetDTO> dsDTO = new ArrayList<>();
        datasetRepository.findAll().forEach(d -> dsDTO.add(new DatasetDTO(d)));
        return dsDTO;
    }

    public DatasetDTO getOne(Long id){
        List<Parameter> parameters = parameterRepository.findAllByDataset_id(id);
        List<Column> columns = columnRepository.findAllByDataset_id(id);
        DatasetDTO dsDTO = new DatasetDTO(datasetRepository.findById(id).get(), columns, parameters);
        return dsDTO;
    }

    public Dataset getOne(DatasetDTO dsDTO){
        return datasetRepository.findById(dsDTO.getId()).get();
    }

    public DatasetDTO save(DatasetDTO dsDTO) {
        Dataset ds = new Dataset();
        Source s = sourceRepository.findById(dsDTO.getSource().getId()).get();
        User u = userRepository.findById(dsDTO.getUser().getId()).get();
        List<Column> columns = new ArrayList<>();
        List<Parameter> parameters = new ArrayList<>();
        dsDTO.getColumns().forEach(c -> columns.add(new Column(c.getId(),ds,c.getName(),c.getType())));
        dsDTO.getParameters().forEach(p -> parameters.add(new Parameter(p.getId(),ds,p.getName(),p.getType(),p.getSqlQuery())));
        ds.setId(dsDTO.getId());
        ds.setName(dsDTO.getName());
        ds.setSqlQuery(dsDTO.getSqlQuery());
        ds.setSource(s);
        ds.setUser(u);
        DatasetDTO new_dsDTO = new DatasetDTO(datasetRepository.save(ds), columns, parameters);
        setColumns(columns);
        setParameters(parameters);
        return new_dsDTO;
    }
    // Подставить параметры в запрос и выполнить
    public Result execute(Long id, Map<String,String> params) {
        Dataset ds = datasetRepository.getOne(id);
        Source src = ds.getSource();
        Executable dbInstance = ConnectionFactory.connect(src);
        String query = prepareQuery(ds, params);
        Result result = dbInstance.execute(query);
        return result;
    }
    // Выполнение еще не сохраненного запроса
    public Result execute(String query, Long sourceId, Map<String,String> params) {
//        Dataset ds = datasetRepository.getOne(id);
        Dataset ds = new Dataset();
        Source src = sourceRepository.findById(sourceId).get();
        Executable dbInstance = ConnectionFactory.connect(src);
        String preparedQuery = prepareQuery(ds, params);
        Result result = dbInstance.execute(preparedQuery);
        return result;
    }

    // Подстановка параметров в запрос
    public String prepareQuery(Dataset ds, Map<String,String> params) {
        String query = ds.getSqlQuery();

        Pattern pattern = Pattern.compile("(\\{:.*})");
        Matcher matcher = pattern.matcher(query);

        StringBuffer result = new StringBuffer();
        // Поочередная замена параметров запроса
        while (matcher.find()) {
            String parameter = matcher.group(1);
            String paramName = parameter.substring(2,parameter.length()-1).trim();
            // Есть ли такой параметр в params?
            if (params.containsKey(paramName)) {
                // Поиск прараметра в списке параметров датасета
                Parameter paramInfo = findParameter(ds,paramName);
                String paramValue = params.get(paramName);
                String preparedParamValue = prepareParamValue(paramInfo, paramValue);
                matcher.appendReplacement(result, Matcher.quoteReplacement(preparedParamValue));
            } else {
                // не заменять
                matcher.appendReplacement(result, matcher.group());
            }
        }
        return result.toString();
    }
    public Parameter findParameter(Dataset ds, String name){
        List<Parameter> paramsInfo = parameterRepository.findAllByDataset_id(ds.getId());
        for (Parameter p : paramsInfo) {
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

    public List<ColumnDTO> setColumns(List<Column> columns) {
        List<ColumnDTO> new_columnsDTO = new ArrayList<>();
        columns.forEach(c -> {
            Column column = columnRepository.save(c);
            new_columnsDTO.add(new ColumnDTO(column));
        });
        return new_columnsDTO;
    }

    public List<ParameterDTO> setParameters(List<Parameter> params) {
        List<ParameterDTO> new_paramsDTO = new ArrayList<>();
        params.forEach(p -> {
            Parameter parameter = parameterRepository.save(p);
            new_paramsDTO.add(new ParameterDTO(parameter));
        });
        return new_paramsDTO;
    }

}
