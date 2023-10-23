package com.grebennikovas.viewpoint.sources.connections;

import com.grebennikovas.viewpoint.datasets.Column;
import com.grebennikovas.viewpoint.datasets.results.Entry;
import com.grebennikovas.viewpoint.datasets.results.Result;
import com.grebennikovas.viewpoint.datasets.results.Row;
import com.grebennikovas.viewpoint.sources.Source;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
        String url = getUrl(source);
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
    public List<Row> execute(String query, Map<String,String> params) {
        String url = getUrl(source);
        try (Connection connection = DriverManager.getConnection(url)) {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            Map<String,String> coltypes = getColtypes(rs);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = rs.getMetaData().getColumnCount();
//            Result result = new Result();
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
                    if (javaColType.equals("int")) {
                        Entry<Integer> entry = new Entry();
                        entry.setValue(rs.getInt(i));
                        entries.put(columnName,entry);
                    }
                    if (javaColType.equals("Timestamp")) {
                        Entry<Timestamp> entry = new Entry();
                        entry.setValue(rs.getTimestamp(i));
                        entries.put(columnName, entry);
                    }
                }
                row.setEntries(entries);
                rows.add(row);
            }
            return rows;
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
    public String getUrl(Source source) {
        String driver = source.getType().getCredit();
        String netloc = source.getNetloc();
        String port = source.getPort();
        String dbname = source.getDbname();
        String params = source.getParams();
        String username = source.getUsername();
        String password = source.getPassword();
        String url = String.format("jdbc:%s://%s:%s/%s?user=%s&password=%s",driver,netloc,port,dbname,username,password);
        return url;
    }

    public Map<String,String> getColtypes(ResultSet rs) throws SQLException {
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

    public String getJavaColType(String queryColumnType) {
        String javaColumnType = "";
        String queryColumnTypeLower = queryColumnType.toLowerCase();
        switch (queryColumnTypeLower) {
            case ("int"):
                javaColumnType = "int";
                break;
            case ("bigserial"):
                javaColumnType = "int";
                break;
            case ("timestamp"):
                javaColumnType = "Timestamp";
                break;
            case ("varchar"):
                javaColumnType = "String";
                break;
        }
        return javaColumnType;
    }
}
