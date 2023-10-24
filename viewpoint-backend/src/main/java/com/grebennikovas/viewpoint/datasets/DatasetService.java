package com.grebennikovas.viewpoint.datasets;

import com.grebennikovas.viewpoint.datasets.results.Result;
import com.grebennikovas.viewpoint.datasets.results.Row;
import com.grebennikovas.viewpoint.sources.DatabaseType;
import com.grebennikovas.viewpoint.sources.Source;
import com.grebennikovas.viewpoint.sources.connections.ConnectionFactory;
import com.grebennikovas.viewpoint.sources.connections.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DatasetService {
    @Autowired
    DatasetRepository datasetRepository;

    public List<Dataset> findAll() {
        return datasetRepository.findAll();
    }
    public Dataset save(Dataset dataset) {
        return datasetRepository.save(dataset);
    }
    // Подставить параметры в запрос и выполнить
    public Result execute(Long id, Map<String,String> params) {
        Dataset ds = datasetRepository.getOne(id);
        Source src = ds.getSource();
        Executable dbInstance = ConnectionFactory.connect(src);
        String query = prepareQuery(ds.getSqlQuery(), params);
        Result result = dbInstance.execute(query);
        return result;
    }
    // Подстановка параметров в запрос
    public String prepareQuery(String query, Map<String,String> params) {
        Pattern pattern = Pattern.compile("(\\{:.*})");
        Matcher matcher = pattern.matcher(query);

        StringBuffer result = new StringBuffer();
        // Поочередная замена параметров запроса
        while (matcher.find()) {
            String parameter = matcher.group(1);
            String paramName = parameter.substring(2,parameter.length()-1).trim();
            // Есть ли такой параметр в params?
            if (params.containsKey(paramName)) {
                String paramValue = " " + params.get(paramName) + " ";
                matcher.appendReplacement(result, Matcher.quoteReplacement(paramValue));
            } else {
                // не заменять
                matcher.appendReplacement(result, matcher.group());
            }
        }
        return result.toString();
    }
}
