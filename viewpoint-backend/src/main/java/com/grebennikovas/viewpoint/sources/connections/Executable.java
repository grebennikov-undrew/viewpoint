package com.grebennikovas.viewpoint.sources.connections;

import com.grebennikovas.viewpoint.datasets.results.Result;
import com.grebennikovas.viewpoint.datasets.results.Row;
import com.grebennikovas.viewpoint.sources.Source;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public interface Executable {
    void setSource(Source source);
    Source getSource();
    boolean validate();
    Result execute(String query, Map<String,String> params);
    ResultSet getTables();
    String getUrl();
}
