package com.grebennikovas.viewpoint.sources.connections;

import com.grebennikovas.viewpoint.datasets.results.Entry;
import com.grebennikovas.viewpoint.datasets.results.Result;
import com.grebennikovas.viewpoint.datasets.results.Row;
import com.grebennikovas.viewpoint.sources.Source;

import java.sql.*;
import java.util.*;

public class PostgreSQLConnection implements Executable {
    private Source source;

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    @Override
    public boolean validate() {
        String url = getUrl();
        try {
            Connection connection = DriverManager.getConnection(url);
            boolean valid = connection.isValid(10);
            connection.close();
            return valid;
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка ошибки подключения
            return false;
        }
    }

    @Override
    public Result execute(String query) {
        String url = getUrl();
        try (Connection connection = DriverManager.getConnection(url)) {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            Map<String,String> coltypes = getColtypes(rs);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = rs.getMetaData().getColumnCount();
            Result result = new Result();
            List<Row> rows = new LinkedList();
            while (rs.next()) {
                Row row = new Row();
                Map<String,Entry> entries = new HashMap();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    String javaColType = coltypes.get(columnName);
                    if (javaColType.equals("String")) {
                        Entry<String> entry = new Entry();
                        entry.setValue(rs.getString(i));
                        entries.put(columnName,entry);
                    }
                    if (javaColType.equals("Double")) {
                        Entry<Double> entry = new Entry();
                        entry.setValue(rs.getDouble(i));
                        entries.put(columnName,entry);
                    }
                    if (javaColType.equals("Timestamp")) {
                        Entry<Timestamp> entry = new Entry();
                        entry.setValue(rs.getTimestamp(i));
                        entries.put(columnName, entry);
                    }
                    if (javaColType.equals("Boolean")) {
                        Entry<Boolean> entry = new Entry();
                        entry.setValue(rs.getBoolean(i));
                        entries.put(columnName, entry);
                    }
                    if (javaColType.equals("Object")) {
                        Entry<Object> entry = new Entry();
                        entry.setValue(rs.getObject(i));
                        entries.put(columnName, entry);
                    }
                }
                row.setEntries(entries);
                rows.add(row);
            }
            result.setRows(rows);
            result.setColtypes(coltypes);
            return result;
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка ошибки подключения
            return null;
        }
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

    public static Map<String,String> getColtypes(ResultSet rs) throws SQLException {
        Map<String,String> coltypes = new HashMap();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            String queryColumnType = metaData.getColumnTypeName(i);
            String javaColumnType = getJavaColType(queryColumnType);
            coltypes.put(columnName,javaColumnType);
        }
        return coltypes;
    }

    public static String getJavaColType(String queryColumnType) {
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
