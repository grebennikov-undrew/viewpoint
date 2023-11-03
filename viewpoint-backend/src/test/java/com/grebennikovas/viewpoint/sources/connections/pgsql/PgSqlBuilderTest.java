package com.grebennikovas.viewpoint.sources.connections.pgsql;

import com.grebennikovas.viewpoint.datasets.parameter.Parameter;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PgSqlBuilderTest {
    @Test
    public void testBuildQuery() {
        // Arrange
        List<String> columns = Arrays.asList("col1", "col2");
        String tableName = "example_table";
        String conditions = "col1 > 10";
        List<String> groupByColumns = Arrays.asList("col1");
        List<String> orderByColumns = Arrays.asList("col2", "col1");
        int limitCount = 5;

        PgSqlBuilder builder = new PgSqlBuilder()
                .select(columns)
                .from(tableName)
                .where(conditions)
                .groupBy(groupByColumns)
                .orderBy(orderByColumns, true)
                .limit(limitCount);

        // Act
        String query = builder.build();

        // Assert
        String expectedQuery = "SELECT col1, col2\n" +
                "FROM example_table\n" +
                "WHERE col1 > 10\n" +
                "GROUP BY col1\n" +
                "ORDER BY col2, col1 DESC\n" +
                "LIMIT 5\n";

        assertEquals(expectedQuery, query);
    }

    @Test
    public void testBuildQueryWithParameters() {
        // Arrange
        List<String> columns = Arrays.asList("col1", "col2");
        String tableName = "example_table";
        String conditions = "col1 > {:param1}";
        List<String> groupByColumns = Arrays.asList("col1");
        List<String> orderByColumns = Arrays.asList("col2", "col1");
        int limitCount = 5;

        List<Parameter> paramInfo= new ArrayList<>();
        Parameter parameter1 = new Parameter();
        parameter1.setName("param1");
        parameter1.setType("Double");
        paramInfo.add(parameter1);

        Map<String, Object> paramValues = new HashMap<>();
        paramValues.put("param1", 10);

        PgSqlBuilder builder = new PgSqlBuilder()
                .select(columns)
                .from(tableName)
                .where(conditions)
                .groupBy(groupByColumns)
                .orderBy(orderByColumns, true)
                .limit(limitCount)
                .parameters(paramInfo, paramValues);

        // Act
        String query = builder.build();

        // Assert
        String expectedQuery = "SELECT col1, col2\n" +
                "FROM example_table\n" +
                "WHERE col1 > 10 \n" +
                "GROUP BY col1\n" +
                "ORDER BY col2, col1 DESC\n" +
                "LIMIT 5\n";

        assertEquals(expectedQuery, query);
    }

}