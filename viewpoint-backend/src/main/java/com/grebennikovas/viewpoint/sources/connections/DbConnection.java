package com.grebennikovas.viewpoint.sources.connections;

import com.grebennikovas.viewpoint.sources.Source;

import java.sql.ResultSet;
import java.util.Map;

public interface DbConnection {
    Source getSource();
    void setSource(Source source);
    String getType();
    ResultSet getTables();
    String getUrl();
    Map<String,String> getJavaColTypes(Map<String,String> queryColTypes);
    String getJavaColType(String queryColumnType);
}
