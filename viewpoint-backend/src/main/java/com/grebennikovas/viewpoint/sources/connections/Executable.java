package com.grebennikovas.viewpoint.sources.connections;

import com.grebennikovas.viewpoint.datasets.results.Result;
import com.grebennikovas.viewpoint.datasets.results.Row;
import com.grebennikovas.viewpoint.sources.Source;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public interface Executable {
    void setSource(SimpleDriverDataSource source);
    SimpleDriverDataSource getSource();
    boolean validate();
    List<Map<String, Object>> execute(String query, Map<String,String> params);
    ResultSet getTables();
    String getUrl();
}
