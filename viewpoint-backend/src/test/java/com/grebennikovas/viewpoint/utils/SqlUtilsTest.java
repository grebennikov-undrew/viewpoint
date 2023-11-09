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
        List<Column> list = Arrays.asList(new Column("Element"));

        // Act
        String result = SqlUtils.getColumns(list);

        // Assert
        assertEquals("Element", result);
    }

    @Test
    public void testConvertToStringMultipleElements() {
        // Arrange
        List<Column> list = Arrays.asList(new Column("Element1"), new Column("Element2"), new Column("Element3"));

        // Act
        String result = SqlUtils.getColumns(list);

        // Assert
        assertEquals("Element1, Element2, Element3", result);
    }

    @Test
    public void testPrepareParamValueString() {
        // Arrange
        String parameterType = "String";
        String paramValue = "example string";

        // Act
        String result = SqlUtils.prepareParamValue(parameterType, paramValue);

        // Assert
        assertEquals("'example string'", result);
    }

    @Test
    public void testPrepareParamValueTimestamp() {
        // Arrange
        String parameterType = "Timestamp";
        String paramValue = "2023-10-31 12:34:56";

        // Act
        String result = SqlUtils.prepareParamValue(parameterType, paramValue);

        // Assert
        assertEquals("'2023-10-31 12:34:56'", result);
    }

    @Test
    public void testPrepareParamValueOther() {
        // Arrange
        String parameterType = "OtherType";
        String paramValue = "123";

        // Act
        String result = SqlUtils.prepareParamValue(parameterType, paramValue);

        // Assert
        assertEquals("123", result);
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
        Column col1 = new Column("col_1","label1", AggFunction.AVG);
        Column col2 = new Column("col_2", "label2", AggFunction.MAX);
        List<Column> columns = Arrays.asList(col1, col2);

        String result = SqlUtils.getColumnsWithLabels(columns);

        assertEquals("AVG(col_1) as \"label1\", MAX(col_2) as \"label2\"", result);
    }

    @Test
    void buildAggregationQueryOneValue() {
        Column col1 = new Column("col_1","label", AggFunction.AVG);
        List<Column> columns = Arrays.asList(col1);
        AggFunction aggregateFunction = AggFunction.AVG;

        String result = SqlUtils.getColumnsWithLabels(columns);

        assertEquals("AVG(col_1) as \"label\"", result);
    }
}