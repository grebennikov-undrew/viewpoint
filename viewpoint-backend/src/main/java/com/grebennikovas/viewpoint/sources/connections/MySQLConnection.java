package com.grebennikovas.viewpoint.sources.connections;

import com.grebennikovas.viewpoint.datasets.results.Result;
import com.grebennikovas.viewpoint.datasets.results.Row;
import com.grebennikovas.viewpoint.sources.Source;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public class MySQLConnection implements Executable{
    private Source source;

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }
    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public Result execute(String query, Map<String,String> params) {
        return null;
    }

    @Override
    public ResultSet getTables() {
        return null;
    }

    @Override
    public String getUrl() {
        return null;
    }
}
