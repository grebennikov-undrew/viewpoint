package com.grebennikovas.viewpoint.sources;

import com.grebennikovas.viewpoint.datasets.results.Result;
import com.grebennikovas.viewpoint.sources.connections.ConnectionFactory;
import com.grebennikovas.viewpoint.sources.connections.DbConnection;
import com.grebennikovas.viewpoint.utils.SqlBuilder;
import com.grebennikovas.viewpoint.utils.SqlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * Получить список источников данных
     * @return список источников в формте коротких DTO
     * */
    public List<SourceDto> findAll(){
        List<Source> sources = sourceRepository.findAll();
        List<SourceDto> mappedSources = sources.stream().map(sourceMapper::toShortDto).toList();
        return mappedSources;
    }

    /**
     * Получить данные по источнику данных по id
     * @param sourceId id источника
     * @return информация о подключении
     * */
    public SourceDto findById(Long sourceId) {
        Source foundSource =  sourceRepository.findById(sourceId).get();
        SourceDto mappedSource = sourceMapper.toDto(foundSource);
        return mappedSource;
    }

    /**
     * Проверить и сохранить новое подключение
     * @param newSource новое подключение
     * @return информация о сохраненном подключении
     * */
    public SourceDto validateAndSave(SourceDto newSource) throws SQLException {
        if (validate(newSource)) {
            Source source = sourceMapper.toEntity(newSource);
            Source savedSource = sourceRepository.save(source);
            return sourceMapper.toDto(savedSource);
        }
        return newSource;
    }

    /**
     * Проверить подключение
     * @param newSource настройки подключения для проверки
     * */
    public boolean validate(SourceDto newSource) throws SQLException {
        Source source = sourceMapper.toEntity(newSource);
        DbConnection connection = connectionFactory.getConnection(source);
        String url = connection.getUrl();
        return SqlUtils.validateConnection(url);

    }

    /**
     * Удалить источник данных по id
     * @param sourceId id источника
     * */
    public void deleteById(Long sourceId) throws SQLException {
        sourceRepository.deleteById(sourceId);
    }

    /**
     * Выполнение не сохраненного запроса
     * @param sourceId id источника
     * @param query SQL запрос
     * @return результат запроса
     * */
    public Result execute(Long sourceId, String query) throws SQLException {
        Source source = sourceRepository.findById(sourceId).get();
        DbConnection connection = connectionFactory.getConnection(source);
        Result result = SqlUtils.execute(connection.getUrl(),query);
        Map<String,String> javaColTypes = connection.getJavaColTypes(result.getColtypes());
        result.setColtypes(javaColTypes);
        return result;
    }

}
