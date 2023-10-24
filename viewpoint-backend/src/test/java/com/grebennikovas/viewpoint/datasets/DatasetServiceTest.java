package com.grebennikovas.viewpoint.datasets;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
class DatasetServiceTest {
    @Test
    void prepareQuery() {
        DatasetService datasetService = new DatasetService();
        String query = "SELECT * FROM users WHERE name ={:p_name}";
        Map<String,String> params = new HashMap<>();
        params.put("p_name","'Andrey'");
        String actual = datasetService.prepareQuery(query,params);
        String expected = "SELECT * FROM users WHERE name = 'Andrey' ";
        assertEquals(actual,expected);
    }
}