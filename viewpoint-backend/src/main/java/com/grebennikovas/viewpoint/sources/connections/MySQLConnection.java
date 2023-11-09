package com.grebennikovas.viewpoint.sources.connections;

import com.grebennikovas.viewpoint.sources.DatabaseType;
import com.grebennikovas.viewpoint.sources.Source;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.Map;

@Component
public class MySQLConnection implements DbConnection {

    @Override
    public Source getSource() {
        return null;
    }

    @Override
    public void setSource(Source source) {

    }

    @Override
    public String getType() {
        return DatabaseType.MYSQL.getCredit();
    }

    @Override
    public ResultSet getTables() {
        return null;
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public Map<String, String> getJavaColTypes(Map<String, String> queryColTypes) {
        return null;
    }

    @Override
    public String getJavaColType(String queryColumnType) {
        return null;
    }


}
