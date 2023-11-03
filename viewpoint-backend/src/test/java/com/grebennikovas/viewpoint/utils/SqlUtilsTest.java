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
        List<String> list = Arrays.asList("Element");

        // Act
        String result = SqlUtils.convertToString(list);

        // Assert
        assertEquals("Element", result);
    }

    @Test
    public void testConvertToStringMultipleElements() {
        // Arrange
        List<String> list = Arrays.asList("Element1", "Element2", "Element3");

        // Act
        String result = SqlUtils.convertToString(list);

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
}