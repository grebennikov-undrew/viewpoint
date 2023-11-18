package com.grebennikovas.viewpoint.sources;

import com.grebennikovas.viewpoint.datasets.parameter.Parameter;
import com.grebennikovas.viewpoint.datasets.results.Result;
import com.grebennikovas.viewpoint.sources.connections.ConnectionFactory;
import com.grebennikovas.viewpoint.sources.connections.DbConnection;
import com.grebennikovas.viewpoint.utils.SqlBuilder;
import com.grebennikovas.viewpoint.utils.SqlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class SourceService {

    @Autowired
    SourceRepository sourceRepository;

    @Autowired
    SourceMapper sourceMapper;

    @Autowired
    private ConnectionFactory connectionFactory;

    public List<SourceDto> findAll(){
        List<Source> sources = sourceRepository.findAll();
        List<SourceDto> mappedSources = sources.stream().map(sourceMapper::toShortDto).toList();
        return mappedSources;
    }

    public SourceDto findById(Long sourceId) {
        Source foundSource =  sourceRepository.findById(sourceId).get();
        SourceDto mappedSource = sourceMapper.toDto(foundSource);
        return mappedSource;
    }

    public SourceDto validateAndSave(SourceDto newSource) throws SQLException {
        if (validate(newSource)) {
            Source source = sourceMapper.toEntity(newSource);
            Source savedSource = sourceRepository.save(source);
            return sourceMapper.toDto(savedSource);
        }
        return newSource;
    }

    public boolean validate(SourceDto newSource) throws SQLException {
        Source source = sourceMapper.toEntity(newSource);
        DbConnection connection = connectionFactory.getConnection(source);
        String url = connection.getUrl();
        return SqlUtils.validateConnection(url);
    }

    // Выполнение запроса с параметрами
    public Result execute(Long sourceId, String query, List<Parameter> parameters, Map<String,String> paramValues) throws SQLException {
        SqlBuilder sqlBuilder = new SqlBuilder();
        String preparedQuery = sqlBuilder
                .select()
                .fromSubQuery(query)
                .parameters(parameters,paramValues)
                .build();
        return execute(sourceId,preparedQuery);
    }

    // Выполнение запроса без параметров и определение типов столбцов
    public Result execute(Long sourceId, String query) throws SQLException {
        Source source = sourceRepository.findById(sourceId).get();
        DbConnection connection = connectionFactory.getConnection(source);
        Result result = SqlUtils.execute(connection.getUrl(),query);
        Map<String,String> javaColTypes = connection.getJavaColTypes(result.getColtypes());
        result.setColtypes(javaColTypes);
        return result;
    }

}
