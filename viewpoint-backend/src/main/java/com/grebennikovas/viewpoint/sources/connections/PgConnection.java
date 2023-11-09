package com.grebennikovas.viewpoint.sources.connections;

import com.grebennikovas.viewpoint.sources.DatabaseType;
import com.grebennikovas.viewpoint.sources.Source;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.*;

@Component
public class PgConnection implements DbConnection {

    private Source source;

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    @Override
    public String getType() {
        return DatabaseType.POSTGRESQL.getCredit();
    }

    @Override
    public ResultSet getTables() {
        return null;
    }

    @Override
    public String getUrl() {
        String driver = source.getType().getCredit();
        String netloc = source.getNetloc();
        String port = source.getPort();
        String dbname = source.getDbname();
        String params = source.getParams();
        if (params.length() > 0) {
            params = "&" + params;
        }
        String username = source.getUsername();
        String password = source.getPassword();
        String url = String.format("jdbc:%s://%s:%s/%s?user=%s&password=%s%s",driver,netloc,port,dbname,username,password,params);
        return url;
    }

    @Override
    public Map<String,String> getJavaColTypes(Map<String,String> queryColTypes){
        Map<String,String> javaColTypes = new HashMap<>();
        queryColTypes.forEach((column, type) -> {
            String javaColType = getJavaColType(type);
            javaColTypes.put(column,javaColType);
        });
        return javaColTypes;
    }

    @Override
    public String getJavaColType(String queryColumnType) {
        List<String> DOUBLE_TYPES = Arrays.asList("int", "bigserial", "bigint", "double precision", "float8", "integer", "int4", "money", "numeric",
                "decimal", "real", "float4", "smallint", "int2","smallserial", "serial2", "serial", "serial4", "serial8");
        List<String> TIME_TYPES = Arrays.asList("date", "timestamp", "time", "timetz", "timestamptz");
        List<String> STRING_TYPES = Arrays.asList("varchar", "char", "character", "character varying", "interval", "json", "text", "uuid", "xml");
        List<String> BOOL_TYPES = Arrays.asList("boolean");
        String javaColumnType = "";
        String queryColumnTypeLower = queryColumnType.toLowerCase();
        if (DOUBLE_TYPES.contains(queryColumnTypeLower)) {
            javaColumnType = "Double";
        } else if (TIME_TYPES.contains(queryColumnTypeLower)) {
            javaColumnType = "Timestamp";
        } else if (STRING_TYPES.contains(queryColumnTypeLower)) {
            javaColumnType = "String";
        } else if (BOOL_TYPES.contains(queryColumnTypeLower)) {
            javaColumnType = "Boolean";
        } else {
            javaColumnType = "Object";
        }
        return javaColumnType;
    }
}
