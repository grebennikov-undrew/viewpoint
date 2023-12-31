package com.grebennikovas.viewpoint.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SqlUtilsTest {

    @Test
    public void testConvertToStringSingleElement() {
        // Arrange
        List<Alias> list = Arrays.asList(new Alias("Element"));

        // Act
        String result = SqlUtils.getColumns(list);

        // Assert
        assertEquals("\"Element\"", result);
    }

    @Test
    public void testConvertToStringMultipleElements() {
        // Arrange
        List<Alias> list = Arrays.asList(new Alias("Element1"), new Alias("Element2"), new Alias("Element3"));

        // Act
        String result = SqlUtils.getColumns(list);

        // Assert
        assertEquals("\"Element1\", \"Element2\", \"Element3\"", result);
    }


    @Test
    void setParameters() {
        // Arrange
        String sqlQuery = "SELECT * FROM users WHERE firstname = {:p_name} AND id = {:p_id} OR empty_param = {:p_null}";
        Map<String, String> paramValues = new HashMap<>();
        paramValues.put("p_name", "'Bob Dylan'");
        paramValues.put("p_id", "123");

        // Act
        String result = SqlUtils.setParameters(sqlQuery, paramValues);

        // Assert
        assertEquals("SELECT * FROM users WHERE firstname = 'Bob Dylan' AND id = 123 OR empty_param = {:p_null}", result);
    }

    @Test
    void buildAggregationQueryManyValues() {
        Alias col1 = new Alias("col_1","label1", AggFunction.AVG);
        Alias col2 = new Alias("col_2", "label2", AggFunction.MAX);
        List<Alias> aliases = Arrays.asList(col1, col2);

        String result = SqlUtils.getColumnsWithLabels(aliases);

        assertEquals("AVG(\"col_1\") as \"label1\", MAX(\"col_2\") as \"label2\"", result);
    }

    @Test
    void buildAggregationQueryOneValue() {
        Alias col1 = new Alias("col_1","label", AggFunction.AVG);
        List<Alias> aliases = Arrays.asList(col1);
        AggFunction aggregateFunction = AggFunction.AVG;

        String result = SqlUtils.getColumnsWithLabels(aliases);

        assertEquals("AVG(\"col_1\") as \"label\"", result);
    }
}