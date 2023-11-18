package com.grebennikovas.viewpoint.utils;

import com.grebennikovas.viewpoint.datasets.column.ColumnDto;
import com.grebennikovas.viewpoint.datasets.results.Entry;
import com.grebennikovas.viewpoint.datasets.results.Result;
import com.grebennikovas.viewpoint.datasets.results.Row;

import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SqlUtils {
    private SqlUtils() {
    }

    // Приводит лист столбцов к строке с лейблами
    public static String getColumnsWithLabels(List<Alias> aliases) {
        StringJoiner joiner = new StringJoiner(", ");
        for (Alias alias : aliases) {
            String columnString;
            if (alias.getAggFunction() != null)
                columnString = alias.getAggFunction().getFunc() + "(\"" + alias.getValue() + "\")";
            else
                columnString = "\"" + alias.getValue() + "\"";
            joiner.add(columnString + " as \"" + alias.getLabel() + "\"");
        }
        return joiner.toString();
    }

    // Приводит лист столбцов к строке без лейблов
    public static String getColumns(List<Alias> aliases) {
        StringJoiner joiner = new StringJoiner(", ");
        for (Alias alias : aliases) {
            String columnString;
            if (alias.getAggFunction() != null)
                columnString = alias.getAggFunction().getFunc() + "(\"" + alias.getValue() + "\")";
            else
                columnString = "\"" + alias.getValue() + "\"";
            joiner.add(columnString);
        }
        return joiner.toString();
    }

    // Приводит значение параметра к строке в SQL запросе
    public static String prepareParamValue(String parameterType, String paramValue) {
        String preparedParamValue;
        if (parameterType.equals("String") || parameterType.equals("Timestamp")) {
            preparedParamValue = String.format("'%s'", paramValue.trim());
        } else {
            preparedParamValue = String.format("%s", paramValue.trim());
        }
        return preparedParamValue;
    }

    // Подставлет значения параметров в запрос
    public static String setParameters(String sqlQuery, Map<String,String> paramValues) {
        Pattern pattern = Pattern.compile("\\s*\\{:(\\w+)}[^\\S\\n]*");
        Matcher matcher = pattern.matcher(sqlQuery);

        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String parameterName = matcher.group(1);
            if (paramValues.containsKey(parameterName)) {
                String parameterValue = " " + paramValues.get(parameterName) + " ";
                matcher.appendReplacement(result, Matcher.quoteReplacement(parameterValue));
            }
        }
        matcher.appendTail(result);
        return result.toString();
    }

    // Проходит по результату вопроса, сохраняя это в объект Result
    public static Result execute(String dbUrl, String query) throws SQLException {
        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            Map<String,String> coltypes = getSqlColtypes(rs);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = rs.getMetaData().getColumnCount();
            Result result = new Result();
            List<Row> rows = new LinkedList();
            while (rs.next()) {
                Row row = new Row();
                Map<String, Entry> entries = new HashMap();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
//                    String javaColType = coltypes.get(columnName);
//                    if (javaColType.equals("String")) {
//                        Entry<String> entry = new Entry();
//                        entry.setValue(rs.getString(i));
//                        entries.put(columnName,entry);
//                    }
//                    if (javaColType.equals("Double")) {
//                        Entry<Double> entry = new Entry();
//                        entry.setValue(rs.getDouble(i));
//                        entries.put(columnName,entry);
//                    }
//                    if (javaColType.equals("Timestamp")) {
//                        Entry<Timestamp> entry = new Entry();
//                        entry.setValue(rs.getTimestamp(i));
//                        entries.put(columnName, entry);
//                    }
//                    if (javaColType.equals("Boolean")) {
//                        Entry<Boolean> entry = new Entry();
//                        entry.setValue(rs.getBoolean(i));
//                        entries.put(columnName, entry);
//                    }
//                    if (javaColType.equals("Object")) {
                        Entry<Object> entry = new Entry(rs.getObject(i));
                        entries.put(columnName, entry);
//                    }
                }
                row.setEntries(entries);
                rows.add(row);
            }
            result.setRows(rows);
            result.setColtypes(coltypes);
            return result;
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка ошибки подключения
            throw e;
        }
    }

    // Возвращает типы данных каждого столбца запроса
    public static Map<String,String> getSqlColtypes(ResultSet rs) throws SQLException {
        Map<String,String> coltypes = new HashMap<>();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            String queryColumnType = metaData.getColumnTypeName(i);
            if (coltypes.containsKey(columnName)) {
                throw new IllegalArgumentException("More than one column with name " + columnName);
            } else {
                coltypes.put(columnName,queryColumnType);
            }

        }
        return coltypes;
    }

    // Проверка подключения к БД
    public static boolean validateConnection (String dbUrl) throws SQLException {
        try (Connection connection = DriverManager.getConnection(dbUrl)){
            boolean valid = connection.isValid(10);
            connection.close();
            return valid;
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка ошибки подключения
            throw e;
        }
    }

    // Преобразовать значения фильтров в условия SQL
    public static List<String> getConditionsFromFilters (List<ColumnDto> columnFilters) {
        List<String> conditions = new ArrayList<>();

        for (ColumnDto filter : columnFilters) {
            String type = filter.getType();

            // Обработка строковых фильтров в формат "column IN ("v1", "v2", "v3")
            if (type.equals("String")) {
                String separatedValues = filter.getFilterValues().stream()
                        .map(value -> "\'" + value.toString() + "\'")
                        .collect(Collectors.joining(", "));

                conditions.add("\"" + filter.getName() + "\"" + " IN (" + separatedValues + ")");
            }

            // Обработка фильтров в формат column BETWEEN min AND max
            if (type.equals("Double")) {
                Object minValue = filter.getFilterValues().get(0);
                Object maxValue = filter.getFilterValues().get(1);
                conditions.add("\"" + filter.getName() + "\"" + " BETWEEN " + minValue + " AND " + maxValue);
            }

            if (type.equals("Timestamp")) {
                Object minValue = filter.getFilterValues().get(0);
                Object maxValue = filter.getFilterValues().get(1);
                conditions.add("\"" + filter.getName() + "\"" + " BETWEEN \'" + minValue + "\' AND \'" + maxValue + "\'");
            }

        }
        return conditions;
    }
}
